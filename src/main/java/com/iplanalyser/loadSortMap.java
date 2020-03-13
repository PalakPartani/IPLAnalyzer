package com.iplanalyser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class loadSortMap {
    Map<SortField, Comparator<IPLDTOClass>> sortMap;

    public loadSortMap() {
        this.sortMap.put(SortField.RUNS, Comparator.comparing(census -> census.runs));
        this.sortMap.put(SortField.AVG, Comparator.comparing(census -> census.battingAvg));
        this.sortMap.put(SortField.STRIKE_RATE, Comparator.comparing(census -> census.strikeRate));
        this.sortMap.put(SortField.STRIKE_RATE_WITH_FIVE_AND_FOUR_WCKTS, Comparator.comparing(census -> census.strikeRate));
        this.sortMap.put(SortField.FOURS_AND_SIX, Comparator.comparing(census -> census.four + census.six));
        this.sortMap.put(SortField.STRIKING_RATES_WITH_FOURS_AND_SIX, Comparator.comparing(census -> census.four + census.six));
        this.sortMap.put(SortField.ECONOMY, Comparator.comparing(stat -> stat.econ));
        this.sortMap.put(SortField.FOUR_AND_FIVE_WKCT, Comparator.comparing(census -> census.four + census.five));
        this.sortMap.put(SortField.MAXIMUM_WICKETS, Comparator.comparing(census -> census.wickets));
        this.sortMap.put(SortField.BEST_BATTING_AND_BOWLING_AVERAGE, new CompareAverage());
        this.sortMap.put(SortField.ALL_ROUNDER, new CompareAllRounder());
    }
}
