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

    private int ptID;
    private int petId;

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

//        String sql = "SELECT BREED_NAME FROM BREED";
//        Connection connection = DbHelper.getInstance().getConnection();
//        PreparedStatement psmt = connection.prepareStatement(sql);
//        ResultSet rs = psmt.executeQuery();
//        ObservableList<String> breed = FXCollections.observableArrayList();
//        while(rs.next()){
//            breed.add(rs.getString("BREED_NAME"));
//        }
//        petBreed.setItems(breed);
//        petBreed2.setItems(breed);
//        rs.close();
//        psmt.close();
//        connection.close();

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
        breedFinder();
    }

    public void breedFinder()throws SQLException{
        petType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            ptID = petType.getSelectionModel().getSelectedIndex()+1;
        try {
            String sql = "SELECT BREED_ID, BREED_NAME FROM BREED WHERE PET_TYPE_ID =" + ptID;
            Connection connection = DbHelper.getInstance().getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet rs = psmt.executeQuery();
            ObservableList<String> breed = FXCollections.observableArrayList();
            while (rs.next()) {
                breed.add(rs.getString("BREED_ID")+"_"+rs.getString("BREED_NAME"));
            }
            petBreed.setItems(breed);
            petBreed2.setItems(breed);
            psmt.close();
            connection.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        });

    }

    public void saveCommand() throws SQLException {
       String s = petBreed.getValue();
       String s1 = petBreed2.getValue();
        String [] brID;
        brID = s.split("_");
        String [] br1ID;
        br1ID = s1.split("_");

        int b = Integer.parseInt(brID[0]);
        int b1 = Integer.parseInt(br1ID[0]);

        LocalDate birthDate = petDoB.getValue();
        LocalDate today = LocalDate.now();
        //String age = Period.between(birthDate, today).toString();
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

        String sql1 = "SELECT BREED_CODE FROM BREED WHERE BREED_ID ="+b;
        String sql2 = "SELECT BREED_NAME FROM BREED WHERE BREED_ID ="+b1;
        Connection connection = DbHelper.getInstance().getConnection();
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql2);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        String breedCode = "";
        String breedDesc = "";
        while (resultSet.next()){
            breedCode = resultSet.getString("BREED_CODE");
        }
        while(resultSet1.next()){
            breedDesc = resultSet1.getString("BREED_NAME");
        }
        resultSet.close();
        resultSet1.close();
        preparedStatement.close();
        preparedStatement1.close();
        connection.close();
        connection1.close();


        ResultSet resultSet2 = p.getGeneratedKeys();
        if(resultSet2.next()){
            petId = resultSet2.getInt(1);
            System.out.println(petId);
        }
        p.close();
        connection4.close();

        String sql3 = "INSERT INTO PET_BREED(PET_ID,PET_BREED_CODE,PET_DESCRIPTION,BREED_ID) VALUES (?,?,?,?)";
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement ps = connection2.prepareStatement(sql3);
        ps.setInt(1,petId);
        ps.setString(2,breedCode);
        ps.setString(3,breedDesc);
        ps.setInt(4,b);
        ps.execute();
        ps.close();
        connection2.close();

        String sql4 = "SELECT CLR_CODE, CLR_DESC FROM CLR WHERE CLR_ID = " + petColor.getSelectionModel().getSelectedIndex()+1;
        Connection connection3 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection3.prepareStatement(sql4);
        String clrCode = "";
        String clrDesc = "";
        ResultSet resultSet3 = preparedStatement2.executeQuery();
        while(resultSet3.next()){
            clrCode = resultSet3.getString("CLR_CODE");
            clrDesc = resultSet3.getString("CLR_DESC");
        }
        resultSet3.close();
        connection3.close();
        preparedStatement2.close();

        String petClrInsert = "INSERT INTO PET_CLR (PET_ID, CLR_ID, PET_CLR_CODE, PET_CLR) VALUES (?,?,?,?)";
        Connection connection5 = DbHelper.getInstance().getConnection();
        PreparedStatement cp = connection5.prepareStatement(petClrInsert);
        cp.setInt(1,petId);
        cp.setInt(2,petColor.getSelectionModel().getSelectedIndex()+1);
        cp.setString(3,clrCode);
        cp.setString(4,clrDesc);
        cp.execute();
        cp.close();
        connection5.close();

        String owner = ownerInfo.getValue();
        String [] ownerId = owner.split("-");
        int i = Integer.parseInt(ownerId[0]);
        System.out.println(i);

        String petOwnerInsert = "INSERT INTO PET_OWNER(PET_ID, CUSTOMER_ID) VALUES (?,?)";
        Connection connection6 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement3 = connection6.prepareStatement(petOwnerInsert);
        preparedStatement3.setInt(1,petId);
        preparedStatement3.setInt(2,i);
        preparedStatement3.execute();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PetBase Update");
        alert.setHeaderText("PET ADDED");
        alert.setContentText(petName.getText() + " has been added to PetBase");
        alert.showAndWait();


        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/addPetScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
