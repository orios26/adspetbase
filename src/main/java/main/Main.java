package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main  extends Application{

    public static BorderPane root = new BorderPane();
    public static BorderPane getRoot(){return root;}

    @Override
    public void start(Stage primaryStage) throws Exception{
        MenuBar navBar = FXMLLoader.load(getClass().getResource("fxmlAssets/MyMenus.fxml"));
        AnchorPane content = FXMLLoader.load(getClass().getResource("fxmlAssets/PaneOne.fxml"));

        //constructing our scene using the static root
        root.setTop(navBar);
        root.setCenter(content);
        primaryStage.setTitle("PetBase");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("fx.css").toExternalForm());
        primaryStage.setScene(scene);

        //primaryStage.getIcons().add(new Image("dog-paw.png"));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, IOException{
        DbHelper dbHelper = DbHelper.getInstance();
        dbHelper.init();
        launch(args);
        //dbHelper.close();
    }
}
