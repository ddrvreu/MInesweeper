package com.beluxha.task3.model;

import com.beluxha.task3.view.MinesweeperView;

public interface MinesweeperGame {

    void startGame();

    void startGame(int gameCode);

    void startGame(int rowNumber, int columnNumber, int bombNumber);

    void attachView(MinesweeperView minesweeperView);

    void openCell(int rowIndex, int columnIndex);

    void openNotFlaggedAdjacentCells(int row, int column);

    void changeFlaggedStatus(int rowIndex, int columnIndex);

    void setNewRecordName(String name);

    void sendRecordsList();
}
