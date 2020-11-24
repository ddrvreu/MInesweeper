package com.beluxha.task3.model.record;

import com.beluxha.task3.model.record.exception.NoSuchCodeException;
import com.beluxha.task3.model.record.entity.Record;

import java.util.List;

public interface GameRecordsHolder {

    long getCurrentBestGameResult(int gameCode) throws NoSuchCodeException;

    String getCurrentBestGamePlayerName(int gameCode) throws NoSuchCodeException;

    void setNewRecord(int gameCode, String playerName, long time) throws NoSuchCodeException;

    List<Record> getRecords();
}
