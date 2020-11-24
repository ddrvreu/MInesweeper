package com.beluxha.task3.view.dialog.gamewon;

import com.beluxha.task3.controller.MinesweeperController;

import javax.swing.*;
import java.awt.*;

public class GameWonDialog extends JDialog {
    private static final String TITLE = "GAME WON";
    private static final String BUTTON_TEXT = "OK";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 120;

    public GameWonDialog(MinesweeperController minesweeperController, long time, long recordTime, String name) {
        setTitle(TITLE);
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Best time: "));
        panel.add(new JLabel(String.valueOf(getCountOfSeconds(recordTime))));
        panel.add(new JLabel("Best player name: "));
        panel.add(new JLabel(String.valueOf(name)));
        panel.add(new JLabel("Your time: "));
        panel.add(new JLabel(String.valueOf(getCountOfSeconds(time))));
        add(panel, BorderLayout.NORTH);

        JButton button = new JButton(BUTTON_TEXT);
        button.addActionListener(ActionListener -> {
            minesweeperController.handleUserStartNewGame();
            this.dispose();
        });
        add(button, BorderLayout.SOUTH);
    }

    private long getCountOfSeconds(long timerOperationTime) {
        final int MILLISECONDS_IN_SECOND = 1000;
        return timerOperationTime / MILLISECONDS_IN_SECOND;
    }
}
