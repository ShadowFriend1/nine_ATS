package entities;

import javafx.scene.control.cell.PropertyValueFactory;

public class StockElementAdvisor {

    private final int code;
    private final long assStart;
    private final long assEnd;
    private final int assAmount;
    private final long soldStart;
    private final long soldEnd;
    private final int soldAmount;
    private final int advisorFinal;

    public int getCode() {return code;}

    public long getAssStart() {return assStart;}

    public long getAssEnd() {return assEnd;}

    public int getAssAmount() {return assAmount;}

    public long getSoldStart() {return soldStart;}

    public long getSoldEnd() {return soldEnd;}

    public int getSoldAmount() {return soldAmount;}

    public int getAdvisorFinal() {return advisorFinal;}

    public StockElementAdvisor(int code, long assStart, long assEnd, int assAmount, long soldStart, long soldEnd,
                               int soldAmount, int advisorFinal) {
        this.code = code;
        this.assStart = assStart;
        this.assEnd = assEnd;
        this.assAmount = assAmount;
        this.soldStart = soldStart;
        this.soldEnd = soldEnd;
        this.soldAmount = soldAmount;
        this.advisorFinal =advisorFinal;
    }
}
