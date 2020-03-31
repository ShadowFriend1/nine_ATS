package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelAdvisorController {

    MyDBConnectivity database = new MyDBConnectivity();

    public TravelAdvisorController() throws SQLException {
    }

    public void createCustomerAccount(){

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
