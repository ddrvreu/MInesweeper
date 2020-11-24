package com.beluxha.task3.model.clickprocessing;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.model.MinesweeperViewNotifier;
import com.beluxha.task3.model.creation.entity.Cell;
import com.beluxha.task3.model.creation.entity.CellTable;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class SelectCellOpenerImpl implements SelectCellOpener {
    private final MinesweeperViewNotifier viewNotifier;
    private final CellTable gameField;

    private boolean bombOpened;
    private final int chosenRowIndex;
    private final int chosenColumnIndex;
    private Set<Cell> cellSet;
    private List<Cell> openedCells;
    public SelectCellOpenerImpl(MinesweeperViewNotifier viewNotifier, CellTable gameField, int chosenRowIndex, int chosenColumnIndex) {
        log.info("opening cell with cords {}:{}", chosenRowIndex, chosenColumnIndex);

        this.viewNotifier = viewNotifier;
        this.gameField = gameField;
        this.chosenRowIndex = chosenRowIndex;
        this.chosenColumnIndex = chosenColumnIndex;
        openSelectedCell();
    }

    private void openSelectedCell() {
        if (gameField.getCell(chosenRowIndex, chosenColumnIndex).isFlagged()) {
            return;
        }
        if (gameField.getCell(chosenRowIndex, chosenColumnIndex).getCellCode() == GameCodesHolder.BOMB_CODE) {
            bombOpened = true;
            sendCellsForCaseWhenBombWasOpened(chosenRowIndex, chosenColumnIndex);
            return;
        }
        openGameField(chosenRowIndex, chosenColumnIndex);
        if (openedCells != null) {
            viewNotifier.notifyViewAboutCellsStatusChanged(openedCells);
            viewNotifier.notifyAboutCountOfCloseNoBombCells(gameField.getCountOfCloseNoBombCells());
        }
    }

    @Override
    public boolean isAllCellsOnGameFieldOpened() {
        return gameField.isAllCellsWithoutBombsOpened();
    }

    @Override
    public boolean isBombOpened() {
        return bombOpened;
    }

    private void openGameField(int rowIndex, int columnIndex) {
        cellSet = new HashSet<>();
        if (!gameField.getCell(rowIndex, columnIndex).isClose()) {
            return;
        }
        if (gameField.getCell(rowIndex, columnIndex).isFlagged()) {
            return;
        }
        if (gameField.getCell(rowIndex, columnIndex).getCellCode() == GameCodesHolder.BOMB_CODE) {
            return;
        }
        if (checkNullCellForOpenness(rowIndex, columnIndex)) {
            openArea(rowIndex, columnIndex);
            openedCells = new ArrayList<>(cellSet);
            return;
        }
        gameField.openCell(rowIndex, columnIndex);
        openedCells = new ArrayList<>(Collections.singletonList(gameField.getCell(rowIndex, columnIndex)));
    }

    private void openArea(int rowIndex, int columnIndex) {
        if (gameField.getCell(rowIndex, columnIndex).isFlagged()) {
            return;
        }
        gameField.openCell(rowIndex, columnIndex);
        cellSet.add(gameField.getCell(rowIndex, columnIndex));
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = columnIndex - 1; j <= columnIndex + 1; j++) {
                openNearToSpaceCell(i, j);
            }
        }
    }

    private void openNearToSpaceCell(int rowIndex, int columnIndex) {
        if (!gameField.checkForArrayIndexExist(rowIndex, columnIndex)) {
            return;
        }
        if (gameField.getCell(rowIndex, columnIndex).isFlagged()) {
            return;
        }
        if (checkNullCellForOpenness(rowIndex, columnIndex)) {
            openArea(rowIndex, columnIndex);
        }
        if (tryToAddClosedCellToSet(rowIndex, columnIndex)) {
            gameField.openCell(rowIndex, columnIndex);
        }
    }

    private void sendCellsForCaseWhenBombWasOpened(int chosenRowIndex, int chosenColumnIndex) {
        List<Cell> listOfCellsForCaseWhenBombWasOpened = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < gameField.getRowNumber(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameField.getColumnNumber(); columnIndex++) {
                if (gameField.getCell(rowIndex, columnIndex).isFlagged() && gameField.getCell(rowIndex, columnIndex).getCellCode() != GameCodesHolder.BOMB_CODE) {
                    listOfCellsForCaseWhenBombWasOpened.add(new Cell(rowIndex, columnIndex, GameCodesHolder.WRONG_FLAG_CODE));
                    continue;
                }
                if (rowIndex == chosenRowIndex && columnIndex == chosenColumnIndex) {
                    listOfCellsForCaseWhenBombWasOpened.add(new Cell(chosenRowIndex, chosenColumnIndex, GameCodesHolder.CHOSEN_BOMB_CODE));
                    continue;
                }
                if (gameField.getCell(rowIndex, columnIndex).getCellCode() == GameCodesHolder.BOMB_CODE) {
                    listOfCellsForCaseWhenBombWasOpened.add(gameField.getCell(rowIndex, columnIndex));
                }
            }
        }
        viewNotifier.notifyViewAboutCellsStatusChanged(listOfCellsForCaseWhenBombWasOpened);
    }

    private boolean checkNullCellForOpenness(int rowIndex, int columnIndex) {
        return gameField.getCell(rowIndex, columnIndex).getCellCode() == GameCodesHolder.NULL_CODE && gameField.getCell(rowIndex, columnIndex).isClose();
    }

    private boolean tryToAddClosedCellToSet(int rowIndex, int columnIndex) {
        return cellSet.add(gameField.getCell(rowIndex, columnIndex)) && gameField.getCell(rowIndex, columnIndex).isClose();
    }
}
