package com.example.tile;


/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/11 16:13
 */

public class Tile {
    public int tileX;
    public int tileY;

    public Tile() {
    }

    public Tile(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }
}
