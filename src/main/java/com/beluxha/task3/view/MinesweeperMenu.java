package com.beluxha.task3.view;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.controller.MinesweeperController;
import com.beluxha.task3.view.dialog.customgame.CustomGameDialog;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class MinesweeperMenu extends JMenuBar {
    private static final String NEW_GAME_MENU_TITLE = "NEW GAME";
    private static final String SIZE_MENU_TITLE = "SIZE";
    private static final String RECORDS_MENU_TITLE = "RECORDS";

    private static final String EASY_GAME_ITEM = "EASY";
    private static final String MIDDLE_GAME_ITEM = "MIDDLE";
    private static final String HURD_GAME_ITEM = "HURD GAME";
    private static final String CUSTOM_GAME_ITEM = "CUSTOM GAME";

    private final MinesweeperController minesweeperController;

    public MinesweeperMenu(MinesweeperController minesweeperController, Map<CellSize, ActionListener> sizeListenerMapping) {
        this.minesweeperController = minesweeperController;

        add(createNewGameMenu());
        add(createRecordsMenu());
        add(createSizeMenu(sizeListenerMapping));
    }

    private JMenu createRecordsMenu() {
        JMenu recordsMenu = new JMenu(RECORDS_MENU_TITLE);
        JMenuItem menuItem = new JMenuItem(RECORDS_MENU_TITLE);
        menuItem.addActionListener(ActionListener -> minesweeperController.handleHighScoreTableRequest());
        recordsMenu.add(menuItem);
        return recordsMenu;
    }

    private JMenu createSizeMenu(Map<CellSize, ActionListener> sizeListenerMapping) {
        JMenu settingsSizeMenu = new JMenu(SIZE_MENU_TITLE);
        for (CellSize cellSize : CellSize.values()) {
            JMenuItem menuItem = new JMenuItem(cellSize.getSizeName());
            menuItem.addActionListener(sizeListenerMapping.get(cellSize));
            settingsSizeMenu.add(menuItem);
        }
        return settingsSizeMenu;
    }

    private JMenu createNewGameMenu() {
        JMenu newGameMenu = new JMenu(NEW_GAME_MENU_TITLE);

        JMenuItem easyGame = new JMenuItem(EASY_GAME_ITEM);
        easyGame.addActionListener(ActionListener -> minesweeperController.handleUserStartNewGame(GameCodesHolder.EASY_GAME_CODE));
        JMenuItem mediumGame = new JMenuItem(MIDDLE_GAME_ITEM);
        mediumGame.addActionListener(ActionListener -> minesweeperController.handleUserStartNewGame(GameCodesHolder.MIDDLE_GAME_CODE));
        JMenuItem hardGame = new JMenuItem(HURD_GAME_ITEM);
        hardGame.addActionListener(ActionListener -> minesweeperController.handleUserStartNewGame(GameCodesHolder.HARD_GAME_CODE));
        JMenuItem customGame = new JMenuItem(CUSTOM_GAME_ITEM);
        customGame.addActionListener(ActionListener -> new CustomGameDialog(minesweeperController));

        newGameMenu.add(easyGame);
        newGameMenu.add(mediumGame);
        newGameMenu.add(hardGame);
        newGameMenu.add(customGame);

        return newGameMenu;
    }
}
