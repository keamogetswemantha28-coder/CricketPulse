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
    private String battingTeamName;
    private int inningsNumber;
    private int overNumber;
    private String extraType;

    public Delivery(String actualDelivery, String batter, String bowler, String nonStriker, int batterRuns, int extras, int totalRuns,
                    String playerOut, String wicketKind, String battingTeamName, int inningsNumber, int overNumber, String extraType) {
        this.actualDelivery = actualDelivery;
        this.batter = batter;
        this.bowler = bowler;
        this.nonStriker = nonStriker;
        this.batterRuns = batterRuns;
        this.extras = extras;
        this.totalRuns = totalRuns;
        this.playerOut = playerOut;
        this.wicketKind = wicketKind;
        this.battingTeamName = battingTeamName;
        this.inningsNumber = inningsNumber;
        this.overNumber = overNumber;
        this.extraType = extraType;
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

    public String getBattingTeamName() {
        return battingTeamName;
    }

    public int getInningsNumber() {
        return inningsNumber;
    }

    public int getOverNumber() {
        return overNumber;
    }

    public String getExtraType() {
        return extraType;
    }
}
