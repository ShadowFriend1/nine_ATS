package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemAdminController implements SystemController {

    private int id;
    MyDBConnectivity database;

    public SystemAdminController() throws SQLException {
    }

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void accessFullStock() throws SQLException {

    }
    public void generateStockReport(){

    }

}
