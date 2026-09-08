package za.co.keamogetswe.cricketpulse;

public class Delivery {
    private String actualDelivery;
    private String batter;
    private String bowler;
    private String nonStriker;
    private int batterRuns;
    private int extras;
    private int totalRuns;

    public Delivery(String actualDelivery, String batter, String bowler,
                    String nonStriker, int batterRuns, int extras, int totalRuns) {
        this.actualDelivery = actualDelivery;
        this.batter = batter;
        this.bowler = bowler;
        this.nonStriker = nonStriker;
        this.batterRuns = batterRuns;
        this.extras = extras;
        this.totalRuns = totalRuns;
    }

    public String getActualDelivery() {
        return actualDelivery;
    }

    public String getBatter() {
        return batter;
    }

    public String getBowler() {
        return bowler;
    }

    public String getNonStriker() {
        return nonStriker;
    }

    public int getBatterRuns() {
        return batterRuns;
    }

    public int getExtras() {
        return extras;
    }

    public int getTotalRuns() {
        return totalRuns;
    }
}
