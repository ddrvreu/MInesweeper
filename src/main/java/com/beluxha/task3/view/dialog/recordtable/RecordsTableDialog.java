package com.beluxha.task3.view.dialog.recordtable;

import com.beluxha.task3.model.record.entity.Record;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecordsTableDialog extends JDialog {
    private static final int WIDTH = 300;
    private static final int HEIGHT_OF_ONE_ROW = 50;

    public RecordsTableDialog(List<Record> records) {
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(new Dimension(WIDTH, HEIGHT_OF_ONE_ROW * records.size()));
        setLayout(new BorderLayout());

        setLayout(new GridLayout(records.size(), 3));
        for (Record record : records) {
            for(GameTypes gameType : GameTypes.values()){
                if(gameType.getGameCode() == record.getGameCode())
                add(new JLabel((gameType.getGameName())));
            }
            add(new JLabel(record.getPlayerName()));
            add(new JLabel(String.valueOf(getCountOfSeconds(record.getResult()))));
        }
    }

    private long getCountOfSeconds(long timerOperationTime) {
        final int MILLISECONDS_IN_SECOND = 1000;
        return timerOperationTime / MILLISECONDS_IN_SECOND;
    }
}
