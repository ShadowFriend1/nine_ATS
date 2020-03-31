import DBConnect.DBConnectivity;
import DBConnect.MyDBConnectivity;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/index.fxml"));
        primaryStage.setTitle("Test");
        scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }










    public static void main(String[] args) throws SQLException {


        launch(args);
        DBConnectivity database = null;
        // TODO remove need for execution arguments and implement database searching algorithm.
        try {
            switch (args.length) {
                case 3:
                    database = new MyDBConnectivity(args[0], args[1], args[2]);
                    break;
                case 0:
                    database = new MyDBConnectivity();
                    break;
                default:
                    System.out.println("incorrect number of arguments");
            }
        } finally {
            if (database!=null) database.close();
        }
    }
}