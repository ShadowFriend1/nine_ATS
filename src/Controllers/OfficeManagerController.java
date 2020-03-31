package Controllers;

import DBConnect.MyDBConnectivity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficeManagerController {

    MyDBConnectivity database = new MyDBConnectivity();

    public OfficeManagerController() throws SQLException {
    }

    public void accessRefundLog(){

    }
    public void allocateBlanks(String email, int blankID) throws SQLException {
        String query = "UPDATE BlankStock SET TravelAgentAgentEmail= " + email +
                "WHERE ID=" + blankID + ";";
        database.update(query);
    }
    public void generateSalesReport(){

    }
    public void giveDiscount(String alias, float discount, String discountType) throws SQLException {
        String query = "UPDATE CustomerAccount SET Discount=" + discount + " SET DiscountType=" + discountType  + " WHERE alias=" + alias + ";";
        database.update(query);
    }
    public void setCurrencyExchangeRate(){

    }
    public void setCustomerType(String alias, int type) throws SQLException {
        String query = "UPDATE CustomerAccount SET Type=" + type + " WHERE alias=" + alias + ";";
        database.update(query);


    }
    public ResultSet viewTravelAgentDetails(int id) throws SQLException {
        String query = "GET * FROM TravelAgent WHERE TravelAgentCode=" + id + ";";
        ResultSet resultSet = database.query(query);
        return resultSet;
    }
    public void setCommissionRate(int id, float commissionRate){
        String query;
    }

}


