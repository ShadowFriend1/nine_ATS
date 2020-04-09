package Controllers;

import DBConnect.MyDBConnectivity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController{

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Text message;

    MyDBConnectivity database;

    public LoginController() throws SQLException {
    }

    // Checks username and password. Logins if correct. Displays a message if login was unsuccessful

    public void onClickLogin(javafx.event.ActionEvent event) throws IOException, SQLException {
        System.out.println("{call Login(" + username.getText() + ", " + password.getText() + ")}");
        CallableStatement cs = database.call("{call Login(?, ?, ?)}");
        cs.setString(1, username.getText());
        cs.setString(2, password.getText());
        cs.registerOutParameter(3, Types.INTEGER);
        int type = 999;
        try {
            cs.execute();
            type = cs.getInt(3);
        } finally {
            cs.close();
        }
        int code = 0;
        String fxmlFile = "none found";

        // if it's an admin type (type 2)
        if (type == 2) {
            System.out.println("Logged in as admin: " + username.getText());
            fxmlFile = "/GUI/admin.fxml";
        }

        // type 1 office manager
        else if (type == 1) {
            System.out.println("Logged in as manager: " + username.getText());
            fxmlFile = "/GUI/manager.fxml";
        }
        /*
            NEED TO DO OTHER ACCOUNT HOME GUI CONNECTIONS
         */

        // type 0 travel advisor
        else if (type == 0) {
            ResultSet rs = database.getStatement().executeQuery("SELECT Code FROM SysAccount WHERE UserName='"+username.getText()+"' "+
                    " AND aes_decrypt(PasswordHash, 'catdog')='"+password.getText()+"' LIMIT 1;");
            if (rs.first()) {
                code = rs.getInt("Code");
            }
            System.out.println("Logged in as advisor: " + username.getText());
            fxmlFile = "/GUI/advisor.fxml";
        }
        if (fxmlFile.equals("none found")) {
            /* type 222 password invalid */
            if (type == 222) {
                System.out.println("Password invalid");
                message.setText("Password invalid");
            }

            /* type 111 username invalid */
            else if (type == 111) {
                System.out.println("Username invalid");
                message.setText("Username invalid");
            } else if (type == 999) {
                System.out.println("Database error");
                message.setText("Database error");
            }
        } else {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent homeView = fxmlloader.load();
            SystemController sys = fxmlloader.getController();
            sys.setDatabaseC(database);
            sys.setId(code);
            Scene homeScene = new Scene(homeView);

            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();
        }
    }

    public void setDatabaseC(MyDBConnectivity db) {
        database = db;
    }
}

