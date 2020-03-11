package com.iplanalyser;

import java.util.Map;

public class IplBallsLoader extends IPLAdapter {
    @Override
    public Map<String, IPLDTOClass> loadIPLData(String csvFilePath) {
        return super.loadIPLData(IPLBallsCSV.class, csvFilePath);
    }
}
