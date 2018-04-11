package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import jfxtras.scene.control.LocalDateTimePicker;

import java.io.IOException;
import java.sql.*;

public class createBoardingApptController {


    @FXML // fx:id = "petInfo"
    private ComboBox<String>petInfo;

    @FXML // fx:id = "arrivalDate"
    private DatePicker arrivalDate;

    @FXML // fx:id = "departureDate"
    private DatePicker departureDate;

    @FXML // fx:id = "kennelSelect"
    private ComboBox<String>kennelSelect;

    @FXML // fx:id = "specialCareInst"
    private TextArea specialCareInst;

    @FXML // fx:id = "saveButton"
    private Button saveButton;

    @FXML // fx:id = "clearButton"
    private Button clearButton;


    public void initialize() throws SQLException{
        String sql = "SELECT PET_ID, PET_NAME, PET_WEIGHT_GRP_ID FROM PET";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<String>pet = FXCollections.observableArrayList();

        while(rs.next()){
            String s = rs.getInt("PET_ID") +"-"+ rs.getString("PET_NAME") + "-" + rs.getInt("PET_WEIGHT_GRP_ID");
            pet.add(s);
        }
        petInfo.setItems(pet);
        rs.close();
        psmt.close();
        connection.close();

        String sql1 = "SELECT KENNEL_ID, KENNEL_SIZE.KENNEL_SIZE FROM KENNEL" +
                " JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID " +
                "JOIN KENNEL_STATUS ON KENNEL.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID  " +
                "WHERE KENNEL_STATUS.KENNEL_STATUS = 'Open' ORDER BY KENNEL_SIZE DESC";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt1 = connection1.prepareStatement(sql1);
        ResultSet rs1 = psmt1.executeQuery();
        ObservableList<String>kennel = FXCollections.observableArrayList();

        while(rs1.next()){
            String s = rs1.getInt("KENNEL_ID") + "-" + rs1.getString("KENNEL_SIZE");
            kennel.add(s);
        }
        kennelSelect.setItems(kennel);
        rs1.close();
        psmt1.close();
        connection1.close();
    }

    public void saveCommand() throws SQLException{
        String sql = "INSERT INTO BOARDING_APPT(PET_ID, PET_WEIGHT_GRP_ID, BOARDING_APPT_DESC, BOARDING_APPT_CHECK_IN, BOARDING_APPT_CHECK_OUT, ORDER_LINE_ID)" +
                "VALUES (?,?,?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement p = connection.prepareStatement(sql);
        p.execute(sql, Statement.RETURN_GENERATED_KEYS);
        String s = petInfo.getValue();
        String[] ids = s.split("-");
        String s1 = ids[0];
        String s2 = ids[2];
        int petid = Integer.parseInt(s1);
        int petwghtgrp = Integer.parseInt(s2);
        p.setInt(1,petid);
        p.setInt(2,petwghtgrp);
        p.setString(3,specialCareInst.getText());
        p.setDate(4,Date.valueOf(arrivalDate.getValue()));
        p.setDate(5,Date.valueOf(departureDate.getValue()));
        //p.setInt(6,);
        p.execute();
        p.close();
        connection.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("BOARDING APPT ADDED");
        alert.setContentText("Appointment created");
        alert.showAndWait();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/createBoardingScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
