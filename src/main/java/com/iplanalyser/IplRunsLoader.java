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

public class IplRunsLoader extends IPLAdapter {

    @Override
    public Map<String, IPLDTOClass> loadIPLData(String... csvFilePath) {
        Map<String, IPLDTOClass> iplData = super.loadIPLData(IplRunsCSV.class, csvFilePath[0]);
        if (csvFilePath.length > 1)
            this.loadIPLData(iplData, csvFilePath[1]);
        return iplData;
    }

    private Map<String, IPLDTOClass> loadIPLData(Map<String, IPLDTOClass> iplMap, String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLBallsCSV> csvIterator = icsvBuilder.getCSVFileIterator(reader, IPLBallsCSV.class);
            Iterable<IPLBallsCSV> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    filter(csvStat->iplMap.get(csvStat.player)!=null).

                   // map(IPLBallsCSV.class::cast).
                    forEach(csvStat ->{iplMap.get(csvStat.player).ballingAvg=csvStat.battingAvg;
                    iplMap.get(csvStat.player).allWicket=csvStat.wickets;
        });

                           // new IPLDTOClass(csvStat)));
            return iplMap;
        }
        catch (IOException e) {
            throw new IplAnalyserException(e.getMessage(), IplAnalyserException.ExceptionType.CRICKET_FILE_PROBLEM);
        }

    }
   /* private void loadBowlingData(Map<String, IPLDTOClass> cricketCsvDtoMap, String csvFilePath) {
        try(Reader reader= Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder iCsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLBallsCSV> iterator=iCsvBuilder.getCSVFileIterator(reader,IPLBallsCSV.class);
            Iterable<IPLBallsCSV> iterable=() ->iterator;
            StreamSupport.stream(iterable.spliterator(),false)
                    .filter(iplMostWicketsAdapter -> cricketCsvDtoMap.get(iplMostWicketsAdapter.player)!=null )
                    .forEach(mergedData->{cricketCsvDtoMap.get(mergedData.player).ballingAvg=mergedData.battingAvg;
                        cricketCsvDtoMap.get(mergedData.player).allWicket=mergedData.wickets;});
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }



/*

    private Map<String, IPLDTO> loadIPLData(Map<String, IPLDTO> censusCSVMap, String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<IPLBowlerCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IPLBowlerCSV.class);
                Iterator<IPLBowlerCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IPLBowlerCSV.class);
                Iterable<IPLBowlerCSV> csvIterable = () -> censusCSVIterator;
                Iterable<IPLBowlerCSV> csvIterable = () -> censusCSVIterator;
                StreamSupport.stream(csvIterable.spliterator(), false).filter(stat -> censusCSVMap.get(stat.player) != null)
                StreamSupport.stream(csvIterable.spliterator(), false).filter(stat -> censusCSVMap.get(stat.player) != null)
                        .forEach(stat -> censusCSVMap.get(stat.player).bowlerAverage = stat.average);
                    .forEach(stat -> {
                    censusCSVMap.get(stat.player).bowlerAverage = stat.average;
                    censusCSVMap.get(stat.player).allWicket=stat.wicket;
                });
*/



