package com.beluxha.task3;

import com.beluxha.task3.api.GameCodesHolder;
import com.beluxha.task3.controller.MinesweeperController;
import com.beluxha.task3.model.MinesweeperGameImpl;
import com.beluxha.task3.view.MinesweeperView;
import com.beluxha.task3.view.SwingMinesweeperView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Minesweeper {

    public static void main(String[] args) {
        log.info("Creating model");
        MinesweeperGameImpl minesweeperGame = new MinesweeperGameImpl();
        log.info("Creating controller");
        MinesweeperController minesweeperController = new MinesweeperController(minesweeperGame);
        log.info("Creating view");
        MinesweeperView minesweeperView = new SwingMinesweeperView(minesweeperController);

        log.info("Attaching views to model");
        minesweeperGame.attachView(minesweeperView);
        log.info("Initiating new game by model");
        minesweeperGame.startGame(GameCodesHolder.EASY_GAME_CODE);
    }
}
