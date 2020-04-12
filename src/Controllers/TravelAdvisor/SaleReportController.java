package Controllers.TravelAdvisor;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import entities.Blank;
import entities.SaleReports.IndividualSaleReport;
import entities.StockReports.StockElementAdvisor;
import entities.StockReports.StockElementAgent;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleReportController extends NavigationController implements SystemController {

    private int id = 0;
    private MyDBConnectivity database;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView<IndividualSaleReport> saleTable;
    @FXML
    private TableColumn<IndividualSaleReport, String> blankIDColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> USDFareColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> fareColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> localTaxColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> otherTaxColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> cashColumn;
    @FXML
    private TableColumn<IndividualSaleReport, String> cardNameColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Long> cardNumberColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> cardAmountColumn;
    @FXML
    private TableColumn<IndividualSaleReport, String> commissionColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> commissionAmountColumn;
    @FXML
    private TableColumn<IndividualSaleReport, String> remittanceColumn;
    @FXML
    private Text message;


    public SaleReportController() throws SQLException{

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

    public void initialize() {
        blankIDColumn.setCellValueFactory((new PropertyValueFactory<>("blankID")));
        USDFareColumn.setCellValueFactory((new PropertyValueFactory<>("USDFare")));
        fareColumn.setCellValueFactory((new PropertyValueFactory<>("fare")));
        localTaxColumn.setCellValueFactory((new PropertyValueFactory<>("localTax")));
        otherTaxColumn.setCellValueFactory((new PropertyValueFactory<>("otherTax")));
        cashColumn.setCellValueFactory((new PropertyValueFactory<>("cash")));
        cardNameColumn.setCellValueFactory((new PropertyValueFactory<>("cardName")));
        cardNumberColumn.setCellValueFactory((new PropertyValueFactory<>("cardNumber")));
        cardAmountColumn.setCellValueFactory((new PropertyValueFactory<>("cardAmount")));
        commissionColumn.setCellValueFactory((new PropertyValueFactory<>("commission")));
        commissionAmountColumn.setCellValueFactory((new PropertyValueFactory<>("commissionAmount")));
        remittanceColumn.setCellValueFactory((new PropertyValueFactory<>("remittance")));
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        database = db;
    }


    public void onClickLoadInterline(ActionEvent event) throws SQLException {
        saleTable.getItems().clear();
        ObservableList<IndividualSaleReport> sales = FXCollections.observableArrayList();

        String blankID;
        float USDFare;
        float fare;
        float localTax;
        float otherTax;
        float cash;
        String cardName;
        long cardNumber;
        float cardAmount;
        String commission;
        float commissionAmount;
        String remittance = "";

        CallableStatement stmt = database.call("{call IndInterReport(?, ?, ?)}");
        try {
            stmt.setInt(1, id);
            stmt.setDate(2, Date.valueOf(startDate.getValue()));
            stmt.setDate(3, Date.valueOf(endDate.getValue()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                blankID = rs.getString(1);
                USDFare = rs.getFloat(2);
                fare = rs.getFloat(3);
                localTax = rs.getFloat(4);
                otherTax = rs.getFloat(5);
                try {
                    cash = rs.getFloat(6);
                } catch (NullPointerException e) {
                    cash = 0;
                }
                try {
                    cardName = rs.getString(7);
                    cardNumber = rs.getLong(8);
                    cardAmount = rs.getFloat(9);
                } catch (NullPointerException e) {
                    cardName = "";
                    cardNumber = 0;
                    cardAmount = 0;
                }
                commission = (rs.getFloat(10) * 100 + "%");
                commissionAmount = rs.getFloat(11);
                if (rs.getString(1).equals("Totals")) {
                    remittance = String.valueOf(cardAmount-commissionAmount);
                }
                IndividualSaleReport s = new IndividualSaleReport(blankID, USDFare, fare, localTax, otherTax, cash, cardName, cardNumber, cardAmount, commission, commissionAmount, remittance);
                sales.add(s);
            }
        } catch (NullPointerException e) {
            message.setText("No Sales in Range");
        } finally {
            saleTable.getItems().addAll(sales);
            stmt.close();
        }
    }

    public void onClickLoadDomestic(ActionEvent event) throws SQLException {
        saleTable.getItems().clear();
        ObservableList<IndividualSaleReport> sales = FXCollections.observableArrayList();

        String blankID;
        float USDFare = 0;
        float fare;
        float localTax;
        float otherTax = 0;
        float cash;
        String cardName;
        long cardNumber;
        float cardAmount;
        String commission;
        float commissionAmount;
        String remittance = "";

        CallableStatement stmt = database.call("{call IndDomReport(?, ?, ?)}");
        try {
            stmt.setInt(1, id);
            stmt.setDate(2, Date.valueOf(startDate.getValue()));
            stmt.setDate(3, Date.valueOf(endDate.getValue()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                blankID = rs.getString("BlankID");
                fare = rs.getFloat("Fare");
                localTax = rs.getFloat("LocalTax");
                try {
                    cash = rs.getFloat("Cash");
                } catch (NullPointerException e) {
                    cash = 0;
                }
                try {
                    cardName = rs.getString("CardName");
                    cardNumber = rs.getLong("CardNumber");
                    cardAmount = rs.getFloat("CardAmount");
                } catch (NullPointerException e) {
                    cardName = "";
                    cardNumber = 0;
                    cardAmount = 0;
                }
                commission = (rs.getFloat("Commission") * 100 + "%");
                commissionAmount = rs.getFloat("CommissionAmount");
                if (rs.getString("BlankID").equals("Totals")) {
                    remittance = String.valueOf(cardAmount-commissionAmount);
                }
                IndividualSaleReport s = new IndividualSaleReport(blankID, USDFare, fare, localTax, otherTax, cash, cardName, cardNumber, cardAmount, commission, commissionAmount, remittance);
                sales.add(s);
            }
        } catch (NullPointerException e) {
            message.setText("No Sales in Range");
        } finally {
            saleTable.getItems().addAll(sales);
            stmt.close();
        }
    }
}
