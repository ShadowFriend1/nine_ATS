import DBConnect.DBConnectivity;
import DBConnect.MyDBConnectivity;

import java.sql.*;

public class main {

    public static void main(String[] args) throws SQLException {
        DBConnectivity database = null;
        // TODO remove need for execution arguments and implement database searching algorithm.
        try {
            switch (args.length) {
                case 3:
                    database = new MyDBConnectivity(args[0], args[1], args[2]);
                    break;
                case 0:
                    database = new MyDBConnectivity();
                    break;
                default:
                    System.out.println("incorrect number of arguments");
            }
        } finally {
            if (database!=null) database.close();
        }
    }
}