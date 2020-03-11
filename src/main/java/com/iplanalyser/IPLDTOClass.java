package com.iplanalyser;

public class IPLDTOClass {
    public int five;
    public String player;
    public int runs;
    public int four;
    public int six;
    public double strikeRate;
    public double battingAvg;
    public double econ;

    public IPLDTOClass(IplRunsCSV iplRunsCSV) {
        runs = iplRunsCSV.runs;
        battingAvg = iplRunsCSV.battingAvg;
        player = iplRunsCSV.player;
        strikeRate = iplRunsCSV.strikeRate;
        four = iplRunsCSV.four;
        six = iplRunsCSV.six;
    }

    public IPLDTOClass(IPLBallsCSV iplBallsCSV) {
        player = iplBallsCSV.player;
        runs = iplBallsCSV.runs;
        four = iplBallsCSV.four;
        five = iplBallsCSV.five;
        strikeRate = iplBallsCSV.strikeRate;
        battingAvg = iplBallsCSV.battingAvg;
        econ = iplBallsCSV.econ;
    }
}
