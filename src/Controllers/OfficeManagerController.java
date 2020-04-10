package Controllers;

import DBConnect.MyDBConnectivity;
import entities.Blank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class OfficeManagerController implements SystemController {

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
    public void allocateBlanks(int code, long start, long finish, Date currentDate) throws SQLException {
        CallableStatement stmt = database.call("{call AssignBlanks(?, ?, ?, ?, ?)}");
        stmt.setLong(1, start);
        stmt.setLong(2, finish);
        stmt.setInt(3, code);
        stmt.setDate(4, currentDate);
        stmt.registerOutParameter(5, Types.VARCHAR);
        stmt.execute();
        System.out.println(stmt.getString(5));
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
        Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/blankStock.fxml"));
        Scene homeScene = new Scene(homeView);


        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
        }
    }



