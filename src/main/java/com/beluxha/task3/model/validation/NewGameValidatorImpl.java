package com.beluxha.task3.model.validation;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.model.creation.entity.Batch;
import lombok.Getter;

@Getter
public class NewGameValidatorImpl implements NewGameValidator {
    private int rowNumber;
    private int columnNumber;
    private int bombNumber;

    @Override
    public boolean validateNewGame(int gameCode) {
        for (Batch batch : Batch.values()) {
            if (batch.getCode() == gameCode) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void validateNewGame(int rowNumber, int columnNumber, int bombNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.bombNumber = bombNumber;
        if (GameCodesHolder.CUSTOM_GAME_MIN_ROW_NUMBER > rowNumber) {
            this.rowNumber = GameCodesHolder.CUSTOM_GAME_MIN_ROW_NUMBER;
        }
        if (GameCodesHolder.CUSTOM_GAME_MAX_ROW_NUMBER < rowNumber) {
            this.rowNumber = GameCodesHolder.CUSTOM_GAME_MAX_ROW_NUMBER;
        }
        if (GameCodesHolder.CUSTOM_GAME_MIN_COLUMN_NUMBER > columnNumber) {
            this.columnNumber = GameCodesHolder.CUSTOM_GAME_MIN_COLUMN_NUMBER;
        }
        if (GameCodesHolder.CUSTOM_GAME_MAX_COLUMN_NUMBER < columnNumber) {
            this.columnNumber = GameCodesHolder.CUSTOM_GAME_MAX_COLUMN_NUMBER;
        }
        if (GameCodesHolder.CUSTOM_GAME_MIN_BOMB_COUNT > bombNumber) {
            this.bombNumber = GameCodesHolder.CUSTOM_GAME_MIN_BOMB_COUNT;
        }
        if (countOfAvailableBombs() < bombNumber) {
            this.bombNumber = countOfAvailableBombs();
        }
    }

    private int countOfAvailableBombs() {
        return rowNumber * columnNumber - 1;
    }
}
