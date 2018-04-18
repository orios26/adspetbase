package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class viewPetBehavior {
    @FXML
    TableView<PetBehavior>tblPetBehavior;
    @FXML
    TableColumn<PetBehavior,String>petName;
    @FXML
    TableColumn<PetBehavior,String>behaviorName;
    @FXML
    TableColumn<PetBehavior,Date>behaviorStartDate;
    @FXML
    TableColumn<PetBehavior,Date>behaviorEndDate;

    @FXML //fx:id ="petSelect"
    private ComboBox<String>petSelect;

    @FXML //fx:id ="refresh"
    private Button refresh;

    public void initialize()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petName"));
        behaviorName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petBehaviorName"));
        behaviorStartDate.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petBehaviorStartDate"));
        behaviorEndDate.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petBehaviorEndDate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT PET.PET_NAME, BEHAVIOR.BEHAVIOR_NAME, PET_BEHAVIOR.PET_BEHAVIOR_STARTDATE, PET_BEHAVIOR.PET_BEHAVIOR_ENDDATE FROM PET " +
                "JOIN PET_BEHAVIOR ON PET.PET_ID = PET_BEHAVIOR.PET_ID " +
                "JOIN BEHAVIOR ON PET_BEHAVIOR.BEHAVIOR_ID = BEHAVIOR.BEHAVIOR_ID";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetBehavior> petbehave = FXCollections.observableArrayList();

        while(rs.next()){
            PetBehavior behave = new PetBehavior();
            behave.setPetName(rs.getString("PET_NAME"));
            behave.setPetBehaviorName(rs.getString("BEHAVIOR_NAME"));
            behave.setPetBehaviorStartdate(rs.getDate("PET_BEHAVIOR_STARTDATE"));
            behave.setPetBehaviorEnddate(rs.getDate("PET_BEHAVIOR_ENDDATE"));
            petbehave.add(behave);
        }
        tblPetBehavior.setItems(petbehave);
        preparedStatement.close();
        rs.close();


        String sql1 = "SELECT PET.PET_ID, PET.PET_NAME FROM PET";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet = preparedStatement1.executeQuery();
        ObservableList<String>petInfo = FXCollections.observableArrayList();
        while (resultSet.next()){
            String s = resultSet.getInt("PET_ID")+"_"+resultSet.getString("PET_NAME");
            petInfo.add(s);
        }
        petSelect.setItems(petInfo);

        petSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterPetBehavior();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void filterPetBehavior()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petName"));
        behaviorName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petBehaviorName"));
        behaviorStartDate.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petBehaviorStartDate"));
        behaviorEndDate.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petBehaviorEndDate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String pID = petSelect.getValue();
        String[]p;
        p = pID.split("_");
        int PID = Integer.parseInt(p[0]);

        String sql = "SELECT PET.PET_NAME, BEHAVIOR.BEHAVIOR_NAME, PET_BEHAVIOR.PET_BEHAVIOR_STARTDATE, PET_BEHAVIOR.PET_BEHAVIOR_ENDDATE FROM PET " +
        "JOIN PET_BEHAVIOR ON PET.PET_ID = PET_BEHAVIOR.PET_ID " +
                "JOIN BEHAVIOR ON PET_BEHAVIOR.BEHAVIOR_ID = BEHAVIOR.BEHAVIOR_ID WHERE PET.PET_ID = "+PID;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetBehavior> petbehave = FXCollections.observableArrayList();

        while(rs.next()){
            PetBehavior behave = new PetBehavior();
            behave.setPetName(rs.getString("PET_NAME"));
            behave.setPetBehaviorName(rs.getString("BEHAVIOR_NAME"));
            behave.setPetBehaviorStartdate(rs.getDate("PET_BEHAVIOR_STARTDATE"));
            behave.setPetBehaviorEnddate(rs.getDate("PET_BEHAVIOR_ENDDATE"));
            petbehave.add(behave);
        }
        tblPetBehavior.setItems(petbehave);
        tblPetBehavior.refresh();
        preparedStatement.close();
        rs.close();

    }
}
