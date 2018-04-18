package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class viewBoardingAppointements {


    @FXML
    TableView<BoardingAppt> tblBoarding;
    @FXML
    TableColumn<BoardingAppt, Integer> boardingId;
    @FXML
    TableColumn<BoardingAppt, String>boardingPet;
    @FXML
    TableColumn<BoardingAppt, String>boardingWeight;
    @FXML
    TableColumn<BoardingAppt, String>boardingBreedCode;
    @FXML
    TableColumn<BoardingAppt, Integer>boardingkennelId;
    @FXML
    TableColumn<BoardingAppt, Timestamp>boardingCheckIn;
    @FXML
    TableColumn<BoardingAppt, Timestamp>boardingCheckOut;


    public void initialize()throws SQLException{

        boardingId.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Integer>("boardingApptId"));
        boardingPet.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petName"));
        boardingWeight.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petWeight"));
        boardingBreedCode.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petBreed"));
        boardingkennelId.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Integer>("kennelId"));
        boardingCheckIn.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Timestamp>("boardingApptCheckIn"));
        boardingCheckOut.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Timestamp>("boardingApptCheckOut"));



        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT BOARDING_APPT.BOARDING_APPT_ID, PET.PET_NAME, PET_WEIGHT_HIST.WEIGHT, PET_BREED.PET_DESCRIPTION, KENNEL.KENNEL_ID, BOARDING_APPT.BOARDING_APPT_CHECK_IN, BOARDING_APPT.BOARDING_APPT_CHECK_OUT FROM BOARDING_APPT " +
                "JOIN PET ON BOARDING_APPT.PET_ID = PET.PET_ID " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN KENNEL_RESERVATION ON BOARDING_APPT.BOARDING_APPT_ID = KENNEL_RESERVATION.BOARDING_APPT_ID " +
                "JOIN KENNEL ON KENNEL_RESERVATION.KENNEL_ID = KENNEL.KENNEL_ID ORDER BY BOARDING_APPT_CHECK_IN DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<BoardingAppt> arrivals = FXCollections.observableArrayList();
        while(resultSet.next()){
            final BoardingAppt arrive = new BoardingAppt();
            arrive.setBoardingApptId(resultSet.getInt("BOARDING_APPT_ID"));
            arrive.setPetName(resultSet.getString("PET_NAME"));
            arrive.setPetWeight(resultSet.getString("WEIGHT")+ " lbs");
            arrive.setPetBreed(resultSet.getString("PET_DESCRIPTION"));
            arrive.setKennelId(resultSet.getInt("KENNEL_ID"));
            arrive.setBoardingApptCheckIn(resultSet.getTimestamp("BOARDING_APPT_CHECK_IN"));
            arrive.setBoardingApptCheckOut(resultSet.getTimestamp("BOARDING_APPT_CHECK_OUT"));
            arrivals.add(arrive);
        }
        tblBoarding.setItems(arrivals);
    }
}
