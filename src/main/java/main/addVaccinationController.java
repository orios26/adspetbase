package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.*;
import java.time.LocalDate;

public class addVaccinationController {

    @FXML // fx:id "petSelect"
    private ComboBox<String>petSelect;

    @FXML //fx:id ="vaccineSelect"
    private ComboBox<String>vaccineSelect;

    @FXML // fx:id "vaccStartDate"
    private DatePicker vaccStartDate;

    @FXML // fx:id "vaccEndDate"
    private DatePicker vaccEndDate;

    @FXML //fx:id "addVacc"
    private Button addVacc;


    private int PTID, PID, VID, PVID;
    private String petName;

    public void initialize()throws SQLException{
    String sql = "SELECT PET.PET_ID, PET.PET_NAME FROM PET";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String>pet = FXCollections.observableArrayList();
        while(resultSet.next()){
            String s = resultSet.getInt("PET_ID") + "_"+resultSet.getString("PET_NAME");
            pet.add(s);
        }
        petSelect.setItems(pet);
        vaccineSelect.setDisable(true);
        petSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                vaccineFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void vaccineFilter()throws SQLException{
        String petID = petSelect.getValue();
        String[]p;
        p = petID.split("_");
        petName = p[1];
        PID = Integer.parseInt(p[0]);

        String sql = "select PET_TYPE_ID FROM PET WHERE PET_ID = "+PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            PTID = resultSet.getInt("PET_TYPE_ID");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();

        String sql1 = "SELECT VACCINATION.VACCINE_ID, VACCINATION.VACCINE_NAME FROM VACCINATION WHERE PET_TYPE_ID = "+PTID;
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<String>vaccineName = FXCollections.observableArrayList();
        while(resultSet1.next()){
            String s = resultSet1.getInt("VACCINE_ID")+"_"+resultSet1.getString("VACCINE_NAME");
           vaccineName.add(s);
        }
        vaccineSelect.setItems(vaccineName);
        vaccineSelect.setDisable(false);
        resultSet1.close();
        preparedStatement1.close();
        connection1.close();
    }

    public void generateVaccine()throws SQLException {
        String vaccineID = vaccineSelect.getValue();
        String v[];
        v = vaccineID.split("_");
        VID = Integer.parseInt(v[0]);

        try {
            String vaccine = "INSERT INTO PET_VACCINE (PET_ID, VACCINE_ID, PET_VACCINE_STARTDATE, PET_VACCINE_ENDDATE) VALUES " +
                    "(?,?,?,?)";
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(vaccine, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, PID);
            preparedStatement.setInt(2, VID);
            preparedStatement.setDate(3, Date.valueOf(vaccStartDate.getValue()));
            preparedStatement.setDate(4, Date.valueOf(vaccEndDate.getValue()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                PVID = resultSet.getInt(1);
                System.out.println(PVID);
            }

            String vaccHist = "INSERT INTO PET_VACCINE_HIST(PET_VACCINE_ID, PET_VACCINE_HIST_DATE) VALUES" +
                    " (?,?)";
            Connection connection1 = DbHelper.getInstance().getConnection();
            PreparedStatement preparedStatement1 = connection1.prepareStatement(vaccHist);
            preparedStatement1.setInt(1, PVID);
            preparedStatement1.setDate(2, Date.valueOf(LocalDate.now()));
            preparedStatement1.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase Update");
            alert.setHeaderText("Vaccination Updated");
            alert.setContentText("Vaccination for "+petName+" has been updated");
            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("PetBase ERROR");
            alert.setHeaderText("Error");
            alert.setContentText("Please verify the information entered and try again");
            alert.showAndWait();

        }
    }

}
