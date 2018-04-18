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
    public static int cid,pid,oid,olid, ptid;
    public static void setPtid(int i){ptid = i;}
    public static int getPtid(){return ptid;}
    public static void setCid(int i){cid = i;}
    public static void setPid(int i){pid = i;}
    public static int getCid(){return cid;}
    public static int getPid(){return pid;}
    public static int getOid(){return oid;}
    public static int getOlid(){return olid;}


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

        //primaryStage.getIcons().add(new Image("C:/Users/Oti/IdeaProjects/target/classes/main/imageAssets/dog-paw.png"));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException, IOException{
        DbHelper dbHelper = DbHelper.getInstance();
        dbHelper.init();
        launch(args);
        //dbHelper.close();
    }
}
