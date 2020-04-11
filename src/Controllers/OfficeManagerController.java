package Controllers;

import DBConnect.MyDBConnectivity;
import entities.Blank;
import entities.LatePayment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

public class OfficeManagerController extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database = new MyDBConnectivity();


    @FXML
    private TableColumn<LatePayment, Integer> saleIDColumn;
    @FXML
    private TableColumn<LatePayment, String> aliasColumn;

    @FXML
    TableView<LatePayment> latePaymentTable;



    public OfficeManagerController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void initialize() throws ParseException, SQLException {
        saleIDColumn.setCellValueFactory(new PropertyValueFactory<>("saleID"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));

        latePaymentTable.getItems().clear();





        // get late payments
        loadLatePayments();


        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2020,03,10);

        if (now.minusDays(30).isAfter(date)){
            System.out.println("Late payment");
        }
    }

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

    public void loadLatePayments() throws SQLException {
        String query = "SELECT SaleID, CustomerAlias, SaleDate FROM Sale WHERE PaymentType = 0;";
        Statement statement = database.getStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ObservableList<LatePayment> latePayments = FXCollections.observableArrayList();

        LocalDate nowDate = LocalDate.now();
        LocalDate saleDate;
        while (resultSet.next()){
            saleDate = resultSet.getDate("SaleDate").toLocalDate();
            if (nowDate.minusDays(30).isAfter(saleDate)){
                LatePayment latePayment = new LatePayment(resultSet.getInt("SaleID"), resultSet.getString("CustomerAlias"));
                latePayments.add(latePayment);
            }

        }
        latePaymentTable.getItems().addAll(latePayments);

    }

    public void blankList(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/blankStock.fxml"));
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
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/customerAccountsManager.fxml"));
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
    public void onClickTravelAdvisors(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Manager/travelAdvisors.fxml"));
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
}


