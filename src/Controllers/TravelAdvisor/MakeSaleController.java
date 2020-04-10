package Controllers.TravelAdvisor;

import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class MakeSaleController implements SystemController{

    private MyDBConnectivity database;
    private int id;

    @FXML
    private TextField blankID;
    @FXML
    private TextField localTax;
    @FXML
    private TextField otherTax;
    @FXML
    private ComboBox<String> alias;
    @FXML
    private ComboBox<String> commission;
    @FXML
    private TextField payment;
    @FXML
    private ComboBox<String> exchangeCode;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField cardType;
    @FXML
    private Text message;

    public MakeSaleController() {}

    public void onClickMakeCashSale() throws SQLException {
        CallableStatement stmt = database.call("{call MakeSaleCash(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(blankID.getText()));
            stmt.setInt(2, id);
            stmt.setFloat(3, Float.parseFloat(localTax.getText()));
            stmt.setFloat(4, Float.parseFloat(otherTax.getText()));
            try {
                stmt.setString(5, alias.getValue());
            } catch (NullPointerException e) {
                System.out.println("casual customer");
                stmt.setString(5, null);
            }
            stmt.setFloat(6, Float.parseFloat(commission.getValue().substring(0, commission.getValue().length()-2))/100);
            stmt.setFloat(7, Float.parseFloat(payment.getText()));
            stmt.setString(8, exchangeCode.getValue());
            stmt.setDate(9, Date.valueOf(LocalDate.now()));
            stmt.registerOutParameter(10, Types.VARCHAR);
            stmt.execute();
            message.setText(stmt.getString(10));
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Broken");
        } finally {
            stmt.close();
        }
    }

    public void onClickMakeCardSale() throws SQLException {
        /*
        IN BlankID bigint,
                                     IN ICode int,
                                     IN ILocalTax float,
                                     IN IOtherTax float,
                                     IN IPayment float,
                                     IN IAlias varchar(10),
                                     IN Commission float,
                                     IN ICardNumber bigint,
                                     IN ICardType varchar(10),
                                     IN IExchangeCode varchar(3),
                                     IN ICurrentDate Date,
                                     OUT Response varchar(255))
         */
        CallableStatement stmt = database.call("{call MakeSaleCard(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(blankID.getText()));
            stmt.setInt(2, id);
            stmt.setFloat(3, Float.parseFloat(localTax.getText()));
            stmt.setFloat(4, Float.parseFloat(otherTax.getText()));
            stmt.setFloat(5, Float.parseFloat(payment.getText()));
            try {
                stmt.setString(6, alias.getValue());
            } catch (NullPointerException e) {
                System.out.println("casual customer");
                stmt.setString(6, null);
            }
            stmt.setFloat(7, Float.parseFloat(commission.getValue().substring(0, commission.getValue().length()-2))/100);
            stmt.setLong(8, Long.parseLong(cardNumber.getText()));
            stmt.setString(9, cardType.getText());
            stmt.setString(10, exchangeCode.getValue());
            stmt.setDate(11, Date.valueOf(LocalDate.now()));
            stmt.registerOutParameter(12, Types.VARCHAR);
            stmt.execute();
            message.setText(stmt.getString(12));
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Broken");
        } finally {
            stmt.close();
        }
    }

    public void onClickMakeDelayedSale() throws SQLException {
        CallableStatement stmt = database.call("{call MakeSaleDelayed(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(blankID.getText()));
            stmt.setInt(2, id);
            stmt.setFloat(3, Float.parseFloat(localTax.getText()));
            stmt.setFloat(4, Float.parseFloat(otherTax.getText()));
            try {
                stmt.setString(5, alias.getValue());
            } catch (NullPointerException e) {
                System.out.println("casual customer");
                stmt.setString(5, null);
            }
            stmt.setFloat(6, Float.parseFloat(commission.getValue().substring(0, commission.getValue().length()-2))/100);
            stmt.setFloat(7, Float.parseFloat(payment.getText()));
            stmt.setString(8, exchangeCode.getValue());
            stmt.setDate(9, Date.valueOf(LocalDate.now()));
            stmt.registerOutParameter(10, Types.VARCHAR);
            stmt.execute();
            message.setText(stmt.getString(10));
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Broken");
        } finally {
            stmt.close();
        }
    }

    public void onClickBlanksCancel(javafx.event.ActionEvent event) throws IOException {
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

    public void configureMenus() throws SQLException {
        Statement stmt = database.getStatement();
        ObservableList<String> cOptions = FXCollections.observableArrayList();
        ObservableList<String> aOptions = FXCollections.observableArrayList();
        ObservableList<String> eOptions = FXCollections.observableArrayList();
        try {
            ResultSet rs = stmt.executeQuery("SELECT Rate FROM CommissionRates WHERE Active=TRUE;");
            while(rs.next()) {
                cOptions.add((rs.getFloat("Rate") * 100 )+"%");
            }
            rs.close();
            rs = stmt.executeQuery("SELECT Alias FROM CustomerAccount;");
            while(rs.next()) {
                aOptions.add(rs.getString("Alias"));
            }
            rs.close();
            rs = stmt.executeQuery("SELECT Code FROM ExchangeRates GROUP BY Code;");
            while(rs.next()) {
                eOptions.add(rs.getString("Code"));
            }
        } finally {
            stmt.close();
            commission.setItems(cOptions);
            alias.setItems(aOptions);
            exchangeCode.setItems(eOptions);
        }
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        this.database = db;
    }
}
