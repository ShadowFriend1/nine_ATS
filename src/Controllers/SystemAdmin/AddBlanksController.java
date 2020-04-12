package Controllers.SystemAdmin;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

public class AddBlanksController extends NavigationController implements SystemController {

    private int id;
    private MyDBConnectivity database;

    @FXML
    private DatePicker blankDate;
    @FXML
    private TextField startBlank;
    @FXML
    private TextField endBlank;
    @FXML
    private Text message;

    public AddBlanksController() throws SQLException {

    }

    public void onClickSubmitBlanks(javafx.event.ActionEvent event) throws SQLException, InterruptedException {
        CallableStatement stmt = database.call("{call AddBlanks(?, ?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(startBlank.getText()));
            stmt.setLong(2, Long.parseLong(endBlank.getText()));
            stmt.setDate(3, Date.valueOf(blankDate.getValue()));
            stmt.registerOutParameter(4, Types.VARCHAR);
            message.setText("waiting");
            stmt.execute();
            message.setText(stmt.getString(4));
            stmt.close();
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Missing");
        } finally {
            stmt.close();
        }
    }

    public void onClickBlanksCancel(javafx.event.ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/GUI/Admin/admin.fxml"));
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

    public void onClickDeleteBlanks(ActionEvent actionEvent) throws SQLException {
        CallableStatement stmt = database.call("{call DeleteBlanks(?, ?, ?)}");
        try {
            stmt.setLong(1, Long.parseLong(startBlank.getText()));
            stmt.setLong(2, Long.parseLong(endBlank.getText()));
            stmt.registerOutParameter(3, Types.VARCHAR);
            message.setText("waiting");
            stmt.execute();
            message.setText(stmt.getString(3));
        } catch (NullPointerException | NumberFormatException e) {
            message.setText("Field Missing");
        }finally {
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
