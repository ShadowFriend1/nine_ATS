package Controllers.OfficeManager;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

public class AssignBlanksController extends NavigationController implements SystemController {

    private int id;
    private MyDBConnectivity database;

    @FXML
    private TextField startBlank;
    @FXML
    private TextField endBlank;
    @FXML
    private TextField advisorCode;
    @FXML
    private Text message;

    public AssignBlanksController() throws SQLException {

    }

    public void onClickAssignBlanks(javafx.event.ActionEvent event) throws SQLException {
        CallableStatement stmt = database.call("{call AssignBlanks(?, ?, ?, ?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(startBlank.getText()));
            stmt.setLong(2, Long.parseLong(endBlank.getText()));
            stmt.setInt(3, Integer.parseInt(advisorCode.getText()));
            stmt.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            stmt.setBoolean(5, false);
            stmt.registerOutParameter(6, Types.VARCHAR);
            message.setText("waiting");
            stmt.execute();
            message.setText(stmt.getString(6));
            stmt.close();
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Missing");
        } finally {
            stmt.close();
        }
    }

    public void onClickBlanksCancel(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/OfficeManager/manager.fxml"));
        Parent homeView = fxmlloader.load();
        SystemController sys = fxmlloader.getController();
        sys.setDatabaseC(database);
        Scene homeScene = new Scene(homeView);
        // Get stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Change the scene
        window.setScene(homeScene);
        window.show();
    }

    public void onClickReassignBlanks(ActionEvent actionEvent) throws SQLException {
        CallableStatement stmt = database.call("{call AssignBlanks(?, ?, ?, ?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(startBlank.getText()));
            stmt.setLong(2, Long.parseLong(endBlank.getText()));
            stmt.setInt(3, Integer.parseInt(advisorCode.getText()));
            stmt.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            stmt.setBoolean(5, true);
            stmt.registerOutParameter(6, Types.VARCHAR);
            message.setText("waiting");
            stmt.execute();
            message.setText(stmt.getString(6));
            stmt.close();
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Missing");
        } finally {
            stmt.close();
        }
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) {
        this.database = db;
    }


}
