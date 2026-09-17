package za.co.keamogetswe.cricketpulse;

public class Delivery {
    private String actualDelivery;
    private String batter;
    private String bowler;
    private String nonStriker;
    private int batterRuns;
    private int extras;
    private int totalRuns;
    private String playerOut;
    private String wicketKind;

    public Delivery(String actualDelivery, String batter, String bowler, String nonStriker,
                    int batterRuns, int extras, int totalRuns, String playerOut, String wicketKind) {
        this.actualDelivery = actualDelivery;
        this.batter = batter;
        this.bowler = bowler;
        this.nonStriker = nonStriker;
        this.batterRuns = batterRuns;
        this.extras = extras;
        this.totalRuns = totalRuns;
        this.playerOut = playerOut;
        this.wicketKind = wicketKind;
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

    public String getPlayerOut() {
        return playerOut;
    }

    public String getWicketKind() {
        return wicketKind;
    }
}
