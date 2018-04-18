package main;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.text.DecimalFormat;

public class createServiceOrderController {

    private int OID, OLID, CID, PID, EID, SID, PWID, KID, BID;
    private String petName;
    private String serviceName;

    @FXML
    TableView<OrderLine>ordertbl;
    @FXML
    TableColumn<OrderLine, Integer>orderId;
    @FXML
    TableColumn<OrderLine, Integer>orderLineId;
    @FXML
    TableColumn<OrderLine, String>lineName;
    @FXML
    TableColumn<OrderLine, String>service;
    @FXML
    TableColumn<OrderLine, String>pet;

    @FXML
    TableColumn<OrderLine, String>employee;

    @FXML
    TableColumn<OrderLine, String>price;

    @FXML
    TableColumn<OrderLine, Date>startDate;

    @FXML
    TableColumn<OrderLine, Date>endDate;

    @FXML // fx:id = "customerInfo"
    private ComboBox<String>customerInfo;

    @FXML // fx:id = "petInfo"
    private ComboBox<String>petInfo;

    @FXML //fx:id ="employeeInfo"
    private ComboBox<String>employeeInfo;

    @FXML //fx:id ="serviceSelect"
    private ComboBox<String>serviceSelect;

    @FXML //fx:id ="kennelSelect"
    private ComboBox<String>kennelSelect;

    @FXML //fx:id ="addLine"
    private Button addLine;

    @FXML //fx:id ="generateButton"
    private Button generateButoon;

    @FXML // fx:id ="serviceLineName"
    private TextField serviceLineName;

    @FXML //fx:id = "startDatePicker"
    private DatePicker startDatePicker;

    @FXML //fx:id = "endDatePicker"
    private DatePicker endDatePicker;

    @FXML //fx:id = "estimatedTotal"
    private TextField estimatedTotal;


    public void initialize()throws SQLException{
        orderId.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("orderId"));
        orderLineId.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("orderLineId"));
        pet.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("petName"));
        service.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("serviceName"));
        employee.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("employeeLastname"));
        price.setCellValueFactory(new PropertyValueFactory<OrderLine, String>("priceString"));
        startDate.setCellValueFactory(new PropertyValueFactory<OrderLine, Date>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<OrderLine, Date>("endDate"));
        addLine.setDisable(true);
        serviceSelect.setDisable(true);
        petInfo.setDisable(true);
        employeeInfo.setDisable(true);
        kennelSelect.setDisable(true);
        estimatedTotal.setEditable(false);

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

        String employee = "SELECT EMPLOYEE_ID, EMPLOYEE_LASTNAME, EMPLOYEE_FIRSTNAME FROM EMPLOYEE";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(employee);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<String> emp = FXCollections.observableArrayList();
        while(resultSet1.next()){
            emp.add(resultSet1.getInt("EMPLOYEE_ID")+"_"+resultSet1.getString("EMPLOYEE_LASTNAME")+"_"+resultSet1.getString("EMPLOYEE_FIRSTNAME"));
        }
        employeeInfo.setItems(emp);
        resultSet1.close();
        preparedStatement1.close();
        connection1.close();

        String service = "SELECT SERVICE_ID, SERVICE_NAME FROM _SERVICE " +
                "JOIN SERVICE_STATUS ON _SERVICE.SERVICE_STATUS_ID = SERVICE_STATUS.SERVICE_STATUS_ID WHERE _SERVICE.SERVICE_STATUS_ID = 1 ";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(service);
        ResultSet resultSet = preparedStatement2.executeQuery();
        ObservableList<String>serviceList = FXCollections.observableArrayList();
        while(resultSet.next()){
            serviceList.add(resultSet.getString("SERVICE_ID") + "_"+ resultSet.getString("SERVICE_NAME"));
        }
        serviceSelect.setItems(serviceList);
        connection2.close();
        preparedStatement2.close();
        connection2.close();

        String sql1 = "SELECT KENNEL_ID, KENNEL_SIZE.KENNEL_SIZE FROM KENNEL" +
                " JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID " +
                "JOIN KENNEL_STATUS ON KENNEL.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID  " +
                "WHERE KENNEL_STATUS.KENNEL_STATUS = 'Open' ORDER BY KENNEL_SIZE DESC";
        Connection connection3 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt1 = connection3.prepareStatement(sql1);
        ResultSet rs1 = psmt1.executeQuery();
        ObservableList<String>kennel = FXCollections.observableArrayList();
        while(rs1.next()){
            String s = rs1.getInt("KENNEL_ID") + "_" + rs1.getString("KENNEL_SIZE");
            kennel.add(s);
        }
        kennelSelect.setItems(kennel);
        rs1.close();
        psmt1.close();
        connection3.close();

        customerInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                petFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        serviceSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        kennelCombo());
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
        ObservableList<String> pets = FXCollections.observableArrayList();
        while(resultSet.next()){
            pets.add(resultSet.getInt("PET_ID") + "_"+resultSet.getString("PET_NAME")+"_"+resultSet.getInt("WEIGHT") +"_lbs");
        }
        petInfo.setItems(pets);
    }
    private void kennelCombo(){
        String serviceID = serviceSelect.getValue();
        String s[];
        s = serviceID.split("_");
        SID = Integer.parseInt(s[0]);
        serviceName = s[1];

        if(SID >=28 && SID <=32){
            kennelSelect.setDisable(false);
        }

    }

    public void GenerateOrder()throws SQLException{
        String customerID = customerInfo.getValue();
        String[]c;
        c = customerID.split("_");
        CID = Integer.parseInt(c[0]);
        try {
            Connection connection = DbHelper.getInstance().getConnection();
            String generateOrder = "INSERT INTO _ORDER(CUSTOMER_ID, ORDER_START, ORDER_END, ORDER_STATUS_ID) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(generateOrder, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, CID);
            preparedStatement.setDate(2, Date.valueOf(startDatePicker.getValue()));
            preparedStatement.setDate(3, Date.valueOf(endDatePicker.getValue()));
            preparedStatement.setInt(4, 1);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                OID = resultSet.getInt(1);
                System.out.println(OID);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase Update");
            alert.setHeaderText("Service Order Created");
            alert.setContentText("Order for " +c[2] + ", " +c[1]+" created");
            alert.showAndWait();
            addLine.setDisable(false);
            serviceSelect.setDisable(false);
            petInfo.setDisable(false);
            employeeInfo.setDisable(false);

        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase ERROR");
            alert.setHeaderText("Error Creating Service Order");
            alert.setContentText("Please Validate Information Entered");
            alert.showAndWait();
        }
    }
    public void generateOrderLine()throws SQLException{
        //String serviceID = serviceSelect.getValue();
        String employeeID = employeeInfo.getValue();
        String petID = petInfo.getValue();
        String[]s,e,p;
        e = employeeID.split("_");
        //s = serviceID.split("_");
        p = petID.split("_");
        //SID = Integer.parseInt(s[0]);
        EID = Integer.parseInt(e[0]);
        PID = Integer.parseInt(p[0]);
        PWID = Integer.parseInt(p[2]);
        petName = p[1];

        try{
        String generateOrderLine = "INSERT INTO ORDER_LINE (ORDER_ID, LINE_NAME, ORDER_LINE_STATUS_ID, SERVICE_ID, QUANTITY, EMPLOYEE_ID, PET_ID)" +
                " VALUES (?,?,?,?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(generateOrderLine, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,OID);
        preparedStatement.setString(2,serviceName);
        preparedStatement.setInt(3,1);
        preparedStatement.setInt(4,SID);
        preparedStatement.setInt(5,1);
        preparedStatement.setInt(6,EID);
        preparedStatement.setInt(7,PID);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while (resultSet.next()){
            OLID = resultSet.getInt(1);
            System.out.println(OLID);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        if(SID >=28 && SID <=32){
            generateBoaarding();
            generateKennelReservation();
        }
        tablePopulate();
        kennelSelect.setDisable(true);}catch (SQLException sql){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase ERROR");
            alert.setHeaderText("Error Creating Service OrderLine");
            alert.setContentText("Please Validate Information Entered");
            alert.showAndWait();

        }
  }

    public void generateBoaarding() throws SQLException{
        String kennelID = kennelSelect.getValue();
        String[]k;
        k = kennelID.split("_");
        KID = Integer.parseInt(k[0]);

        try{
        String generateBoarding = "INSERT INTO BOARDING_APPT (PET_ID, PET_WEIGHT_GRP_ID,BOARDING_APPT_DESC, BOARDING_APPT_CHECK_IN, BOARDING_APPT_CHECK_OUT, ORDER_LINE_ID)" +
                " VALUES (?,?,?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(generateBoarding,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,PID);
        preparedStatement.setInt(2,petWeightGrpChecker(PWID));
        preparedStatement.setString(3,"Boarding for " +petName);
        preparedStatement.setDate(4,Date.valueOf(startDatePicker.getValue()));
        preparedStatement.setDate(5,Date.valueOf(endDatePicker.getValue()));
        preparedStatement.setInt(6,OLID);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()){
            BID = resultSet.getInt(1);
            System.out.println(BID);
        }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase ERROR");
            alert.setHeaderText("Error Creating Service OrderLine");
            alert.setContentText("Please Validate Information Entered");
            alert.showAndWait();
        }
  }

  public void generateKennelReservation()throws SQLException{
        try {
            String kennelReservation = "INSERT INTO KENNEL_RESERVATION(BOARDING_APPT_ID,KENNEL_ID,KENNEL_RSVP_DESC,KENNEL_RSVP_START_DATE,KENNEL_RSVP_END_DATE) VALUES (?,?,?,?,?)";
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(kennelReservation, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, BID);
            preparedStatement.setInt(2, KID);
            preparedStatement.setString(3, "Boarding for " + petName);
            preparedStatement.setDate(4, Date.valueOf(startDatePicker.getValue()));
            preparedStatement.setDate(5, Date.valueOf(endDatePicker.getValue()));
            preparedStatement.execute();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase ERROR");
            alert.setHeaderText("Error Creating Service OrderLine");
            alert.setContentText("Please Validate Information Entered");
            alert.showAndWait();
        }
  }

    public int petWeightGrpChecker(int i){
        if( i >= 0 && i <= 20 ){return 1;}
        if(i >= 21 && i <= 45){return 2;}
        if(i >= 46 && i <= 59){return 3;}
        if(i >= 60 && i <= 99){return 4;}
        else {return 5;}
    }

    public void tablePopulate()throws SQLException{
        String populate = "SELECT ORDER_LINE.ORDER_ID, ORDER_LINE.ORDER_LINE_ID, PET.PET_NAME,_SERVICE.SERVICE_NAME, EMPLOYEE.EMPLOYEE_LASTNAME, _SERVICE.SERVICE_PRICE, _ORDER.ORDER_START, _ORDER.ORDER_END FROM ORDER_LINE " +
                "JOIN _ORDER ON ORDER_LINE.ORDER_ID = _ORDER.ORDER_ID " +
                "JOIN _SERVICE ON ORDER_LINE.SERVICE_ID = _SERVICE.SERVICE_ID " +
                "JOIN PET ON ORDER_LINE.PET_ID = PET.PET_ID " +
                "JOIN EMPLOYEE ON ORDER_LINE.EMPLOYEE_ID = EMPLOYEE.EMPLOYEE_ID WHERE _ORDER.CUSTOMER_ID ="+CID+" AND _ORDER.ORDER_ID = " + OID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(populate);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<OrderLine>orders = FXCollections.observableArrayList();
        double estimatedPrice = 0;
        while(resultSet.next()){
            OrderLine order = new OrderLine();
            DecimalFormat formatter = new DecimalFormat("#0.00");
            order.setOrderId(resultSet.getInt("ORDER_ID"));
            order.setOrderLineId(resultSet.getInt("ORDER_LINE_ID"));
            order.setPetName(resultSet.getString("PET_NAME"));
            order.setServiceName(resultSet.getString("SERVICE_NAME"));
            order.setEmployeeLastname(resultSet.getString("EMPLOYEE_LASTNAME"));
            order.setPrice(resultSet.getDouble("SERVICE_PRICE"));
            order.setPriceString(String.format("$ "+"%.2f",resultSet.getDouble("SERVICE_PRICE")));
            order.setStartDate(resultSet.getDate("ORDER_START"));
            order.setEndDate(resultSet.getDate("ORDER_END"));
            estimatedPrice+=order.getPrice();
            orders.add(order);
        }
        ordertbl.setItems(orders);
        estimatedTotal.setText("$ " + String.format("%.2f",estimatedPrice));
    }
}
