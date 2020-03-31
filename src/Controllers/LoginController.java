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
        // intially type set to 0
        int type = 0;
        String query = "SELECT * FROM `SysAccount` WHERE username ='" + username.getText() + "' AND password_ ='" + password.getText() + "';";
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

        /*
            NEED TO CONNECT WITH ADMIN HOME PAGE GUI
         */
        if (type == 1) {


            Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/blankList.fxml"));
            Scene homeScene = new Scene(homeView);


            // Get stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Change the scene
            window.setScene(homeScene);
            window.show();
            Blank test = new Blank(2120000, 212, 2, "2019-02-02");
            test.insertBlankIntoDB();
        }
        /*
            NEED TO DO OTHER ACCOUNT HOME GUI CONNECTIONS
         */

    }
}

