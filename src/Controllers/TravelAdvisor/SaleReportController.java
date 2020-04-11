package Controllers.TravelAdvisor;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import entities.Blank;
import entities.StockReports.StockElementAdvisor;
import entities.StockReports.StockElementAgent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableView<StockElementAdvisor> advisorTable;
    @FXML
    private TableView<StockElementAgent> agentTable;
    @FXML
    private TableColumn<Blank, Long> recStartColumn;
    @FXML
    private TableColumn<Blank, Long> recEndColumn;
    @FXML
    private TableColumn<Blank, Integer> recAmountColumn;
    @FXML
    private TableColumn<Blank, Integer> codeColumn;
    @FXML
    private TableColumn<Blank, Long> assStartColumn;
    @FXML
    private TableColumn<Blank, Long> assEndColumn;
    @FXML
    private TableColumn<Blank, Integer> assAmountColumn;
    @FXML
    private TableColumn<Blank, Long> soldStartColumn;
    @FXML
    private TableColumn<Blank, Long> soldEndColumn;
    @FXML
    private TableColumn<Blank, Integer> soldAmountColumn;
    @FXML
    private TableColumn<Blank, Integer> advisorFinalColumn;
    @FXML
    private TableColumn<Blank, Integer> finalAmountColumn;


    public SaleReportController() {

    }

    public void initialize() {
        recStartColumn.setCellValueFactory((new PropertyValueFactory<>("recStart")));
        recEndColumn.setCellValueFactory((new PropertyValueFactory<>("recEnd")));
        recAmountColumn.setCellValueFactory((new PropertyValueFactory<>("recAmount")));
        codeColumn.setCellValueFactory((new PropertyValueFactory<>("code")));
        assStartColumn.setCellValueFactory((new PropertyValueFactory<>("assStart")));
        assEndColumn.setCellValueFactory((new PropertyValueFactory<>("assEnd")));
        assAmountColumn.setCellValueFactory((new PropertyValueFactory<>("assAmount")));
        soldStartColumn.setCellValueFactory((new PropertyValueFactory<>("soldStart")));
        soldEndColumn.setCellValueFactory((new PropertyValueFactory<>("soldEnd")));
        soldAmountColumn.setCellValueFactory((new PropertyValueFactory<>("soldAmount")));
        advisorFinalColumn.setCellValueFactory((new PropertyValueFactory<>("advisorFinal")));
        finalAmountColumn.setCellValueFactory((new PropertyValueFactory<>("finalAmount")));
    }

    public void loadReport() throws SQLException {
        advisorTable.getItems().clear();
        agentTable.getItems().clear();

        CallableStatement stmt = database.call("{call StockTurnover(?, ?)}");
        try {
            stmt.setDate(1, Date.valueOf(startDate.getValue()));
            stmt.setDate(2, Date.valueOf(endDate.getValue()));
            ResultSet rs = stmt.executeQuery();

            ObservableList<StockElementAdvisor> elements = FXCollections.observableArrayList();
            ObservableList<StockElementAgent> agentElements = FXCollections.observableArrayList();
            int code;
            long assStart;
            long assEnd;
            int assAmount;
            long soldStart;
            long soldEnd;
            int soldAmount;
            int advisorFinal;
            long finalStart;
            long finalEnd;

            try {
                while (rs.next()) {
                    code = rs.getInt("Code");
                    assStart = rs.getLong("AssStartBlank");
                    assEnd = rs.getLong("AssEndBlank");
                    assAmount = rs.getInt("AssAmount");
                    soldStart = rs.getLong("SoldStartBlank");
                    soldEnd = rs.getLong("SoldEndBLank");
                    soldAmount = rs.getInt("SoldAmount");
                    advisorFinal = rs.getInt("AdvisorAmount");
                    StockElementAdvisor se = new StockElementAdvisor(code, assStart, assEnd, assAmount,
                            soldStart, soldEnd, soldAmount, advisorFinal);
                    elements.add(se);
                }
            } finally {
                advisorTable.getItems().addAll(elements);
            }
            long recStart;
            long recEnd;
            int recAmount;
            int finalAmount;

            try {
                stmt.getMoreResults();
                ResultSet rs2 = stmt.getResultSet();
                while (rs2.next()) {
                    finalAmount = rs2.getInt("FinalAmount");
                    recStart = rs2.getLong("RecStartBlank");
                    recEnd = rs2.getLong("RecEndBlank");
                    recAmount = rs2.getInt("RecAmount");
                    StockElementAgent sa = new StockElementAgent(recStart, recEnd, recAmount, finalAmount);
                    agentElements.add(sa);
                }
            } finally {
                agentTable.getItems().addAll(agentElements);
            }
        } catch (NullPointerException e) {
            System.out.println("date missing");
        } finally {
            stmt.close();
        }
    }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }


}
