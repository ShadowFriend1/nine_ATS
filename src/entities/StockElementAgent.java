package entities;

public class StockElementAgent {

    private final long recStart;
    private final long recEnd;
    private final int recAmount;
    private final int finalAmount;

    public long getRecStart() {return recStart;}

    public long getRecEnd() {return recEnd;}

    public int getRecAmount() {return recAmount;}

    public int getFinalAmount() {return finalAmount;}

    public StockElementAgent(long recStart, long recEnd, int recAmount, int finalAmount) {
        this.recStart = recStart;
        this.recEnd = recEnd;
        this.recAmount = recAmount;
        this.finalAmount = finalAmount;
    }

}
