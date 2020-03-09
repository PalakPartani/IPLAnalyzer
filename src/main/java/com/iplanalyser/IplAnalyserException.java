package com.iplanalyser;

public class IplAnalyserException extends RuntimeException {
    enum ExceptionType {
        CRICKET_FILE_PROBLEM, CRICKET_DATA_NOT_FOUND;
    }

    ExceptionType type;

    public IplAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
