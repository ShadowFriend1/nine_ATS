package Controllers;

import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;

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
    private TextField type;



    public void createCustomerAccount(ActionEvent event){

    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }
}
