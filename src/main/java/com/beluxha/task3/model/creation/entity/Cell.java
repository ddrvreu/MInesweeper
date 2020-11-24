package com.beluxha.task3.model.creation.entity;

import lombok.Getter;

@Getter
public class Cell {
    private final int cellCode;
    private final int rowIndex;
    private final int columnIndex;
    private boolean close = true;
    private boolean flagged;

    public Cell(int rowIndex, int columnIndex, int cellCode) {
        this.cellCode = cellCode;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public void assignOpenedStatus() {
        close = false;
    }

    public void setFlagged(boolean isFlagged) {
        this.flagged = isFlagged;
    }
}
