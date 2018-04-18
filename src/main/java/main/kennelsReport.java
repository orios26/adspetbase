package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class kennelsReport {

    @FXML
    TableView<Kennel>tblKennels;

    @FXML
    TableColumn<Kennel, Integer>kennelId;
    @FXML
    TableColumn<Kennel, String>kennelSize;
    @FXML
    TableColumn<Kennel, String>kennelSizeDesc;
    @FXML
    TableColumn<Kennel, String>kennelStatus;
    @FXML
    TableColumn<Kennel, String>kennelRsvpDesc;

    @FXML // fx:id ="kennelStatusSelect"
    private ComboBox<String>kennelStatusSelect;

    public void initialize()throws SQLException{
        kennelId.setCellValueFactory(new PropertyValueFactory<Kennel, Integer>("kennelId"));
        kennelSize.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelSize"));
        kennelSizeDesc.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelSizeDesc"));
        kennelStatus.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelStatus"));
        kennelRsvpDesc.setCellValueFactory(new PropertyValueFactory<Kennel, String>("kennelRsvpDesc"));

        String sql = "SELECT K.KENNEL_ID, KENNEL_SIZE, KENNEL_SIZE_DESC, KENNEL_STATUS, KENNEL_RSVP_DESC FROM KENNEL K " +
                "JOIN KENNEL_RESERVATION ON K.KENNEL_ID = KENNEL_RESERVATION.KENNEL_ID " +
                "JOIN KENNEL_SIZE ON K.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID " +
                "JOIN KENNEL_STATUS ON K.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Kennel>kennels = FXCollections.observableArrayList();
        while (resultSet.next()){
            final Kennel kennel = new Kennel();
            kennel.setKennelId(resultSet.getInt("KENNEL_ID"));
            kennel.setKennelSize(resultSet.getString("KENNEL_SIZE"));
            kennel.setKennelSizeDesc(resultSet.getString("KENNEL_SIZE_DESC"));
            kennel.setKennelStatus(resultSet.getString("KENNEL_STATUS"));
            kennel.setKennelRsvpDesc(resultSet.getString("KENNEL_RSVP_DESC"));
            kennels.add(kennel);
        }
        tblKennels.setItems(kennels);
        resultSet.close();
        preparedStatement.close();
        connection.close();

        String sql1 = "SELECT KENNEL_STATUS FROM KENNEL_STATUS";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<String> status = FXCollections.observableArrayList();
        while (resultSet1.next()){
            status.add(resultSet1.getString("KENNEL_STATUS"));
        }
        kennelStatusSelect.setItems(status);
        resultSet1.close();
        preparedStatement1.close();
        connection1.close();

        kennelStatusSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                statusSelect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void statusSelect()throws SQLException{
        int i = kennelStatusSelect.getSelectionModel().getSelectedIndex()+1;

        String sql = "SELECT K.KENNEL_ID, KENNEL_SIZE, KENNEL_SIZE_DESC, KENNEL_STATUS, KENNEL_RSVP_DESC FROM KENNEL K " +
                "JOIN KENNEL_RESERVATION ON K.KENNEL_ID = KENNEL_RESERVATION.KENNEL_ID " +
                "JOIN KENNEL_SIZE ON K.KENNEL_SIZE_ID = KENNEL_SIZE.KENNEL_SIZE_ID " +
                "JOIN KENNEL_STATUS ON K.KENNEL_STATUS_ID = KENNEL_STATUS.KENNEL_STATUS_ID WHERE KENNEL_STATUS.KENNEL_STATUS_ID = "+i;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Kennel>kennels = FXCollections.observableArrayList();
        while (resultSet.next()){
            final Kennel kennel = new Kennel();
            kennel.setKennelId(resultSet.getInt("KENNEL_ID"));
            kennel.setKennelSize(resultSet.getString("KENNEL_SIZE"));
            kennel.setKennelSizeDesc(resultSet.getString("KENNEL_SIZE_DESC"));
            kennel.setKennelStatus(resultSet.getString("KENNEL_STATUS"));
            kennel.setKennelRsvpDesc(resultSet.getString("KENNEL_RSVP_DESC"));
            kennels.add(kennel);
        }
        tblKennels.setItems(kennels);
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

}
