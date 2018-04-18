package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class microchippedPets {

    @FXML
    TableView<Pet>tblPet;

    @FXML
    TableColumn<Pet, String>petName;

    @FXML
    TableColumn<Pet, Date>petDob;

    @FXML
    TableColumn<Pet, String>petGender;

    @FXML
    TableColumn<Pet, String>petType;

    @FXML
    TableColumn<Pet, String>petWeight;

    @FXML
    TableColumn<Pet, String>petMicrochip;
    @FXML
    TableColumn<Pet, String>petBreed;

    @FXML
    TableColumn<Pet, String>petStatus;


    public void initialize()throws SQLException{
        petName.setCellValueFactory(new PropertyValueFactory<Pet, String>("petName"));
        petDob.setCellValueFactory(new PropertyValueFactory<Pet, Date>("petDob"));
        petGender.setCellValueFactory(new PropertyValueFactory<Pet, String>("petGender"));
        petType.setCellValueFactory(new PropertyValueFactory<Pet, String>("petType"));
        petWeight.setCellValueFactory(new PropertyValueFactory<Pet, String>("petWeight"));
        petMicrochip.setCellValueFactory(new PropertyValueFactory<Pet, String>("petMicrochipNum"));
        petBreed.setCellValueFactory(new PropertyValueFactory<Pet, String>("petBreed"));
        petStatus.setCellValueFactory(new PropertyValueFactory<Pet, String>("petStatus"));

        String sql = "SELECT PET_NAME, PET_DOB, PET_GENDER, PET_TYPE, PET_WEIGHT_HIST.WEIGHT, PET_MICROCHIP_NUM, PET_BREED.PET_DESCRIPTION, PET_STATUS FROM PET " +
                "JOIN PET_TYPE ON PET.PET_TYPE_ID = PET_TYPE.PET_TYPE_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_WEIGHT_GRP ON PET.PET_WEIGHT_GRP_ID = PET_WEIGHT_GRP.PET_WEIGHT_GRP_ID " +
                "JOIN PET_STATUS ON PET.PET_STATUS_ID = PET_STATUS.PET_STATUS_ID " +
                "WHERE PET_MICROCHIP_NUM IS NOT NULL " +
                "ORDER BY PET_STATUS ASC";

        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Pet> pets = FXCollections.observableArrayList();
        while (resultSet.next()){
            final Pet pet = new Pet();
            pet.setPetName(resultSet.getString("PET_NAME"));
            pet.setPetDob(resultSet.getDate("PET_DOB"));
            pet.setPetGender(resultSet.getString("PET_GENDER"));
            pet.setPetType(resultSet.getString("PET_TYPE"));
            pet.setPetWeight(resultSet.getString("WEIGHT")+" lbs");
            pet.setPetMicrochipNum(resultSet.getString("PET_MICROCHIP_NUM"));
            pet.setPetBreed(resultSet.getString("PET_DESCRIPTION"));
            pet.setPetStatus(resultSet.getString("PET_STATUS"));
            pets.add(pet);
        }
        tblPet.setItems(pets);

    }
}
