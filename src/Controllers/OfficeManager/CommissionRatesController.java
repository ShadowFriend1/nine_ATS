package Controllers.OfficeManager;

import Controllers.NavigationController;
import Controllers.SystemController;
import DBConnect.MyDBConnectivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class CommissionRatesController extends NavigationController implements SystemController {

    @FXML
    private TextField commissionRate;
    @FXML
    private Text message;

    private MyDBConnectivity database;
    private int id;

    @Override
    public void setId(int id) {
        this.id=id;
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) throws SQLException {
        this.database = db;
    }

    public CommissionRatesController() throws SQLException {
    }

    public void onClickAddRate(ActionEvent event) throws SQLException {
        Statement stmt = database.getStatement();
        try {
            int i = stmt.executeUpdate("INSERT INTO CommissionRates (Rate, CommissionDate, Active) VALUES ("+((float)Integer.parseInt(commissionRate.getText()))/100+", '"+ LocalDate.now() +"', TRUE);");
            if (i>0) {
                message.setText("Rate Successfully added");
            } else {
                message.setText("Rate could not be added");
            }
        } catch (NullPointerException e) {
            message.setText("FIelds Broken");
        } finally {
            stmt.close();
        }
    }

    public void onClickDeleteRate(ActionEvent event) throws SQLException {
        Statement stmt = database.getStatement();
        try {
            int i = stmt.executeUpdate("UPDATE CommissionRates SET Active = 0 WHERE Rate = "+((float)Integer.parseInt(commissionRate.getText()))/100+";");
            if (i>0) {
                message.setText("Rate Successfully removed");
            } else {
                message.setText("Rate could not be removed");
            }
        } catch (NullPointerException e) {
            message.setText("Fields Broken");
        } catch (SQLException e) {
            message.setText("Rate already exists");
        } finally {
            stmt.close();
        }
    }
}
