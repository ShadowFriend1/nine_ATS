package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelAdvisorController {

    private MyDBConnectivity database;

    public TravelAdvisorController() throws SQLException {}

    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    public void onCLickCreateCustomerAccount(){

    }
    public void giveDiscount(){

    }
    public ResultSet returnBlanks(int id) throws SQLException {
        String query = "SELECT * FROM BlankStock WHERE TravelAgentCode = " + id + ";";
        ResultSet resultSet = database.query(query);
        return resultSet;

    }
    public void setCurrencyExchangeRate(){

    }
    public void writeRefundLog(){

    }
    public void accessRefundLog(){

    }
    public void generateSalesReport(){

    }
}
