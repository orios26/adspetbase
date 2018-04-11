package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import jfxtras.scene.control.LocalDateTimePicker;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class ManageEmployeeController {

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

    @FXML // fx:id = "employeeFirst"
    private TextField employeeFirst;

    @FXML // fx:id = "employeeLast"
    private TextField employeeLast;

    @FXML // fx:id = "startDatePicker"
    private DatePicker startDateSelect;

    @FXML // fx:id = "statusCombo"
    private ComboBox<String> statusCombo;

    @FXML // fx:id ="phoneText"
    private TextField phoneText;

    @FXML // fx:id = "addButton"
    private Button addButton;

    @FXML // fx:id = "editButton"
    private Button editButton;

    @FXML // fx:id = "deleteButton"
    private Button deleteButton;

    @FXML // fx:id = "clearButton"
    private Button clearButton;

    @FXML // fx: id ="editFirst"
    private TextField editFirst;

    @FXML // fx: id ="editLast"
    private TextField editLast;

    @FXML // fx:id = "editStart"
    private DatePicker editStart;

    @FXML // fx:id = "editEnd"
    private DatePicker editEnd;

    @FXML // fx:id = "editStatus"
    private ComboBox<String> editStatus;

    @FXML // fx:id = "editPhone"
    private TextField editPhone;

    @FXML
    private EmployeeDetailController employeeDetailController;

    private boolean editable;
    private int id = 0;

    public void initialize() throws SQLException {
        EmployeeId.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeId"));
        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeFirstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeeLastname"));
        status.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employeeStatus"));
        startDate.setCellValueFactory(new PropertyValueFactory<Employee, Date>("employeeStartDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<Employee, Date>("employeeEndDate"));
        phone.setCellValueFactory(new PropertyValueFactory<Employee, String>("employeePhone"));

        ObservableList employee = null;
        try {
            employee = FXCollections.observableArrayList(EmployeeHelper.getInstance().getEmployees());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblEmployees.setItems(employee);
        tblEmployees.getSelectionModel().selectFirst();

        String sql = "SELECT EMPLOYEE_STATUS FROM EMPLOYEE_STATUS";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<String> statusDesc = FXCollections.observableArrayList();

        while (rs.next()) {
            statusDesc.add(rs.getString("EMPLOYEE_STATUS"));
        }
        statusCombo.setItems(statusDesc);
        //editStatus.setItems(statusDesc);
        rs.close();
        psmt.close();
        connection.close();

    }

    public void editButtonPressed() throws SQLException {
        id = tblEmployees.getSelectionModel().getSelectedIndex() + 1;
        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, EMPLOYEE_START_DATE, EMPLOYEE_STATUS.EMPLOYEE_STATUS, EMPLOYEE_PHONE FROM EMPLOYEE " +
                "JOIN EMPLOYEE_STATUS ON EMPLOYEE.EMPLOYEE_STATUS_ID = EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID WHERE EMPLOYEE_ID = " + id;
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            employeeFirst.setText(rs.getString("EMPLOYEE_FIRSTNAME"));
            employeeLast.setText(rs.getString("EMPLOYEE_LASTNAME"));
            startDateSelect.setValue(rs.getDate("EMPLOYEE_START_DATE").toLocalDate());
            statusCombo.setValue(rs.getString("EMPLOYEE_STATUS"));
            phoneText.setText(rs.getString("EMPLOYEE_PHONE"));
            editable = true;
        }
    }

    public void savePressed() throws SQLException {
        if (editable == true) {
            Connection connection = DbHelper.getInstance().getConnection();
            String sql = "UPDATE EMPLOYEE" +
                    " SET EMPLOYEE_FIRSTNAME = ?, EMPLOYEE_LASTNAME = ?," +
                    " EMPLOYEE_STATUS_ID =?, EMPLOYEE_START_DATE = ?, " +
                    " EMPLOYEE_END_DATE = ?, EMPLOYEE_PHONE = ? WHERE EMPLOYEE_ID = " + id;
            PreparedStatement p = connection.prepareStatement(sql);
            p.setString(1, employeeFirst.getText());
            p.setString(2, employeeLast.getText());
            p.setInt(3, statusCombo.getSelectionModel().getSelectedIndex() + 1);
            p.setDate(4, Date.valueOf(startDateSelect.getValue()));
            p.setDate(5, Date.valueOf(startDateSelect.getValue()));
            p.setString(6, phoneText.getText());
            p.execute();
            connection.close();
            p.close();
            id = 0;
        } else {
            Connection connection = DbHelper.getInstance().getConnection();
            String sql = "INSERT INTO EMPLOYEE" +
                    "(EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME," +
                    " EMPLOYEE_STATUS_ID, EMPLOYEE_START_DATE, " +
                    " EMPLOYEE_END_DATE, EMPLOYEE_PHONE) VALUES (?,?,?,?,?,?)";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setString(1, employeeFirst.getText());
            p.setString(2, employeeLast.getText());
            p.setInt(3, statusCombo.getSelectionModel().getSelectedIndex() + 1);
            p.setDate(4, Date.valueOf(startDateSelect.getValue()));
            p.setDate(5, Date.valueOf(startDateSelect.getValue()));
            p.setString(6, phoneText.getText());
            p.execute();
            connection.close();
            p.close();
        }
        initialize();
    }

}
