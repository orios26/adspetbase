package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;

public class addMedicationController {

    @FXML //fx:id ="medicationName"
    private TextField medicationName;

    @FXML // fx:id ="medDesc"
    private TextArea medDesc;

    @FXML // fx:id = "medDosage"
    private TextArea medDosage;

    @FXML // fx:id = "medStart"
    private DatePicker medStart;

    @FXML // fx:id = "medEnd"
    private DatePicker medEnd;

    @FXML //fx:id ="petSelect"
    private ComboBox<String>petSelect;

    @FXML //fx:id ="addMed"
    private Button addMed;

    @FXML // fx:id ="clear"
    private Button clear;

    private int PID, PMID;
    private String petName;

    public void initialize()throws SQLException{
        String sql = "SELECT PET.PET_ID, PET.PET_NAME FROM PET";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ObservableList<String>pet = FXCollections.observableArrayList();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String s = resultSet.getInt("PET_ID")+"_"+resultSet.getString("PET_NAME");
            pet.add(s);
        }
        petSelect.setItems(pet);
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    public void generatePetMed()throws SQLException {
        String petID = petSelect.getValue();
        String[] p;
        p = petID.split("_");
        PID = Integer.parseInt(p[0]);
        petName = p[1];
        try {
            String insertMed = "INSERT INTO PET_MED(PET_ID, MED_NAME,PET_MED_STATUS_ID,PET_MED_DESC,PET_MED_DOSSAGE,PET_MED_START_DATE,PET_MED_END_DATE) VALUES" +
                    " (?,?,?,?,?,?,?)";
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertMed, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, PID);
            preparedStatement.setString(2, medicationName.getText() + "");
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, medDesc.getText() + "");
            preparedStatement.setString(5, medDosage.getText() + "");
            preparedStatement.setDate(6, Date.valueOf(medStart.getValue()));
            preparedStatement.setDate(7, Date.valueOf(medEnd.getValue()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                PMID = resultSet.getInt(1);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase Update");
            alert.setHeaderText("Medication for " +petName+" added!");
            alert.setContentText("Medication for " + petName+ " added!");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase Error");
            alert.setHeaderText("PetBase Error");
            alert.setContentText("Please validate the information entered");
            alert.showAndWait();
        }
    }

    public void clearScreen(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("addMedicationScreen.fxml"));
            BorderPane borderPane =Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){e.printStackTrace();}
    }

}
