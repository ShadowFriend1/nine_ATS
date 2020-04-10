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
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class BlankListController implements SystemController {


    MyDBConnectivity database;
    @FXML
    private TableView<Blank> blankTable;
    @FXML
    private TableColumn<Blank, Long> blankIDColumn;
    @FXML
    private TableColumn<Blank, Integer> blankTypeColumn;
    @FXML
    private TableColumn<Blank, Integer> travelAgentCodeColumn;
    @FXML
    private TableColumn<Blank, LocalDate> blankDateColumn;
    @FXML
    private TableColumn<Blank, String> assignedDateColumn;
    @FXML
    private TableColumn<Blank, String> mcoTextColumn;
    @FXML
    private TextField searchID;
    @FXML
    private Text message;


    public BlankListController() throws SQLException {
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    // configure table columnn
    public void initialize() throws SQLException {
        blankIDColumn.setCellValueFactory(new PropertyValueFactory<>("blankID"));
        blankTypeColumn.setCellValueFactory(new PropertyValueFactory<>("blankType"));
        travelAgentCodeColumn.setCellValueFactory(new PropertyValueFactory<>("travelAdvisorCode"));
        blankDateColumn.setCellValueFactory(new PropertyValueFactory<>("blankDate"));
        mcoTextColumn.setCellValueFactory(new PropertyValueFactory<>("mcoText"));
        assignedDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));



        loadBlanks();
    }

    // This method will load blanks from the database and put them in the tableview object

    public void loadBlanks() throws SQLException {
        ObservableList<Blank> blanks = FXCollections.observableArrayList();

        // Create temp variables to check for null
            long tempID;
            int tempType;
            int tempAdvisorCode;
            String tempAssignedDate;
            String tempMCOText;
            LocalDate tempDate;

        // get blanks
        // get blanks
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirVia", "root", ""); Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM BlankStock;")) {

            // I tried to use database connection manually to maybe avoid opening many connections. Still doesn't work

            // create blank objects from each record
            while (resultSet.next()) {
                tempID = resultSet.getLong("ID");
                tempType = resultSet.getInt("Type");
                tempAdvisorCode = resultSet.getInt("TravelAgentCode");
                if (resultSet.wasNull()) {
                    tempAdvisorCode = 0;
                }
                try {
                    tempAssignedDate = resultSet.getDate("AssignedDate").toString();
                } catch (NullPointerException e) {
                    tempAssignedDate = "none";
                }
                tempMCOText = resultSet.getString("MCOText");
                tempDate = resultSet.getDate("Date").toLocalDate();
                Blank newBlank = new Blank(tempID, tempType, tempAdvisorCode, tempAssignedDate.toString(), tempMCOText, tempDate);
                blanks.add(newBlank);


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            blankTable.getItems().addAll(blanks);





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

        long tempID;
        int tempType;
        int tempAdvisorCode;
        String tempAssignedDate;
        String tempMCOText;
        LocalDate tempDate;

        blankTable.getItems().clear();
        // get blanks
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirVia", "root", ""); Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM BlankStock WHERE ID = " + searchID.getText() + ";")) {

            // create blank objects from each record
            while (resultSet.next()) {
                tempID = resultSet.getLong("ID");
                tempType = resultSet.getInt("Type");
                tempAdvisorCode = resultSet.getInt("TravelAgentCode");
                if (resultSet.wasNull()) {
                    tempAdvisorCode = 0;
                    System.out.println(tempAdvisorCode);
                }
                try {
                    tempAssignedDate = resultSet.getDate("AssignedDate").toString();
                } catch (NullPointerException e) {
                    tempAssignedDate = "none";
                }
                tempMCOText = resultSet.getString("MCOText");
                tempDate = resultSet.getDate("Date").toLocalDate();
                Blank newBlank = new Blank(tempID, tempType, tempAdvisorCode, tempAssignedDate.toString(), tempMCOText, tempDate);
                blanks.add(newBlank);


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
