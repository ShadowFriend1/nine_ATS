package Controllers.OfficeManager;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class OfficeManagerController extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database;


    public OfficeManagerController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void accessRefundLog(){

    }
    public void onClickAllocateBlanks(javafx.event.ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/OfficeManager/assignBlanks.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        Scene homeScene = new Scene(homeView);
        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }
    public void generateSalesReport(){

    }

    public void giveFixedDiscount(String alias, float value) throws SQLException {
        CallableStatement stmt = database.call("{call AddFixedDiscount(?, ?, ?)}");
        stmt.setString(1, alias);
        stmt.setFloat(2, value);
        stmt.registerOutParameter(3, Types.VARCHAR);
        stmt.execute();
        System.out.println(stmt.getString(3));
    }

    public void giveFlaxibleDiscount(String alias, float value) {

    }
    public void setCurrencyExchangeRate(){

    }
    public void setCustomerType(String alias, int type) throws SQLException {

    }
    public void viewTravelAgentDetails(int id) throws SQLException {
    }
    public void setCommissionRate(int id, float commissionRate){
        String query;
    }

    public void blankList(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/SystemAdmin/blankStock.fxml"));
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

    public void onClickCustomerAccounts(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/customerAccounts.fxml"));
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
}


