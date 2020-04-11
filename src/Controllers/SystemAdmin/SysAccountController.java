package Controllers.SystemAdmin;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import entities.CustomerAccount;
import entities.SysAccount;
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


public class SysAccountController extends NavigationController implements SystemController {

    @FXML
    TableView<SysAccount> accountTable;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ComboBox accountType;
    @FXML
    private TextField accountID;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    @FXML
    private TextField deleteID;
    @FXML
    private Text message2;

    @FXML
    private Text message;



    @FXML
    private TableColumn<SysAccount, Integer> idColumn;
    @FXML
    private TableColumn<SysAccount, String> usernameColumn;
    @FXML
    private TableColumn<SysAccount, String> typeColumn;

    private int id;
    MyDBConnectivity database;


    public SysAccountController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void initialize(){
        accountType.getItems().setAll("Manager", "System Administrator", "Travel Advisor");
        firstName.setVisible(false);
        lastName.setVisible(false);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    }
    public void comboBoxListener(ActionEvent event){
        if (accountType.getValue().toString().equals("Travel Advisor")){
            firstName.setVisible(true);
            lastName.setVisible(true);
        }
        else {
            firstName.setVisible(false);
            lastName.setVisible(false);
        }
    }
    public void createNewAccount(ActionEvent event) throws SQLException {

        CallableStatement stmt;
        try {
            switch (accountType.getValue().toString()) {
                case "Manager":
                    stmt = database.call("{call AddUser(?, ?, ?, ?, ?)}");
                    stmt.setInt(1, Integer.parseInt(accountID.getText()));
                    stmt.setString(2, username.getText());
                    stmt.setString(3, password.getText());
                    stmt.setInt(4, 1);
                    stmt.registerOutParameter(5, Types.VARCHAR);
                    stmt.executeQuery();
                    message.setText(stmt.getString(5));
                    break;
                case "System Administrator":
                    stmt = database.call("{call AddUser(?, ?, ?, ?, ?)}");
                    stmt.setInt(1, Integer.parseInt(accountID.getText()));
                    stmt.setString(2, username.getText());
                    stmt.setString(3, password.getText());
                    stmt.setInt(4, 2);
                    stmt.registerOutParameter(5, Types.VARCHAR);
                    stmt.executeQuery();
                    message.setText(stmt.getString(5));
                    break;
                case "Travel Advisor":
                    stmt = database.call("{call AddAdvisor(?, ?, ?, ?, ?, ?)}");
                    stmt.setInt(1, Integer.parseInt(accountID.getText()));
                    stmt.setString(2, username.getText());
                    stmt.setString(3, password.getText());
                    stmt.setString(4, firstName.getText());
                    stmt.setString(5, lastName.getText());
                    stmt.registerOutParameter(6, Types.VARCHAR);
                    stmt.executeQuery();
                    message.setText(stmt.getString(6));
            }
        }
        catch (NullPointerException e) {
            message.setText("Provide all information");
        }
    }
    public void deleteAccount() throws SQLException {
        try {
            String deleteAccount = "DELETE FROM SysAccount WHERE Code = " + deleteID.getText() + ";";
            Statement statement = database.getStatement();
            statement.executeUpdate(deleteAccount);
            message2.setText("Account deleted");
            statement.close();
        }
        catch (Exception e){
            message2.setText("ID incorrect");
        }


    }

    public void loadAccounts() throws SQLException {
        try {
            accountTable.getItems().clear();
        }
        catch (NullPointerException e){

        }

        // get accounts
        Statement stmt = database.getStatement();

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM SysAccount;");
        try {
            // create blank objects from each record
            addAccounts(resultSet);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }
    public void addAccounts(ResultSet resultSet) throws SQLException {
        ObservableList<SysAccount> accounts = FXCollections.observableArrayList();

        int tempID;
        String tempUsername;
        String tempType;


        while (resultSet.next()) {
            tempID = resultSet.getInt("Code");
            tempUsername = resultSet.getString("UserName");
            tempType = " ";

            if (resultSet.getInt("Type") == 1) tempType="Manager";
            else if (resultSet.getInt("Type") == 2) tempType="Admin";
            else if (resultSet.getInt("Type") == 0) tempType="Advisor";

            SysAccount newAccount = new SysAccount(tempID, tempUsername, tempType);
            accounts.add(newAccount);
        }
        accountTable.getItems().addAll(accounts);


    }
}
