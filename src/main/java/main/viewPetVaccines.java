package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class viewPetVaccines {

    @FXML
    TableView<PetVaccine>tblVaccines;

    @FXML
    TableColumn<PetVaccine, String>petName;
    @FXML
    TableColumn<PetVaccine, String>vaccineName;
    @FXML
    TableColumn<PetVaccine, Date>vaccStartDate;
    @FXML
    TableColumn<PetVaccine, Date>vaccEndDate;

    @FXML //fx:id ="petSelect"
    private ComboBox<String>petSelect;


    public void initialize() throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetVaccine, String>("petName"));
        vaccineName.setCellValueFactory(new PropertyValueFactory<PetVaccine, String>("vaccineName"));
        vaccStartDate.setCellValueFactory(new PropertyValueFactory<PetVaccine, Date>("petVaccineStartdate"));
        vaccEndDate.setCellValueFactory(new PropertyValueFactory<PetVaccine, Date>("petVaccineEnddate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT PET.PET_NAME, VACCINATION.VACCINE_NAME, PET_VACCINE.PET_VACCINE_STARTDATE, PET_VACCINE.PET_VACCINE_ENDDATE FROM PET_VACCINE " +
                "JOIN PET ON PET_VACCINE.PET_ID = PET.PET_ID " +
                "JOIN VACCINATION ON PET_VACCINE.VACCINE_ID = VACCINATION.VACCINE_ID";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetVaccine> vaccs = FXCollections.observableArrayList();

        while(rs.next()){
            final PetVaccine vacc = new PetVaccine();
            vacc.setPetName(rs.getString("PET_NAME"));
            vacc.setVaccineName(rs.getString("VACCINE_NAME"));
            vacc.setPetVaccineStartdate(rs.getDate("PET_VACCINE_STARTDATE"));
            vacc.setPetVaccineEnddate(rs.getDate("PET_VACCINE_ENDDATE"));
            vaccs.add(vacc);
        }
        tblVaccines.setItems(vaccs);
        preparedStatement.close();
        rs.close();
        connection.close();

        String sql1 = "SELECT PET.PET_ID, PET.PET_NAME FROM PET";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet = preparedStatement1.executeQuery();
        ObservableList<String>petInfo = FXCollections.observableArrayList();
        while (resultSet.next()){
            String s = resultSet.getInt("PET_ID")+"_"+resultSet.getString("PET_NAME");
            petInfo.add(s);
        }
        petSelect.setItems(petInfo);

        petSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                vaccFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void vaccFilter()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetVaccine, String>("petName"));
        vaccineName.setCellValueFactory(new PropertyValueFactory<PetVaccine, String>("vaccineName"));
        vaccStartDate.setCellValueFactory(new PropertyValueFactory<PetVaccine, Date>("petVaccineStartdate"));
        vaccEndDate.setCellValueFactory(new PropertyValueFactory<PetVaccine, Date>("petVaccineEnddate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String pID = petSelect.getValue();
        String[]p;
        p = pID.split("_");
        int PID = Integer.parseInt(p[0]);


        String sql = "SELECT PET.PET_NAME, VACCINATION.VACCINE_NAME, PET_VACCINE.PET_VACCINE_STARTDATE, PET_VACCINE.PET_VACCINE_ENDDATE FROM PET_VACCINE " +
                "JOIN PET ON PET_VACCINE.PET_ID = PET.PET_ID " +
                "JOIN VACCINATION ON PET_VACCINE.VACCINE_ID = VACCINATION.VACCINE_ID WHERE PET.PET_ID = "+PID;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetVaccine> vaccs = FXCollections.observableArrayList();

        while(rs.next()){
            final PetVaccine vacc1 = new PetVaccine();
            vacc1.setPetName(rs.getString("PET_NAME"));
            vacc1.setVaccineName(rs.getString("VACCINE_NAME"));
            vacc1.setPetVaccineStartdate(rs.getDate("PET_VACCINE_STARTDATE"));
            vacc1.setPetVaccineEnddate(rs.getDate("PET_VACCINE_ENDDATE"));
            vaccs.add(vacc1);
        }
        tblVaccines.setItems(vaccs);
        preparedStatement.close();
        rs.close();
    }
}
