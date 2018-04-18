package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.sql.*;
import java.time.LocalDate;
import java.util.Random;

public class daycareCheckIn {

    @FXML //fx:id = "ownerSelect"
    private ComboBox<String>ownerSelect;

    @FXML //fx:id = "petSelect"
    private ComboBox<String>petSelect;

    @FXML //fx:id = "roomSelect"
    private ComboBox<String>roomSelect;

    @FXML // fx:id ="specialInstr"
    private TextArea specialInstr;

    private int CID, PID, VID, OID, OLID, DVID;
    private String petName;


    public void initialize()throws SQLException{
        String sql = "SELECT CUSTOMER_ID, CUS_LASTNAME, CUS_FIRSTNAME FROM CUSTOMER";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<String> cus = FXCollections.observableArrayList();
        while (rs.next()) {
            String string = rs.getInt("CUSTOMER_ID") + "_" + rs.getString("CUS_LASTNAME") + "_" + rs.getString("CUS_FIRSTNAME");
            cus.add(string);
        }
        ownerSelect.setItems(cus);
        preparedStatement.close();
        connection.close();
        rs.close();

        String sql1 = "SELECT DAYCARE_ROOM_NAME FROM DAYCARE_ROOM";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet rs1 = preparedStatement1.executeQuery();
        ObservableList<String>rooms = FXCollections.observableArrayList();
        while(rs1.next()){
            rooms.add(rs1.getString("DAYCARE_ROOM_NAME"));
        }
        roomSelect.setItems(rooms);

        ownerSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                petFilter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }
    private void petFilter() throws SQLException {
        String customrID = ownerSelect.getValue();
        String[]c;
        c = customrID.split("_");
        CID = Integer.parseInt(c[0]);
        String sql = "SELECT PET.PET_ID, PET.PET_NAME, PET_WEIGHT_HIST.WEIGHT FROM PET  " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_OWNER ON PET.PET_ID = PET_OWNER.PET_ID " +
                "JOIN CUSTOMER ON PET_OWNER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID WHERE CUSTOMER.CUSTOMER_ID ="+CID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String>pets = FXCollections.observableArrayList();
        while(resultSet.next()){
            pets.add(resultSet.getInt("PET_ID") + "_"+resultSet.getString("PET_NAME")+"_"+resultSet.getInt("WEIGHT") +"_lbs");
        }
        petSelect.setItems(pets);
    }

    public void generateOrder()throws SQLException{
        String customerID = ownerSelect.getValue();
        String[]c;
        c = customerID.split("_");
        CID = Integer.parseInt(c[0]);

        String petID = petSelect.getValue();
        String[]p;
        p = petID.split("_");
        PID = Integer.parseInt(p[0]);
        petName = p[1];

        String order = "INSERT INTO _ORDER(CUSTOMER_ID, ORDER_START, ORDER_STATUS_ID)" +
                " VALUES (?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(order, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,CID);
        preparedStatement.setDate(2, Date.valueOf(LocalDate.of(2018,04,26)));
        preparedStatement.setInt(3,1);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while (resultSet.next()){
            OID = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        generateOrderLine();
    }

    public void generateOrderLine()throws  SQLException{
        Random random = new Random();
        int i = random.nextInt(20)+1;
        String orderLine = "INSERT INTO ORDER_LINE(ORDER_ID, LINE_NAME, ORDER_LINE_STATUS_ID, SERVICE_ID, QUANTITY, EMPLOYEE_ID, PET_ID)" +
                " VALUES (?,?,?,?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(orderLine, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,OID);
        preparedStatement.setString(2,"DOGGY DAYCARE FOR "+petName);
        preparedStatement.setInt(3,1);
        preparedStatement.setInt(4,15);
        preparedStatement.setInt(5,1);
        preparedStatement.setInt(6,i);
        preparedStatement.setInt(7,PID);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()){
            OLID = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        generateVisit();
    }

    public void generateVisit()throws SQLException{
        String visit = "INSERT INTO VISIT(VISIT_DESC, VISIT_CHECK_IN, PET_ID, ORDER_LINE_ID)" +
                " VALUES (?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(visit,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,"DAYCARE VISIT FOR "+petName);
        preparedStatement.setDate(2,Date.valueOf(LocalDate.now()));
        preparedStatement.setInt(3,PID);
        preparedStatement.setInt(4,OLID);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()){
            VID = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        generateDaycareVisit();
    }

    public void generateDaycareVisit()throws SQLException{
        String daycareVisit = "INSERT INTO DAYCARE_VISIT(VISIT_ID, DAYCARE_ROOM_ID, DAYCARE_VISIT_DESC, DAYCARE_VISIT_START_DATE)" +
                " VALUES (?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(daycareVisit, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,VID);
        preparedStatement.setInt(2, roomSelect.getSelectionModel().getSelectedIndex()+1);
        preparedStatement.setString(3,specialInstr.getText());
        preparedStatement.setDate(4,Date.valueOf(LocalDate.of(2018,04,26)));
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while (resultSet.next()){
            DVID = resultSet.getInt(1);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("Daycare Visit "+DVID +" created");
        alert.setContentText("Daycare Visit for "+ petName);
        alert.showAndWait();

    }
}
