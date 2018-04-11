package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;
import java.time.LocalDateTime;


public class ViewGroomingAppointments {

    @FXML
    TableView<Visit>tblgrooming;
    @FXML
    TableColumn<Visit, Integer>groomingId;
    @FXML
    TableColumn<Visit, String>petName;
    @FXML
    TableColumn<Visit, String>petGender;
    @FXML
    TableColumn<Visit, String>petBreed;
    @FXML
    TableColumn<Visit, Date>checkIn;
    @FXML
    TableColumn<Visit, Date>checkOut;
    @FXML
    TableColumn<Visit, String>visitEmployeeFirst;
    @FXML
    TableColumn<Visit, String>visitEmployeeLast;
    @FXML
    TableColumn<Visit, String>status;

    public void initialize() throws SQLException{
        groomingId.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("visitId"));
        petName.setCellValueFactory(new PropertyValueFactory<Visit, String>("petName"));
        petGender.setCellValueFactory(new PropertyValueFactory<Visit, String>("petGender"));
        petBreed.setCellValueFactory(new PropertyValueFactory<Visit, String>("petBreed"));
        checkIn.setCellValueFactory(new PropertyValueFactory<Visit, Date>("checkIn"));
        checkOut.setCellValueFactory(new PropertyValueFactory<Visit,Date>("checkOut"));
        visitEmployeeFirst.setCellValueFactory(new PropertyValueFactory<Visit, String>("employeeFirstName"));
        visitEmployeeLast.setCellValueFactory(new PropertyValueFactory<Visit, String>("employeeLastName"));
        status.setCellValueFactory(new PropertyValueFactory<Visit, String>("serviceStatus"));
        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT VISIT.VISIT_ID, PET.PET_NAME, PET.PET_GENDER, BREED.BREED_NAME, VISIT.VISIT_CHECK_IN," +
                " VISIT.VISIT_CHECK_OUT, EMPLOYEE.EMPLOYEE_FIRSTNAME, EMPLOYEE.EMPLOYEE_LASTNAME, SERVICE_STATUS.SERVICE_STATUS FROM VISIT " +
                "JOIN PET ON PET.PET_ID = VISIT.PET_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN BREED ON PET_BREED.BREED_ID = BREED.BREED_ID " +
                "JOIN ORDER_LINE ON VISIT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID  " +
                "JOIN SERVICE_STATUS ON _SERVICE.SERVICE_STATUS_ID = SERVICE_STATUS.SERVICE_STATUS_ID " +
                "WHERE _SERVICE.SERVICE_ID = 1  AND SERVICE_STATUS.SERVICE_STATUS_ID = 1 ORDER BY VISIT.VISIT_CHECK_IN DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<Visit> visits = FXCollections.observableArrayList();

        while(rs.next()){
            final Visit v = new Visit();
            v.setVisitId(rs.getInt("VISIT_ID"));
            v.setPetName(rs.getString("PET_NAME"));
            v.setPetGender(rs.getString("PET_GENDER"));
            v.setPetBreed(rs.getString("BREED_NAME"));
            //v.setVisitCheckIn(rs.getDate("VISIT_CHECK_IN"));
           //v.setVisitCheckOut(rs.getTimestamp("VISIT_CHECK_OUT"));
            v.setEmployeeFirstName(rs.getString("EMPLOYEE_FIRSTNAME"));
            v.setEmployeeLastName(rs.getString("EMPLOYEE_LASTNAME"));
            v.setServiceStatus(rs.getString("SERVICE_STATUS"));
            visits.add(v);
        }
        tblgrooming.setItems(visits);
        preparedStatement.close();
        rs.close();
    }
}
