package com.beluxha.task3.model.clickprocessing;

import com.beluxha.task3.model.MinesweeperViewNotifier;
import com.beluxha.task3.model.creation.entity.Cell;
import com.beluxha.task3.model.creation.entity.CellTable;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CellsAroundSelectCellOpenerImpl implements CellsAroundSelectCellOpener {
    private final MinesweeperViewNotifier viewNotifier;
    private final CellTable gameField;
    private final int chosenRowIndex;
    private final int chosenColumnIndex;
    private boolean allCellsOnGameFieldOpened;
    private boolean bombOpened;
    private List<Cell> cells;

    public CellsAroundSelectCellOpenerImpl(MinesweeperViewNotifier viewNotifier, CellTable gameField, int chosenRowIndex, int chosenColumnIndex) {
        log.info("opening cells around cell with cords {}:{}", chosenRowIndex, chosenColumnIndex);
        this.viewNotifier = viewNotifier;
        this.gameField = gameField;
        this.chosenRowIndex = chosenRowIndex;
        this.chosenColumnIndex = chosenColumnIndex;
        fillListWithCellsToOpen();
        openCellsAround();
    }

    @Override
    public boolean isBombOpened() {
        return bombOpened;
    }

    @Override
    public boolean isAllCellsOnGameFieldOpened() {
        return allCellsOnGameFieldOpened;
    }

    private void openCellsAround() {
        for (Cell cell : cells) {
            SelectCellOpener selectCellOpener = new SelectCellOpenerImpl(viewNotifier, gameField, cell.getRowIndex(), cell.getColumnIndex());
            if (selectCellOpener.isBombOpened()) {
                this.bombOpened = true;
                return;
            }
            if (selectCellOpener.isAllCellsOnGameFieldOpened()) {
                this.allCellsOnGameFieldOpened = true;
                return;
            }
        }
    }

    private void fillListWithCellsToOpen() {
        cells = new ArrayList<>();
        if (checkPossibilityForOpeningTheAreaAroundTheCell()) {
            return;
        }
        for (int rowIndex = chosenRowIndex - 1; rowIndex <= chosenRowIndex + 1; rowIndex++) {
            for (int columnIndex = chosenColumnIndex - 1; columnIndex <= chosenColumnIndex + 1; columnIndex++) {
                addToListNoFlaggedCellAround(rowIndex, columnIndex);
            }
        }
    }

    private void addToListNoFlaggedCellAround(int row, int column) {
        if (gameField.checkForArrayIndexExist(row, column) && !(gameField.getCell(row, column).isFlagged())) {
            cells.add(gameField.getCell(row, column));
        }
    }

    private boolean checkPossibilityForOpeningTheAreaAroundTheCell() {
        return gameField.getCell(chosenRowIndex, chosenColumnIndex).isClose()
                || countNumberOfFlagsAdjacentToCell(chosenRowIndex, chosenColumnIndex) != gameField.getCell(chosenRowIndex, chosenColumnIndex).getCellCode();
    }

    private int countNumberOfFlagsAdjacentToCell(int chosenRowIndex, int chosenColumnIndex) {
        int countOfAdjacentFlags = 0;
        for (int rowIndex = chosenRowIndex - 1; rowIndex <= chosenRowIndex + 1; rowIndex++) {
            for (int columnIndex = chosenColumnIndex - 1; columnIndex <= chosenColumnIndex + 1; columnIndex++) {
                if (gameField.checkForArrayIndexExist(rowIndex, columnIndex) && gameField.getCell(rowIndex, columnIndex).isFlagged()) {
                    countOfAdjacentFlags++;
                }
            }
        }
        return countOfAdjacentFlags;
    }
}
