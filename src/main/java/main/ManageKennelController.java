package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    @FXML
    TableColumn<Kennel, String> kennelStatus;
    @FXML
    TableColumn<Kennel, String> kennelSize;

    @FXML //fx:id = "edit"
    private Button edit;

    @FXML //fx:id ="save"
    private Button save;

    @FXML // fx:id ="clear"
    private Button clear;

    @FXML // fx:id = "kennelSizeCombo"
    private ComboBox<String>kennelSizeCombo;

    @FXML // fx:id = "kennelStatusCombo"
    private ComboBox<String>kennelStatusCombo;

    private Boolean editable;
    private int id =0;

    public void initialize()throws SQLException{
        kennelId.setCellValueFactory(new PropertyValueFactory<Kennel, Integer>("kennelId"));
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
        kennelList.removeAll();

        String sql1 = "SELECT KENNEL_SIZE FROM KENNEL_SIZE";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt1 = connection.prepareStatement(sql1);
        ResultSet rs1 = psmt1.executeQuery();
        ObservableList<String> sizes = FXCollections.observableArrayList();
        while(rs1.next()){
            sizes.add(rs1.getString("KENNEL_SIZE"));
        }
        kennelSizeCombo.setItems(sizes);

        String sql2 = "SELECT KENNEL_STATUS FROM KENNEL_STATUS";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt2 = connection2.prepareStatement(sql2);
        ResultSet rs2 = psmt2.executeQuery();
        ObservableList<String> statuses = FXCollections.observableArrayList();
        while(rs2.next()){
            statuses.add(rs2.getString("KENNEL_STATUS"));
        }
        kennelStatusCombo.setItems(statuses);
    }

    public void editPressed()throws SQLException{
        id = tblkennel.getSelectionModel().getSelectedIndex()+1;
        String sql = "SELECT KENNEL_STATUS.KENNEL_STATUS, KENNEL_SIZE.KENNEL_SIZE FROM KENNEL  " +
                "JOIN KENNEL_SIZE ON KENNEL.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID " +
                "JOIN KENNEL_STATUS ON KENNEL.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID WHERE KENNEL_ID = "+id;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            kennelStatusCombo.setValue(resultSet.getString("KENNEL_STATUS"));
            kennelSizeCombo.setValue(resultSet.getString("KENNEL_SIZE"));
        }
        editable = true;
    }

    public void savePressed()throws SQLException{
        if(editable == true){
            String sql = "UPDATE KENNEL SET KENNEL_STATUS_ID = ?, KENNEL_SIZE_ID = ? WHERE KENNEL_ID =" + id;
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1,kennelStatusCombo.getSelectionModel().getSelectedIndex()+1);
            p.setInt(2,kennelSizeCombo.getSelectionModel().getSelectedIndex()+1);
            p.execute();
        } else{
            String sql = "INSERT INTO KENNEL (KENNEL_STATUS_ID, KENNEL_SIZE_ID) VALUES (?,?)";
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1,kennelStatusCombo.getSelectionModel().getSelectedIndex()+1);
            p.setInt(2,kennelSizeCombo.getSelectionModel().getSelectedIndex()+1);
            p.execute();
        }
        initialize();
    }
}
