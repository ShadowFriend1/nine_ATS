package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemAdminController implements SystemController {

    MyDBConnectivity database;

    public SystemAdminController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    public ResultSet accessFullStock() throws SQLException {
        String query = "SELECT * FROM BlankStock;";
        return database.query(query);
    }
    public void generateStockReport(){

    }

}
