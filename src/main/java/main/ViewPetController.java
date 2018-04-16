package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ViewPetController {

    @FXML
    TableView<Pet> tblpet;
    @FXML
    TableColumn<Pet, Integer> petId;
    @FXML
    TableColumn<Pet, String> petName;
    @FXML
    TableColumn<Pet, Date>petDoB;
    @FXML
    TableColumn<Pet, String>breed;
    @FXML
    TableColumn<Pet, String>color;
    @FXML
    TableColumn<Pet, String>weight;
    @FXML
    TableColumn<Pet, String> ownerLast;
    @FXML
    TableColumn<Pet, String> ownerFirst;
    @FXML
    TableColumn<Pet, String>status;


    public void initialize()throws SQLException{
        petId.setCellValueFactory(new PropertyValueFactory<Pet, Integer>("petId"));
        petName.setCellValueFactory(new PropertyValueFactory<Pet, String>("petName"));
        petDoB.setCellValueFactory(new PropertyValueFactory<Pet, Date>("petDob"));
        breed.setCellValueFactory(new PropertyValueFactory<Pet, String>("petBreed"));
        color.setCellValueFactory(new PropertyValueFactory<Pet, String>("petColor"));
        weight.setCellValueFactory(new PropertyValueFactory<Pet, String>("petWeight"));
        ownerLast.setCellValueFactory(new PropertyValueFactory<Pet, String>("ownerLast"));
        ownerFirst.setCellValueFactory(new PropertyValueFactory<Pet, String>("ownerFirst"));
        status.setCellValueFactory(new PropertyValueFactory<Pet, String>("petStatus"));

        Connection connection = DbHelper.getInstance().getConnection();
        String sql = "SELECT PET.PET_ID, PET.PET_NAME, PET.PET_DOB, PET_BREED.PET_DESCRIPTION, PET_CLR.PET_CLR,  PET_WEIGHT_HIST.WEIGHT, CUSTOMER.CUS_LASTNAME, CUSTOMER.CUS_FIRSTNAME, PET_STATUS.PET_STATUS FROM PET " +
                "JOIN PET_OWNER ON PET.PET_ID = PET_OWNER.PET_ID " +
                "JOIN CUSTOMER ON PET_OWNER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_CLR ON PET.PET_ID = PET_CLR.PET_ID " +
                "JOIN CLR ON PET_CLR.CLR_ID = CLR.CLR_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN BREED ON PET_BREED.BREED_ID = BREED.BREED_ID " +
                "JOIN PET_STATUS ON PET.PET_STATUS_ID = PET_STATUS.PET_STATUS_ID ORDER BY PET_STATUS";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Pet> pets = FXCollections.observableArrayList();
        while(resultSet.next()){
            final Pet pet = new Pet();
            pet.setPetId(resultSet.getInt("PET_ID"));
            pet.setPetName(resultSet.getString("PET_NAME"));
            pet.setPetDob(resultSet.getDate("PET_DOB"));
            pet.setPetBreed(resultSet.getString("PET_DESCRIPTION"));
            pet.setPetColor(resultSet.getString("PET_CLR"));
            //pet.setPetWeight(resultSet.getInt("WEIGHT")+ " lbs");
            pet.setOwnerLast(resultSet.getString("CUS_LASTNAME"));
            pet.setOwnerFirst(resultSet.getString("CUS_FIRSTNAME"));
            pet.setPetStatus(resultSet.getString("PET_STATUS"));
            pets.add(pet);
        }
        tblpet.setItems(pets);
    }
}
