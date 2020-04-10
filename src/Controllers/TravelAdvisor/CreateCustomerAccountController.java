package Controllers.TravelAdvisor;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;

import entities.Blank;
import entities.CustomerAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class CreateCustomerAccountController extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database;


    @FXML
    private TextField alias;
@FXML
    private TextField email;
@FXML
    private TextField firstName;
@FXML
    private TextField lastName;
@FXML
    private ComboBox<String> type;
@FXML
    private Text message;

    @FXML
    private TableView<CustomerAccount> customerAccountTable;
    @FXML
    private TableColumn<CustomerAccount, String> aliasColumn;
    @FXML
    private TableColumn<CustomerAccount, String> emailColumn;
    @FXML
    private TableColumn<CustomerAccount, String> firstNameColumn;
    @FXML
    private TableColumn<CustomerAccount, String> lastNameColumn;
    @FXML
    private TableColumn<CustomerAccount, String> typeColumn;
    @FXML
    private TableColumn<CustomerAccount, Integer> discountID;
    @FXML
    private TextField searchID;




    public CreateCustomerAccountController()  throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void initialize() throws SQLException {
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        discountID.setCellValueFactory(new PropertyValueFactory<>("discountID"));

    }

    public void createCustomerAccount(ActionEvent event) throws SQLException {

        String addCustomer = "{call AddCustomer(?, ?, ?, ?, ?, ?)}";
        CallableStatement cs = database.call(addCustomer);
        cs.setString(1, alias.getText());
        cs.setString(2, email.getText());
        cs.setString(3, firstName.getText());
        cs.setString(4, lastName.getText());
        cs.setInt(5, 1);

        cs.registerOutParameter(6, Types.VARCHAR);
        cs.execute();
        message.setText(cs.getString(6));
    }
    public void loadCustomerAccounts(ActionEvent event) throws SQLException {
        System.out.println("pressed");
        try {
            customerAccountTable.getItems().clear();
        }
        catch (NullPointerException e){

        }

        // get accounts
        Statement stmt = database.getStatement();

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM CustomerAccount;");
        try {
            // create blank objects from each record
            addCustomerAccounts(resultSet);
        } catch (Exception e) {
            System.out.println("erro called");
            System.err.println(e.getMessage());
        } finally {
            stmt.close();
        }


    }
    public void addCustomerAccounts(ResultSet resultSet) throws SQLException {
        ObservableList<CustomerAccount> customerAccounts = FXCollections.observableArrayList();

        String tempAlias;
        String tempEmail;
        String tempFirstName;
        String tempLastName;
        String tempType = " ";
        int tempdiscountID;


        while (resultSet.next()) {
            tempAlias = resultSet.getString("Alias");
            tempEmail = resultSet.getString("CustomerEmail");

            try {
                tempdiscountID = resultSet.getInt("DiscountID");
            } catch (NullPointerException e) {
                tempdiscountID = 0;
            }
            if (resultSet.getInt("Type") == 1) tempType="Regular";
            else if (resultSet.getInt("Type") == 2) tempType="Valued";
            tempFirstName = resultSet.getString("FirstName");
            tempLastName = resultSet.getString(("LastName"));
            CustomerAccount newAccount = new CustomerAccount(tempAlias, tempEmail, tempFirstName, tempLastName, tempType, tempdiscountID);
            customerAccounts.add(newAccount);
        }
            customerAccountTable.getItems().addAll(customerAccounts);


    }



}
