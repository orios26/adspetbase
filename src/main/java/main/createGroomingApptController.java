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

public class createGroomingApptController {


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

    private int CID, OLID, OID, PID, EID, GID;


    public void initialize() throws SQLException{
        String sql = "SELECT CUSTOMER_ID, CUS_LASTNAME, CUS_FIRSTNAME FROM CUSTOMER";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs3 = preparedStatement.executeQuery();
        ObservableList<String> cus = FXCollections.observableArrayList();
        while (rs3.next()) {
            String string = rs3.getInt("CUSTOMER_ID") + "_" + rs3.getString("CUS_LASTNAME") + "_" + rs3.getString("CUS_FIRSTNAME");
            cus.add(string);
        }
        customerSelect.setItems(cus);
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

        customerSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                petFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void petFilter() throws SQLException {
        String customerID = customerSelect.getValue();
        String []c;
        c = customerID.split("_");
        CID = Integer.parseInt(c[0]);
        String sql = "SELECT PET.PET_ID, PET.PET_NAME FROM PET " +
                "JOIN PET_OWNER ON PET.PET_ID = PET_OWNER.PET_ID " +
                "JOIN CUSTOMER ON PET_OWNER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID WHERE CUSTOMER.CUSTOMER_ID ="+CID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String>pets = FXCollections.observableArrayList();
        while(resultSet.next()){
            pets.add(resultSet.getInt("PET_ID") + "_"+resultSet.getString("PET_NAME"));
        }
        petSelect.setItems(pets);
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

        Connection connection = DbHelper.getInstance().getConnection();
        String orderGenerate = "INSERT INTO _ORDER (CUSTOMER_ID, ORDER_START, ORDER_END, ORDER_STATUS_ID) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(orderGenerate, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,CID);
        preparedStatement.setDate(2, java.sql.Date.valueOf(date));
        preparedStatement.setDate(3, java.sql.Date.valueOf(date));
        preparedStatement.setInt(4,1);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()){
            OID = resultSet.getInt(1);
            System.out.println(OID);
        }
        resultSet.close();
        connection.close();
        preparedStatement.close();

        Connection connection1 = DbHelper.getInstance().getConnection();
        String orderLineGenerate = "INSERT INTO ORDER_LINE (ORDER_ID, LINE_NAME, ORDER_LINE_STATUS_ID, SERVICE_ID, QUANTITY, EMPLOYEE_ID, PET_ID) " +
                "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection1.prepareStatement(orderLineGenerate, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement1.setInt(1, OID);
        preparedStatement1.setString(2,"Grooming appt for:  " + p[1]);
        preparedStatement1.setInt(3,1);
        preparedStatement1.setInt(4,1);
        preparedStatement1.setInt(5,1);
        preparedStatement1.setInt(6,EID);
        preparedStatement1.setInt(7,PID);
        preparedStatement1.execute();
        ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
        while (resultSet1.next()){
            OLID = resultSet1.getInt(1);
        }
        resultSet1.close();
        preparedStatement1.close();
        connection1.close();
        System.out.println(OLID);

        String groomingApptGenerate = "INSERT INTO VISIT(VISIT_DESC, VISIT_CHECK_IN, VISIT_CHECK_OUT, PET_ID, ORDER_LINE_ID) VALUES " +
                "(?,?,?,?,?)";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(groomingApptGenerate, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement2.setString(1,specialCareInst.getText());
        preparedStatement2.setTimestamp(2, Timestamp.valueOf(startTime.getLocalDateTime()));
        preparedStatement2.setTimestamp(3,Timestamp.valueOf(endTime.getLocalDateTime()));
        preparedStatement2.setInt(4,PID);
        preparedStatement2.setInt(5,OLID);
        preparedStatement2.executeUpdate();
        ResultSet resultSet2 = preparedStatement2.getGeneratedKeys();
        while (resultSet2.next()){
            GID = resultSet2.getInt(1);
        }
        resultSet2.close();
        preparedStatement2.close();
        connection2.close();
        System.out.println(GID);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("Grooming Appt for "+p[1] +" created");
        alert.setContentText("Grooming Appt for "+p[1] +" created");
    }
}

