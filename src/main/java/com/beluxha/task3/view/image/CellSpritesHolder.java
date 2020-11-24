package com.beluxha.task3.view.image;

import com.beluxha.task3.api.GameCodesHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CellSpritesHolder implements ImageHolder {
    private final Map<Integer, Image> imagesMap;

    public CellSpritesHolder(int imageWidth, int imageHeight) {
        String pathToCellSprites = "/cell sprites/";
        this.imagesMap = new HashMap<>();
        this.imagesMap.put(GameCodesHolder.NULL_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "null.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.ONE_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "one.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.TWO_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "two.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.THREE_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "three.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.FORE_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "fore.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.FIVE_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "five.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.SIX_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "six.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.SEVEN_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "seven.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.EIGHT_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "eight.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.BOMB_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "bomb.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.FLAG_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "flag.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.SPACE_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "space.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.CHOSEN_BOMB_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "the bomb.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
        this.imagesMap.put(GameCodesHolder.WRONG_FLAG_CODE,
                new ImageIcon(getClass().getResource(pathToCellSprites + "wrong flag.png")).getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT));
    }

    @Override
    public Image getImage(int imageKey) {
        return imagesMap.get(imageKey);
    }
}
