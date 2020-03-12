package com.iplanalyser;

import com.csvreader.CSVBuilderFactory;
import com.csvreader.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class IPLAdapter {

    public abstract Map<String, IPLDTOClass> loadIPLData(String... csvFilePath);

    public static <E> Map<String, IPLDTOClass> loadIPLData(Class<E> iplClass, String csvFilePath) {
        Map<String, IPLDTOClass> iplMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvIterator = icsvBuilder.getCSVFileIterator(reader, iplClass);
            Iterable<E> csvIterable = () -> csvIterator;
            if (iplClass.getName() == "com.iplanalyser.IplRunsCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IplRunsCSV.class::cast).
                        forEach(csvStat -> iplMap.put(csvStat.player, new IPLDTOClass(csvStat)));
            } else if (iplClass.getName() == "com.iplanalyser.IPLBallsCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IPLBallsCSV.class::cast).
                        forEach(csvStat -> iplMap.put(csvStat.player, new IPLDTOClass(csvStat)));
            }
            return iplMap;
        } catch (IOException e) {
            throw new IplAnalyserException(e.getMessage(), IplAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM);
        }
    }
}

