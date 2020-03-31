package Controllers;

import DBConnect.MyDBConnectivity;
import entities.Blank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


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
        finally {

        }

    }
}
