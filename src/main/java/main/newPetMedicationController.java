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

public class newPetMedicationController {

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

    @FXML //fx:id ="addMed"
    private Button addMed;

    @FXML // fx:id ="clear"
    private Button clear;

    @FXML //fx:id ="next"
    private Button next;

    @FXML
    private newPetController newPetController;

    private int PID, PMID, PTID;
    private String petName;

    public void initialize()throws SQLException{

    }

    public void generatePetMed()throws SQLException {

        PID = Main.getPid();
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
            alert.setHeaderText("Medication added!");
            alert.setContentText("Medication for added!");
            alert.showAndWait();
            try{
                AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/newPetMedicationScreen.fxml"));
                BorderPane borderPane =Main.getRoot();
                borderPane.setCenter(pane);
            }catch (IOException e){e.printStackTrace();}

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase Error");
            alert.setHeaderText("PetBase Error");
            alert.setContentText("Please validate the information entered");
            alert.showAndWait();
        }
    }

    public void vaccinationLoader(){
        PTID = Main.getPtid();
        if(PTID ==1){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/newDogVaccination.fxml"));
                BorderPane borderPane = Main.getRoot();
                borderPane.setCenter(pane);
            }catch (IOException e){e.printStackTrace();}
        }else{
            try{
                AnchorPane pane =FXMLLoader.load(getClass().getResource("fxmlAssets/newCatVaccinationScreen.fxml"));
                BorderPane borderPane = Main.getRoot();
                borderPane.setCenter(pane);
            }catch (IOException e){e.printStackTrace();}
        }

    }

    public void clearScreen(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/newPetMedicationScreen.fxml"));
            BorderPane borderPane =Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){e.printStackTrace();}
    }

}
