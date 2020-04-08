package Controllers;

import DBConnect.MyDBConnectivity;
import entities.Blank;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import org.h2.command.dml.Call;


import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Text message;

    MyDBConnectivity database = new MyDBConnectivity();

    public LoginController() throws SQLException {
    }

    // Checks username and password. Logins if correct. Displays a message if login was unsuccessful

    public void onClickLogin(javafx.event.ActionEvent event) throws IOException, SQLException {

        String login = "{call Login(?, ?, ?)}";
        System.out.println("{call Login("+username.getText()+", "+password.getText()+")}");
        CallableStatement cs = database.call(login);
        cs.setString(1, username.getText());
        cs.setString(2, password.getText());
        cs.registerOutParameter(3, Types.INTEGER);
        cs.execute();
        int type = cs.getInt(3);

        // if it's an admin type (type 2)
        if (type == 2) {


            Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/admin.fxml"));
            Scene homeScene = new Scene(homeView);


            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();

        }

        // type 1 office manager
        else if (type == 1){
            Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/manager.fxml"));
            Scene homeScene = new Scene(homeView);


            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();
        }
        /*
            NEED TO DO OTHER ACCOUNT HOME GUI CONNECTIONS
         */

        // type 0 travel advisor
        else if (type == 0){
            Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/advisor.fxml"));
            Scene homeScene = new Scene(homeView);


            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();
        }
        /* type 222 password invalid */
        else if (type == 222){
            System.out.println("Password invalid");
            message.setText("Password invalid");
        }

        /* type 111 username invalid */
        else if (type == 111) {
            System.out.println("Username invalid");
            message.setText("Username invalid");
        }

        else if (type == 999) {
            System.out.println("Database error");
            message.setText("Database error");
        }
    }
}

