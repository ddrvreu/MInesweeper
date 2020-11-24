package com.beluxha.task3.view.dialog.newrecord;

import com.beluxha.task3.controller.MinesweeperController;

import javax.swing.*;
import java.awt.*;

public class NewRecordDialog extends JDialog {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 140;

    public NewRecordDialog(MinesweeperController minesweeperController, long timerOperationTime) {
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(new Dimension(WIDTH, HEIGHT));

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Your time:"));
        panel.add(new JLabel(String.valueOf(getCountOfSeconds(timerOperationTime))));
        panel.add(new JLabel("Your name:"));
        JTextField textField = new JTextField();
        panel.add(textField);
        add(panel, BorderLayout.NORTH);

        JButton button = new JButton("OK");
        button.addActionListener(ActionListener -> {
            minesweeperController.handleRecordNameInput(textField.getText());
            dispose();

        });
        add(button, BorderLayout.SOUTH);
    }

    private long getCountOfSeconds(long timerOperationTime) {
        final int MILLISECONDS_IN_SECOND = 1000;
        return timerOperationTime / MILLISECONDS_IN_SECOND;
    }
}
