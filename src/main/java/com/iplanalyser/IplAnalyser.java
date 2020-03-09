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
    SortField sortField;

    public IplAnalyser() {
        this.sortMap = new HashMap<>();
        this.iplCSVList = new ArrayList<>();
        this.sortMap.put(SortField.RUNS, Comparator.comparing(census -> census.player));
        this.sortMap.put(SortField.AVG, Comparator.comparing(census -> census.avg));
        this.sortMap.put(SortField.STRIKE_RATE, Comparator.comparing(census -> census.strikeRate));
        this.sortMap.put(SortField.FOURS_AND_SIX, Comparator.comparing(census -> census.four + census.six));
        this.sortMap.put(SortField.STRIKING_RATES_WITH_FOURS_AND_SIX, Comparator.comparing(census -> census.four + census.six));
    }

    public Map<String, IPLDTOClass> loadIplData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IplRunsCSV> iterator = csvBuilder.getCSVFileIterator(reader, IplRunsCSV.class);
            Iterable<IplRunsCSV> csvIterable = () -> iterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .forEach(csvName -> iplMap.put(csvName.player, new IPLDTOClass(csvName)));
            iplCSVList = iplMap.values().stream().collect(Collectors.toList());
            System.out.println(iplMap);
            return iplMap;
        } catch (IOException e) {
            throw new IplAnalyserException(e.getMessage(), IplAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM);
        }
    }

    public String getSortedCricketData(SortField field) {

        if (iplCSVList == null || iplCSVList.size() == 0) {
            throw new IplAnalyserException("No Data found ", IplAnalyserException.ExceptionType.CRICKET_DATA_NOT_FOUND);
        }
        this.sort(iplCSVList, this.sortMap.get(field));
        Collections.reverse(iplCSVList);
        String sortedStateCensus = new Gson().toJson(iplCSVList);
        return sortedStateCensus;
    }

    private void sort(List<IPLDTOClass> cricketCSVList, Comparator<IPLDTOClass> censusComparator) {
        for (int i = 0; i < cricketCSVList.size() - 1; i++) {
            for (int j = 0; j < cricketCSVList.size() - i - 1; j++) {
                IPLDTOClass run1 = cricketCSVList.get(j);
                IPLDTOClass run2 = cricketCSVList.get(j + 1);
                if (censusComparator.compare(run1, run2) > 0) {
                    cricketCSVList.set(j, run2);
                    cricketCSVList.set(j + 1, run1);
                }
            }
        }
    }
}
