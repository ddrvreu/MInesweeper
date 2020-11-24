package com.beluxha.task3.view.dialog.customgame;

import com.beluxha.task3.controller.MinesweeperController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomGameDialog extends JDialog {
    private static final String BUTTON_TEXT = "START NEW CUSTOM GAME";
    private static final String TITLE_TEXT = "NEW CUSTOM GAME";
    private static final String ROWS_LABEL_TEXT = "COUNT OF ROWS:";
    private static final String COWS_LABEL_TEXT = "COUNT OF COLUMNS:";
    private static final String BOMBS_LABEL_TEXT = "COUNT OF BOMBS:";
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 140;
    private static final int MIN_COUNT_OF_ROWS = 1;
    private static final int MAX_COUNT_OF_ROWS = 40;
    private static final int MIN_COUNT_OF_COLUMNS = 1;
    private static final int MAX_COUNT_OF_COLUMNS = 60;
    private static final int MIN_COUNT_OF_BOMBS = 0;
    private final HintTextField countOfRowsTextField = new HintTextField("Number from " + MIN_COUNT_OF_ROWS + " to " + MAX_COUNT_OF_ROWS, false);
    private final HintTextField countOfColumnsTextField = new HintTextField("Number from " + MIN_COUNT_OF_COLUMNS + " to " + MAX_COUNT_OF_COLUMNS, false);
    private final HintTextField countOfBombsTextField = new HintTextField("", false);

    public CustomGameDialog(MinesweeperController minesweeperController) {
        setTitle(TITLE_TEXT);
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);
        setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        setLayout(new BorderLayout());

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(3, 2, 5, 5));
        editPanel.add(new JLabel(ROWS_LABEL_TEXT));
        editPanel.add(countOfRowsTextField);
        editPanel.add(new JLabel(COWS_LABEL_TEXT));
        editPanel.add(countOfColumnsTextField);
        editPanel.add(new JLabel(BOMBS_LABEL_TEXT));
        editPanel.add(countOfBombsTextField);
        add(editPanel, BorderLayout.NORTH);

        JButton button = new JButton(BUTTON_TEXT);
        add(button, BorderLayout.SOUTH);
        button.addActionListener(createActionListenerForButton(minesweeperController));
    }

    private ActionListener createActionListenerForButton(MinesweeperController minesweeperController) {
        return actionEvent -> {
            int countOfRows;
            int countOfColumns;
            int countOfBombs;
            try {
                countOfRows = receiveTextFrom(countOfRowsTextField, MIN_COUNT_OF_ROWS, MAX_COUNT_OF_ROWS);
            } catch (NumberFormatException e) {
                countOfRowsTextField.switchOnHint();
                countOfBombsTextField.switchOnHint("");
                return;
            }
            try {
                countOfColumns = receiveTextFrom(countOfColumnsTextField, MIN_COUNT_OF_COLUMNS, MAX_COUNT_OF_COLUMNS);
            } catch (NumberFormatException e) {
                countOfColumnsTextField.switchOnHint();
                countOfBombsTextField.switchOnHint("");
                return;
            }
            try {
                countOfBombs = receiveTextFrom(countOfBombsTextField, MIN_COUNT_OF_COLUMNS, calculatePermissibleCountOfBombs(countOfRows, countOfColumns));
            } catch (NumberFormatException e) {
                countOfBombsTextField.switchOnHint("Number from " + MIN_COUNT_OF_BOMBS + " to " + calculatePermissibleCountOfBombs(countOfRows, countOfColumns));
                return;
            }
            minesweeperController.handleUserStartNewGame(countOfRows, countOfColumns, countOfBombs);
            dispose();
        };
    }

    private int receiveTextFrom(HintTextField hintTextField, int intervalStart, int intervalEnd) throws NumberFormatException {
        int countOFRows = Integer.parseInt(hintTextField.getText());
        if (intervalStart > countOFRows || intervalEnd < countOFRows) {
            throw new NumberFormatException();
        }
        return countOFRows;
    }

    private int calculatePermissibleCountOfBombs(int countOFRows, int countOFColumns) {
        return countOFRows * countOFColumns - 1;
    }
}
