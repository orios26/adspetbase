package main;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.Employee;

import java.util.Date;

public class EmployeeDetailController {
    private Employee employee;

    @FXML
    private TextField employeeFirst;
    @FXML
    private TextField employeeLast;
    @FXML
    private DatePicker startDate;
    @FXML
    private Button save;

    private ChangeListener<String>firstNameListener;
    private ChangeListener<String>lastNameListener;
    private ChangeListener<Date>startDateListener;
    private ChangeListener<Date>endDate;
    private ChangeListener<String>phone;

    public void setEmployee(Employee employee){
        if(this.employee != null){
            unhookListener();
        }
        this.employee = employee;
        hookTo(employee);

    }

    private void hookTo(Employee employee){
        if ( employee == null) {
            employeeFirst.setText("");
            employeeLast.setText("");
        } else {
            employeeFirst.setText(employee.getEmployeeFirstname());
            employeeLast.setText(employee.getEmployeeLastname());

            //call to Employee save <needs creating>
            firstNameListener = (((observable, oldValue, newValue) -> employee.setEmployeeFirstname(newValue)));
            lastNameListener = (((observable, oldValue, newValue) -> employee.setEmployeeLastname(newValue)));

        }
    }

    private void unhookListener(){
        save.disableProperty().unbind();
        employeeFirst.textProperty().removeListener(firstNameListener);
        employeeLast.textProperty().removeListener(lastNameListener);


    }

}
