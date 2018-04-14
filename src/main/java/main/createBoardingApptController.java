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

public class createBoardingApptController {


    @FXML // fx:id = "petInfo"
    private ComboBox<String>petInfo;

    @FXML // fx:id = "customerInfo"
    private ComboBox<String>customerInfo;

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

    private int OID, OLID, CID, PID, KID, BID, EID, PWID;


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
        customerInfo.setItems(cus);
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


        customerInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                petFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void petFilter() throws SQLException {
        int i = customerInfo.getSelectionModel().getSelectedIndex()+1;
        String sql = "SELECT PET.PET_ID, PET_NAME, PET_WEIGHT_HIST.WEIGHT FROM PET  " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_OWNER ON PET.PET_ID = PET_OWNER.PET_ID " +
                "JOIN CUSTOMER ON PET_OWNER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID WHERE CUSTOMER.CUSTOMER_ID ="+i;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String>pets = FXCollections.observableArrayList();
        while(resultSet.next()){
            pets.add(resultSet.getInt("PET_ID") + "_"+resultSet.getString("PET_NAME")+"_"+resultSet.getInt("WEIGHT") +"_lbs");
        }
        petInfo.setItems(pets);
    }

    public void saveCommand() throws SQLException{
        //getting IDS from comboboxes
        String customerID = customerInfo.getValue();
        String kennelID = kennelSelect.getValue();
        String petID = petInfo.getValue();
        String employeeID = employeeSelect.getValue();
        String []c,k,p,e;
        //splitting id from rest of string
        c = customerID.split("_");
        k = kennelID.split("_");
        p = petID.split("_");
        e = employeeID.split("_");
        //converting strings to ints
        CID = Integer.parseInt(c[0]);
        KID = Integer.parseInt(k[0]);
        PID = Integer.parseInt(p[0]);
        EID = Integer.parseInt(e[0]);
        PWID = Integer.parseInt(p[2]);
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
        String orderLineGenerate = "INSERT INTO ORDER_LINE (ORDER_ID, LINE_NAME, ORDER_LINE_STATUS_ID, SERVICE_ID, QUANTITY, EMPLOYEE_ID) " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement1 = connection1.prepareStatement(orderLineGenerate, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement1.setInt(1, OID);
        preparedStatement1.setString(2,"Boarding appointment for:  " + p[1]);
        preparedStatement1.setInt(3,1);
        preparedStatement1.setInt(4,1);
        preparedStatement1.setInt(5,1);
        preparedStatement1.setInt(6,EID);
        preparedStatement1.execute();
        ResultSet resultSet1 = preparedStatement1.getGeneratedKeys();
        while (resultSet1.next()){
            OLID = resultSet1.getInt(1);
        }
        resultSet1.close();
        preparedStatement1.close();
        connection1.close();
        System.out.println(OLID);

        String sql = "INSERT INTO BOARDING_APPT(PET_ID, PET_WEIGHT_GRP_ID, BOARDING_APPT_DESC, BOARDING_APPT_CHECK_IN, BOARDING_APPT_CHECK_OUT, ORDER_LINE_ID)" +
                "VALUES (?,?,?,?,?,?)";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(sql);
//        preparedStatement2.execute(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement2.setInt(1,PID);
        preparedStatement2.setInt(2,petWeightGrpChecker(PWID));
        preparedStatement2.setString(3,specialCareInst.getText());
        preparedStatement2.setDate(4,Date.valueOf(arrivalDate.getValue()));
        preparedStatement2.setDate(5,Date.valueOf(departureDate.getValue()));
        preparedStatement2.setInt(6,OLID);
        preparedStatement2.execute();
//
//        ResultSet resultSet2 = preparedStatement2.getGeneratedKeys();
//        while (resultSet2.next()){
//            BID = resultSet2.getInt(1);
//        }
        preparedStatement2.close();
        connection2.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("BOARDING APPT ADDED");
        alert.setContentText("Appointment for " +p[1] +" created\nOwner Name: " + c[2]+ " "+ c[1]);
        alert.showAndWait();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/createBoardingScreen.fxml"));
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
