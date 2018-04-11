package main;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewPetController {

    @FXML
    TableView<Pet> tblpet;
    @FXML
    TableColumn<Pet, Integer> petId;
    TableColumn<Pet, String> petName;
    TableColumn<Pet, String> ownerFirst;
    TableColumn<Pet, String> ownerLast;

}
