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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class BlankListController implements SystemController {

    private int id;
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
    private TableColumn<Blank, LocalDate> assignedDateColumn;
    @FXML
    private TableColumn<Blank, String> mcoTextColumn;
    @FXML
    private TextField searchID;
    @FXML
    private Text message;


    public BlankListController() throws SQLException {
    }

    @Override
    public void setId (int id) { this.id = id; }

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
    }

    // This method will load blanks from the database and put them in the tableview object

    public void loadBlanks() throws SQLException {

        blankTable.getItems().clear();

        // get blanks
        try (ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM BlankStock;")) {
            // create blank objects from each record
            addBlanks(resultSet);
        } catch (Exception e) {
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

        blankTable.getItems().clear();

        try (ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM BlankStock WHERE ID LIKE '%" + searchID.getText() + "%';")) {
            // create blank objects from each record
            addBlanks(resultSet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBlanks(ResultSet resultSet) throws SQLException {
        ObservableList<Blank> blanks = FXCollections.observableArrayList();

        long tempID;
        int tempType;
        int tempAdvisorCode;
        LocalDate tempAssignedDate;
        String tempMCOText;
        LocalDate tempDate;
        try {
            while (resultSet.next()) {
                System.out.println("blank retrieved");
                tempID = resultSet.getLong("ID");
                tempType = resultSet.getInt("Type");
                try {
                    tempAdvisorCode = resultSet.getInt("TravelAgentCode");
                } catch (NullPointerException e) {
                    tempAdvisorCode = 0;
                }
                try {
                    tempAssignedDate = resultSet.getDate("AssignedDate").toLocalDate();
                } catch (NullPointerException e) {
                    tempAssignedDate = null;
                }
                try {
                    tempMCOText = resultSet.getString("MCOText");
                } catch (NullPointerException e) {
                    tempMCOText = "";
                }
                tempDate = resultSet.getDate("Date").toLocalDate();
                Blank newBlank = new Blank(tempID, tempType, tempAdvisorCode, tempAssignedDate, tempMCOText, tempDate);
                blanks.add(newBlank);
            }
        } finally {
            resultSet.close();
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
