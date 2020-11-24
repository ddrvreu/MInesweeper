package com.beluxha.task3.view.dialog.recordtable;

import com.beluxha.task3.api.GameCodesHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameTypes {
    EASY(GameCodesHolder.EASY_GAME_CODE, "EASY GAME"),
    MIDDLE(GameCodesHolder.MIDDLE_GAME_CODE, "MIDDLE GAME"),
    HARD(GameCodesHolder.HARD_GAME_CODE, "HARD GAME");

    private final int gameCode;
    private final String gameName;
}
