import Controllers.TravelAdvisor.TravelAdvisorController;
import DBConnect.MyDBConnectivity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GUI/Advisor/advisor.fxml"));
        Parent root = fxmlLoader.load();
        TravelAdvisorController sys = fxmlLoader.getController();
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
        sys.setId(211);
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