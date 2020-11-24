package com.beluxha.task3.model.clickprocessing;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.model.MinesweeperViewNotifier;
import com.beluxha.task3.model.creation.entity.CellTable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlagStatusChanger {
    private final MinesweeperViewNotifier minesweeperViewNotifier;
    private final CellTable gameField;
    private int countOfFlags;

    public FlagStatusChanger(MinesweeperViewNotifier minesweeperViewNotifier, CellTable gameField) {
        this.minesweeperViewNotifier = minesweeperViewNotifier;
        this.gameField = gameField;
    }

    public void changeCellFlagStatus(int rowIndex, int columnIndex) {
        log.info("Changing FLAG STATUS for cell with cords {}:{}", rowIndex, columnIndex);
        if (!gameField.getCell(rowIndex, columnIndex).isClose()) {
            return;
        }
        if (gameField.getCell(rowIndex, columnIndex).isFlagged()) {
            countOfFlags--;
            gameField.getCell(rowIndex, columnIndex).setFlagged(false);
            minesweeperViewNotifier.notifyViewAboutCellFlaggedStatusChanged(rowIndex, columnIndex, GameCodesHolder.SPACE_CODE, countOfFlags);
        } else {
            countOfFlags++;
            gameField.getCell(rowIndex, columnIndex).setFlagged(true);
            minesweeperViewNotifier.notifyViewAboutCellFlaggedStatusChanged(rowIndex, columnIndex, GameCodesHolder.FLAG_CODE, countOfFlags);
        }
    }

    public int getCountOfFlags() {
        return countOfFlags;
    }
}
