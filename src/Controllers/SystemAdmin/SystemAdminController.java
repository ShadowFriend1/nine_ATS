package Controllers.SystemAdmin;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SystemAdminController extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database;

    public SystemAdminController() {
    }

    public void onClickAddBlanks(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/SystemAdmin/addBlanks.fxml"));
        Parent homeView = fxmlloader.load();
        AddBlanksController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        Scene homeScene = new Scene(homeView);
        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void accessFullStock(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/SystemAdmin/blankStock.fxml"));
        Parent homeView = fxmlloader.load();
        BlankListController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.loadBlanks();
        Scene homeScene = new Scene(homeView);
        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void accessStockTurnover(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/SystemAdmin/turnover.fxml"));
        Parent homeView = fxmlloader.load();
        StockTurnoverController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        Scene homeScene = new Scene(homeView);
        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }
}
