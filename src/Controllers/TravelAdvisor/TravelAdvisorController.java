package Controllers.TravelAdvisor;

import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class TravelAdvisorController implements SystemController {

    private MyDBConnectivity database;
    private int id;
    public TravelAdvisorController() {}

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void onLogin() throws SQLException {

    }

    @Override
    public void setId (int id) {
        System.out.println(id);
        this.id = id;
    }

    public void onCLickCreateCustomerAccount(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/customerAccounts.fxml"));
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

    public void onClickMakeSale(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/makeSale.fxml"));
        Parent homeView = fxmlloader.load();
        MakeSaleController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        sys.configureMenus();
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void onClickCheckBlankStock(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/blankStock.fxml"));
        Parent homeView = fxmlloader.load();
        BlankListController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        sys.loadBlanks();
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void onClickRefund(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/refund.fxml"));
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

    public void onClickSalesReport(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/saleReportIndividual.fxml"));
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

    public void onClickLatePayment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/LatePayment/latePaymentFind.fxml"));
        Parent homeView = fxmlloader.load();
        LatePaymentController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        sys.setId(id);
        sys.setupList();
        Scene homeScene = new Scene(homeView);

        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }
}
