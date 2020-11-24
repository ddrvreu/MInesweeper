package com.beluxha.task3.model.creation.entity;

import com.beluxha.task3.api.GameCodesHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Batch {
    EASY(GameCodesHolder.EASY_GAME_CODE, 12, 12, 12),
    MIDDLE(GameCodesHolder.MIDDLE_GAME_CODE, 60, 16, 26),
    HARD(GameCodesHolder.HARD_GAME_CODE, 100, 20, 30);

    private final int code;
    private final int countOfBombs;
    private final int rowNumber;
    private final int columnNumber;
}

