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


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        // initially type set to 0
        int type = 0;
        String query = "CALL Login('" + username.getText() + "', '" + password.getText() + "', @a)";
        System.out.println(query);
        ResultSet resultSet = database.query(query);
        if (resultSet.next()) {

            // get the type and set it
            type = resultSet.getInt("accountType");
        }
        else {
            message.setText("Invalid username or password");
        }


        // if it's an admin type (type 1)


        if (type == 1) {


            Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/admin.fxml"));
            Scene homeScene = new Scene(homeView);


            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();

        }

        // type 2 office manager
        else if (type == 2){
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

        // type 3 travel advisor
        else if (type == 3){
            Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/advisor.fxml"));
            Scene homeScene = new Scene(homeView);


            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();
        }
    }
}

