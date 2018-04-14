package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class addCustomerController {



    @FXML // fx:id = "firstName"
    private TextField firstName;

    @FXML // fx:id = "lastName"
    private TextField lastName;

    @FXML // fx:id = "addressLine"
    private TextField addressLine;

    @FXML // fx:id = "city"
    private TextField city;

    @FXML // fx:id = "zip"
    private TextField zip;

    @FXML // fx:id = "cusPhone"
    private TextField cusPhone;

    @FXML // fx: id ="cusPhoneAlt"
    private TextField cusPhoneAlt;

    @FXML //fx: id ="cusEmgContact"
    private TextField cusEmgContact;

    @FXML // fx:id ="cusEmgPhone"
    private TextField cusEmgPhone;

    @FXML // fx:id = "email"
    private TextField email;

    @FXML // fx:id = "state"
    private ComboBox<String> state;

    @FXML // fx:id = "country"
    private ComboBox<String> country;



    @FXML //fx:id = "save"
    private Button save;

    @FXML // fx:id = "clear"
    private Button clear;

    public void initialize()throws SQLException{
        String sql = "SELECT STATE_NAME FROM STATE_PROVINCE";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<String> states = FXCollections.observableArrayList();

        while (rs.next()){
             states.add(rs.getString("STATE_NAME"));
            state.setItems(states);
        }
        rs.close();
        psmt.close();

        String sql2 = "SELECT COUNTRY_NAME FROM COUNTRY";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt2 = connection1.prepareStatement(sql2);
        ResultSet rs2 = psmt2.executeQuery();
        ObservableList<String> countries = FXCollections.observableArrayList();

        while(rs2.next()){
            countries.add(rs2.getString("COUNTRY_NAME"));
            country.setItems(countries);
        }
        rs2.close();
        psmt2.close();

        state.setValue("Texas");
        country.setValue("United States");
    }



   public void saveCommand() throws SQLException{
       Connection connection = DbHelper.getInstance().getConnection();
       LocalDate date = LocalDate.now();
       String insert = "INSERT INTO CUSTOMER(CUS_FIRSTNAME, CUS_LASTNAME, CUS_START_DATE,CUS_ADDRESS_LINE, CUS_ADDRESS_CITY," +
               "STATE_ID, CUS_ADDRESS_ZIP, COUNTRY_ID, CUS_EMAIL, CUS_PHONE, CUS_PHONE_ALT, CUS_EMG_CONTACT_NAME1, CUS_EMG_CONTACT_NUM1," +
               "CUS_STATUS_ID, DOGGY_DAYCARE_DAY) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
       PreparedStatement psmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
       psmt.setString(1,firstName.getText());
       psmt.setString(2,lastName.getText());
       psmt.setDate(3,Date.valueOf(date));
       psmt.setString(4,addressLine.getText());
       psmt.setString(5,city.getText());
       psmt.setInt(6,state.getSelectionModel().getSelectedIndex()+1);
       psmt.setString(7,zip.getText());
       psmt.setInt(8,country.getSelectionModel().getSelectedIndex()+1);
       psmt.setString(9,email.getText());
       psmt.setString(10,cusPhone.getText());
       psmt.setString(11,cusPhoneAlt.getText());
       psmt.setString(12,cusEmgContact.getText());
       psmt.setString(13,cusEmgPhone.getText());
       psmt.setInt(14,1);
       psmt.setInt(15,0);
       psmt.execute();
       psmt.close();

       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Succesfully added " + firstName.getText() + " to Customer");
       alert.setHeaderText("Customers Updated");
       alert.setContentText("Succesfully added " + firstName.getText() + " to Customers");
       alert.showAndWait();
       try {
           AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/addCustomerScreen.fxml"));
           BorderPane borderPane = Main.getRoot();
           borderPane.setCenter(pane);
       }catch (IOException e){
           e.printStackTrace();
       }

   }

}
