package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


public class NewClientScreenController {

    @FXML // fx:id = "cancelButton"
    private Button cancelButton;

    @FXML // fx:id = "petInfo"
    private Button petInfo;

    @FXML // fx:id = "petMedIno
    private Button petmedInfo;

    @FXML // fx:id = "vaccineDone"
    private Button vaccineDone;

    @FXML // fx:id = "done"
    private Button done;

    @FXML
    void storeandNext(ActionEvent event){
        try{
            AnchorPane pane =  FXMLLoader.load(getClass().getResource("fxmlAssets/newPetScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
//            borderPane.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        //cancelButton.setOnAction(actionEvent -> Platform.exit());
    }

    @FXML
    void petNext(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/newPetMedicationScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            //BorderPane borderPane = MyMenuController.getInformationPane();
            borderPane.setCenter(pane);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void petMedNext(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/newDogVaccination.fxml"));
            BorderPane borderPane = Main.getRoot();
            //BorderPane borderPane = MyMenuController.getInformationPane();
            borderPane.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void addMed(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/newPetMedicationScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void processDone(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/PaneOne.fxml"));
            BorderPane borderpane = Main.getRoot();
            borderpane.setCenter(pane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}

