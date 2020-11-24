package com.beluxha.task3.view.dialog.gamelost;

import com.beluxha.task3.controller.MinesweeperController;

import javax.swing.*;
import java.awt.*;

public class GameLostDialog extends JDialog {
    private static final int WIDTH = 150;
    private static final int HEIGHT = 80;
    private static final String TITLE = "GAME OVER";
    private static final String BUTTON_TEXT = "NEW GAME";

    public GameLostDialog(MinesweeperController minesweeperController) {
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setSize(new Dimension(WIDTH, HEIGHT));
        setTitle(TITLE);
        JButton button = new JButton(BUTTON_TEXT);
        button.addActionListener(ActionListener -> startNewGame(minesweeperController));
        add(button);
    }

    private void startNewGame(MinesweeperController minesweeperController) {
        minesweeperController.handleUserStartNewGame();
        dispose();
    }
}
