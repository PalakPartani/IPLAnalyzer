package com.iplanalyser;

import java.util.Comparator;

public class ComparatorCheck implements Comparator<IPLDTOClass> {
    @Override
    public int compare(IPLDTOClass player1, IPLDTOClass player2) {
        int i = (player1.four + player1.six) - (player2.six  - player2.four);
        return i;
    }
}
