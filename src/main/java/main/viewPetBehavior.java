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
    TableColumn<PetBehavior, Date>petDob;
    @FXML
    TableColumn<PetBehavior, String>petGender;
    @FXML
    TableColumn<PetBehavior,String>behaviorName;
    @FXML
    TableColumn<PetBehavior, String>petDescription;
    @FXML
    TableColumn<PetBehavior,Date>behaviorStartDate;

    @FXML //fx:id ="petSelect"
    private ComboBox<String>petSelect;



    public void initialize()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petName"));
        petDob.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petDob"));
        petGender.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petGender"));
        behaviorName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petBehaviorName"));
        petDescription.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petDescription"));
        behaviorStartDate.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petBehaviorStartDate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT PET_NAME, PET_DOB, PET_DOB, PET_GENDER, BEHAVIOR_NAME, PET_DESCRIPTION, PET_BEHAVIOR_STARTDATE FROM PET " +
                "JOIN PET_BEHAVIOR ON PET.PET_ID = PET_BEHAVIOR.PET_ID " +
                "JOIN BEHAVIOR ON PET_BEHAVIOR.BEHAVIOR_ID = BEHAVIOR.BEHAVIOR_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetBehavior> petbehave = FXCollections.observableArrayList();

        while(rs.next()){
            PetBehavior behave = new PetBehavior();
            behave.setPetName(rs.getString("PET_NAME"));
            behave.setPetDob(rs.getDate("PET_DOB"));
            behave.setPetGender(rs.getString("PET_GENDER"));
            behave.setPetBehaviorName(rs.getString("BEHAVIOR_NAME"));
            behave.setPetDescription(rs.getString("PET_DESCRIPTION"));
            behave.setPetBehaviorStartdate(rs.getDate("PET_BEHAVIOR_STARTDATE"));
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
        petDob.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petDob"));
        petGender.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petGender"));
        behaviorName.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petBehaviorName"));
        petDescription.setCellValueFactory(new PropertyValueFactory<PetBehavior, String>("petDescription"));
        behaviorStartDate.setCellValueFactory(new PropertyValueFactory<PetBehavior, Date>("petBehaviorStartDate"));

        Connection connection = DbHelper.getInstance().getConnection();
        String pID = petSelect.getValue();
        String[]p;
        p = pID.split("_");
        int PID = Integer.parseInt(p[0]);

        String sql = "SELECT PET_NAME, PET_DOB, PET_DOB, PET_GENDER, BEHAVIOR_NAME, PET_DESCRIPTION, PET_BEHAVIOR_STARTDATE FROM PET " +
                "JOIN PET_BEHAVIOR ON PET.PET_ID = PET_BEHAVIOR.PET_ID " +
                "JOIN BEHAVIOR ON PET_BEHAVIOR.BEHAVIOR_ID = BEHAVIOR.BEHAVIOR_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID WHERE PET.PET_ID ="+PID;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetBehavior> petbehave = FXCollections.observableArrayList();

        while(rs.next()){
            PetBehavior behave = new PetBehavior();
            behave.setPetName(rs.getString("PET_NAME"));
            behave.setPetDob(rs.getDate("PET_DOB"));
            behave.setPetGender(rs.getString("PET_GENDER"));
            behave.setPetBehaviorName(rs.getString("BEHAVIOR_NAME"));
            behave.setPetDescription(rs.getString("PET_DESCRIPTION"));
            behave.setPetBehaviorStartdate(rs.getDate("PET_BEHAVIOR_STARTDATE"));

            petbehave.add(behave);
        }
        tblPetBehavior.setItems(petbehave);
        tblPetBehavior.refresh();
        preparedStatement.close();
        rs.close();

    }
}
