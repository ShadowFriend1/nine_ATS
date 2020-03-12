package DBConnect;
import java.sql.*;

// An implementation of a database connection.

public class MyDBConnectivity implements DBConnectivity {

    final private Connection conn;

    // If arguments are provided for the creation of the connection then that database connection is attempted
    public MyDBConnectivity(String address, String userName, String password) throws SQLException {
        conn = DriverManager.getConnection(address, userName, password);
    }

    // If no arguments are provided for the database connection then the default values
    // (contained within the Interface) are used.
    public MyDBConnectivity() throws SQLException {
        conn = DriverManager.getConnection
                (DBConnectivity.defaultAddress, DBConnectivity.defaultUserName, DBConnectivity.defaultPassword);
    }

    @Override
    public int update(String sqlStatement) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeUpdate(sqlStatement);
    }

    @Override
    public ResultSet query(String sqlStatement) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeQuery(sqlStatement);
    }

    @Override
    public void close() throws SQLException{
        conn.close();
    }
}