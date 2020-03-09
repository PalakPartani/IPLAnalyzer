package com.iplanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IplAnalyserTest {
    private static IplAnalyser iplAnalyser;
    private static final String IPL_MOST_RUNS_FILE_PATH = "./src/test/resources/IPL2019FactsheetMostRuns.csv";

    @BeforeClass
    public static void setUp() throws Exception {
        iplAnalyser = new IplAnalyser();
    }

    @Test
    public void givenCricketMostRunData_WhenSorted_ShouldReturnMostRuns() {
        try {
            iplAnalyser.loadIplData(IPL_MOST_RUNS_FILE_PATH);
            String sortedCricketData = iplAnalyser.getSortedCricketData(SortField.AVG);
            IPLDTOClass[] mostRunCsv = new Gson().fromJson(sortedCricketData, IPLDTOClass[].class);
            Assert.assertEquals(83.2, mostRunCsv[0].battingAvg, 0.0);
        } catch (IplAnalyserException e) {
        }
    }
}


