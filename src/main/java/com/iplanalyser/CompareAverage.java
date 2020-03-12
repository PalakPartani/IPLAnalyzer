package com.iplanalyser;

import java.util.Comparator;

public class CompareAverage implements Comparator<IPLDTOClass> {
    @Override
    public int compare(IPLDTOClass p1, IPLDTOClass p2) {
        int avg = (int) ((p1.battingAvg + p1.ballingAvg) - (p2.battingAvg + p2.ballingAvg));
        return avg;
    }
}
