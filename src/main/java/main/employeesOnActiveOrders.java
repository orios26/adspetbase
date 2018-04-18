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

public class employeesOnActiveOrders {

    @FXML
    TableView<Employee>tblEmployees;
    @FXML
    TableColumn<Employee, String>firstName;
    @FXML
    TableColumn<Employee, String>lastName;
    @FXML
    TableColumn<Employee, Integer>orderLineId;
    @FXML
    TableColumn<Employee, String>service;
    @FXML
    TableColumn<Employee, String>serviceType;

    public void initialize() throws SQLException{
        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeFirstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeLastname"));
        orderLineId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("orderLineId"));
        service.setCellValueFactory(new PropertyValueFactory<Employee, String>("service"));
        serviceType.setCellValueFactory(new PropertyValueFactory<Employee, String>("serviceType"));

        String sql = "SELECT EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, ORDER_LINE_ID, SERVICE_NAME, SERVICE_TYPE FROM EMPLOYEE " +
                "JOIN ORDER_LINE ON EMPLOYEE.EMPLOYEE_ID = ORDER_LINE.EMPLOYEE_ID " +
                "JOIN _ORDER ON ORDER_LINE.ORDER_ID = _ORDER.ORDER_ID " +
                "JOIN ORDER_LINE_STATUS ON ORDER_LINE.ORDER_LINE_STATUS_ID = ORDER_LINE_STATUS.ORDER_LINE_STATUS_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "JOIN SERVICE_TYPE ON _SERVICE.SERVICE_TYPE_ID = SERVICE_TYPE.SERVICE_TYPE_ID " +
                "ORDER BY ORDER_LINE_ID ASC";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Employee>employees = FXCollections.observableArrayList();
        while(resultSet.next()){
            final Employee employee = new Employee();
            employee.setEmployeeFirstname(resultSet.getString("EMPLOYEE_FIRSTNAME"));
            employee.setEmployeeLastname(resultSet.getString("EMPLOYEE_LASTNAME"));
            employee.setOrderLineId(resultSet.getInt("ORDER_LINE_ID"));
            employee.setService(resultSet.getString("SERVICE_NAME"));
            employee.setServiceType(resultSet.getString("SERVICE_TYPE"));
            employees.add(employee);
        }
        tblEmployees.setItems(employees);

    }
}
