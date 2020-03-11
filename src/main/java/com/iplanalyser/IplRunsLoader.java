package com.iplanalyser;

import java.util.Map;

public class IplRunsLoader extends IPLAdapter{


    @Override
    public Map<String, IPLDTOClass> loadIPLData(String csvFilePath) {
        return super.loadIPLData(IplRunsCSV.class, csvFilePath);
    }
}
