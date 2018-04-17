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

public class editMedicationController {

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
    private ComboBox<String> petSelect;

    @FXML //fx:id ="medSelect"
    private ComboBox<String>medSelect;

    @FXML //fx:id ="medStatus"
    private ComboBox<String> medStatus;

    @FXML //fx:id ="updateMed"
    private Button updatedMed;

    @FXML // fx:id ="clear"
    private Button clear;

    private int PID, PMID;
    private String petName;


    public void initialize()throws SQLException{
        medSelect.setDisable(true);
        String sql = "SELECT PET.PET_ID, PET.PET_NAME FROM PET";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ObservableList<String> pet = FXCollections.observableArrayList();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String s = resultSet.getInt("PET_ID")+"_"+resultSet.getString("PET_NAME");
            pet.add(s);
        }
        petSelect.setItems(pet);
        resultSet.close();
        preparedStatement.close();
        connection.close();

        String sql1 = "SELECT PET_MED_STATUS FROM PET_MED_STATUS";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ObservableList<String>status =FXCollections.observableArrayList();
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        while(resultSet1.next()){
            status.add(resultSet1.getString("PET_MED_STATUS"));
        }
        medStatus.setItems(status);
        medScreenLoad();
        selectSpecificMed();
    }

    public void selectSpecificMed() {
        medSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try{
                medPicker();
            }catch (SQLException e){e.printStackTrace();}
        });
    }

    public void selectPetMed()throws SQLException{
        String PetID = petSelect.getValue();
        String p[];
        p = PetID.split("_");
        PID = Integer.parseInt(p[0]);
        petName = p[1];

        String sql = "SELECT PET_MED.PET_MED_ID, PET_MED.MED_NAME FROM PET_MED " +
                "JOIN PET ON PET_MED.PET_ID = PET.PET_ID WHERE PET_MED.PET_ID = "+PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ObservableList<String>petMedList = FXCollections.observableArrayList();
        try{
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String s = resultSet.getInt("PET_MED_ID")+"_"+resultSet.getString("MED_NAME");
                petMedList.add(s);
            }
            medSelect.setItems(petMedList);
            medSelect.setDisable(false);
        }catch (SQLException e){e.printStackTrace();}

    }

    public void medScreenLoad()throws SQLException{
        petSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try{
                selectPetMed();
            }catch (SQLException e){e.printStackTrace();}
        });
    }

    public void medPicker()throws SQLException{
        String petMedId = medSelect.getValue();
        String[]m;
        m = petMedId.split("_");
        PMID = Integer.parseInt(m[0]);

        String sql = "SELECT MED_NAME, PET_MED_STATUS.PET_MED_STATUS, PET_MED_DESC, PET_MED_DOSSAGE, PET_MED_START_DATE, PET_MED_END_DATE FROM PET_MED " +
                "JOIN PET_MED_STATUS ON PET_MED.PET_MED_STATUS_ID = PET_MED_STATUS.PET_MED_STATUS_ID " +
                "JOIN PET ON PET_MED.PET_ID = PET.PET_ID WHERE PET.PET_ID = "+PID+" AND PET_MED_ID = "+PMID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                medicationName.setText(resultSet.getString("MED_NAME"));
                medStatus.setValue(resultSet.getString("PET_MED_STATUS"));
                medDesc.setText(resultSet.getString("PET_MED_DESC"));
                medDosage.setText(resultSet.getString("PET_MED_DOSSAGE"));
                medStart.setValue(resultSet.getDate("PET_MED_START_DATE").toLocalDate());
                medEnd.setValue(resultSet.getDate("PET_MED_END_DATE").toLocalDate());
            }
        }catch (SQLException e){
           //insert  Alert
        }

    }

    public void updateMed()throws SQLException{
        try{
            String update = "UPDATE PET_MED SET MED_NAME = ?, PET_MED_STATUS_ID = ?, PET_MED_DESC = ?, PET_MED_DOSSAGE = ?, PET_MED_START_DATE = ?, PET_MED_END_DATE=? WHERE PET_MED_ID ="+PMID;
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1,medicationName.getText());
            preparedStatement.setInt(2,medStatus.getSelectionModel().getSelectedIndex()+1);
            preparedStatement.setString(3,medDesc.getText());
            preparedStatement.setString(4,medDosage.getText());
            preparedStatement.setDate(5, Date.valueOf(medStart.getValue()));
            preparedStatement.setDate(6,Date.valueOf(medEnd.getValue()));
            preparedStatement.execute();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("PetBase ERROR");
            alert.setHeaderText("INVALID INFORMATION");
            alert.setContentText("Please check the information entered and try again");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("Medication Updated");
        alert.setContentText("Medication for "+petName+" has been updated");
        alert.showAndWait();
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editMedicationScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){e.printStackTrace();}
    }

    public void clearScreen(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editMedicationScreen.fxml"));
            BorderPane borderPane =Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){e.printStackTrace();}
    }

}
