package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemAdminController {

    MyDBConnectivity database;

    public SystemAdminController() throws SQLException {
    }

    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    public ResultSet accessFullStock() throws SQLException {
        String query = "SELECT * FROM BlankStock;";
        ResultSet resultSet = database.query(query);
        return resultSet;
    }
    public void generateStockReport(){

    }

}
