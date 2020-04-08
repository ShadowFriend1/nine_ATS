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
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficeManagerController {

    MyDBConnectivity database = new MyDBConnectivity();


    public OfficeManagerController() throws SQLException {
    }


    public void accessRefundLog(){

    }
    public void allocateBlanks(String email, int blankID) throws SQLException {
        String query = "UPDATE BlankStock SET TravelAgentAgentEmail= " + email +
                "WHERE ID=" + blankID + ";";
        database.update(query);
    }
    public void generateSalesReport(){

    }
    public void giveDiscount(String alias, float discount, String discountType) throws SQLException {
        String query = "UPDATE CustomerAccount SET Discount=" + discount + " SET DiscountType=" + discountType  + " WHERE alias=" + alias + ";";
        database.update(query);
    }
    public void setCurrencyExchangeRate(){

    }
    public void setCustomerType(String alias, int type) throws SQLException {
        String query = "UPDATE CustomerAccount SET Type=" + type + " WHERE alias=" + alias + ";";
        database.update(query);


    }
    public ResultSet viewTravelAgentDetails(int id) throws SQLException {
        String query = "GET * FROM TravelAgent WHERE TravelAgentCode=" + id + ";";
        ResultSet resultSet = database.query(query);
        return resultSet;
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



