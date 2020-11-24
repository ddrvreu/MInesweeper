package com.beluxha.task3.model;

import com.beluxha.task3.model.creation.entity.Cell;
import com.beluxha.task3.model.record.entity.Record;
import com.beluxha.task3.view.MinesweeperView;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperViewNotifier {

    private final List<MinesweeperView> minesweeperViews = new ArrayList<>();

    void attachView(MinesweeperView minesweeperView) {
        minesweeperViews.add(minesweeperView);
    }

    void notifyViewsAboutNewGame(int rowNumber, int columnNumber) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.renderNewGame(rowNumber, columnNumber));
    }

    public void notifyViewAboutCellsStatusChanged(List<Cell> cells) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.fillCells(cells));
    }

    public void notifyViewAboutCellFlaggedStatusChanged(int row, int column, int cellSpriteKey, int countOfFlags) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.updateFlag(row, column, cellSpriteKey, countOfFlags));
    }

    public void notifyViewsAboutTimerChanged(long currentSeconds) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.updateTimer(currentSeconds));
    }

    public void notifyViewsAboutGameWon(long currentSeconds, long recordTime, String name) {
        minesweeperViews.forEach(minesweeperView->minesweeperView.renderGameWon(currentSeconds, recordTime, name));
    }

    public void notifyViewsAboutGameLost(long currentSeconds) {
        minesweeperViews.forEach(minesweeperView->minesweeperView.renderGameLost(currentSeconds));
    }

    public void notifyViewsAboutNewRecord(long timerOperationTime) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.showNewGameRecord(timerOperationTime));
    }

    public void notifyViewsAboutRecordList(List<Record> records) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.renderRecordTable(records));
    }

    public void notifyAboutCountOfCloseNoBombCells(int countOfCloseNoBombCells) {
        minesweeperViews.forEach(minesweeperView -> minesweeperView.updateCountOfCloseNoBombCells(countOfCloseNoBombCells));
    }
}
