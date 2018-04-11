package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;


public class addPetController  {

    @FXML // fx:id = "petName"
    private TextField petName;

    @FXML // fx:id = "petMicroChip"
    private TextField petMicroChip;

    @FXML // fx:id = "petDoB"
    private DatePicker petDoB;

    @FXML // fx:id = "petType"
    private ComboBox<String>petType;

    @FXML // fx:id = "petGender"
    private ComboBox<String>petGender;

    @FXML
    private ComboBox<String>petFixed;

    @FXML // fx:id = "petBreed"
    private ComboBox<String>petBreed;

    @FXML // fx:id = "petBreed2"
    private ComboBox<String>petBreed2;

    @FXML // fx:id = "petColor"
    private ComboBox<String>petColor;

    @FXML // fx:id "petWeight"
    private ComboBox<String>petWeight;

    @FXML // fx:id = "petColor2"
    private ComboBox<String>petColor2;

    @FXML // fx:id = "petBehavior"
    private ComboBox<String>petBehavior;

    @FXML // fx:id = "ownerInfo"
    private ComboBox<String>ownerInfo;

    @FXML // fx:id = "saveButton"
    private Button saveButton;

    @FXML // fx:id = "clearButton"
    private Button clearButton;

    public void initialize()throws SQLException{
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("Dog");
        type.add("Cat");
        petType.setItems(type);

        ObservableList<String> gender = FXCollections.observableArrayList();
        gender.add("M");
        gender.add("F");
        petGender.setItems(gender);

        ObservableList<String> fixed = FXCollections.observableArrayList();
        fixed.add("Y");
        fixed.add("N");
        petFixed.setItems(fixed);

        ObservableList<String> weight = FXCollections.observableArrayList();
        weight.add("0-20 lbs");
        weight.add("21-45 lbs");
        weight.add("46-59 lbs");
        weight.add("60-90 lbs");
        weight.add("100+ lbs");
        petWeight.setItems(weight);

        String sql = "SELECT BREED_NAME FROM BREED";
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        ObservableList<String> breed = FXCollections.observableArrayList();
        while(rs.next()){
            breed.add(rs.getString("BREED_NAME"));
        }
        petBreed.setItems(breed);
        petBreed2.setItems(breed);
        rs.close();
        psmt.close();
        connection.close();

        String sql1 = "SELECT CLR_DESC FROM CLR";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt1 = connection1.prepareStatement(sql1);
        ResultSet rs1 = psmt1.executeQuery();
        ObservableList<String>color = FXCollections.observableArrayList();
        while(rs1.next()){
            color.add(rs1.getString("CLR_DESC"));
        }
        petColor.setItems(color);
        petColor2.setItems(color);
        rs1.close();
        psmt1.close();
        connection1.close();

        String sql2 = "SELECT BEHAVIOR_NAME FROM BEHAVIOR";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt2 = connection2.prepareStatement(sql2);
        ResultSet rs2 = psmt2.executeQuery();
        ObservableList<String>behavior = FXCollections.observableArrayList();
        while (rs2.next()){
            behavior.add(rs2.getString("BEHAVIOR_NAME"));
        }
        petBehavior.setItems(behavior);
        rs2.close();
        psmt2.close();
        connection2.close();

        String sql3 = "SELECT CUSTOMER_ID,CUS_LASTNAME, CUS_FIRSTNAME FROM CUSTOMER";
        Connection connection3 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt3 = connection3.prepareStatement(sql3);
        ResultSet rs3 = psmt3.executeQuery();
        ObservableList<String>info = FXCollections.observableArrayList();
        while (rs3.next()){
            String s = rs3.getInt("CUSTOMER_ID")+"-" + rs3.getString("CUS_LASTNAME")+"-" + rs3.getString("CUS_FIRSTNAME");
            info.add(s);
        }
        ownerInfo.setItems(info);
        rs3.close();
        psmt3.close();
        connection3.close();

    }

    public void saveCommand() throws SQLException {
        LocalDate birthDate = petDoB.getValue();
        LocalDate today = LocalDate.now();
        String age = Period.between(birthDate, today).toString();
        Connection connection4 = DbHelper.getInstance().getConnection();
        String sql = "INSERT INTO PET(PET_NAME,PET_DOB,PET_GENDER,FIXED,PET_TYPE_ID,PET_MICROCHIP_NUM,PET_WEIGHT_GRP_ID,PET_START_DATE,PET_STATUS_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement p = connection4.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        p.setString(1, petName.getText());
        p.setDate(2, Date.valueOf(birthDate));
        //p.setString(3, age);
        p.setString(3, petGender.getValue());
        p.setString(4, petFixed.getValue());
        p.setInt(5, petType.getSelectionModel().getSelectedIndex()+1);
        p.setString(6, petMicroChip.getText());
        p.setInt(7, petWeight.getSelectionModel().getSelectedIndex()+1);
        p.setDate(8, Date.valueOf(today));
        p.setInt(9, 1);
        p.execute();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("PET ADDED");
        alert.setContentText(petName.getText() + " has been added to PetBase");
        alert.showAndWait();
        ResultSet resultSet = p.getGeneratedKeys();
        if(resultSet.next()){
            int keyid = resultSet.getInt(1);
            System.out.println(keyid);
        }
        p.close();
        connection4.close();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/addPetScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
