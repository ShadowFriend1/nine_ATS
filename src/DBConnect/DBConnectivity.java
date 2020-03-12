package DBConnect;

import java.sql.ResultSet;

// An interface defining what a class allowing for connection to a database needs to contain
interface DBConnectivity {
    String defaultAddress = "jdbc:h2:localhost:8000/ATS";
    String defaultUserName = "sa";
    String defaultPassword = "";

    int update(String sqlStatement) throws Exception;

    ResultSet query(String sqlStatement) throws Exception;
}