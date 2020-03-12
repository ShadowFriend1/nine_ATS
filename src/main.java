import DBConnect.DBConnectivity;
import DBConnect.MyDBConnectivity;

import java.sql.*;

public class main {

    public static void main(String[] args) throws SQLException {
        DBConnectivity database = null;
        // TODO remove need for execution arguments and implement database searching algorithm.
        if (args.length==3|args.length==0) {
            try {
                if (args.length == 3) {
                    database = new MyDBConnectivity(args[0], args[1], args[2]);
                } else {
                    database = new MyDBConnectivity();
                }
            } finally {
                try {
                    database.close();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            // TODO add tests queries and updates to mke sure connection is working properly
        } else {
            System.out.println("incorrect number of arguments");
        }
    }
}