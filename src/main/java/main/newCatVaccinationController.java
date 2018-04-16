package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class newCatVaccinationController {

    @FXML //fx:id ="vStart"
    private DatePicker vStart;

    @FXML //fx:id ="vStart1"
    private DatePicker vStart1;

    @FXML //fx:id ="vEnd"
    private DatePicker vEnd;

    @FXML //fx:id ="vEnd1"
    private DatePicker vEnd1;


    private int PID, VID;

    public void getVaccData()throws SQLException{
        try{
            PID = Main.getPid();
            addVacc(4,PID,vStart.getValue(),vEnd.getValue());
            addVacc(2,PID,vStart1.getValue(),vEnd1.getValue());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PetBase Update");
            alert.setHeaderText("Vaccination Updated");
            alert.setContentText("Vaccination has been updated");
            alert.showAndWait();
            try{
                AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/PaneOne.fxml"));
                BorderPane borderPane = Main.getRoot();
                borderPane.setCenter(pane);
            }catch (IOException e){
                e.printStackTrace();
            }


        }catch (SQLException e){

        }
    }

    public void addVacc(int v, int p, LocalDate d, LocalDate d1)throws SQLException{
        String sql = "INSERT INTO PET_VACCINE(PET_ID, VACCINE_ID, PET_VACCINE_STARTDATE, PET_VACCINE_ENDDATE) VALUES (?,?,?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1,p);
        preparedStatement.setInt(2,v);
        preparedStatement.setDate(3, Date.valueOf(d));
        preparedStatement.setDate(4,Date.valueOf(d1));
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()){
            VID = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        addVaccHist();
    }

    public void addVaccHist()throws SQLException{
        String sql = "INSERT INTO PET_VACCINE_HIST(PET_VACCINE_ID, PET_VACCINE_HIST_DATE) VALUES (?,?)";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,VID);
        preparedStatement.setDate(2,Date.valueOf(LocalDate.now()));
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

}
