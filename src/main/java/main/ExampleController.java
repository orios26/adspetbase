package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;

public class ExampleController{

    @FXML
    TableView<Employee> tblEmployees;
    @FXML
    TableColumn<Employee, Integer> EmployeeId;
    @FXML
    TableColumn<Employee, String> firstName;
    @FXML
    TableColumn<Employee, String> lastName;
    @FXML
    TableColumn<Employee, Integer> status;
    @FXML
    TableColumn<Employee, Date> startDate;
    @FXML
    TableColumn<Employee, Date> endDate;
    @FXML
    TableColumn<Employee, String> phone;

    @FXML
    private EmployeeDetailController employeeDetailController;
    public void initialize() throws SQLException{
        EmployeeId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeId"));
        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeFirstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeLastname"));
        status.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeStatus"));
        startDate.setCellValueFactory(new PropertyValueFactory<Employee, Date>("employeeStartDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<Employee, Date>("employeeEndDate"));
        phone.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePhone"));

        ObservableList employee = null;
        try{
            employee = FXCollections.observableArrayList(EmployeeHelper.getInstance().getEmployees());
        }catch (SQLException e){
            e.printStackTrace();
        }
        tblEmployees.setItems(employee);
        tblEmployees.getSelectionModel().selectFirst();
        //employeeDetailController.setEmployee(tblEmployees.getSelectionModel().getSelectedItem());
    }


}
