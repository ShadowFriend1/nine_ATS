package entities;

import DBConnect.MyDBConnectivity;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.Date;

public class Blank {

    MyDBConnectivity database = new MyDBConnectivity();
    private int blankID;
    private int blankType;
    private int travelAdvisorCode;
    private String blankDate;


    public int getBlankID() {
        return blankID;
    }

    public void setBlankID(int blankID) {
        this.blankID = blankID;
    }

    public int getBlankType() {
        return blankType;
    }

    public void setBlankType(int blankType) {
        this.blankType = blankType;
    }

    public int getTravelAdvisorCode() {
        return travelAdvisorCode;
    }

    public void setTravelAdvisorCode(int travelAdvisorCode) throws SQLException {

        this.travelAdvisorCode = travelAdvisorCode;
        database.update("UPDATE blanks SET travelAdvisorCode = " + travelAdvisorCode);
    }

    public String getBlankDate() {
        return blankDate;
    }

    public void setBlankDate(String blankDate) {
        this.blankDate = blankDate;
    }

    public Blank(int blankID, int blankType, int travelAdvisorCode, String blankDate) throws SQLException {
        this.blankID = blankID;
        this.blankType = blankType;
        this.travelAdvisorCode = travelAdvisorCode;
        this.blankDate = blankDate;
    }

    public void insertBlankIntoDB() throws SQLException{
        Date db = Date.valueOf(blankDate);
        String sql = "INSERT INTO blanks (blankID, blankType, travelAdvisorCode, blankDate) VALUES\n" +
                "(" + blankID + ", " + blankType +", "+ travelAdvisorCode +", '" + blankDate +"');";
        database.update(sql);
        System.out.println(sql);

    }
}
