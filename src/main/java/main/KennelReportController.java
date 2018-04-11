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

public class KennelReportController {


    @FXML
    TableView<Kennel>openKennels;
    TableColumn<Kennel, Integer>openKennelId;
    TableColumn<Kennel, String>openKennelStatus;
    TableColumn<Kennel, String>openKennelSize;

    @FXML
    TableView<Kennel>dirtyKennels;
    TableColumn<Kennel, Integer>dirtyKennelId;
    TableColumn<Kennel, String>dirtyKennelStatus;
    TableColumn<Kennel, String>dirtyKennelSize;

    public void initialize() throws SQLException{
        openKennelId.setCellValueFactory(new PropertyValueFactory<Kennel, Integer>("kennelId"));
        openKennelStatus.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelStatus"));
        openKennelSize.setCellValueFactory(new PropertyValueFactory<Kennel, String >("kennelSize"));

        dirtyKennelId.setCellValueFactory(new PropertyValueFactory<Kennel, Integer>("kennelId"));
        dirtyKennelStatus.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelStatus"));
        dirtyKennelSize.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelSize"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT KENNEL.KENNEL_ID, KENNEL_STATUS.KENNEL_STATUS, KENNEL_SIZE.KENNEL_SIZE FROM KENNEL\n" +
                "JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID\n" +
                "JOIN KENNEL_STATUS ON KENNEL.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID WHERE KENNEL_STATUS = 'Open' ORDER BY KENNEL_SIZE DESC;";
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<Kennel> openKennelList = FXCollections.observableArrayList();

        while (rs.next()){
            final Kennel kclean = new Kennel();
            kclean.setKennelId(rs.getInt("KENNEL_ID"));
            kclean.setKennelStatus(rs.getString("KENNEL_STATUS"));
            kclean.setKennelSize(rs.getString("KENNEL_SIZE"));
            openKennelList.add(kclean);
        }
    }
}
