package Controllers.TravelAdvisor;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;

public class RefundController extends NavigationController implements SystemController {

    private int id;
    private MyDBConnectivity database;


    @FXML
    private TextField blankID;
    @FXML
    private Text message;

    public RefundController() throws SQLException {

    }

    /*
    SaleID              int AUTO_INCREMENT,
    TravelAgentCode     int   DEFAULT 0 NOT NULL,
    CustomerAlias       varchar(10),
    CommissionRatesRate float DEFAULT 0 NOT NULL,
    BlankStockID        bigint          NOT NULL,
    fee                 float           NOT NULL,
    payment             float           NOT NULL,
    PaymentType         int             NOT NULL,
    ExchangeRatesDate   date,
    ExchangeRatesCode   varchar(3),
    CardNumber          bigint,
    CardName            varchar(255),
    SaleDate            date            NOT NULL,
    LocalTax            float,
    OtherTax            float,
     */

    public void onClickRefundBlank(javafx.event.ActionEvent event) throws SQLException, IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Refund_Log.txt", true));
        CallableStatement stmt = database.call("{call RefundSale(?)}");
        try {
            stmt.setLong(1, Long.parseLong(blankID.getText()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                writer.write("Sale ID: " + rs.getInt("SaleID") + " Refunded, Date: " + LocalDate.now() + "\n" +
                        "Travel Agent Code: " + rs.getInt("TravelAgentCode") + "\n" +
                        "Customer Alias: " + rs.getString("CustomerAlias") + "\n" +
                        "Commissions Rate: " + rs.getFloat("CommissionRatesRate") + "\n" +
                        "Blank ID: " + rs.getLong("BlankStockID") + "\n" +
                        "Total Fee: " + rs.getFloat("fee") + "\n" +
                        "Travel Payment: " + rs.getFloat("payment") + "\n" +
                        "Local Tax: " + rs.getFloat("LocalTax") + "\n" +
                        "Other Tax: " + rs.getFloat("OtherTax") + "\n" +
                        "Sale Date: " + rs.getDate("SaleDate") + "\n");
                switch (rs.getInt("PaymentType")) {
                    case 2:
                        writer.write("Payment Type: Card\n" +
                                "Card Type: " + rs.getString("CardName") + "\n" +
                                "Card Number: " + rs.getLong("CardNumber") + "\n" +
                                "Refund Completed\n\n");
                        break;
                    case 1:
                        writer.write("Payment Type: Cash\n" +
                                "Refund Completed\n\n");
                        break;
                    default:
                        writer.write("Payment Type: *NOT PAID*\n" +
                                "Refund Completed\n\n");
                }
                message.setText("Successfully Refunded");
            } else {
                message.setText("Refund Failed");
            }
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Invalid Blank ID");
        } finally {
            writer.close();
            stmt.close();
        }
    }

    public void onClickHome(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/SystemAdmin/admin.fxml"));
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

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        this.database = db;
    }


}
