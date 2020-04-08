package DBConnect;

import java.sql.*;

// An interface defining what a class allowing for connection to a database needs to contain
public interface DBConnectivity {
    // TODO sort whatever this is out
    String defaultAddress = "jdbc:mysql://localhost:3306/AirVia";
    String defaultUserName = "root";
    String defaultPassword = "giratina950";

    int update(String sqlStatement) throws SQLException;

    ResultSet query(String sqlStatement) throws SQLException;

    CallableStatement call(String sqlStatement) throws SQLException;

    void close() throws SQLException;
}