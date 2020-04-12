package Controllers.OfficeManager;

import Controllers.NavigationController;
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
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OfficeManagerController extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database;

    @FXML
    private Text message;

    public OfficeManagerController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void onLogin() throws SQLException {
        List<String> ls = new ArrayList<>();
        Statement stmt = database .getStatement();
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -30);
            ResultSet rs = stmt.executeQuery("SELECT CustomerAlias FROM Sale WHERE PaymentType=0 AND SaleDate<="+ cal.getTime().getDate() +";");
            while (rs.next()) {
                ls.add(rs.getString(1));
            }
            message.setText("Customers with overdue payments:");
            for (String n : ls) {
                message.setText(message.getText()+" "+n);
            }
        } finally {
            stmt.close();
        }
    }

    public void blankList(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/blankStock.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void onClickCustomerAccounts(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/customerAccountsManager.fxml"));
        Parent homeView = fxmlloader.load();
        CustomerAccountsManager sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        sys.loadCustomerAccounts();
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }
    public void onClickTravelAdvisors(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/travelAdvisors.fxml"));
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

    public void onClickAssignBlanks(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/assignBlanks.fxml"));
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

    public void onClickSalesReport(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/saleReportGlobal.fxml"));
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
}


