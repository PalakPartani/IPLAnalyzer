package com.iplanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class IplAnalyser {

    public enum Player {
        BATSMAN, BOWLER
    }

    List<IPLDTOClass> iplCSVList = null;
    Map<String, IPLDTOClass> iplStoreMap = new HashMap<>();
    Map<SortField, Comparator<IPLDTOClass>> sortMap;

    public int loadIPLData(Player player, String... csvFilePath) {
        iplStoreMap = new IPLAdapterFactory().getIPLAdaptor(player, csvFilePath);
        return iplStoreMap.size();
    }

    public String getSortedCricketData(SortField field) {

        iplCSVList = iplStoreMap.values().stream().collect(Collectors.toList());

        if (iplCSVList == null || iplCSVList.size() == 0) {
            throw new IplAnalyserException("No Data found ", IplAnalyserException.ExceptionType.CRICKET_DATA_NOT_FOUND);
        }
        iplCSVList = iplStoreMap.values().stream().collect(Collectors.toList());
        this.sort(new loadSortMap().sortMap.get(field).reversed());
        String sortedStateCensus = new Gson().toJson(iplCSVList);
        return sortedStateCensus;
    }

    private void sort(Comparator<IPLDTOClass> censusComparator) {
        for (int i = 0; i < this.iplCSVList.size() - 1; i++) {
            for (int j = 0; j < this.iplCSVList.size() - i - 1; j++) {
                IPLDTOClass playerData1 = this.iplCSVList.get(j);
                IPLDTOClass playerData2 = this.iplCSVList.get(j + 1);
                if (censusComparator.compare(playerData1, playerData2) > 0) {
                    this.iplCSVList.set(j, playerData2);
                    this.iplCSVList.set(j + 1, playerData1);
                }
            }
        }
    }
}
