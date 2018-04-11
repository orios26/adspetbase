package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ManageDaycareRoomController {

    @FXML
    TableView<DaycareRoom> tbldaycareRoom;
    @FXML
    TableColumn<DaycareRoom, Integer>daycareRoomId;
    @FXML
    TableColumn<DaycareRoom, Integer>daycareRoomNum;
    @FXML
    TableColumn<DaycareRoom, String>daycareRoomName;
    @FXML
    TableColumn<DaycareRoom, String>daycareRoomSize;
    @FXML
    TableColumn<DaycareRoom, Date>createDate;

    public void initialize()throws SQLException{
        daycareRoomId.setCellValueFactory(new PropertyValueFactory<DaycareRoom, Integer>("daycareRoomId"));
        //daycareRoomNum.setCellValueFactory(new PropertyValueFactory<DaycareRoom, Integer>("DAYCARE_ROOM_NUM"));
        daycareRoomName.setCellValueFactory(new PropertyValueFactory<DaycareRoom, String>("daycareRoomName"));
        daycareRoomSize.setCellValueFactory(new PropertyValueFactory<DaycareRoom, String>("daycareRoomSize"));
        //createDate.setCellValueFactory(new PropertyValueFactory<DaycareRoom, Date>("daycareRoomCreateDate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT * FROM DAYCARE_ROOM";
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();

        ObservableList<DaycareRoom> rooms = FXCollections.observableArrayList();

        while(rs.next()){
            final DaycareRoom room = new DaycareRoom();
            room.setDaycareRoomId(rs.getInt("DAYCARE_ROOM_ID"));
            //room.setDaycareRoomNum(rs.getInt("DAYCARE_ROOM_NUM"));
            room.setDaycareRoomName(rs.getString("DAYCARE_ROOM_NAME"));
            room.setDaycareRoomSize(rs.getString("DAYCARE_ROOM_SIZE"));
            //room.setDaycareRoomCreateDate(rs.getDate("DAYCARE_ROOM_CREATE_DATE"));
            rooms.add(room);
        }
        tbldaycareRoom.setItems(rooms);
    }

}
