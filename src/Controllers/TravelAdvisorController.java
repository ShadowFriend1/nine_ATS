package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelAdvisorController implements SystemController{

    private MyDBConnectivity database;
    private int id;
    public TravelAdvisorController() throws SQLException {}

    @Override
    public void setDatabaseC(MyDBConnectivity db) { database = db; }

    @Override
    public void setId (int id) { this.id = id; }

    public void onCLickCreateCustomerAccount(){

    }
    public void giveDiscount(){

    }
    public void returnBlanks() throws SQLException {

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
