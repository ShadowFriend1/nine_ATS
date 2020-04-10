package DBConnect;


import java.sql.*;

// An implementation of a database connection.

public class MyDBConnectivity implements DBConnectivity {

    private String address;
    private String userName;
    private String password;
    final private Connection conn;

    // If arguments are provided for the creation of the connection then that database connection is attempted
    public MyDBConnectivity(String address, String userName, String password) throws SQLException {
        conn = DriverManager.getConnection(address, userName, password);
        this.address = address;
        this.userName = userName;
        this.password = password;
    }

    public void reconnect() throws SQLException{
    }

    // If no arguments are provided for the database connection then the default values
    // (contained within the Interface) are used.
    public MyDBConnectivity() throws SQLException {
        conn = DriverManager.getConnection
                (DBConnectivity.defaultAddress, DBConnectivity.defaultUserName, DBConnectivity.defaultPassword);
    }

    @Override
    public Statement getStatement() throws SQLException {
        return conn.createStatement();
    }

    @Override
    public CallableStatement call(String sqlStatement) throws SQLException {
        return conn.prepareCall(sqlStatement);
    }

    @Override
    public void close() throws SQLException{
        conn.close();
    }

}