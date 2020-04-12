package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.SQLException;

public interface SystemController {
    void setId (int id);
    void setDatabaseC(MyDBConnectivity db) throws SQLException;
    void onLogin () throws SQLException;
}
