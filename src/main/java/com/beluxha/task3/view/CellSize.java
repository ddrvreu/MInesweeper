package com.beluxha.task3.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CellSize {
    LITTLE(2, "LITTLE"),
    MIDDLE(3, "MIDDLE"),
    BIG(4, "BIG");

    private final int size;
    private final String sizeName;
}
