package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

// An interface defining what a class allowing for connection to a database needs to contain
public interface DBConnectivity {
    String defaultAddress = "jdbc:h2:tcp://127.0.1.1:9092/~/Documents/City_Uni_cs/university_course/team_project/ATS_DB";
    String defaultUserName = "sa";
    String defaultPassword = "";

    int update(String sqlStatement) throws SQLException;

    ResultSet query(String sqlStatement) throws SQLException;

    void close() throws SQLException;
}