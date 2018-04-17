package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import jfxtras.scene.control.LocalDateTimePicker;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class editBoardingController {

    @FXML // fx:id = "petInfo"
    private ComboBox<String>petInfo;

    @FXML //fx:id ="boardingSelect"
    private ComboBox<String>boardingSelect;

    @FXML // fx:id = "arrivalDate"
    private DatePicker arrivalDate;

    @FXML // fx:id = "departureDate"
    private DatePicker departureDate;

    @FXML // fx:id = "kennelSelect"
    private ComboBox<String>kennelSelect;

    @FXML // fx:id = "employeeSelect"
    private ComboBox<String>employeeSelect;

    @FXML // fx:id = "specialCareInst"
    private TextArea specialCareInst;

    @FXML // fx:id = "saveButton"
    private Button saveButton;

    @FXML // fx:id = "clearButton"
    private Button clearButton;

    private int OID, OLID, CID, PID, KID, BID, EID, PWID, KRID;


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
        petInfo.setItems(pet);
        preparedStatement.close();
        connection.close();
        rs3.close();

        String sql1 = "SELECT KENNEL_ID, KENNEL_SIZE.KENNEL_SIZE FROM KENNEL" +
                " JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID " +
                "JOIN KENNEL_STATUS ON KENNEL.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID  " +
                "WHERE KENNEL_STATUS.KENNEL_STATUS = 'Open' ORDER BY KENNEL_SIZE DESC";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt1 = connection1.prepareStatement(sql1);
        ResultSet rs1 = psmt1.executeQuery();
        ObservableList<String>kennel = FXCollections.observableArrayList();
        while(rs1.next()){
            String s = rs1.getInt("KENNEL_ID") + "_" + rs1.getString("KENNEL_SIZE");
            kennel.add(s);
        }
        kennelSelect.setItems(kennel);
        rs1.close();
        psmt1.close();
        connection1.close();

        String sql2 = "SELECT EMPLOYEE_ID, EMPLOYEE_LASTNAME, EMPLOYEE_FIRSTNAME FROM EMPLOYEE";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(sql2);
        ResultSet resultSet1 = preparedStatement2.executeQuery();
        ObservableList<String> emp = FXCollections.observableArrayList();
        while(resultSet1.next()){
            emp.add(resultSet1.getInt("EMPLOYEE_ID")+"_"+resultSet1.getString("EMPLOYEE_LASTNAME")+"_"+resultSet1.getString("EMPLOYEE_FIRSTNAME"));
        }
        employeeSelect.setItems(emp);
        kennelSelect.setDisable(true);
        employeeSelect.setDisable(true);
        arrivalDate.setDisable(true);
        departureDate.setDisable(true);


        petInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                apptFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        boardingSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadDetails();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadDetails()throws SQLException{
        String boardingID = boardingSelect.getValue();
        BID = Integer.parseInt(boardingID);

        String sql = "SELECT BOARDING_APPT.BOARDING_APPT_CHECK_IN, BOARDING_APPT.BOARDING_APPT_CHECK_OUT, BOARDING_APPT.BOARDING_APPT_DESC, ORDER_LINE.EMPLOYEE_ID, EMPLOYEE.EMPLOYEE_LASTNAME, EMPLOYEE.EMPLOYEE_FIRSTNAME, KENNEL.KENNEL_ID, KENNEL_SIZE FROM BOARDING_APPT " +
                "JOIN ORDER_LINE ON BOARDING_APPT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID " +
                "JOIN KENNEL_RESERVATION ON BOARDING_APPT.BOARDING_APPT_ID = KENNEL_RESERVATION.BOARDING_APPT_ID " +
                "JOIN KENNEL ON KENNEL_RESERVATION.KENNEL_ID = KENNEL.KENNEL_ID " +
                "JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID WHERE BOARDING_APPT.BOARDING_APPT_ID ="+BID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            arrivalDate.setValue(resultSet.getDate("BOARDING_APPT_CHECK_IN").toLocalDate());
            departureDate.setValue(resultSet.getDate("BOARDING_APPT_CHECK_OUT").toLocalDate());
            specialCareInst.setText(resultSet.getString("BOARDING_APPT_DESC"));
            kennelSelect.setValue(resultSet.getString("KENNEL_ID")+"_"+resultSet.getString("KENNEL_SIZE"));
            employeeSelect.setValue(resultSet.getString("EMPLOYEE_ID")+"_"+resultSet.getString("EMPLOYEE_LASTNAME")+"_"+resultSet.getString("EMPLOYEE_FIRSTNAME"));
        }
    }

    private void apptFilter() throws SQLException {
        String petID = petInfo.getValue();
        String[]p;
        p = petID.split("_");
        PID = Integer.parseInt(p[0]);
        String sql = "SELECT BOARDING_APPT.BOARDING_APPT_ID FROM BOARDING_APPT " +
                "JOIN PET ON BOARDING_APPT.PET_ID = PET.PET_ID WHERE PET.PET_ID ="+PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String>appt = FXCollections.observableArrayList();
        while(resultSet.next()){
            appt.add(resultSet.getString("BOARDING_APPT_ID"));
        }
        boardingSelect.setItems(appt);
        kennelSelect.setDisable(false);
        employeeSelect.setDisable(false);
        arrivalDate.setDisable(false);
        departureDate.setDisable(false);
    }

    public void saveCommand() throws SQLException{
        //getting IDS from comboboxes

        String kennelID = kennelSelect.getValue();
        String petID = petInfo.getValue();
        String employeeID = employeeSelect.getValue();
        String []c,k,p,e;
        //splitting id from rest of string

        k = kennelID.split("_");
        p = petID.split("_");
        e = employeeID.split("_");
        //converting strings to ints

        KID = Integer.parseInt(k[0]);
        PID = Integer.parseInt(p[0]);
        EID = Integer.parseInt(e[0]);
        PWID = Integer.parseInt(p[2]);
        LocalDate date = LocalDate.now();

//        Connection connection = DbHelper.getInstance().getConnection();
//        String orderGenerate = "UPDATE  _ORDER " +
//                "SET ORDER_START =?, ORDER_END =?";
//        PreparedStatement preparedStatement = connection.prepareStatement(orderGenerate, PreparedStatement.RETURN_GENERATED_KEYS);
//        preparedStatement.setInt(1,CID);
//        preparedStatement.setDate(2, java.sql.Date.valueOf(date));
//        preparedStatement.setDate(3, java.sql.Date.valueOf(date));
//        preparedStatement.setInt(4,1);
//        preparedStatement.execute();
//
//        ResultSet resultSet = preparedStatement.getGeneratedKeys();
//        while(resultSet.next()){
//            OID = resultSet.getInt(1);
//            System.out.println(OID);
//        }
//        resultSet.close();
//        connection.close();
//        preparedStatement.close();
        Connection connection = DbHelper.getInstance().getConnection();
        String findOrderId = "SELECT ORDER_LINE.ORDER_LINE_ID FROM ORDER_LINE " +
                "JOIN BOARDING_APPT ON BOARDING_APPT.ORDER_LINE_ID = ORDER_LINE.ORDER_LINE_ID WHERE BOARDING_APPT_ID =" +BID;
        PreparedStatement preparedStatement = connection.prepareStatement(findOrderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            OLID = resultSet.getInt("ORDER_LINE_ID");
        }

        Connection connection1 = DbHelper.getInstance().getConnection();
        String orderLineGenerate = "UPDATE ORDER_LINE SET EMPLOYEE_ID = ? WHERE ORDER_LINE_ID =" +OLID;
        PreparedStatement preparedStatement1 = connection1.prepareStatement(orderLineGenerate);
        preparedStatement1.setInt(1,EID);
        preparedStatement1.execute();
        preparedStatement1.close();
        connection1.close();
        System.out.println(OLID);

        String sql = "UPDATE BOARDING_APPT SET PET_ID = ?, PET_WEIGHT_GRP_ID = ?, BOARDING_APPT_DESC = ?, BOARDING_APPT_CHECK_IN = ?, BOARDING_APPT_CHECK_OUT = ? WHERE BOARDING_APPT_ID = "+BID;
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(sql);
//        preparedStatement2.execute(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement2.setInt(1,PID);
        preparedStatement2.setInt(2,petWeightGrpChecker(PWID));
        preparedStatement2.setString(3,specialCareInst.getText());
        preparedStatement2.setDate(4,Date.valueOf(arrivalDate.getValue()));
        preparedStatement2.setDate(5,Date.valueOf(departureDate.getValue()));
        preparedStatement2.execute();

        preparedStatement2.close();
        connection2.close();

        Connection connection4 = DbHelper.getInstance().getConnection();
        String findKennelID = "SELECT KENNEL_RESERVATION.KENNEL_RESERVATION_ID FROM KENNEL_RESERVATION " +
                "JOIN BOARDING_APPT ON KENNEL_RESERVATION.BOARDING_APPT_ID = BOARDING_APPT.BOARDING_APPT_ID WHERE BOARDING_APPT.BOARDING_APPT_ID ="+BID;
        PreparedStatement psmt = connection4.prepareStatement(findKennelID);
        ResultSet rs = psmt.executeQuery();
        while(rs.next()){
            KRID = rs.getInt("KENNEL_RESERVATION_ID");
        }
        rs.close();
        psmt.close();
        connection4.close();

        Connection connection3 = DbHelper.getInstance().getConnection();
        String kennelreserve = "UPDATE KENNEL_RESERVATION SET KENNEL_ID = ?, KENNEL_RSVP_START_DATE =?, KENNEL_RSVP_END_DATE =? WHERE KENNEL_RESERVATION_ID= "+KRID ;
        PreparedStatement preparedStatement3 = connection3.prepareStatement(kennelreserve);
        preparedStatement3.setInt(1,KID);
        preparedStatement3.setDate(2,Date.valueOf(arrivalDate.getValue()));
        preparedStatement3.setDate(3,Date.valueOf(departureDate.getValue()));
        preparedStatement3.execute();
        preparedStatement3.close();
        connection3.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("BOARDING APPT UPDATED");
        alert.setContentText("Appointment for " +p[1] +" updated");
        alert.showAndWait();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editBoarding.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public int petWeightGrpChecker(int i){
        if( i >= 0 && i <= 20 ){return 1;}
        if(i >= 21 && i <= 45){return 2;}
        if(i >= 46 && i <= 59){return 3;}
        if(i >= 60 && i <= 99){return 4;}
        else {return 5;}
    }

}
