package com.beluxha.task3.view;

import com.beluxha.task3.model.creation.entity.Cell;
import com.beluxha.task3.model.record.entity.Record;

import java.util.List;

public interface MinesweeperView {

    void fillCells(List<Cell> cells);

    void updateTimer(long time);

    void renderNewGame(int rowNumber, int columnNumber);

    void renderGameWon(long time, long recordTime, String name);

    void updateFlag(int row, int column, int cellFlagKey, int countOfFlags);

    void renderGameLost(long time);

    void renderTableWithNewSize(int size);

    void showNewGameRecord(long timerOperationTime);

    void renderRecordTable(List<Record> records);

    void updateCountOfCloseNoBombCells(int countOfCloseNoBombCells);
}
