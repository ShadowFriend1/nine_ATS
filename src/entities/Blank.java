package entities;

import DBConnect.MyDBConnectivity;

import java.sql.SQLException;

import java.sql.Date;
import java.time.LocalDate;

public class Blank {

    private long blankID;
    private int blankType;
    private Integer travelAdvisorCode;
    private LocalDate blankDate;
    private String mcoText;
    private String assignedDate;

    public String getAssignedDate() { return assignedDate; }

    public void setAssignedDate(String assignedDate) {
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

    public Blank(long blankID, int blankType, Integer travelAdvisorCode ,String assignedDate, String mcoText, LocalDate blankDate) throws SQLException {
        this.blankID = blankID;
        this.blankType = blankType;
        this.travelAdvisorCode = travelAdvisorCode;
        this.blankDate = blankDate;
        this.mcoText = mcoText;
        this.assignedDate = assignedDate;
    }


}
