package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class editGroomingApptController {

    @FXML // fx:id = "customerSelect"
    private ComboBox<String> customerSelect;

    @FXML // fx:id ="petSelect"
    private ComboBox<String> petSelect;

    @FXML // fx:id ="groomerSelect"
    private ComboBox<String> groomerSelect;

    @FXML // fx:id ="startTime"
    private LocalDateTimeTextField startTime;

    @FXML // fx:id = "endTime"
    private LocalDateTimeTextField endTime;

    @FXML // fx:id = "specialCareInst"
    private TextArea specialCareInst;

    @FXML //fx:id ="appointmentSelect"
    private ComboBox<String>appointmentSelect;

    @FXML //fx:id ="appointmentStatus"
    private ComboBox<String>appointmentStatus;

    private int CID, OLID, OID, PID, EID, GID, VID;


    public void initialize() throws SQLException{
        String sql = "SELECT PET.PET_ID, PET.PET_NAME, PET_WEIGHT_HIST.WEIGHT FROM PET  " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs3 = preparedStatement.executeQuery();
        ObservableList<String> pet = FXCollections.observableArrayList();
        while (rs3.next()) {
            String string = rs3.getInt("PET_ID") + "_" + rs3.getString("PET_NAME") + "_" + rs3.getString("WEIGHT");
            pet.add(string);
        }
        petSelect.setItems(pet);
        preparedStatement.close();
        connection.close();
        rs3.close();

        String sql1 = "SELECT EMPLOYEE_ID, EMPLOYEE_LASTNAME, EMPLOYEE_FIRSTNAME FROM EMPLOYEE";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<String> emp = FXCollections.observableArrayList();
        while(resultSet1.next()){
            emp.add(resultSet1.getInt("EMPLOYEE_ID")+"_"+resultSet1.getString("EMPLOYEE_LASTNAME")+"_"+resultSet1.getString("EMPLOYEE_FIRSTNAME"));
        }
        groomerSelect.setItems(emp);

        String sql2 = "SELECT ORDER_STATUS FROM ORDER_STATUS";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
        ResultSet resultSet = preparedStatement2.executeQuery();
        ObservableList<String>status = FXCollections.observableArrayList();
        while(resultSet.next()){
            status.add(resultSet.getString("ORDER_STATUS"));
        }
        appointmentStatus.setItems(status);

        groomerSelect.setDisable(true);
        startTime.setDisable(true);
        endTime.setDisable(true);
        appointmentStatus.setDisable(true);

        petSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                apptFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        appointmentSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                detailLoad();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void apptFilter() throws SQLException {
        String petID = petSelect.getValue();
        String[]p;
        p = petID.split("_");
        PID = Integer.parseInt(p[0]);
        String sql = "SELECT VISIT.VISIT_ID FROM VISIT " +
                "JOIN PET ON VISIT.PET_ID = PET.PET_ID " +
                "JOIN ORDER_LINE ON VISIT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID WHERE _SERVICE.SERVICE_ID = 1 AND PET.PET_ID = "+PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String>appt = FXCollections.observableArrayList();
        while(resultSet.next()){
            appt.add(resultSet.getString("VISIT_ID"));
        }
        appointmentSelect.setItems(appt);
        groomerSelect.setDisable(false);
        startTime.setDisable(false);
        endTime.setDisable(false);
        appointmentStatus.setDisable(false);
    }

    public void detailLoad()throws SQLException{
        String visitID = appointmentSelect.getValue();
        VID = Integer.parseInt(visitID);

        String sql ="SELECT VISIT.VISIT_ID, PET.PET_ID, PET.PET_NAME, VISIT.VISIT_DESC, VISIT.VISIT_CHECK_IN, VISIT.VISIT_CHECK_OUT, EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.EMPLOYEE_LASTNAME, EMPLOYEE.EMPLOYEE_FIRSTNAME FROM VISIT " +
                "JOIN PET ON VISIT.PET_ID = PET.PET_ID " +
                "JOIN ORDER_LINE ON VISIT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID WHERE VISIT.VISIT_ID ="+VID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            groomerSelect.setValue(resultSet.getInt("EMPLOYEE_ID")+"_"+resultSet.getString("EMPLOYEE_LASTNAME")+"_"+resultSet.getString("EMPLOYEE_FIRSTNAME"));
            startTime.setLocalDateTime(resultSet.getTimestamp("VISIT_CHECK_IN").toLocalDateTime());
            endTime.setLocalDateTime(resultSet.getTimestamp("VISIT_CHECK_OUT").toLocalDateTime());
            specialCareInst.setText(resultSet.getString("VISIT_DESC"));
        }

    }

    public void generateGroomingAppt()throws SQLException{
        //getting IDS from comboboxes
        String employeeID = groomerSelect.getValue();
        String petID = petSelect.getValue();
        String []c,e,p;
        //splitting id from rest of string
        e = employeeID.split("_");
        p = petID.split("_");
        //converting strings to ints
        EID = Integer.parseInt(e[0]);
        PID = Integer.parseInt(p[0]);
        LocalDate date = LocalDate.now();

        String orderID = "SELECT _ORDER.ORDER_ID FROM VISIT " +
                "JOIN PET ON VISIT.PET_ID = PET.PET_ID " +
                "JOIN ORDER_LINE ON VISIT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID " +
                "JOIN _ORDER ON ORDER_LINE.ORDER_ID = _ORDER.ORDER_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID WHERE VISIT.VISIT_ID ="+VID;
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(orderID);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        while(resultSet1.next()){
            OID = resultSet1.getInt("ORDER_ID");
        }

        Connection connection = DbHelper.getInstance().getConnection();
        String orderGenerate = "UPDATE _ORDER SET ORDER_START = ?, ORDER_END = ?, ORDER_STATUS_ID = ? WHERE ORDER_ID ="+OID;
        PreparedStatement preparedStatement = connection.prepareStatement(orderGenerate);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(startTime.getLocalDateTime()));
        preparedStatement.setTimestamp(2,Timestamp.valueOf(endTime.getLocalDateTime()));
        preparedStatement.setInt(3,appointmentStatus.getSelectionModel().getSelectedIndex()+1);
        preparedStatement.execute();
        connection.close();
        preparedStatement.close();

        String orderLineId = "SELECT ORDER_LINE.ORDER_LINE_ID FROM VISIT " +
                "JOIN PET ON VISIT.PET_ID = PET.PET_ID " +
                "JOIN ORDER_LINE ON VISIT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID " +
                "JOIN _ORDER ON ORDER_LINE.ORDER_ID = _ORDER.ORDER_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID WHERE VISIT.VISIT_ID ="+VID;
        Connection connection4 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement4 =connection4.prepareStatement(orderLineId);
        ResultSet rs = preparedStatement4.executeQuery();
        while(rs.next()){
            OLID = rs.getInt("ORDER_LINE_ID");
        }

        Connection connection2 = DbHelper.getInstance().getConnection();
        String orderLineGenerate = "UPDATE ORDER_LINE  SET ORDER_LINE_STATUS_ID =?,  EMPLOYEE_ID=?, PET_ID=? WHERE ORDER_LINE_ID ="+OLID;
        PreparedStatement preparedStatement2 = connection2.prepareStatement(orderLineGenerate);
        preparedStatement2.setInt(1, appointmentStatus.getSelectionModel().getSelectedIndex()+1);
        preparedStatement2.setInt(2,EID);
        preparedStatement2.setInt(3,PID);
        preparedStatement2.execute();

        preparedStatement2.close();
        connection2.close();
        System.out.println(OLID);

        String groomingApptGenerate = "UPDATE VISIT SET VISIT_DESC=?, VISIT_CHECK_IN=?, VISIT_CHECK_OUT=?, PET_ID=? WHERE VISIT_ID ="+VID;
        Connection connection5 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement5 = connection5.prepareStatement(groomingApptGenerate);
        preparedStatement5.setString(1,specialCareInst.getText());
        preparedStatement5.setTimestamp(2, Timestamp.valueOf(startTime.getLocalDateTime()));
        preparedStatement5.setTimestamp(3,Timestamp.valueOf(endTime.getLocalDateTime()));
        preparedStatement5.setInt(4,PID);
        preparedStatement5.execute();

        preparedStatement5.close();
        connection5.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("Grooming Appt for "+p[1] +" updated");
        alert.setContentText("Grooming Appt for "+p[1] +" updated");
        alert.showAndWait();
        System.out.println(GID);
    }
}

