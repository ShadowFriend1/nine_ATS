package Controllers.Manager;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import entities.SysAccount;
import entities.TravelAdvisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.*;

public class AdvisorListController extends NavigationController implements SystemController {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField accountID;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;


    @FXML
    private TableColumn<TravelAdvisor, Integer> idColumn;
    @FXML
    private TableColumn<TravelAdvisor, String> firstNameColumn;
    @FXML
    private TableColumn<TravelAdvisor, String> lastNameColumn;

    @FXML
    TableView<TravelAdvisor> advisorsTable;
    @FXML
    private Text message;

    MyDBConnectivity database;
    private int id;

    public AdvisorListController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        database = db;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    public void loadAdvisors() throws SQLException {
        try {
            advisorsTable.getItems().clear();
        } catch (NullPointerException e) {

        }

        // get advisors
        Statement stmt = database.getStatement();

        ResultSet resultSet = stmt.executeQuery("SELECT * FROM TravelAgent;");
        try {
             //create advisor objects;
            addAdvisors(resultSet);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            stmt.close();
        }
    }

    public void addAdvisors(ResultSet resultSet) throws SQLException {
        ObservableList<TravelAdvisor> accounts = FXCollections.observableArrayList();

        int tempID;
        String tempFirstName;
        String tempLastName;

        while (resultSet.next()) {
            tempID = resultSet.getInt("SysAccountCode");
            tempFirstName = resultSet.getString("FirstName");
            tempLastName = resultSet.getString("LastName");

            TravelAdvisor newAccount = new TravelAdvisor(tempID, tempFirstName, tempLastName);
            accounts.add(newAccount);
        }
        advisorsTable.getItems().addAll(accounts);
    }
    public void createAdvisor() throws SQLException {
        CallableStatement stmt;
        try {
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
        catch (NullPointerException e){
            message.setText("Provide all information");
        }
    }
}
