package entities.SaleReports;

public class IndividualSaleReport {

    private final String blankID;
    private final float USDFare;
    private final float fare;
    private final float localTax;
    private final float otherTax;
    private final float cash;
    private final String cardName;
    private final long cardNumber;
    private final float cardAmount;
    private final String commission;
    private final float commissionAmount;
    private final String remittance;

    public String getBlankID() {
        return blankID;
    }

    public float getUSDFare() {
        return USDFare;
    }

    public IndividualSaleReport(String id, float usdFare, float fare, float lTax, float oTax, float cash, String cName, long cNum, float cAmount, String commission, float commissionAmount, String rem) {
        blankID = id;
        USDFare = usdFare;
        this.fare = fare;
        localTax = lTax;
        otherTax = oTax;
        this.cash = cash;
        cardName = cName;
        cardNumber = cNum;
        cardAmount = cAmount;
        this.commission = commission;
        this.commissionAmount = commissionAmount;
        remittance = rem;
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

    public String getCardName() {
        return cardName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public float getCardAmount() {
        return cardAmount;
    }

    public String getCommission() {
        return commission;
    }

    public float getCommissionAmount() {
        return commissionAmount;
    }

    public String getRemittance() {
        return remittance;
    }

    public float getFare() {
        return fare;
    }
}
