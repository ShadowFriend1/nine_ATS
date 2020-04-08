package entities;

import DBConnect.MyDBConnectivity;

import java.sql.SQLException;

import java.sql.Date;
import java.time.LocalDate;

public class Blank {

    MyDBConnectivity database = new MyDBConnectivity();
    private long blankID;
    private int blankType;
    private Integer travelAdvisorCode;
    private String blankDate;
    private String mcoText;
    private String assignedDate;


    public long getBlankID() {
        return blankID;
    }

    public void setBlankID(long blankID) {
        this.blankID = blankID;
    }

    public int getBlankType() {
        return blankType;
    }

    public void setBlankType(int blankType) {
        this.blankType = blankType;
    }

    public Integer getTravelAdvisorCode() {
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

    public Blank(long blankID, int blankType, Integer travelAdvisorCode ,String assignedDate, String mcoText, String blankDate) throws SQLException {
        this.blankID = blankID;
        this.blankType = blankType;
        if (travelAdvisorCode == null){
            this.travelAdvisorCode = 0;
        }
        else {
            this.travelAdvisorCode = travelAdvisorCode;
        }
        this.blankDate = blankDate;
        this.mcoText = mcoText;
        this.assignedDate = assignedDate;
    }


}
