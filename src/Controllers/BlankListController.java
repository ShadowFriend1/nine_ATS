package Controllers;

import DBConnect.MyDBConnectivity;
import entities.Blank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BlankListController {


    MyDBConnectivity database = new MyDBConnectivity();
    @FXML
    private TableView<Blank> blankTable;
    @FXML
    private TableColumn<Blank, Integer> blankIDColumn;
    @FXML
    private TableColumn<Blank, Integer> blankTypeColumn;
    @FXML
    private TableColumn<Blank, Integer> travelAgentCodeColumn;
    @FXML
    private TableColumn<Blank, String> blankDateColumn;
    @FXML
    private TextField searchID;
    @FXML
    private Text message;


    public BlankListController() throws SQLException {
    }

    // configure table columnn
    public void initialize() {
        blankIDColumn.setCellValueFactory(new PropertyValueFactory<Blank, Integer>("blankID"));
        blankTypeColumn.setCellValueFactory(new PropertyValueFactory<Blank, Integer>("blankType"));
        travelAgentCodeColumn.setCellValueFactory(new PropertyValueFactory<Blank, Integer>("travelAdvisorCode"));
        blankDateColumn.setCellValueFactory(new PropertyValueFactory<Blank, String>("blankDate"));

       loadBlanks();
    }

    // This method will load blanks from the database and put them in the tableview object

    public void loadBlanks(){
        ObservableList<Blank> blanks = FXCollections.observableArrayList();


        try {
            // get blanks
            ResultSet resultSet = database.query("SELECT * FROM blanks");
            // create blank objects from each record
            while(resultSet.next()){
                Blank newBlank = new Blank(resultSet.getInt("blankID"),
                                            resultSet.getInt("blankType"),
                                            resultSet.getInt("travelAdvisorCode"),
                                            resultSet.getString("blankDate"));
                blanks.add(newBlank);
            }
            blankTable.getItems().addAll(blanks);

        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }


    }


    public void goHome(ActionEvent event) throws IOException {
        Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/manager.fxml"));
        Scene homeScene = new Scene(homeView);


        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void searchBlank(ActionEvent event) throws SQLException {

        ObservableList<Blank> blanks = FXCollections.observableArrayList();

            // get blanks
            ResultSet resultSet = database.query("SELECT * FROM blanks WHERE blankID = " + searchID.getText() + ";");
            // create blank objects from each record
            while (resultSet.next()) {
                Blank newBlank = new Blank(resultSet.getInt("blankID"),
                        resultSet.getInt("blankType"),
                        resultSet.getInt("travelAdvisorCode"),
                        resultSet.getString("blankDate"));
                blanks.add(newBlank);
            }
            blankTable.getItems().addAll(blanks);


    }

    public void logout(ActionEvent event) throws IOException {
        Parent homeView = FXMLLoader.load(getClass().getResource("/GUI/login.fxml"));
        Scene homeScene = new Scene(homeView);


        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }
}
