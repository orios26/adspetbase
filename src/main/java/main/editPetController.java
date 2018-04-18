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



public class editPetController {



    @FXML // fx:id = "petMicroChip"
    private TextField petMicroChip;

    @FXML // fx:id = "petDoB"
    private DatePicker petDoB;

    @FXML // fx:id = "petType"
    private ComboBox<String> petType;

    @FXML // fx:id = "petGender"
    private ComboBox<String> petGender;

    @FXML // fx:id = "petFixed"
    private ComboBox<String> petFixed;

    @FXML // fx:id = "orderStart"
    private ComboBox<String> petBreed;

    @FXML // fx:id = "petBreed2"
    private ComboBox<String> petBreed2;

    @FXML // fx:id = "petColor"
    private ComboBox<String> petColor;

    @FXML // fx:id = "petColor2"
    private ComboBox<String> petColor2;

    @FXML // fx:id = "petBehavior"
    private ComboBox<String> petBehavior;

    @FXML // fx:id = "petInfo"
    private ComboBox<String> petInfo;

    @FXML // fx:id = "saveButton"
    private Button saveButton;

    @FXML // fx:id = "clearButton"
    private Button clearButton;

    @FXML // fx:id ="petWeightInsert"
    private TextField petWeightInsert;

    @FXML //fx:id ="specialCareInstr"
    private TextArea specialCareInstr;

    private int ptID;
    private int petId;
    private int PID;

    public void initialize() throws SQLException {
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


        String sql1 = "SELECT CLR_DESC FROM CLR";
        Connection connection1 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt1 = connection1.prepareStatement(sql1);
        ResultSet rs1 = psmt1.executeQuery();
        ObservableList<String> color = FXCollections.observableArrayList();
        while (rs1.next()) {
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
        ObservableList<String> behavior = FXCollections.observableArrayList();
        while (rs2.next()) {
            behavior.add(rs2.getString("BEHAVIOR_NAME"));
        }
        petBehavior.setItems(behavior);
        rs2.close();
        psmt2.close();
        connection2.close();

        String sql3 = "SELECT PET_ID, PET_NAME FROM PET";
        Connection connection3 = DbHelper.getInstance().getConnection();
        PreparedStatement psmt3 = connection3.prepareStatement(sql3);
        ResultSet rs3 = psmt3.executeQuery();
        ObservableList<String> info = FXCollections.observableArrayList();
        while (rs3.next()) {
            String s = rs3.getInt("PET_ID") + "_" + rs3.getString("PET_NAME");
            info.add(s);
        }
        petInfo.setItems(info);

        petInfo.getSelectionModel().selectedItemProperty().addListener(observable -> {
            try {
                loadDetails();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        rs3.close();
        psmt3.close();
        connection3.close();
        breedFinder();
    }

    public void loadDetails() throws SQLException {
        String pet = petInfo.getValue();
        String p[];
        p = pet.split("_");
        PID = Integer.parseInt(p[0]);
        String sql = "SELECT PET.PET_MICROCHIP_NUM, PET.PET_DOB, PET_TYPE.PET_TYPE, PET.PET_GENDER, PET_BREED.PET_DESCRIPTION, PET.FIXED, PET_WEIGHT_HIST.WEIGHT, PET_CLR.PET_CLR, PET.PET_FEEDING_INSTRUCTIONS FROM PET " +
                "JOIN PET_CLR ON PET.PET_ID = PET_CLR.PET_ID " +
                "JOIN PET_WEIGHT_HIST ON PET.PET_ID = PET_WEIGHT_HIST.PET_ID " +
                "JOIN PET_BREED ON PET.PET_ID = PET_BREED.PET_ID " +
                "JOIN PET_TYPE ON PET.PET_TYPE_ID = PET_TYPE.PET_TYPE_ID WHERE PET.PET_ID=" + PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            petMicroChip.setText(resultSet.getString("PET_MICROCHIP_NUM"));
            petDoB.setValue(resultSet.getDate("PET_DOB").toLocalDate());
            petType.setValue(resultSet.getString("PET_TYPE"));
            petGender.setValue(resultSet.getString("PET_GENDER"));
            petBreed.setValue(resultSet.getString("PET_DESCRIPTION"));
            petFixed.setValue(resultSet.getString("FIXED"));
            petWeightInsert.setText(resultSet.getString("WEIGHT"));
            petColor.setValue(resultSet.getString("PET_CLR"));
            specialCareInstr.setText(resultSet.getString("PET_FEEDING_INSTRUCTIONS"));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    public void update()throws SQLException{
        String update = "UPDATE PET SET PET_DOB = ?, PET_GENDER=?, FIXED=?, PET_TYPE_ID=?, PET_MICROCHIP_NUM, PET_WEIGHT_GRP_ID, PET_FEEDING_INSTRUCTIONS WHERE PET_ID ="+PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setDate(1,Date.valueOf(petDoB.getValue()));
        preparedStatement.setString(2,petGender.getValue());
        preparedStatement.setString(3,petFixed.getValue());
        preparedStatement.setInt(4,petType.getSelectionModel().getSelectedIndex()+1);
        preparedStatement.setString(5,petMicroChip.getText());
        preparedStatement.setInt(6,petWeightGrpChecker(Integer.parseInt(petWeightInsert.getText())));
        preparedStatement.setString(7,specialCareInstr.getText());
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
        breedSelection();
        colorSelection();
    }

    public void breedSelection()throws SQLException{
        String s = petBreed.getValue();
        String s1 = petBreed2.getValue();
        String [] brID;
        brID = s.split("_");
        String [] br1ID;
        br1ID = s1.split("_");

        int b = Integer.parseInt(brID[0]);
        int b1 = Integer.parseInt(br1ID[0]);


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

        String update = "UPDATE PET_BREED SET PET_BREED_CODE =?, PET_DESCRIPTION=?, BREED_ID =? WHERE PET_ID="+PID;
        Connection connection2 = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement2 = connection2.prepareStatement(update);
        preparedStatement2.setString(1,breedCode);
        preparedStatement2.setString(2,breedDesc);
        preparedStatement2.setInt(3,b);
        preparedStatement2.execute();
        preparedStatement2.close();
        connection2.close();
    }

    public void colorSelection()throws SQLException{
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

        String update ="UPDATE PET_CLR SET CLR_ID =?, PET_CLR_CODE=?, PET_CLR=? WHERE PET_ID="+PID;
        Connection connection = DbHelper.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        preparedStatement.setInt(1,petColor.getSelectionModel().getSelectedIndex()+1);
        preparedStatement.setString(2,clrCode);
        preparedStatement.setString(3,clrDesc);
        preparedStatement.execute();
        preparedStatement.close();
        connection.close();
    }

    public void breedFinder() throws SQLException {
        petType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ptID = petType.getSelectionModel().getSelectedIndex() + 1;
            try {
                String sql = "SELECT BREED_ID, BREED_NAME FROM BREED WHERE PET_TYPE_ID =" + ptID;
                Connection connection = DbHelper.getInstance().getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql);
                ResultSet rs = psmt.executeQuery();
                ObservableList<String> breed = FXCollections.observableArrayList();
                while (rs.next()) {
                    breed.add(rs.getString("BREED_ID") + "_" + rs.getString("BREED_NAME"));
                }
                petBreed.setItems(breed);
                petBreed2.setItems(breed);
                psmt.close();
                connection.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    public int petWeightGrpChecker(int i){
        if( i >= 0 && i <= 20 ){return 1;}
        if(i >= 21 && i <= 45){return 2;}
        if(i >= 46 && i <= 59){return 3;}
        if(i >= 60 && i <= 99){return 4;}
        else {return 5;}
    }
}