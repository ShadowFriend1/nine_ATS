package Controllers.TravelAdvisor;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import entities.Blank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class LatePaymentController extends NavigationController implements SystemController {

    private MyDBConnectivity database;
    private int id;

    @FXML
    private ComboBox<String> customerAlias;
    @FXML
    private ComboBox<Long> blankID;
    @FXML
    private TextField cardName;
    @FXML
    private TextField cardNumber;
    @FXML
    private Text message;
    @FXML
    private Text message2;

    public LatePaymentController() throws SQLException {
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        this.database = db;
    }

    public void setupList(String alias) throws SQLException {
        ObservableList<Long> list = FXCollections.observableArrayList();

        Statement stmt = database.getStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT BlankStockID FROM Sale WHERE CustomerAlias='" + alias + "' AND PaymentType=0;");
            while (rs.next()) {
                list.add(rs.getLong(1));
            }
        } finally {
            blankID.setItems(list);
            stmt.close();
        }
    }

    public void onClickFindSales(ActionEvent event) throws SQLException, IOException {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Advisor/LatePayment/latePaymentSale.fxml"));
            Parent homeView = fxmlloader.load();
            LatePaymentController sys = fxmlloader.getController();
            sys.setDatabaseC(database);
            sys.setId(id);
            sys.setupList(customerAlias.getValue());
            Scene homeScene = new Scene(homeView);

            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();
        } catch (NullPointerException e) {
            message.setText("No Alias Selected");
        }
    }

    public void setupList() throws SQLException {
        ObservableList<String> alias = FXCollections.observableArrayList();

        Statement stmt = database.getStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT CustomerAlias FROM Sale WHERE PaymentType=0 GROUP BY CustomerAlias");
            while (rs.next()) {
                alias.add(rs.getString(1));
            }
        } catch (NullPointerException e) {
            message.setText("");
        } finally {
            customerAlias.setItems(alias);
            stmt.close();
        }
    }

    public void onClickMakeCashSale(ActionEvent event) throws SQLException, IOException {
        Statement stmt = database.getStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT CustomerAlias FROM Sale WHERE BlankStockID="+ blankID.getValue() +";");
            if (rs.next()) {
                String alias = rs.getString(1);
                int b = stmt.executeUpdate("UPDATE Sale, CustomerAccount SET PaymentType=1, outstandingBalance=outstandingBalance-fee WHERE BlankStockID=" + blankID.getValue() + " AND Alias='"+alias+"';");
                if (b > 0) {
                    message2.setText("Payment Successful");
                } else {
                    message2.setText("Payment Failed");
                }
            }
        } catch (NullPointerException e) {
            message2.setText("Field Broken");
        } finally {
            stmt.close();
        }
    }

    @Override
    public void goAdvisorHome(ActionEvent event) throws IOException, SQLException {
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

    public void onClickMakeCardSale(ActionEvent event) throws SQLException, IOException {
        Statement stmt = database.getStatement();
        try {
            int b = stmt.executeUpdate("UPDATE Sale, CustomerAccount SET PaymentType=2, CardName='"+cardName.getText()+"', CardNumber="+Long.parseLong(cardNumber.getText())+", outstandingBalance=outstandingBalance-fee WHERE BlankStockID=" + blankID.getValue() + ";");
            if (b > 0) {
                message2.setText("Payment Successful");
            } else {
                message2.setText("Payment Failed");
            }
        } catch (NullPointerException e) {
            message2.setText("Field Broken");
        } finally {
            stmt.close();
        }
    }
}
