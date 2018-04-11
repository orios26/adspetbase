package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageKennelController {

    @FXML
    TableView<Kennel> tblkennel;
    @FXML
    TableColumn<Kennel, Integer> kennelId;
//    @FXML
//    TableColumn<Kennel, String> kennelName;
    @FXML
    TableColumn<Kennel, String> kennelStatus;
    @FXML
    TableColumn<Kennel, String> kennelSize;

    public void initialize()throws SQLException{
        kennelId.setCellValueFactory(new PropertyValueFactory<Kennel, Integer>("kennelId"));
        //kennelName.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelName"));
        kennelStatus.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelStatus"));
        kennelSize.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelSize"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT KENNEL.KENNEL_ID, KENNEL_STATUS.KENNEL_STATUS," +
                "KENNEL_SIZE.KENNEL_SIZE" +
                " FROM KENNEL " +
                "JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID" +
                " JOIN KENNEL_STATUS ON KENNEL.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID";
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();

        ObservableList<Kennel> kennelList = FXCollections.observableArrayList();

        while (rs.next()){
          final Kennel kennelrs = new Kennel();
          kennelrs.setKennelId(rs.getInt("KENNEL_ID"));
          //kennelrs.setKennelName(rs.getString("KENNEL_NAME"));
          kennelrs.setKennelStatus(rs.getString("KENNEL_STATUS"));
          kennelrs.setKennelSize(rs.getString("KENNEL_SIZE"));
          kennelList.add(kennelrs);
        }
       tblkennel.setItems(kennelList);
    }
}
