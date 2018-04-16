package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.*;

public class addPetBehaviorController {

    @FXML //fx:id = "petSelect"
    private ComboBox<String>petSelect;

    @FXML //fx:id = "behaviorSelect"
    private ComboBox<String>behaviorSelect;

    @FXML //fx:id = "behaviorStartDate"
    private DatePicker behaviorStartDate;

    @FXML //fx:id ="behaviorEndDate"
    private DatePicker behaviorEndDate;

    @FXML //fx:id =addBehavior"
    private Button addBehavior;

    private int PID;
    private String petName;

    public void initialize()throws SQLException{
        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT PET_ID, PET_NAME FROM PET";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ObservableList<String>pet = FXCollections.observableArrayList();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            String s = resultSet.getInt("PET_ID")+"_"+resultSet.getString("PET_NAME");
            pet.add(s);
        }
        petSelect.setItems(pet);
        resultSet.close();
        preparedStatement.close();
        connection.close();

        Connection connection1 = DbHelper.getInstance().getConnection();
        String sql1 = "SELECT BEHAVIOR_NAME FROM BEHAVIOR";
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<String>bNames = FXCollections.observableArrayList();
        while (resultSet1.next()){
            bNames.add(resultSet1.getString("BEHAVIOR_NAME"));
        }
        behaviorSelect.setItems(bNames);
        resultSet1.close();
        preparedStatement1.close();
        connection1.close();
    }

    public void addBehaviorCommand()throws SQLException{
        String petID = petSelect.getValue();
        String[]p;
        p = petID.split("_");
        PID = Integer.parseInt(p[0]);
        petName = p[1];
                String sql = "INSERT INTO PET_BEHAVIOR(PET_ID, BEHAVIOR_ID, PET_BEHAVIOR_STARTDATE,PET_BEHAVIOR_ENDDATE) VALUES (?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,PID);
        preparedStatement.setInt(2,behaviorSelect.getSelectionModel().getSelectedIndex()+1);
        preparedStatement.setDate(3, Date.valueOf(behaviorStartDate.getValue()));
        preparedStatement.setDate(4,Date.valueOf(behaviorEndDate.getValue()));
        preparedStatement.execute();
    }
}
