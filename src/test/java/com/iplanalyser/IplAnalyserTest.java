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
            iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH);
            String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
            IPLDTOClass[] mostRunCsv = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
            Assert.assertEquals(83.2, mostRunCsv[0].battingAvg, 0.0);
        } catch (IplAnalyserException e) {
        }
    }

    @Test
    public void givenCricketStrikingRateData_WhenSorted_ShouldReturnMostStrikingRuns() {
        try {
            iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH);
            String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.STRIKE_RATE);
            IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
            Assert.assertEquals(333.33, censusCSV[0].strikeRate, 0.0);
        } catch (IplAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCricketMaximumFourAndSixes_WhenSorted_ShouldReturnMaxFoursAndSixes() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.FOURS_AND_SIX);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(83, censusCSV[0].four + censusCSV[0].six, 0.0);
    }

    @Test
    public void givenCricketMaximumFourAndSixes_WhenSorted_ShouldReturnBestStrikingRates() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.FOURS_AND_SIX);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Andre Russell", censusCSV[0].player);
    }

    @Test
    public void givenCricketMaximumAverages_WhenSorted_ShouldReturnBestStrikingRates() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("MS Dhoni", censusCSV[0].player);
    }

    @Test
    public void givenCricketMostRunData_WhenSorted_ShouldReturnBestAverage() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.RUNS);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(69.2, censusCSV[0].battingAvg, 0.0);
    }

    @Test
    public void givenCricketBowlingData_WhenSorted_ShouldReturnBestAverage() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BOWLER, IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals(166.0, censusCSV[0].battingAvg, 0.0);
    }

    @Test
    public void givenCricketBowlingStrikeRate_WhenSorted_ShouldReturnBestStrikingRatedPlayer() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BOWLER, IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.STRIKE_RATE);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Krishnappa Gowtham", censusCSV[0].player);
    }

    @Test
    public void givenCricketBowlingDataEconomy_WhenSorted_ShouldReturnBestEconomyRatedPlayer() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BOWLER, IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.ECONOMY);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Ben Cutting", censusCSV[0].player);
    }

    @Test
    public void givenCricketBowlingData5wand4w_WhenSorted_ShouldReturnBestEconomyRatedPlayer() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BOWLER, IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.FOUR_AND_FIVE_WKCT);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Lasith Malinga", censusCSV[0].player);
    }

    @Test
    public void givenCricketBowlingDataAverage_WhenSorted_ShouldReturnBestAveragedPlayer() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BOWLER, IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Krishnappa Gowtham", censusCSV[0].player);
    }

    @Test
    public void givenCricketBowlingDataWithMaximumWickets_WhenSorted_ShouldReturnBestAveragedPlayer() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BOWLER, IPL_BOWLING_FILE_PATH);
        String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.MAXIMUM_WICKETS);
        IPLDTOClass[] censusCSV = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
        Assert.assertEquals("Imran Tahir", censusCSV[0].player);
    }

    @Test
    public void givenCricketData_WhenSortedOnAverages_ShouldReturnBestBatsmanAndBowler() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH,IPL_BOWLING_FILE_PATH);
        String sortedBattingData = iplAnalyser.getSortedCricketData(SortField.BEST_BATTING_AND_BOWLING_AVERAGE);
        IPLDTOClass[] battingCensusCSV = new Gson().fromJson(sortedBattingData, IPLDTOClass[].class);
        Assert.assertEquals("Krishnappa Gowtham",battingCensusCSV[0].player);
    }

    @Test
    public void givenCricketData_WhenSortedOnAllRounder_ShouldReturnAllRounderPlayer() {
        iplAnalyser.loadIPLData(IplAnalyser.Player.BATSMAN, IPL_MOST_RUNS_FILE_PATH,IPL_BOWLING_FILE_PATH);
        String sortedBattingData = iplAnalyser.getSortedCricketData(SortField.ALL_ROUNDER);
        IPLDTOClass[] battingCensusCSV = new Gson().fromJson(sortedBattingData, IPLDTOClass[].class);
        Assert.assertEquals("Hardik Pandya",battingCensusCSV[0].player);

    }
}


