package com.beluxha.task3.model.creation.entity;

import lombok.Getter;

public class CellTable {
    private final Cell[][] gameField;
    @Getter
    private  final int rowNumber;
    @Getter
    private  final int columnNumber;
    private int countOfCloseNoBombCells;

    public CellTable(int rowNumber, int columnNumber, int bombNumber) {
        this.gameField = new Cell[rowNumber][columnNumber];
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.countOfCloseNoBombCells = rowNumber * columnNumber - bombNumber;
    }

    public int getCountOfCloseNoBombCells() {
        return countOfCloseNoBombCells;
    }

    public boolean isAllCellsWithoutBombsOpened() {
        return countOfCloseNoBombCells == 0;
    }

    public void openCell(int rowIndex, int columnIndex) {
        gameField[rowIndex][columnIndex].assignOpenedStatus();
        countOfCloseNoBombCells--;
    }

    public Cell getCell(int rowIndex, int columnIndex) {
        return gameField[rowIndex][columnIndex];
    }

    public void setCell(Cell cell, int rowIndex, int columnIndex) {
        gameField[rowIndex][columnIndex] = cell;
    }

    public boolean checkForArrayIndexExist(int rowIndex, int columnIndex) {
        return rowIndex >= 0
                && columnIndex >= 0
                && rowIndex < rowNumber
                && columnIndex < columnNumber;
    }
}
