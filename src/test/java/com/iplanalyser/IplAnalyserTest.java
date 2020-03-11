package com.iplanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IplAnalyserTest {
    private static IplAnalyser iplAnalyser;
    private static final String IPL_MOST_RUNS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";
    private static final String IPL_BOWLING_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostWkts.csv";

    @BeforeClass
    public static void setUp() throws Exception {
        iplAnalyser = new IplAnalyser();
    }

    @Test
    public void givenCricketMostRunData_WhenSorted_ShouldReturnMostRuns() {
        try {
            iplAnalyser.loadIplRunsData(IPL_MOST_RUNS_FILE_PATH);
            String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
            IPLDTOClass[] mostRunCsv = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
            Assert.assertEquals(83.2, mostRunCsv[0].battingAvg, 0.0);
        } catch (IplAnalyserException e) {
        }
    }

    @Test
    public void givenCricketStrikingRateData_WhenSorted_ShouldReturnMostStrikingRuns() {
        try {
            iplAnalyser.loadIplRunsData(IPL_MOST_RUNS_FILE_PATH);
            String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.STRIKE_RATE);
            IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
            Assert.assertEquals(333.33, censusCSV[0].strikeRate, 0.0);
        } catch (IplAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCricketMaximumFourAndSixes_WhenSorted_ShouldReturnMaxFoursAndSixes() {
        iplAnalyser.loadIplRunsData(IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.FOURS_AND_SIX);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(83, censusCSV[0].four + censusCSV[0].six, 0.0);
    }

    @Test
    public void givenCricketMaximumFourAndSixes_WhenSorted_ShouldReturnBestStrikingRates() {
        iplAnalyser.loadIplRunsData(IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.STRIKING_RATES_WITH_FOURS_AND_SIX);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Andre Russell", censusCSV[0].player);
    }

    @Test
    public void givenCricketMaximumAverages_WhenSorted_ShouldReturnBestStrikingRates() {
        iplAnalyser.loadIplRunsData(IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(134.62, censusCSV[0].strikeRate, 0.0);
    }

    @Test
    public void givenCricketMostRunData_WhenSorted_ShouldReturnBestAverage() {
        iplAnalyser.loadIplRunsData(IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.RUNS);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(69.2, censusCSV[0].battingAvg, 0.0);
    }

    @Test
    public void givenCricketBowlingData_WhenSorted_ShouldReturnBestAverage() {
        iplAnalyser.loadIplBallData(IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(166.0, censusCSV[0].battingAvg, 0.0);
    }
}


