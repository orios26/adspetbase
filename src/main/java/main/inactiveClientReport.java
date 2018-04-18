package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inactiveClientReport {

    @FXML
    TableView<Customer>tblCustomer;
    @FXML
    TableColumn<Customer, String>firstName;
    @FXML
    TableColumn<Customer, String>lastName;
    @FXML
    TableColumn<Customer, String>state;
    @FXML
    TableColumn<Customer, String>country;
    @FXML
    TableColumn<Customer, String>email;
    @FXML
    TableColumn<Customer, String>status;

    public void initialize()throws SQLException{
        firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusFirstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusLastname"));
        state.setCellValueFactory(new PropertyValueFactory<Customer, String>("stateName"));
        country.setCellValueFactory(new PropertyValueFactory<Customer, String>("countryName"));
        email.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusEmail"));
        status.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusStatus"));


        String sql ="SELECT CUS_FIRSTNAME, CUS_LASTNAME, STATE_NAME, COUNTRY_NAME, CUS_EMAIL, CUS_STATUS FROM CUSTOMER " +
                "JOIN CUSTOMER_STATUS ON CUSTOMER.CUS_STATUS_ID = CUSTOMER_STATUS.CUS_STATUS_ID " +
                "JOIN STATE_PROVINCE ON CUSTOMER.STATE_ID = STATE_PROVINCE.STATE_ID " +
                "JOIN COUNTRY ON CUSTOMER.COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                "WHERE CUS_STATUS = 'Inactive'";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Customer>customers = FXCollections.observableArrayList();
        while(resultSet.next()){
            final Customer customer = new Customer();
             customer.setCusFirstname(resultSet.getString("CUS_FIRSTNAME"));
             customer.setCusLastname(resultSet.getString("CUS_LASTNAME"));
             customer.setStateName(resultSet.getString("STATE_NAME"));
             customer.setCountryName(resultSet.getString("COUNTRY_NAME"));
             customer.setCusEmail(resultSet.getString("CUS_EMAIL"));
             customer.setCusStatus(resultSet.getString("CUS_STATUS"));
             customers.add(customer);
        }
        tblCustomer.setItems(customers);
    }
}
