package com.beluxha.task3.model.creation;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.model.creation.entity.Cell;
import com.beluxha.task3.model.creation.entity.CellTable;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GameFieldCreatorImpl implements GameFieldCreator {
    private final static boolean BOMB = true;
    private final static boolean NO_BOMB = false;
    private final int rowNumber;
    private final int columnNumber;
    private final int bombCount;
    private final int chosenCellRowIndex;
    private final int chosenCellColumnIndex;

    private CellTable gameField;
    List<Boolean> bombPositionsList;

    public GameFieldCreatorImpl(int chosenCellRowIndex, int chosenCellColumnIndex, int rowNumber, int columnNumber, int bombCount) {
        log.info("GameFieldCreatorImpl is starting to create game field");
        this.chosenCellRowIndex = chosenCellRowIndex;
        this.chosenCellColumnIndex = chosenCellColumnIndex;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.bombCount = bombCount;
        createGameField();
    }

    @Override
    public CellTable getGameField() {
        return gameField;
    }

    private void createGameField() {
        creatingAndMixingBombsList();
        gameField = new CellTable(rowNumber, columnNumber, bombCount);
        for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {
                gameField.setCell(createCell(rowIndex, columnIndex), rowIndex, columnIndex);
            }
        }
    }

    private Cell createCell(int rowIndex, int columnIndex) {
        if (bombPositionsList.get(rowIndex * columnNumber + columnIndex) == BOMB) {
            return new Cell(rowIndex, columnIndex, GameCodesHolder.BOMB_CODE);
        } else {
            return new Cell(rowIndex, columnIndex, calculateNumberOfAdjacentBombs(rowIndex, columnIndex));
        }
    }

    private void creatingAndMixingBombsList() {
        bombPositionsList = new ArrayList<>();
        for (int bombPositionListIndex = 0; bombPositionListIndex < bombCount; bombPositionListIndex++) {
            bombPositionsList.add(BOMB);
        }
        for (int bombPositionListIndex = bombCount; bombPositionListIndex < rowNumber * columnNumber - 1; bombPositionListIndex++) {
            bombPositionsList.add(NO_BOMB);
        }
        Collections.shuffle(bombPositionsList);
        bombPositionsList.add(chosenCellRowIndex * columnNumber + chosenCellColumnIndex, NO_BOMB);
    }

    private int calculateNumberOfAdjacentBombs(int i, int j) {
        int countOfAdjacentBombs = 0;
        for (int rowIndex = i - 1; rowIndex <= i + 1; rowIndex++) {
            for (int columnIndex = j - 1; columnIndex <= j + 1; columnIndex++) {
                countOfAdjacentBombs = incrementCountOfAdjacentBombsIfAdjacentCellHasBomb(countOfAdjacentBombs, rowIndex, columnIndex);
            }
        }
        return countOfAdjacentBombs;
    }

    private int incrementCountOfAdjacentBombsIfAdjacentCellHasBomb(int countOfAdjacentBombs, int rowIndex, int columnIndex) {
        if (gameField.checkForArrayIndexExist(rowIndex, columnIndex) && bombPositionsList.get(rowIndex * columnNumber + columnIndex) == BOMB) {
            countOfAdjacentBombs++;
        }
        return countOfAdjacentBombs;
    }

}
