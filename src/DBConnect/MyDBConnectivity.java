package DBConnect;
import java.sql.*;

public class MyDBConnectivity implements DBConnectivity {

    MyDBConnectivity(String address, String username, String password) {
        try {
            DriverManager.getConnection(address, username, password);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    MyDBConnectivity() {
        try {
            DriverManager.getConnection(DBConnectivity.dafaultAddress, DBConnectivity.dafaultAddress, DBConnectivity.dafaultAddress);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String query() {
        return null;
    }
}