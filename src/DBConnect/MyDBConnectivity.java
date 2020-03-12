package DBConnect;
import java.sql.*;

// An implementation of a database connection.

public class MyDBConnectivity implements DBConnectivity {

    final private Connection conn;

    // If arguments are provided for the creation of the connection then that database connection is attempted
    MyDBConnectivity(String address, String username, String password) throws Exception {
        conn = DriverManager.getConnection(address, username, password);
    }

    // If no arguments are provided for the database connection then the default values
    // (contained within the Interface) are used.
    MyDBConnectivity() throws Exception {
        conn = DriverManager.getConnection
                (DBConnectivity.defaultAddress, DBConnectivity.defaultUserName, DBConnectivity.defaultPassword);
    }

    @Override
    public int update(String sqlStatement) throws Exception {
        Statement statement = conn.createStatement();
        return statement.executeUpdate(sqlStatement);
    }

    @Override
    public ResultSet query(String sqlStatement) throws Exception {
        Statement statement = conn.createStatement();
        return statement.executeQuery(sqlStatement);
    }
}