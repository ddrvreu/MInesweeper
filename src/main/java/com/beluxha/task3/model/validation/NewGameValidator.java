package com.beluxha.task3.model.validation;

public interface NewGameValidator {

    boolean validateNewGame(int gameCode);

    void validateNewGame(int rowNumber, int columnNumber, int bombNumber);

    int getRowNumber();

    int getColumnNumber();

    int getBombNumber();
}
