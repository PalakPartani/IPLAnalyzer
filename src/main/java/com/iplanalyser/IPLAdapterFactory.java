package com.iplanalyser;

import java.util.Map;

public class IPLAdapterFactory {

    public <E> Map<String, IPLDTOClass> getIPLAdaptor(IplAnalyser.Player player, String csvFilePath) {
        if (player.equals(IplAnalyser.Player.BATSMAN))
            return new IplRunsLoader().loadIPLData(csvFilePath);
        if (player.equals(IplAnalyser.Player.BOWLER))
            return new IplBallsLoader().loadIPLData(csvFilePath);
        else
            throw new IplAnalyserException("Invalid stat type", IplAnalyserException.ExceptionType.INVALID);
    }
}

