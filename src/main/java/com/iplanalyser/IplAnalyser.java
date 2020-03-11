package com.iplanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class IplAnalyser {

    public enum Player {
        BATSMAN, BOWLER
    }

    List<IPLDTOClass> iplCSVList = null;
    Map<String, IPLDTOClass> iplMap = new HashMap<>();
    Map<SortField, Comparator<IPLDTOClass>> sortMap;
    // SortField sortField;

    public IplAnalyser() {
        this.sortMap = new HashMap<>();
        this.iplCSVList = new ArrayList<>();

        this.sortMap.put(SortField.RUNS, Comparator.comparing(census -> census.runs));
        this.sortMap.put(SortField.AVG, Comparator.comparing(census -> census.battingAvg));
        this.sortMap.put(SortField.STRIKE_RATE, Comparator.comparing(census -> census.strikeRate));
        this.sortMap.put(SortField.FOURS_AND_SIX, Comparator.comparing(census -> census.four + census.six));
        this.sortMap.put(SortField.STRIKING_RATES_WITH_FOURS_AND_SIX, Comparator.comparing(census -> census.four + census.six));
        this.sortMap.put(SortField.ECONOMY, Comparator.comparing(stat -> stat.econ));
    }

    public int loadIPLData(Player player, String csvFilePath) {
        iplMap = new IPLAdapterFactory().getIPLAdaptor(player, csvFilePath);
        return iplMap.size();
    }




       /* public int loadIplRunsData(String csvFilePath) {
        iplMap = IPLAdapter.loadIPLData(IplRunsCSV.class, csvFilePath);
        return iplMap.size();
    }

    public int loadIplBallData(String csvFilePath) {
        iplMap = IPLAdapter.loadIPLData(IPLBallsCSV.class, csvFilePath);
        System.out.println("MAO" + iplMap);
        return iplMap.size();
    }*/

    public String getSortedCricketData(SortField field) {

        iplCSVList = iplMap.values().stream().collect(Collectors.toList());

        if (iplCSVList == null || iplCSVList.size() == 0) {
            throw new IplAnalyserException("No Data found ", IplAnalyserException.ExceptionType.CRICKET_DATA_NOT_FOUND);
        }
        iplCSVList = iplMap.values().stream().collect(Collectors.toList());
        this.sort(this.sortMap.get(field).reversed());
        String sortedStateCensus = new Gson().toJson(iplCSVList);
        return sortedStateCensus;
    }

    private void sort(Comparator<IPLDTOClass> censusComparator) {
        for (int i = 0; i < iplCSVList.size() - 1; i++) {
            for (int j = 0; j < iplCSVList.size() - i - 1; j++) {
                IPLDTOClass run1 = iplCSVList.get(j);
                IPLDTOClass run2 = iplCSVList.get(j + 1);
                if (censusComparator.compare(run1, run2) > 0) {
                    iplCSVList.set(j, run2);
                    iplCSVList.set(j + 1, run1);
                }
            }
        }
    }
}
