package Controllers;

import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NavigationController implements SystemController {

    private int id;
    private MyDBConnectivity database = new MyDBConnectivity();

    public NavigationController() throws SQLException {
    }

    public void goHome(ActionEvent event) throws IOException, SQLException {
        Statement stmt = database.getStatement();
        ResultSet rs = stmt.executeQuery("SELECT Type FROM SysAccount WHERE Code="+id+";");
        if (rs.next()) {
            switch (rs.getInt(1)) {
                case 0:
                    goAdvisorHome(event);
                    break;
                case 1:
                    goManagerHome(event);
                    break;
                case 2:
                    goAdminHome(event);
            }
        }
    }

    public void goManagerHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/manager.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();

    }
    public void goAdvisorHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/advisor.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();

    }
    public void goAdminHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/admin.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();

    }
    public void goLogout(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/login.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        database = db;
    }
}
