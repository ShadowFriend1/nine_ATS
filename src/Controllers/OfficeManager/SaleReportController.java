package Controllers.OfficeManager;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import entities.Blank;
import entities.SaleReports.GlobalSaleReport;
import entities.SaleReports.IndividualSaleReport;
import entities.StockReports.StockElementAdvisor;
import entities.StockReports.StockElementAgent;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

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
    private TableView<GlobalSaleReport> saleTable;
    @FXML
    private TableColumn<IndividualSaleReport, String> codeColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Integer> numColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> fareColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> localTaxColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> otherTaxColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> cashColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> cardAmountColumn;
    @FXML
    private TableColumn<IndividualSaleReport, Float> commissionColumn;
    @FXML
    private TableColumn<IndividualSaleReport, String> remittanceColumn;
    @FXML
    private Text message;


    public SaleReportController() {

    }

    public void initialize() {
        codeColumn.setCellValueFactory((new PropertyValueFactory<>("code")));
        numColumn.setCellValueFactory((new PropertyValueFactory<>("numSales")));
        fareColumn.setCellValueFactory((new PropertyValueFactory<>("payment")));
        localTaxColumn.setCellValueFactory((new PropertyValueFactory<>("localTax")));
        otherTaxColumn.setCellValueFactory((new PropertyValueFactory<>("otherTax")));
        cashColumn.setCellValueFactory((new PropertyValueFactory<>("cash")));
        cardAmountColumn.setCellValueFactory((new PropertyValueFactory<>("card")));
        commissionColumn.setCellValueFactory((new PropertyValueFactory<>("commission")));
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
        ObservableList<GlobalSaleReport> sales = FXCollections.observableArrayList();

        String code;
        int numSales;
        float fare;
        float localTax;
        float otherTax;
        float cash;
        float card;
        float commission;
        String remittance = "";

        CallableStatement stmt = database.call("{call GloIntAdvReport(?, ?)}");
        try {
            stmt.setDate(1, Date.valueOf(startDate.getValue()));
            stmt.setDate(2, Date.valueOf(endDate.getValue()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                code = rs.getString(1);
                numSales = rs.getInt(2);
                fare = rs.getFloat(3);
                localTax = rs.getFloat(4);
                otherTax = rs.getFloat(5);
                try {
                    cash = rs.getFloat(6);
                } catch (NullPointerException e) {
                    cash = 0;
                }
                try {
                    card = rs.getFloat(7);
                } catch (NullPointerException e) {
                    card = 0;
                }
                commission = rs.getFloat(8);
                if (rs.getString(1).equals("Totals")) {
                    remittance = String.valueOf(card-commission);
                }
                GlobalSaleReport s = new GlobalSaleReport(code, numSales, fare, localTax, otherTax, cash, card, commission, remittance);
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
        ObservableList<GlobalSaleReport> sales = FXCollections.observableArrayList();

        String code;
        int numSales;
        float fare;
        float localTax;
        float otherTax = 0;
        float cash;
        float card;
        float commission;
        String remittance = "";

        CallableStatement stmt = database.call("{call GloDomAdvReport(?, ?)}");
        try {
            stmt.setDate(1, Date.valueOf(startDate.getValue()));
            stmt.setDate(2, Date.valueOf(endDate.getValue()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                code = rs.getString(1);
                numSales = rs.getInt(2);
                fare = rs.getFloat(3);
                localTax = rs.getFloat(4);
                try {
                    cash = rs.getFloat(5);
                } catch (NullPointerException e) {
                    cash = 0;
                }
                try {
                    card = rs.getFloat(6);
                } catch (NullPointerException e) {
                    card = 0;
                }
                commission = rs.getFloat(7);
                if (rs.getString(1).equals("Totals")) {
                    remittance = String.valueOf(card-commission);
                }
                GlobalSaleReport s = new GlobalSaleReport(code, numSales, fare, localTax, otherTax, cash, card, commission, remittance);
                sales.add(s);
            }
        } catch (NullPointerException e) {
            message.setText("No Sales in Range");
        } finally {
            saleTable.getItems().addAll(sales);
            stmt.close();
        }
    }

    public void onClickLoadUSDInterline(ActionEvent event) throws SQLException {
        saleTable.getItems().clear();
        ObservableList<GlobalSaleReport> sales = FXCollections.observableArrayList();

        String code;
        int numSales;
        float fare;
        float localTax;
        float otherTax;
        float cash;
        float card;
        float commission;
        String remittance = "";

        CallableStatement stmt = database.call("{call GloIntUSDReport(?, ?)}");
        try {
            stmt.setDate(1, Date.valueOf(startDate.getValue()));
            stmt.setDate(2, Date.valueOf(endDate.getValue()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                code = rs.getString(1);
                numSales = rs.getInt(2);
                fare = rs.getFloat(3);
                localTax = rs.getFloat(4);
                otherTax = rs.getFloat(5);
                try {
                    cash = rs.getFloat(6);
                } catch (NullPointerException e) {
                    cash = 0;
                }
                try {
                    card = rs.getFloat(7);
                } catch (NullPointerException e) {
                    card = 0;
                }
                commission = rs.getFloat(8);
                if (rs.getString(1).equals("Totals")) {
                    remittance = String.valueOf(card-commission);
                }
                GlobalSaleReport s = new GlobalSaleReport(code, numSales, fare, localTax, otherTax, cash, card, commission, remittance);
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
