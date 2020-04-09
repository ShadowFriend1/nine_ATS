package Controllers;

import DBConnect.MyDBConnectivity;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class SysAccountController implements SystemController{

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField accountType;
    @FXML
    private TextField accountID;

    MyDBConnectivity database;


    public SysAccountController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    public void createNewAccount(ActionEvent event) throws SQLException {
        String query = "INSERT INTO SysAccount (username, password_, accountType, accountID) VALUES ('" + username + "', '" + password +"', " +
                accountType  +", " + accountID + ");";
        database.update(query);
    }
}
