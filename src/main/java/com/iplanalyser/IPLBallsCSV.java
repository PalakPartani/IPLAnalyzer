package com.iplanalyser;

import com.opencsv.bean.CsvBindByName;

public class IPLBallsCSV {

    @CsvBindByName(column = "PLAYER", required = true)
    public String player;

    @CsvBindByName(column = "Runs", required = true)
    public int runs;

    @CsvBindByName(column = "4w", required = true)
    public int four;

    @CsvBindByName(column = "SR", required = true)
    public double strikeRate;

    @CsvBindByName(column = "Avg", required = true)
    public double battingAvg;

    @CsvBindByName(column = "5w", required = true)
    public int five;
}
