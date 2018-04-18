package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class customersServicedThisMonth {


    @FXML
    TableView<Customer> tblCustomer;
    @FXML
    TableColumn<Customer, String> firstName;
    @FXML
    TableColumn<Customer, String>lastName;
    @FXML
    TableColumn<Customer, String>email;
    @FXML
    TableColumn<Customer, Date>orderStart;

    public void initialize()throws SQLException{
        firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusFirstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusLastname"));
        email.setCellValueFactory(new PropertyValueFactory<Customer, String>("cusEmail"));
        orderStart.setCellValueFactory(new PropertyValueFactory<Customer, Date>("orderStart"));


        String sql = "SELECT DISTINCT CUS_FIRSTNAME, CUS_LASTNAME, CUS_EMAIL, ORDER_START FROM CUSTOMER " +
                "JOIN _ORDER ON CUSTOMER.CUSTOMER_ID = _ORDER.CUSTOMER_ID " +
                "JOIN ORDER_LINE ON _ORDER.ORDER_ID = ORDER_LINE.ORDER_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "WHERE ORDER_START BETWEEN {ts '2018-03-26 00:00:00'} AND {ts '2018-04-26 23:59:59'} " +
                "ORDER BY ORDER_START ASC";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        while(resultSet.next()){
            final Customer customer = new Customer();
            customer.setCusFirstname(resultSet.getString("CUS_FIRSTNAME"));
            customer.setCusLastname(resultSet.getString("CUS_LASTNAME"));
            customer.setCusEmail(resultSet.getString("CUS_EMAIL"));
            customer.setOrderStart(resultSet.getDate("ORDER_START"));
            customers.add(customer);
        }
        tblCustomer.setItems(customers);

    }
}
