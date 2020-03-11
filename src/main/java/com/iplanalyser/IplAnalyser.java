package com.iplanalyser;

import com.csvreader.CSVBuilderFactory;
import com.csvreader.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IplAnalyser {
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
    }

    public int loadIplRunsData(String csvFilePath) {
        return loadIplData(IplRunsCSV.class, csvFilePath);
    }

    private <E> int loadIplData(Class<E> iplClass, String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> iterator = csvBuilder.getCSVFileIterator(reader, iplClass);
            Iterable<E> csvIterable = () -> iterator;
            if (iplClass.getName() == "com.iplanalyser.IplRunsCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IplRunsCSV.class::cast).
                        forEach(csvName -> iplMap.put(csvName.player, new IPLDTOClass(csvName)));
            } else if (iplClass.getName() == "com.iplanalyser.IPLBallsCSV") {
                StreamSupport.stream(csvIterable.spliterator(), false).
                        map(IPLBallsCSV.class::cast).
                        forEach(csvName -> iplMap.put(csvName.player, new IPLDTOClass(csvName)));
                iplCSVList = iplMap.values().stream().collect(Collectors.toList());
            }
            return iplMap.size();
        } catch (IOException e) {
            throw new IplAnalyserException(e.getMessage(), IplAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM);
        }
    }

    public int loadIplBallData(String csvFilePath) {
        return loadIplData(IPLBallsCSV.class, csvFilePath);
    }

    public String getSortedCricketData(SortField field) {

        if (iplMap == null || iplMap.size() == 0) {
            throw new IplAnalyserException("No Data found ", IplAnalyserException.ExceptionType.CRICKET_DATA_NOT_FOUND);
        }
        iplCSVList = iplMap.values().stream().collect(Collectors.toList());
        this.sort(this.sortMap.get(field).reversed());
        //Collections.reverse(iplCSVList);
        //  String sortedjson=new Gson().toJson(iplCSVList)
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
