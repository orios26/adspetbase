package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


import java.io.IOException;


public class MyMenuController {


    static BorderPane informationPane = new BorderPane();

    static BorderPane getInformationPane(){ return informationPane;}

    @FXML // fx:id = "home"
    private Menu home;

    @FXML //fx:id = "newClientInformation"
    private MenuItem newClientInformation;

    @FXML // fx:id = "homemenuitem"
    private MenuItem homemenuitem;

    @FXML // fx:id = "addCustomer"
    private MenuItem addCustomer;

    @FXML // fx:id = "editCustomer"
    private MenuItem editCustomer;

    @FXML // fx:id = "deleteCustomer"
    private MenuItem deleteCustomer;

    @FXML // fx:id = "viewCustomer"
    private MenuItem viewCustomer;

    @FXML // fx:id = "addPet"
    private MenuItem addPet;

    @FXML // fx:id = "editPet"
    private MenuItem editPet;

    @FXML // fx:id = "deletePet"
    private MenuItem deletePet;

    @FXML // fx:id = "viewPet"
    private MenuItem viewPet;

    @FXML // fx:id = "createBoarding"
    private MenuItem createBoarding;

    @FXML // fx:id = "editBoarding"
    private MenuItem editBoarding;

    @FXML // fx:id = "cancelBoarding"
    private MenuItem cancelBoarding;

    @FXML // fx:id = "viewBoarding"
    private MenuItem viewBoarding;

    @FXML // fx:id = "createGrooming"
    private MenuItem createGrooming;

    @FXML // fx:id = "editGrooming"
    private MenuItem editGrooming;

    @FXML // fx:id = "cancelGrooming"
    private MenuItem cancelGrooming;

    @FXML // fx:id = "viewGrooming"
    private MenuItem viewGrooming;

    @FXML // fx:id = "checkinDaycare"
    private MenuItem checkinDaycare;

    @FXML // fx:id = "checkoutDaycare"
    private MenuItem checkoutDaycare;

    @FXML // fx:id = "viewDaycareroom"
    private MenuItem viewDaycareroom;

    @FXML // fx:id = "manageEmployees"
    private MenuItem manageEmployees;

    @FXML // fx:id = "manageDaycareRooms"
    private MenuItem  manageDaycareRooms;

    @FXML // fx:id = "manageKennels"
    private MenuItem  manageKennels;

    @FXML
    void switchHome(ActionEvent event) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/PaneOne.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void newCustomerInformationStart(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/NewCustomerScreen.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);

//            AnchorPane pane = new AnchorPane();
//            pane.getChildren().add(FXMLLoader.load(getClass().getResource("fxmlAssets/newCustomerScreen.fxml")));
//            BorderPane borderPane = MyMenuController.getInformationPane();
//            borderPane.setCenter(pane);
//            //Scene scene = borderPane.getScene();
//            //scene.setRoot(borderPane);
//            Scene scene = new Scene(borderPane);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initModality(Modality.APPLICATION_MODAL);
//            if(event.getTarget()==newClientInformation)
//                stage.showAndWait();
//            else
//                stage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchActiveClientList(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/activeClientReport.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchInactiveClientList(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/inactiveClientReport.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchServicedThisMonth(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/customersServicedThisMonth.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchActiveEmployeeJobs(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/employeesOnActiveOrders.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void switchKennelReports(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/kennelReport.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchMicrochippedPets(ActionEvent event){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/microchippedPets.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switcheditCustomer(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editCustomerInformation.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchaddCustomer(ActionEvent event){
        try{
            AnchorPane addCustomer = FXMLLoader.load(getClass().getResource("fxmlAssets/addCustomerScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(addCustomer);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void switchdeleteCustomer(ActionEvent event){
        try{
            AnchorPane deleteCustomer = FXMLLoader.load(getClass().getResource("deleteCustomerScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(deleteCustomer);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void switchviewCustomer(ActionEvent event){
        try{
            AnchorPane viewCustomer = FXMLLoader.load(getClass().getResource("fxmlAssets/viewCustomerInformation.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(viewCustomer);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchaddPet(ActionEvent event){
        try{
            AnchorPane addPet = FXMLLoader.load(getClass().getResource("fxmlAssets/addPetScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(addPet);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switcheditPet(ActionEvent event){
        try{
            AnchorPane editPet = FXMLLoader.load(getClass().getResource("fxmlAssets/editPetScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(editPet);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchdeletePet(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("deletePetScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchviewPet(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewPetInformation.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchcreateBoarding(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/createBoardingScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switcheditBoarding(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("editBoardingScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchcancelBoarding(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("cancelBoardingScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchviewBoarding(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewBoardingAppointments.fxml"));
            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchEditBoarding(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editBoarding.fxml"));
            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchcreateGrooming(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/createGroomingScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchEditGrooming(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editGrooming.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchcancelGrooming(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("cancelGroomingScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchAddMedication(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/addMedicationScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void switchEditMedication(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editPetMedScreen.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void switchViewPetMed(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewPetMed.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchAddVaccination(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/addPetVaccination.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchViewVaccines(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewPetVaccines.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchAddBehavior(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/addPetBehavior.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void switchViewPetBehavior(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewPetBehaviors.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    void switchviewGrooming(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewGroomingAppointments.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchcheckinDaycare(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/daycareCheckIn.fxml"));
            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchcheckoutDaycare(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/daycareCheckOut.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchviewDaycareroom(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewDaycareVisits.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchManageKennels(ActionEvent event) {
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/manageKennels.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    void switchCreateServiceOrder(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/createServiceOrder.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void switchEditServiceOrder(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/editServiceOrder.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void switchViewServiceOrder(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/viewSalesOrders.fxml"));
            BorderPane borderPane = Main.getRoot();
            borderPane.setCenter(pane);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchManageEmployees(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/manageEmployee.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void switchManageDaycareRooms(ActionEvent event){
        try{
            AnchorPane pane = FXMLLoader.load(getClass().getResource("fxmlAssets/manageDaycareRooms.fxml"));

            BorderPane border = Main.getRoot();
            border.setCenter(pane);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
