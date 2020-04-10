package Controllers;

import DBConnect.MyDBConnectivity;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.h2.command.dml.Call;

import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;


public class SysAccountController implements SystemController{

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField accountType;
    @FXML
    private TextField accountID;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;

    private int id;
    MyDBConnectivity database;


    public SysAccountController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void createNewAccount(ActionEvent event) throws SQLException {

        CallableStatement stmt;
        String rs = "";
        switch (accountType.toString()) {
            case "Manager":
                stmt = database.call("{call AddUser(?, ?, ?, ?, ?)}");
                stmt.setInt(1, Integer.parseInt(accountID.toString()));
                stmt.setString(2, username.getText());
                stmt.setString(3, password.getText());
                stmt.setInt(4, 1);
                stmt.registerOutParameter(5, Types.VARCHAR);
                stmt.executeQuery();
                rs = stmt.getString(5);
                break;
            case "System Administrator":
                stmt = database.call("{call AddAccount(?, ?, ?, ?, ?)}");
                stmt.setInt(1, Integer.parseInt(accountID.toString()));
                stmt.setString(2, username.getText());
                stmt.setString(3, password.getText());
                stmt.setInt(4, 2);
                stmt.registerOutParameter(5, Types.VARCHAR);
                stmt.executeQuery();
                rs = stmt.getString(5);
                break;
            case "Travel Advisor":
                stmt = database.call("{call AddAdvisor(?, ?, ?, ?)}");
                stmt.setInt(1, Integer.parseInt(accountID.toString()));
                stmt.setString(2, username.getText());
                stmt.setString(3, password.getText());
                stmt.setString(4, firstName.getText());
                stmt.setString(5, lastName.getText());
                stmt.registerOutParameter(6, Types.VARCHAR);
                stmt.executeQuery();
                rs = stmt.getString(6);
        }
        System.out.println(rs);
    }
}
