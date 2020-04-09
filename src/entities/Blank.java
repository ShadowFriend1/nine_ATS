package entities;

import DBConnect.MyDBConnectivity;

import java.sql.SQLException;

import java.sql.Date;
import java.time.LocalDate;

public class Blank {

    MyDBConnectivity database = new MyDBConnectivity();
    private long blankID;
    private int blankType;
    private int travelAdvisorCode;
    private LocalDate blankDate;
    private String mcoText;
    private LocalDate assignedDate;
    private String customerAlias;

    public String getCustomerAlias() { return customerAlias; }

    public LocalDate getAssignedDate() { return assignedDate; }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public void setMcoText(String mcoText) {
        this.mcoText = mcoText;
    }

    public String getMcoText() { return mcoText; }

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

    public LocalDate getBlankDate() {
        return blankDate;
    }

    public void setBlankDate(LocalDate blankDate) {
        this.blankDate = blankDate;
    }

    public Blank(long blankID, int blankType, Integer travelAdvisorCode ,LocalDate assignedDate, String mcoText, LocalDate blankDate, String customerAlias) throws SQLException {
        this.blankID = blankID;
        this.blankType = blankType;
        this.travelAdvisorCode = travelAdvisorCode;
        this.blankDate = blankDate;
        this.mcoText = mcoText;
        this.assignedDate = assignedDate;
        this.customerAlias = customerAlias;
    }


}
