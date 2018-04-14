package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class editCustomerInformationController {

    @FXML //fx:id = "state"
    private ComboBox<String> state;

    @FXML // fx:id = "country"
    private ComboBox<String> country;

    @FXML //fx:id="customerInfo"
    private ComboBox<String> customerInfo;

    @FXML //fx:id="customerStatus"
    private ComboBox<String> customerStatus;

    @FXML // fx:id="firstName"
    private TextField firstName;

    @FXML // fx:id="lastName"
    private TextField lastName;

    @FXML // fx:id="addressLine"
    private TextField addressLine;

    @FXML // fx:id="addressCity"
    private TextField addressCity;

    @FXML // fx:id="addressZip"
    private TextField addressZip;

    @FXML // fx:id="phone"
    private TextField phone;

    @FXML // fx:id="phoneAlt"
    private TextField phoneAlt;

    @FXML // fx:id="email"
    private TextField email;

    @FXML // fx:id="emgContact"
    private TextField emgContact;

    @FXML // fx:id="emgNum"
    private TextField emgNum;

    int id;


    public void initialize() throws SQLException {

        String sql = "SELECT STATE_NAME FROM STATE_PROVINCE";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<String> states = FXCollections.observableArrayList();

        while (rs.next()) {
            states.add(rs.getString("STATE_NAME"));
            state.setItems(states);
        }
        rs.close();
        psmt.close();
        connection.close();

        String sql2 = "SELECT COUNTRY_NAME FROM COUNTRY";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt2 = connection1.prepareStatement(sql2);
        ResultSet rs2 = psmt2.executeQuery();
        ObservableList<String> countries = FXCollections.observableArrayList();

        while (rs2.next()) {
            countries.add(rs2.getString("COUNTRY_NAME"));
            country.setItems(countries);
        }
        rs2.close();
        psmt2.close();
        connection1.close();

        String sql3 = "SELECT CUSTOMER_ID, CUS_LASTNAME, CUS_FIRSTNAME FROM CUSTOMER";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt3 = connection2.prepareStatement(sql3);
        ResultSet rs3 = psmt3.executeQuery();
        ObservableList<String> cus = FXCollections.observableArrayList();
        while (rs3.next()) {
            String string = rs3.getInt("CUSTOMER_ID") + "-" + rs3.getString("CUS_LASTNAME") + "-" + rs3.getString("CUS_FIRSTNAME");
            cus.add(string);
        }
        customerInfo.setItems(cus);
        psmt3.close();
        connection2.close();
        rs3.close();

        Connection connection3 = DbHelper.getInstance().getConnection();
        String sql4 = "SELECT CUS_STATUS FROM CUSTOMER_STATUS";
        PreparedStatement psmt4 = connection3.prepareStatement(sql4);
        ResultSet resultSet = psmt4.executeQuery();
        ObservableList<String> statusDesc = FXCollections.observableArrayList();
        while (resultSet.next()) {
            statusDesc.add(resultSet.getString("CUS_STATUS"));
        }
        customerStatus.setItems(statusDesc);
        customerScreenLoad();


    }

    public void customerScreenLoad() throws SQLException{

            customerInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    customerPicker();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                });

    }

    private void customerPicker() throws SQLException {
        String s = customerInfo.getValue();
        String[] ids = s.split("-");
        String s1 = ids[0];
        String s2 = ids[1];
        id = Integer.parseInt(s1);
        String sql = "SELECT CUS_FIRSTNAME, CUS_LASTNAME, CUS_ADDRESS_LINE, CUS_ADDRESS_CITY, STATE_PROVINCE.STATE_NAME, CUS_ADDRESS_ZIP, " +
                " COUNTRY.COUNTRY_NAME, CUS_EMAIL, CUS_PHONE, CUS_PHONE_ALT, CUS_EMG_CONTACT_NAME1, CUS_EMG_CONTACT_NUM1, CUSTOMER_STATUS.CUS_STATUS FROM CUSTOMER " +
                "JOIN CUSTOMER_STATUS ON CUSTOMER.CUS_STATUS_ID = CUSTOMER_STATUS.CUS_STATUS_ID " +
                "JOIN COUNTRY ON CUSTOMER.COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                "JOIN STATE_PROVINCE ON CUSTOMER.STATE_ID = STATE_PROVINCE.STATE_ID WHERE CUSTOMER_ID = " + id;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        try {
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                firstName.setText(rs.getString("CUS_FIRSTNAME"));
                lastName.setText(rs.getString("CUS_LASTNAME"));
                addressLine.setText(rs.getString("CUS_ADDRESS_LINE"));
                addressCity.setText(rs.getString("CUS_ADDRESS_CITY"));
                addressZip.setText(rs.getString("CUS_ADDRESS_ZIP"));
                state.setValue(rs.getString("STATE_NAME"));
                country.setValue(rs.getString("COUNTRY_NAME"));
                email.setText(rs.getString("CUS_EMAIL"));
                phone.setText(rs.getString("CUS_PHONE"));
                phoneAlt.setText(rs.getString("CUS_PHONE_ALT"));
                emgContact.setText(rs.getString("CUS_EMG_CONTACT_NAME1"));
                emgNum.setText(rs.getString("CUS_EMG_CONTACT_NUM1"));
                customerStatus.setValue(rs.getString("CUS_STATUS"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void customerUpdate() throws SQLException{
        try{
        String update = "UPDATE CUSTOMER SET CUS_FIRSTNAME = ?, CUS_LASTNAME = ?, CUS_ADDRESS_LINE = ?, CUS_ADDRESS_CITY = ?, CUS_ADDRESS_ZIP = ?," +
                "STATE_ID = ?, COUNTRY_ID = ?, CUS_EMAIL =?, CUS_PHONE = ?, CUS_PHONE_ALT = ?, CUS_EMG_CONTACT_NAME1 = ?," +
                "CUS_EMG_CONTACT_NUM1 =?, CUS_STATUS_ID = ? WHERE CUSTOMER_ID ="+id;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement p = connection.prepareStatement(update);
        p.setString(1,firstName.getText());
        p.setString(2,lastName.getText());
        p.setString(3,addressLine.getText());
        p.setString(4,addressCity.getText());
        p.setString(5,addressZip.getText());
        p.setInt(6,state.getSelectionModel().getSelectedIndex()+1);
        p.setInt(7,country.getSelectionModel().getSelectedIndex()+1);
        p.setString(8,email.getText());
        p.setString(9,phone.getText());
        p.setString(10,phoneAlt.getText());
        p.setString(11,emgContact.getText());
        p.setString(12,emgNum.getText());
        p.setInt(13,customerStatus.getSelectionModel().getSelectedIndex()+1);
        p.execute();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR ");
            alert.setHeaderText("INVALID INFORMATION");
            alert.setContentText("Please check the information entered and try again");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succesfully updated " + firstName.getText() + " in Customer");
        alert.setHeaderText("Customers Updated");
        alert.setContentText("Succesfully updated " + firstName.getText() + " to Customers");
        alert.showAndWait();

    }
}
