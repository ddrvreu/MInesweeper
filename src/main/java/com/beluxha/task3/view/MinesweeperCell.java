package com.beluxha.task3.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@AllArgsConstructor
@Getter
public class MinesweeperCell extends JLabel {
    private final int row;
    private final int column;
    @Setter
    private int imageCode;
}
