package Controllers.SystemAdmin;

import Controllers.NavigationController;
import Controllers.SystemAdmin.SystemAdminController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SystemAdminController extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database;

    @FXML
    private Text message;

    public SystemAdminController() throws SQLException {
    }

    public void onClickAddBlanks(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/addBlanks.fxml"));
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
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/blankStock.fxml"));
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
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/turnover.fxml"));
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

    public void sysAccounts(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/account.fxml"));
        Parent homeView = fxmlloader.load();
        SysAccountController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        Scene homeScene = new Scene(homeView);
        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void backupDatabase(){
        Process p = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            // change username (-u) and path.
            p = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump --user=root --password=giratina950 AirVia -r C:\\Users\\jackn\\IdeaProjects\\nine_ATS\\AirVia.sql");

            int processComplete = p.waitFor();

            if (processComplete == 0) {

                message.setText("Backup created successfully!");

            } else {
                message.setText("Couldn't backup db");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void restoreDatabase(){
        Process p = null;

        try {
            Runtime runtime = Runtime.getRuntime();
            // change username (-u) and path.
            p = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql --user=root --password=giratina950 AirVia -r C:\\Users\\jackn\\IdeaProjects\\nine_ATS\\AirVia.sql");

            int processComplete = p.waitFor();

            if (processComplete == 0) {

                message.setText("Restored successfully");

            } else {
                message.setText("Couldn't restore db");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
