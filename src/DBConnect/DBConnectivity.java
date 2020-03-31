package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// An interface defining what a class allowing for connection to a database needs to contain
public interface DBConnectivity {
    // TODO sort whatever this is out
    String defaultAddress = "jdbc:mysql://localhost:3306/AirVia";
    String defaultUserName = "root";
    String defaultPassword = "";

    int update(String sqlStatement) throws SQLException;

    ResultSet query(String sqlStatement) throws SQLException;

    void close() throws SQLException;
}