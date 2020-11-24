package com.beluxha.task3.model;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.model.clickprocessing.*;
import com.beluxha.task3.model.creation.GameFieldCreatorImpl;
import com.beluxha.task3.model.creation.entity.Batch;
import com.beluxha.task3.model.creation.entity.CellTable;
import com.beluxha.task3.model.record.GameRecordsHolder;
import com.beluxha.task3.model.record.GameRecordsHolderImpl;
import com.beluxha.task3.model.record.exception.NoSuchCodeException;
import com.beluxha.task3.model.time.MinesweeperTimer;
import com.beluxha.task3.model.time.MinesweeperTimerListener;
import com.beluxha.task3.model.validation.NewGameValidator;
import com.beluxha.task3.model.validation.NewGameValidatorImpl;
import com.beluxha.task3.view.MinesweeperView;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;

@Slf4j
public class MinesweeperGameImpl implements MinesweeperGame, MinesweeperTimerListener {
    private final MinesweeperViewNotifier viewNotifier = new MinesweeperViewNotifier();
    private final GameRecordsHolder gameRecordsHolder = new GameRecordsHolderImpl();
    private FlagStatusChanger flagStatusChanger;
    private int currentGameCode;
    private int bombNumber;
    private int rowNumber;
    private int columnNumber;
    private boolean end;
    private boolean wasFirstOpeningOfCell;
    private MinesweeperTimer timer;
    private CellTable gameField;

    @Override
    public void attachView(MinesweeperView minesweeperView) {
        viewNotifier.attachView(minesweeperView);
    }

    @Override
    public void startGame() {
        changeGameToInitialState(currentGameCode);
    }

    @Override
    public void startGame(int gameCode) {

        NewGameValidator newGameValidator = new NewGameValidatorImpl();
        if (!newGameValidator.validateNewGame(gameCode)) {
            gameCode = GameCodesHolder.EASY_GAME_CODE;
        }
        for (Batch batch : Batch.values()) {
            if (batch.getCode() == gameCode) {
                this.rowNumber = batch.getRowNumber();
                this.columnNumber = batch.getColumnNumber();
                this.bombNumber = batch.getCountOfBombs();
            }
        }
        changeGameToInitialState(gameCode);
    }

    @Override
    public void startGame(int rowNumber, int columnNumber, int bombNumber) {
        NewGameValidator newGameValidator = new NewGameValidatorImpl();
        newGameValidator.validateNewGame(rowNumber, columnNumber, bombNumber);
        this.rowNumber = newGameValidator.getRowNumber();
        this.columnNumber = newGameValidator.getColumnNumber();
        this.bombNumber = newGameValidator.getBombNumber();
        changeGameToInitialState(GameCodesHolder.CUSTOM_GAME_CODE);
    }

    @Override
    public void openCell(int rowIndex, int columnIndex) {
        if (end) {
            return;
        }
        if (!wasFirstOpeningOfCell) {
            log.info("model is processing first move");
            wasFirstOpeningOfCell = true;
            newGameInitialization(rowIndex, columnIndex);
            timer = new MinesweeperTimer(new ArrayList<>(Collections.singletonList(this)));
            timer.start();
            handleCellOpening(rowIndex, columnIndex);
            return;
        }
        if (gameField.getCell(rowIndex, columnIndex).isFlagged()) {
            return;
        }
        handleCellOpening(rowIndex, columnIndex);
    }

    @Override
    public void openNotFlaggedAdjacentCells(int row, int column) {
        CellsAroundSelectCellOpener cellsAroundSelectCellOpener = new CellsAroundSelectCellOpenerImpl(viewNotifier, gameField, row, column);
        if (cellsAroundSelectCellOpener.isBombOpened()) {
            gameLose();
        }
        if (cellsAroundSelectCellOpener.isAllCellsOnGameFieldOpened()) {
            gameWin();
        }
    }

    @Override
    public void changeFlaggedStatus(int rowIndex, int columnIndex) {
        if (gameField == null) {
            return;
        }
        if (end) {
            return;
        }
        flagStatusChanger.changeCellFlagStatus(rowIndex, columnIndex);
    }

    @Override
    public void setNewRecordName(String name) {
        try {
            gameRecordsHolder.setNewRecord(currentGameCode, name, timer.getTimerOperationTime());
        } catch (NoSuchCodeException e) {
            log.info(e.getMessage());
        }
        changeGameToInitialState(currentGameCode);
    }

    @Override
    public void sendRecordsList() {
        viewNotifier.notifyViewsAboutRecordList(gameRecordsHolder.getRecords());
    }

    @Override
    public void updateTime(long time) {
        viewNotifier.notifyViewsAboutTimerChanged(time);
    }

    private void changeGameToInitialState(int currentGameCode) {
        log.info("changing game to initial state");
        this.currentGameCode = currentGameCode;
        if (timer != null) {
            timer.stopTimer();
        }
        end = false;
        wasFirstOpeningOfCell = false;
        viewNotifier.notifyViewsAboutNewGame(rowNumber, columnNumber);
    }

    private void newGameInitialization(int rowIndex, int columnIndex) {
        gameField = new GameFieldCreatorImpl(rowIndex, columnIndex, rowNumber, columnNumber, bombNumber).getGameField();
        flagStatusChanger = new FlagStatusChanger(viewNotifier, gameField);

    }

    private void gameWin() {
        finishGame();
        try {
            if (timer.getTimerOperationTime() < gameRecordsHolder.getCurrentBestGameResult(currentGameCode)) {
                viewNotifier.notifyViewsAboutNewRecord(timer.getTimerOperationTime());
                return;
            }
            viewNotifier.notifyViewsAboutGameWon(timer.getTimerOperationTime(), gameRecordsHolder.getCurrentBestGameResult(currentGameCode),
                    gameRecordsHolder.getCurrentBestGamePlayerName(currentGameCode));
        } catch (NoSuchCodeException e) {
            log.error(e.getMessage());
        }
    }

    private void gameLose() {
        log.info("game was lose");
        finishGame();
        viewNotifier.notifyViewsAboutGameLost(timer.getTimerOperationTime());
    }

    private void finishGame() {
        log.info("finishing game");
        end = true;
        timer.stopTimer();
        wasFirstOpeningOfCell = false;
    }

    private void handleCellOpening(int rowIndex, int columnIndex) {
        SelectCellOpener selectCellOpener = new SelectCellOpenerImpl(viewNotifier, gameField, rowIndex, columnIndex);
        if (selectCellOpener.isBombOpened()) {
            gameLose();
            return;
        }
        if (selectCellOpener.isAllCellsOnGameFieldOpened()) {
            gameWin();
        }
    }
}
