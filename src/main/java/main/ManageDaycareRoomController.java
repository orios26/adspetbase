package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ManageDaycareRoomController {

    @FXML
    TableView<DaycareRoom> tbldaycareRoom;
    @FXML
    TableColumn<DaycareRoom, Integer>daycareRoomId;
    @FXML
    TableColumn<DaycareRoom, String>daycareRoomName;
    @FXML
    TableColumn<DaycareRoom, String>daycareRoomSize;

    @FXML //fx:id = "roomName"
    private TextField roomName;

    @FXML // fx:id = "roomSize"
    private TextField roomSize;

    @FXML // fx:id ="dateCreate"
    private DatePicker dateCreate;

    @FXML // fx:id ="save"
    private Button save;

    @FXML //fx:id ="edit"
    private Button edit;

    private boolean editable = false;
    private int id = 0;

    public void initialize()throws SQLException{
        daycareRoomId.setCellValueFactory(new PropertyValueFactory<DaycareRoom, Integer>("daycareRoomId"));
        daycareRoomName.setCellValueFactory(new PropertyValueFactory<DaycareRoom, String>("daycareRoomName"));
        daycareRoomSize.setCellValueFactory(new PropertyValueFactory<DaycareRoom, String>("daycareRoomSize"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT * FROM DAYCARE_ROOM";
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();

        ObservableList<DaycareRoom> rooms = FXCollections.observableArrayList();

        while(rs.next()){
            final DaycareRoom room = new DaycareRoom();
            room.setDaycareRoomId(rs.getInt("DAYCARE_ROOM_ID"));
            room.setDaycareRoomName(rs.getString("DAYCARE_ROOM_NAME"));
            room.setDaycareRoomSize(rs.getString("DAYCARE_ROOM_SIZE"));
            rooms.add(room);
        }
        tbldaycareRoom.setItems(rooms);
    }

    public void editButtonPressed()throws SQLException{
        id = tbldaycareRoom.getSelectionModel().getSelectedIndex()+1;
        String sql = "SELECT DAYCARE_ROOM_NAME, DAYCARE_ROOM_SIZE, DAYCARE_ROOM_CREATE_DATE FROM DAYCARE_ROOM";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        while(rs.next()){
            roomName.setText(rs.getString("DAYCARE_ROOM_NAME"));
            roomSize.setText(rs.getString("DAYCARE_ROOM_SIZE"));
            dateCreate.setValue(rs.getDate("DAYCARE_ROOM_CREATE_DATE").toLocalDate());
            editable=true;
        }
        connection.close();
        psmt.close();
        rs.close();
    }

    public void saveButtonPressed()throws SQLException{
        if (editable=true){
            Connection connection = DbHelper.getInstance().getConnection();
            String sql = "UPDATE DAYCARE_ROOM SET DAYCARE_ROOM_NAME = ?, DAYCARE_ROOM_SIZE = ?, DAYCARE_ROOM_CREATE_DATE = ? WHERE DAYCARE_ROOM_ID ="+id;
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1,roomName.getText());
            psmt.setString(2,roomSize.getText());
            psmt.setDate(3,Date.valueOf(dateCreate.getValue()));
            psmt.execute();
            psmt.close();
            connection.close();

        }else {
            Connection connection = DbHelper.getInstance().getConnection();
            String sql = "INSERT INTO DAYCARE_ROOM (DAYCARE_ROOM_NAME, DAYCARE_ROOM_SIZE, DAYCARE_ROOM_CREATE_DATE) VALUES (?,?,?)";
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1,roomName.getText());
            psmt.setString(2,roomSize.getText());
            psmt.setDate(3,Date.valueOf(dateCreate.getValue()));
            psmt.execute();
            psmt.close();
            connection.close();
        }
        initialize();
    }

}
