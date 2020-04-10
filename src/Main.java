import Controllers.BlankListController;
import Controllers.SystemController;
import DBConnect.DBConnectivity;
import DBConnect.MyDBConnectivity;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/login.fxml"));
        Parent root = fxmlLoader.load();
        SystemController sys = fxmlLoader.getController();
        MyDBConnectivity database = new MyDBConnectivity();
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("database connection closed");
            try {
                database.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        sys.setDatabaseC(database);
        primaryStage.setTitle("AirVia");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }










    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}