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

public class IPLLoader {
    public static <E> Map<String, IPLDTOClass> loadIPLData(IplAnalyser.Player player, String csvFilePath) {
        if (player.equals(IplAnalyser.Player.BATSMAN))
            return loadIPLData(IplRunsCSV.class, csvFilePath);
        if (player.equals(IplAnalyser.Player.BOWLER))
            return loadIPLData(IPLBallsCSV.class, csvFilePath);
        else
            throw new IplAnalyserException("INVALID", IplAnalyserException.ExceptionType.INVALID_FILE_TYPE);
    }

    private static <E> Map<String, IPLDTOClass> loadIPLData(Class<E> iplClass, String csvFilePath) {
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

