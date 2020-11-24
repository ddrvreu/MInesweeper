package com.beluxha.task3.model.record.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Record {
    private static final String DEFAULT_PLAYER_NAME = "Minesweeper";
    private static final long DEFAULT_RESULT = 999999999999999L;

    private int gameCode;
    private String playerName;
    private long result;

    public Record(int gameCode) {
        this.gameCode = gameCode;
        playerName = DEFAULT_PLAYER_NAME;
        result = DEFAULT_RESULT;
    }

    public String getStringWithRecordFieldsWithSeparator(String separator) {
        return gameCode + separator + playerName + separator + result;
    }
}
