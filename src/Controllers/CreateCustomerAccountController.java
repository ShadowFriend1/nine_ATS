package Controllers;

import DBConnect.MyDBConnectivity;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;


public class CreateCustomerAccountController implements SystemController {

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

    int typeInt = -1;



    public CreateCustomerAccountController() throws SQLException {
    }

    public void initialize(){
        type.getItems().setAll("Regular", "Valued");

    }

    public void createCustomerAccount(ActionEvent event) throws SQLException {

        String addCustomer = "{call AddCustomer(?, ?, ?, ?, ?, ?)}";
        CallableStatement cs = database.call(addCustomer);
        cs.setString(1, alias.getText());
        cs.setString(2, email.getText());
        cs.setString(3, firstName.getText());
        cs.setString(4, lastName.getText());
        cs.setInt(5, typeInt);

        cs.registerOutParameter(6, Types.VARCHAR);
        cs.execute();
        message.setText(cs.getString(6));
    }

    public void setType(ActionEvent actionEvent) {
        // set customer type to int value upon selection
        if (type.getValue().equals("Regular")) typeInt = 1;
        else if (type.getValue().equals("Valued")) typeInt = 2;

    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }
}
