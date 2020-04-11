package entities.SaleReports;

public class GlobalSaleReport {

    private final String code;
    private final int numSales;
    private final float payment;
    private final float localTax;
    private final float otherTax;
    private final float cash;
    private final float card;
    private final float commission;
    private final String remittance;

    public GlobalSaleReport(String code, int numSales, float payment, float localTax, float otherTax, float cash, float card, float commission, String remittance) {
        this.code = code;
        this.numSales = numSales;
        this.payment = payment;
        this.localTax = localTax;
        this.otherTax = otherTax;
        this.cash = cash;
        this.card = card;
        this.commission = commission;
        this.remittance = remittance;
    }

    public float getOtherTax() {
        return otherTax;
    }

    public float getLocalTax() {
        return localTax;
    }

    public float getCash() {
        return cash;
    }

    public float getCommission() {
        return commission;
    }

    public String getCode() {
        return code;
    }

    public int getNumSales() {
        return numSales;
    }

    public float getPayment() {
        return payment;
    }

    public float getCard() {
        return card;
    }

    public String getRemittance() {
        return remittance;
    }
}
