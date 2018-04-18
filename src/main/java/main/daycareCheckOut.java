package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.time.LocalDate;

public class daycareCheckOut {

    @FXML //fx:id ="daycareRoom"
    ComboBox<String>daycareRoom;

    @FXML //fx:id ="petSelect"
    ComboBox<String>petSelect;
    private int r;
    private String petName, roomName;
    private int VID, OLID, OID;

    public void initialize()throws SQLException{
        String room = "SELECT DAYCARE_ROOM_ID, DAYCARE_ROOM_NAME FROM DAYCARE_ROOM";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(room);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String> roomPopulate = FXCollections.observableArrayList();
        while (resultSet.next()){
            roomPopulate.add(resultSet.getString("DAYCARE_ROOM_ID")+"_"+resultSet.getString("DAYCARE_ROOM_NAME"));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        daycareRoom.setItems(roomPopulate);

        daycareRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                petFinder();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void petFinder()throws SQLException{
        String room = daycareRoom.getValue();
        String[]s;
        s = room.split("_");
        roomName = s[1];
        r = Integer.parseInt(s[0]);
        String petFinder = "SELECT PET.PET_ID, PET.PET_NAME FROM DAYCARE_VISIT " +
                "JOIN DAYCARE_ROOM ON DAYCARE_VISIT.DAYCARE_ROOM_ID = DAYCARE_ROOM.DAYCARE_ROOM_ID " +
                "JOIN VISIT ON DAYCARE_VISIT.VISIT_ID = VISIT.VISIT_ID " +
                "JOIN PET ON VISIT.PET_ID = PET.PET_ID WHERE DAYCARE_VISIT_START_DATE BETWEEN {ts '2018-04-26 00:00:00'} AND {ts '2018-04-26 23:59:59'} AND DAYCARE_ROOM.DAYCARE_ROOM_ID ="+r;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(petFinder);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<String> pets = FXCollections.observableArrayList();
        while (resultSet.next()){
            pets.add(resultSet.getInt("PET_ID")+"_"+resultSet.getString("PET_NAME"));
        }
        petSelect.setItems(pets);

    }

    public void checkOut()throws SQLException{
        String petId = petSelect.getValue();
        String[]p;
        p = petId.split("_");
        int PID = Integer.parseInt(p[0]);
        petName = p[1];

        String sql1 = "SELECT DAYCARE_VISIT.DAYCARE_VISIT_ID FROM DAYCARE_VISIT " +
                "JOIN DAYCARE_ROOM ON DAYCARE_VISIT.DAYCARE_ROOM_ID = DAYCARE_ROOM.DAYCARE_ROOM_ID " +
                "JOIN VISIT ON DAYCARE_VISIT.VISIT_ID = VISIT.VISIT_ID " +
                "JOIN PET ON VISIT.PET_ID = PET.PET_ID WHERE PET.PET_ID = "+PID+" AND DAYCARE_ROOM.DAYCARE_ROOM_ID = "+r;
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()){
            VID = resultSet.getInt("DAYCARE_VISIT_ID");
        }
        resultSet.close();
        preparedStatement1.close();
        connection1.close();


        String checkOut = "UPDATE DAYCARE_VISIT SET DAYCARE_VISIT_START_DATE=?, DAYCARE_VISIT_END_DATE=? WHERE DAYCARE_VISIT_ID ="+VID;
        LocalDate date = LocalDate.of(2012,01,01);
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(checkOut);
        preparedStatement.setNull(1, Types.DATE);
        preparedStatement.setDate(2,Date.valueOf(LocalDate.now()));
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        findOrderLine();
        findOrder();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("Daycare Check out");
        alert.setContentText(petName+ " has been checked out of "+roomName+ " room");
        alert.showAndWait();
    }

    public void findOrderLine() throws  SQLException{
        String orderLine = "SELECT ORDER_LINE.ORDER_LINE_ID FROM ORDER_LINE " +
                "JOIN VISIT ON ORDER_LINE.ORDER_LINE_ID = VISIT.ORDER_LINE_ID WHERE VISIT.VISIT_ID= "+VID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(orderLine);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            OLID = resultSet.getInt("ORDER_LINE_ID");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();


        String updateOrderLine = "UPDATE ORDER_LINE SET ORDER_LINE_STATUS_ID = ? WHERE ORDER_LINE_ID = "+OLID;
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(updateOrderLine);
        preparedStatement1.setInt(1,2);
        preparedStatement1.execute();
        preparedStatement1.close();
       connection.close();
    }

    public void findOrder() throws SQLException{
        String order = "SELECT _ORDER.ORDER_ID FROM _ORDER " +
                "JOIN ORDER_LINE ON _ORDER.ORDER_ID = ORDER_LINE.ORDER_ID WHERE ORDER_LINE_ID = "+OLID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(order);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            OID = resultSet.getInt("ORDER_ID");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();

        String updateOrder = "UPDATE _ORDER SET ORDER_STATUS_ID =? WHERE ORDER_ID ="+OID;
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(updateOrder);
        preparedStatement1.setInt(1,2);
        preparedStatement1.execute();
        preparedStatement1.close();
        connection1.close();


    }





}
