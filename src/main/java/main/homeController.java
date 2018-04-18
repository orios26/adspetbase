package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.BoardingAppt;
import main.DbHelper;

import java.sql.*;

public class homeController {


    @FXML
    TableView<BoardingAppt>tblArrivals;
    @FXML
    TableColumn<BoardingAppt, Integer>arrivalId;
    @FXML
    TableColumn<BoardingAppt, String>arrivalPet;
    @FXML
    TableColumn<BoardingAppt, String>arrivalWeight;
    @FXML
    TableColumn<BoardingAppt, String>arrivalBreedCode;
    @FXML
    TableColumn<BoardingAppt, Integer>arrivalkennelId;
    @FXML
    TableColumn<BoardingAppt, Date>arrivalCheckIn;
    @FXML
    TableColumn<BoardingAppt, Date>arrivalCheckOut;

    @FXML
    TableView<BoardingAppt>tblDepartures;
    @FXML
    TableColumn<BoardingAppt, Integer>departureId;
    @FXML
    TableColumn<BoardingAppt, String>departurePet;
    @FXML
    TableColumn<BoardingAppt, String>departureWeight;
    @FXML
    TableColumn<BoardingAppt, String>departureBreedCode;
    @FXML
    TableColumn<BoardingAppt, Integer>departurekennelId;
    @FXML
    TableColumn<BoardingAppt, Timestamp>departureCheckIn;
    @FXML
    TableColumn<BoardingAppt, Timestamp>departureCheckOut;

    public void initialize()throws SQLException{

        arrivalId.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Integer>("boardingApptId"));
        arrivalPet.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petName"));
        arrivalWeight.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petWeight"));
        arrivalBreedCode.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petBreed"));
        arrivalkennelId.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Integer>("kennelId"));
        arrivalCheckIn.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Date>("boardingApptCheckIn"));
        arrivalCheckOut.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Date>("boardingApptCheckOut"));

        departureId.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Integer>("boardingApptId"));
        departurePet.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petName"));
        departureWeight.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petWeight"));
        departureBreedCode.setCellValueFactory(new PropertyValueFactory<BoardingAppt,String>("petBreed"));
        departurekennelId.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Integer>("kennelId"));
        departureCheckIn.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Timestamp>("boardingApptCheckIn"));
        departureCheckOut.setCellValueFactory(new PropertyValueFactory<BoardingAppt,Timestamp>("boardingApptCheckOut"));


        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT BOARDING_APPT.BOARDING_APPT_ID, PET.PET_NAME, PET_WEIGHT_HIST.WEIGHT, PET_BREED.PET_DESCRIPTION, KENNEL.KENNEL_ID, BOARDING_APPT.BOARDING_APPT_CHECK_IN, BOARDING_APPT.BOARDING_APPT_CHECK_OUT FROM BOARDING_APPT " +
                "JOIN PET ON BOARDING_APPT.PET_ID = PET.PET_ID " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN KENNEL_RESERVATION ON BOARDING_APPT.BOARDING_APPT_ID = KENNEL_RESERVATION.BOARDING_APPT_ID " +
                "JOIN KENNEL ON KENNEL_RESERVATION.KENNEL_ID = KENNEL.KENNEL_ID WHERE BOARDING_APPT_CHECK_IN BETWEEN {ts '2018-04-27 00:00:00'} AND {ts '2018-05-03 23:59:59'}";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<BoardingAppt>arrivals = FXCollections.observableArrayList();
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
        tblArrivals.setItems(arrivals);

        Connection connection1 = DbHelper.getInstance().getConnection();
        String sql1 = "SELECT BOARDING_APPT.BOARDING_APPT_ID, PET.PET_NAME, PET_WEIGHT_HIST.WEIGHT, PET_BREED.PET_DESCRIPTION, KENNEL.KENNEL_ID, BOARDING_APPT.BOARDING_APPT_CHECK_IN, BOARDING_APPT.BOARDING_APPT_CHECK_OUT FROM BOARDING_APPT " +
                "JOIN PET ON BOARDING_APPT.PET_ID = PET.PET_ID " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN KENNEL_RESERVATION ON BOARDING_APPT.BOARDING_APPT_ID = KENNEL_RESERVATION.BOARDING_APPT_ID " +
                "JOIN KENNEL ON KENNEL_RESERVATION.KENNEL_ID = KENNEL.KENNEL_ID WHERE BOARDING_APPT_CHECK_OUT BETWEEN {ts '2018-04-27 00:00:00'} AND {ts '2018-05-03 23:59:59'}";
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        ObservableList<BoardingAppt>departures = FXCollections.observableArrayList();
        while(resultSet1.next()){
            final BoardingAppt depart = new BoardingAppt();
            depart.setBoardingApptId(resultSet1.getInt("BOARDING_APPT_ID"));
            depart.setPetName(resultSet1.getString("PET_NAME"));
            depart.setPetWeight(resultSet1.getString("WEIGHT")+ " lbs");
            depart.setPetBreed(resultSet1.getString("PET_DESCRIPTION"));
            depart.setKennelId(resultSet1.getInt("KENNEL_ID"));
            depart.setBoardingApptCheckIn(resultSet1.getTimestamp("BOARDING_APPT_CHECK_IN"));
            depart.setBoardingApptCheckOut(resultSet1.getTimestamp("BOARDING_APPT_CHECK_OUT"));
            departures.add(depart);
        }
        tblDepartures.setItems(departures);
    }

}
