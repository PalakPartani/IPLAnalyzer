package com.iplanalyser;

public class IPLDTOClass {
    public String player;
    public int runs;
    public int four;
    public int six;
    public double strikeRate;
    public double avg;
    public double battingAvg;

    public IPLDTOClass(IplRunsCSV iplRunsCSV) {
        battingAvg = iplRunsCSV.battingAvg;
        player = iplRunsCSV.player;
        strikeRate = iplRunsCSV.strikeRate;
    }

}
