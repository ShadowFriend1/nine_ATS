package entities;

import java.time.LocalDate;

public class LatePayment {

    private int saleID;
    private String alias;

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public LatePayment(int saleID, String alias) {
        this.saleID = saleID;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
