package com.beluxha.task3.view;

import com.beluxha.task3.controller.MinesweeperController;
import com.beluxha.task3.model.creation.entity.Cell;
import com.beluxha.task3.model.record.entity.Record;
import com.beluxha.task3.view.dialog.gamelost.GameLostDialog;
import com.beluxha.task3.view.dialog.gamewon.GameWonDialog;
import com.beluxha.task3.view.dialog.newrecord.NewRecordDialog;
import com.beluxha.task3.view.dialog.recordtable.RecordsTableDialog;
import com.beluxha.task3.view.image.CellSpritesHolder;
import com.beluxha.task3.view.image.ImageHolder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.beluxha.task3.api.GameCodesHolder.SPACE_CODE;

@Slf4j
public class SwingMinesweeperView extends JFrame implements MinesweeperView {
    private static final String GAME_TITLE = "MINESWEEPER";
    private static final String FLAGS_LABEL_TEXT = "FLAGS: ";
    private static final String TIME_LABEL_TEXT = "TIME: ";
    private static final String BOMB_LABEL_TEXT = "BOMBS: ";
    private static final String NULL_FOR_LABEL = "0";
    private static final int ACTIVE_CELL_WIDTH = 10;
    private static final int ACTIVE_CELL_HEIGHT = 10;
    private static final int CELL_WIDTH = ACTIVE_CELL_WIDTH;
    private static final int CELL_HEIGHT = ACTIVE_CELL_HEIGHT;
    private static final int TIMER_PANEL_HEIGHT = 40;
    private static final int DEFAULT_SELL_SIZE_COEFFICIENT = 3;

    private final MinesweeperController minesweeperController;
    private MinesweeperCell[][] viewCells;

    private int rowNumber;
    private int columnNumber;

    private int cellSizeCoefficient = DEFAULT_SELL_SIZE_COEFFICIENT;
    private final JLabel timerLabel = new JLabel();
    private final JLabel countOfFlagsLabel = new JLabel();
    private final JLabel countOfBombsLabel = new JLabel();
    private ImageHolder cellSprites;
    private JPanel cellPanel;
    private JPanel informationPanel;

    public SwingMinesweeperView(MinesweeperController minesweeperController) {
        super(GAME_TITLE);
        this.minesweeperController = minesweeperController;
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setGameMenu(minesweeperController);
    }

    @Override
    public void renderNewGame(int rowNumber, int columnNumber) {
        log.info("View is rendering new game");
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        renderNewGameTable(true);
    }

    @Override
    public void renderGameWon(long time, long recordTime, String name) {
        log.info("View is starting GAME WON DIALOG");
        new GameWonDialog(minesweeperController, time, recordTime, name);
    }

    @Override
    public void renderGameLost(long time) {
        log.info("View is starting GAME LOST DIALOG");
        new GameLostDialog(minesweeperController);
    }

    @Override
    public void fillCells(List<Cell> cells) {
        log.info("View is filling cells with new image codes");
        for (Cell cell : cells) {
            fillCell(cell);
        }
    }

    @Override
    public void updateTimer(long time) {
        //log.info("View is updating timer");
        timerLabel.setText(TIME_LABEL_TEXT + time);
    }

    @Override
    public void updateFlag(int rowIndex, int columnIndex, int cellSpriteKey, int countOfFlags) {
        log.info("View is flagging or unflagging cell");
        viewCells[rowIndex][columnIndex].setIcon(new ImageIcon(cellSprites.getImage(cellSpriteKey)));
        viewCells[rowIndex][columnIndex].setImageCode(cellSpriteKey);
        this.countOfFlagsLabel.setText(FLAGS_LABEL_TEXT + countOfFlags);
    }

    @Override
    public void renderTableWithNewSize(int size) {
        log.info("View is changing size");
        this.cellSizeCoefficient = size;
        renderNewGameTable(false);
    }

    @Override
    public void showNewGameRecord(long timerOperationTime) {
        log.info("View is starting NEW RECORD DIALOG");
        new NewRecordDialog(minesweeperController, timerOperationTime);
    }

    @Override
    public void renderRecordTable(List<Record> records) {
        log.info("View is starting RECORD TABLE DIALOG");
        new RecordsTableDialog(records);
    }

    @Override
    public void updateCountOfCloseNoBombCells(int countOfCloseNoBombCells) {
        countOfBombsLabel.setText(BOMB_LABEL_TEXT + countOfCloseNoBombCells);
    }

    private void renderNewGameTable(boolean isInitiatingNewGame) {
        if (isInitiatingNewGame) {
            this.viewCells = new MinesweeperCell[rowNumber][columnNumber];
        }

        cellSprites = new CellSpritesHolder(CELL_WIDTH * cellSizeCoefficient, CELL_HEIGHT * cellSizeCoefficient);
        setGameGridPanel(isInitiatingNewGame);
        setGameInformationPanel();
        setSize(cellSizeCoefficient * CELL_WIDTH * columnNumber, cellSizeCoefficient * CELL_HEIGHT * rowNumber + TIMER_PANEL_HEIGHT);
        pack();
        setLocationRelativeTo(null);

        timerLabel.setText(TIME_LABEL_TEXT + NULL_FOR_LABEL);
        countOfFlagsLabel.setText(FLAGS_LABEL_TEXT + NULL_FOR_LABEL);
        countOfBombsLabel.setText(BOMB_LABEL_TEXT + NULL_FOR_LABEL);
    }

    private void fillCell(Cell cell) {
        int cellCode = cell.getCellCode();
        ImageIcon icon = new ImageIcon(cellSprites.getImage(cellCode));
        viewCells[cell.getRowIndex()][cell.getColumnIndex()].setIcon(icon);
        viewCells[cell.getRowIndex()][cell.getColumnIndex()].setImageCode(cellCode);
    }

    private void setGameGridPanel(boolean isOnlyChangingSize) {
        if (cellPanel != null) {
            remove(cellPanel);
        }
        cellPanel = new JPanel();
        cellPanel.setLayout(new GridLayout(rowNumber, columnNumber));
        cellPanel.setPreferredSize(new Dimension(cellSizeCoefficient * (columnNumber * CELL_WIDTH), cellSizeCoefficient * (rowNumber * CELL_HEIGHT)));

        for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {
                if (isOnlyChangingSize) {
                    MinesweeperCell cell = createMinesweeperCell(rowIndex, columnIndex);
                    viewCells[rowIndex][columnIndex] = cell;
                }
                viewCells[rowIndex][columnIndex].setIcon(new ImageIcon(cellSprites.getImage(viewCells[rowIndex][columnIndex].getImageCode())));
                cellPanel.add(viewCells[rowIndex][columnIndex]);
            }
        }
        add(cellPanel, BorderLayout.CENTER);
    }

    private MinesweeperCell createMinesweeperCell(int rowIndex, int columnIndex) {
        MinesweeperCell cell = new MinesweeperCell(rowIndex, columnIndex, SPACE_CODE);
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                log.info("Processing pushing mouse button with code: {}", e.getButton());
                if (e.getButton() == MouseEvent.BUTTON1) {
                    minesweeperController.handleUserClickedOnCell(cell.getRow(), cell.getColumn());
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    minesweeperController.handleUserClickedOnCellRightMouseButton(cell.getRow(), cell.getColumn());
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    minesweeperController.handleUserClickedOnCellCenterMouseButton(cell.getRow(), cell.getColumn());
                }
            }
        });
        return cell;
    }

    private void setGameInformationPanel() {
        if (informationPanel != null) {
            remove(informationPanel);
        }
        informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(1, 3));
        informationPanel.setPreferredSize(new Dimension(cellSizeCoefficient * (columnNumber * CELL_WIDTH), TIMER_PANEL_HEIGHT));
        countOfFlagsLabel.setHorizontalAlignment(JLabel.CENTER);
        informationPanel.add(countOfFlagsLabel);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        informationPanel.add(timerLabel);
        countOfBombsLabel.setHorizontalAlignment(JLabel.CENTER);
        informationPanel.add(countOfBombsLabel);
        add(informationPanel, BorderLayout.SOUTH);
    }

    private void setGameMenu(MinesweeperController minesweeperController) {
        Map<CellSize, ActionListener> sizeListenerMapping = new HashMap<>();
        for (CellSize cellSize : CellSize.values()) {
            sizeListenerMapping.put(cellSize, actionEvent -> renderTableWithNewSize(cellSize.getSize()));
        }
        add(new MinesweeperMenu(minesweeperController, sizeListenerMapping), BorderLayout.PAGE_START);
    }
}
