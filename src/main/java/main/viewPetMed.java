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

public class viewPetMed {
    @FXML
    TableView<PetMed>tblPetMed;
    @FXML
    TableColumn<PetMed,String>petName;
    @FXML
    TableColumn<PetMed,String>medName;
    @FXML
    TableColumn<PetMed,Date>petMedStartDate;
    @FXML
    TableColumn<PetMed,Date>petMedEndDate;
    @FXML
    TableColumn<PetMed,String>medStatus;

    @FXML //fx:id ="petSelect"
    private ComboBox<String>petSelect;

    public void initialize()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetMed, String>("petName"));
        medName.setCellValueFactory(new PropertyValueFactory<PetMed, String>("medName"));
        petMedStartDate.setCellValueFactory(new PropertyValueFactory<PetMed, Date>("petMedStartDate"));
        petMedEndDate.setCellValueFactory(new PropertyValueFactory<PetMed, Date>("petMedEndDate"));
        medStatus.setCellValueFactory(new PropertyValueFactory<PetMed, String>("medStatus"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT PET.PET_NAME, PET_MED.MED_NAME, PET_MED.PET_MED_START_DATE, PET_MED.PET_MED_END_DATE, PET_MED_STATUS.PET_MED_STATUS FROM PET_MED " +
                "JOIN PET_MED_STATUS ON PET_MED.PET_MED_STATUS_ID = PET_MED_STATUS.PET_MED_STATUS_ID " +
                "JOIN PET ON PET_MED.PET_ID = PET.PET_ID";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetMed> petMeds = FXCollections.observableArrayList();
        while(rs.next()){
            final PetMed petMed = new PetMed();
            petMed.setPetName(rs.getString("PET_NAME"));
            petMed.setMedName(rs.getString("MED_NAME"));
            petMed.setPetMedStartDate(rs.getDate("PET_MED_START_DATE"));
            petMed.setPetMedEndDate(rs.getDate("PET_MED_END_DATE"));
            petMed.setMedStatus(rs.getString("PET_MED_STATUS"));
            petMeds.add(petMed);
        }
        tblPetMed.setItems(petMeds);
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
                filterMedTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void filterMedTable()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<PetMed, String>("petName"));
        medName.setCellValueFactory(new PropertyValueFactory<PetMed, String>("medName"));
        petMedStartDate.setCellValueFactory(new PropertyValueFactory<PetMed, Date>("petMedStartDate"));
        petMedEndDate.setCellValueFactory(new PropertyValueFactory<PetMed, Date>("petMedEndDate"));
        medStatus.setCellValueFactory(new PropertyValueFactory<PetMed, String>("medStatus"));

        Connection connection = DbHelper.getInstance().getConnection();
        String pID = petSelect.getValue();
        String[]p;
        p = pID.split("_");
        int PID = Integer.parseInt(p[0]);


        String sql = "SELECT PET.PET_NAME, PET_MED.MED_NAME, PET_MED.PET_MED_START_DATE, PET_MED.PET_MED_END_DATE, PET_MED_STATUS.PET_MED_STATUS FROM PET_MED " +
                "JOIN PET_MED_STATUS ON PET_MED.PET_MED_STATUS_ID = PET_MED_STATUS.PET_MED_STATUS_ID " +
                "JOIN PET ON PET_MED.PET_ID = PET.PET_ID WHERE PET.PET_ID = "+ PID;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<PetMed> petMeds = FXCollections.observableArrayList();

        while(rs.next()){
            final PetMed petMed1 = new PetMed();
            petMed1.setPetName(rs.getString("PET_NAME"));
            petMed1.setMedName(rs.getString("MED_NAME"));
            petMed1.setPetMedStartDate(rs.getDate("PET_MED_START_DATE"));
            petMed1.setPetMedEndDate(rs.getDate("PET_MED_END_DATE"));
            petMed1.setMedStatus(rs.getString("PET_MED_STATUS"));
            petMeds.add(petMed1);
        }
        tblPetMed.setItems(petMeds);
        preparedStatement.close();
        rs.close();

    }
}
