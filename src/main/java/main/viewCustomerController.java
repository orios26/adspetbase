package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class viewCustomerController {

    @FXML
    TableView<Customer>tblcustomer;
    @FXML
    TableColumn<Customer, Integer>custId;
    @FXML
    TableColumn<Customer, String>custFirstName;
    @FXML
    TableColumn<Customer, String>custLastName;
    @FXML
    TableColumn<Customer, String>custEmail;
    @FXML
    TableColumn<Customer, String>custPhone;
    @FXML
    TableColumn<Customer, String>custAddress;
    @FXML
    TableColumn<Customer, String>custCity;
    @FXML
    TableColumn<Customer, String>custState;
    @FXML
    TableColumn<Customer, String>custZip;
    @FXML
    TableColumn<Customer, String>custCountry;
    @FXML
    TableColumn<Customer, String>custStatus;
    @FXML
    private Button delete;

    public void initialize() throws SQLException {
        custId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        custFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusFirstname"));
        custLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusLastname"));
        custEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusEmail"));
        custPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusPhone"));
        custAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusAddressLine"));
        custCity.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusAddressCity"));
        custState.setCellValueFactory(new PropertyValueFactory<Customer, String>("stateName"));
        custZip.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusAddressZip"));
        custCountry.setCellValueFactory(new PropertyValueFactory<Customer, String>("countryName"));
        custStatus.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusStatus"));
        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT CUSTOMER.CUSTOMER_ID, CUSTOMER.CUS_FIRSTNAME, CUSTOMER.CUS_LASTNAME, CUSTOMER.CUS_EMAIL, CUSTOMER.CUS_PHONE, CUSTOMER.CUS_ADDRESS_LINE, CUSTOMER.CUS_ADDRESS_CITY, STATE_PROVINCE.STATE_CODE, CUSTOMER.CUS_ADDRESS_ZIP, COUNTRY.COUNTRY_CODE, CUSTOMER_STATUS.CUS_STATUS FROM CUSTOMER " +
                "JOIN CUSTOMER_STATUS ON CUSTOMER.CUS_STATUS_ID = CUSTOMER_STATUS.CUS_STATUS_ID " +
                "JOIN COUNTRY ON CUSTOMER.COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                "JOIN STATE_PROVINCE ON CUSTOMER.STATE_ID = STATE_PROVINCE.STATE_ID;";
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        while(rs.next()){
            final Customer c = new Customer();
            c.setCustomerId(rs.getInt("CUSTOMER_ID"));
            c.setCusFirstname(rs.getString("CUS_FIRSTNAME"));
            c.setCusLastname(rs.getString("CUS_LASTNAME"));
            c.setCusEmail(rs.getString("CUS_EMAIL"));
            c.setCusPhone(rs.getString("CUS_PHONE"));
            c.setCusAddressLine(rs.getString("CUS_ADDRESS_LINE"));
            c.setCusAddressCity(rs.getString("CUS_ADDRESS_CITY"));
            c.setStateName(rs.getString("STATE_CODE"));
            c.setCusAddressZip(rs.getString("CUS_ADDRESS_ZIP"));
            c.setCountryName(rs.getString("COUNTRY_CODE"));
            c.setCusStatus(rs.getString("CUS_STATUS"));
            customers.add(c);
        }
        rs.close();
        psmt.close();
        connection.close();
        tblcustomer.setItems(customers);
    }

    public void deleteButtonPressed(ActionEvent event) throws SQLException{
        int id = tblcustomer.getSelectionModel().getSelectedIndex()+1;
        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "UPDATE CUSTOMER SET CUS_STATUS_ID = 2 WHERE CUSTOMER_ID= "+id;
        PreparedStatement psmt = connection.prepareStatement(sql);
        psmt.execute();
        psmt.close();
        initialize();
    }

}
