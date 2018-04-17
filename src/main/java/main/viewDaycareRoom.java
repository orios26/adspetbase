package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class viewDaycareRoom {

   @FXML
    TableView<DaycareVisit>tblDaycareVisits;
   @FXML
    TableColumn<DaycareVisit, String>roomName;
   @FXML
    TableColumn<DaycareVisit, Integer>petId;
   @FXML
    TableColumn<DaycareVisit, String>petName;
   @FXML
    TableColumn<DaycareVisit, String>cusLastname;
   @FXML
    TableColumn<DaycareVisit, String>cusFirstname;
   @FXML
    TableColumn<DaycareVisit, Timestamp>checkin;

   public void initialize()throws SQLException{
       roomName.setCellValueFactory(new PropertyValueFactory<DaycareVisit, String>("roomName"));
       petId.setCellValueFactory(new PropertyValueFactory<DaycareVisit, Integer>("petId"));
       petName.setCellValueFactory(new PropertyValueFactory<DaycareVisit, String>("petName"));
       cusLastname.setCellValueFactory(new PropertyValueFactory<DaycareVisit, String>("cusLastname"));
       cusFirstname.setCellValueFactory(new PropertyValueFactory<DaycareVisit, String>("cusFirstname"));
       checkin.setCellValueFactory(new PropertyValueFactory<DaycareVisit, Timestamp>("daycareVisitStartDate"));

       Connection connection = DbHelper.getInstance().getConnection();
       String sql = "SELECT DAYCARE_ROOM.DAYCARE_ROOM_NAME, PET.PET_ID, PET.PET_NAME, CUSTOMER.CUS_LASTNAME, CUSTOMER.CUS_FIRSTNAME, DAYCARE_VISIT.DAYCARE_VISIT_START_DATE FROM DAYCARE_VISIT " +
               "JOIN VISIT ON DAYCARE_VISIT.VISIT_ID = VISIT.VISIT_ID " +
               "JOIN PET ON VISIT.PET_ID = PET.PET_ID " +
               "JOIN PET_OWNER ON PET.PET_ID = PET_OWNER.PET_ID " +
               "JOIN CUSTOMER ON PET_OWNER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID " +
               "JOIN DAYCARE_ROOM ON DAYCARE_VISIT.DAYCARE_ROOM_ID = DAYCARE_ROOM.DAYCARE_ROOM_ID WHERE DAYCARE_VISIT_START_DATE BETWEEN {ts '2018-04-26 00:00:00'} AND {ts '2018-04-26 23:59:59'}";
       PreparedStatement preparedStatement = connection.prepareStatement(sql);
       ResultSet rs = preparedStatement.executeQuery();
       ObservableList<DaycareVisit> orders = FXCollections.observableArrayList();

       while(rs.next()){
           final DaycareVisit daycareVisit = new DaycareVisit();
           daycareVisit.setRoomName(rs.getString("DAYCARE_ROOM_NAME"));
           daycareVisit.setPetId(rs.getInt("PET_ID"));
           daycareVisit.setPetName(rs.getString("PET_NAME"));
           daycareVisit.setCusLastname(rs.getString("CUS_LASTNAME"));
           daycareVisit.setCusFirstname(rs.getString("CUS_FIRSTNAME"));
           daycareVisit.setDaycareVisitStartDate(rs.getTimestamp("DAYCARE_VISIT_START_DATE"));
           orders.add(daycareVisit);
       }
       tblDaycareVisits.setItems(orders);
       preparedStatement.close();
       rs.close();

   }
}
