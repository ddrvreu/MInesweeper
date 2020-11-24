package com.beluxha.task3.controller;

import com.beluxha.task3.model.MinesweeperGame;
import com.beluxha.task3.model.MinesweeperGameImpl;

public class MinesweeperController {

    private final MinesweeperGame minesweeperGame;

    public MinesweeperController(MinesweeperGameImpl minesweeperGame) {
        this.minesweeperGame = minesweeperGame;
    }

    public void handleUserClickedOnCell(int row, int column) {
        minesweeperGame.openCell(row, column);
    }

    public void handleUserClickedOnCellRightMouseButton(int row, int column) {
        minesweeperGame.changeFlaggedStatus(row, column);
    }

    public void handleUserClickedOnCellCenterMouseButton(int row, int column) {
        minesweeperGame.openNotFlaggedAdjacentCells(row, column);
    }

    public void handleUserStartNewGame() {
        minesweeperGame.startGame();
    }

    public void handleUserStartNewGame(int gameTypeCode) {
        minesweeperGame.startGame(gameTypeCode);
    }

    public void handleUserStartNewGame(int rowNumber, int columnNumber, int bombNumber) {
        minesweeperGame.startGame(rowNumber, columnNumber, bombNumber);
    }

    public void handleRecordNameInput(String name) {
        minesweeperGame.setNewRecordName(name);
    }

    public void handleHighScoreTableRequest() {
        minesweeperGame.sendRecordsList();
    }
}
