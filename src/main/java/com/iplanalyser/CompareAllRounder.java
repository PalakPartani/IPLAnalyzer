package com.iplanalyser;

public class CompareAllRounder implements java.util.Comparator<IPLDTOClass> {

    @Override
    public int compare(IPLDTOClass p1, IPLDTOClass p2) {
        int total = (p1.runs * p1.allWicket) - (p2.runs * p2.allWicket);
        return total;
    }
}
