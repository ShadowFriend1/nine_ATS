package Controllers.OfficeManager;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
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

import java.sql.*;


public class CustomerAccountsManager extends NavigationController implements SystemController {

    private int id;
    MyDBConnectivity database;


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
    private TableColumn<CustomerAccount, Float> outstandingBalanceColumn;

    @FXML
    private TextField searchID;


    @FXML
    private TextField aliasType;
    @FXML
    private TextField aliasDiscount;
    @FXML
    private ComboBox<String> customerType;
    @FXML
    private ComboBox<String> customerDiscount;
    @FXML
    private TextField lowerValue;
    @FXML
    private TextField upperValue;
    @FXML
    private TextField value;
    @FXML
    private Text typeMessage;
    @FXML
    private Text discountMessage;





    public CustomerAccountsManager()  throws SQLException {
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
        outstandingBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("outstandingBalance"));

        ObservableList<String> items1 = FXCollections.observableArrayList();
        ObservableList<String> items2 = FXCollections.observableArrayList();
        items1.addAll("Regular", "Valued");
        items2.addAll("Flexible", "Fixed");
        customerType.setItems(items1);
        customerDiscount.setItems(items2);

    }

    public void loadCustomerAccounts() throws SQLException {

        // get accounts
        Statement stmt = database.getStatement();

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM CustomerAccount;");
        try {
            // create blank objects from each record
            addCustomerAccounts(resultSet);
        } catch (Exception e) {
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
        float tempOutstandingBalance;


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
            try {
                tempOutstandingBalance = resultSet.getFloat("outstandingBalance");
            }
            catch (NullPointerException e){
                tempOutstandingBalance = 0;
            }
            CustomerAccount newAccount = new CustomerAccount(tempAlias, tempEmail, tempFirstName, tempLastName, tempType, tempdiscountID, tempOutstandingBalance);
            customerAccounts.add(newAccount);
        }
            customerAccountTable.getItems().setAll(customerAccounts);


    }
    public void changeType() throws SQLException {
        int typeInt=0;
        try {
            if (customerType.getValue().equals("Regular")) typeInt = 1;
            else if (customerType.getValue().equals("Valued")) typeInt = 2;
            String changeType = "UPDATE CustomerAccount SET Type = " + typeInt + " WHERE Alias = '" + aliasType.getText() + "';";
            Statement statement = database.getStatement();
            statement.executeUpdate(changeType);
            typeMessage.setText("Type has been changed");
            statement.close();
        } catch (NullPointerException e) {
            typeMessage.setText("No Type Selected");
        } finally {
            loadCustomerAccounts();
        }

    }
    public void addDiscount() throws SQLException {
        try {
            if (customerDiscount.getValue().equals("Fixed")) {
                CallableStatement stmt = database.call("{call AddFixedDiscount(?, ?, ?)}");
                stmt.setString(1, aliasDiscount.getText());
                stmt.setFloat(2, Float.parseFloat(value.getText()));
                stmt.registerOutParameter(3, Types.VARCHAR);

                stmt.execute();
                discountMessage.setText(stmt.getString(3));
                stmt.close();
            }
            if (customerDiscount.getValue().equals("Flexible")) {
                CallableStatement stmt = database.call("{call AddFlexibleDiscount(?, ?, ?, ?, ?)}");
                stmt.setString(1, aliasDiscount.getText());
                try {
                    stmt.setInt(2, Integer.parseInt(lowerValue.getText()));
                } catch (NullPointerException e) {}
                try {
                    stmt.setInt(3, Integer.parseInt(upperValue.getText()));
                } catch (NullPointerException e) {}
                stmt.setFloat(4, Float.parseFloat(value.getText()));
                stmt.registerOutParameter(5, Types.VARCHAR);

                stmt.execute();
                discountMessage.setText(stmt.getString(5));
                stmt.close();
            }
        } catch (NullPointerException e) {
            discountMessage.setText("Field Broken");
        } finally {
            loadCustomerAccounts();
        }
    }




}
