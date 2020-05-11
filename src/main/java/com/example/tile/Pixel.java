package com.example.tile;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/11 16:23
 */
public class Pixel {
    public int pixelX;
    public int pixelY;

    public Pixel() {

    }

    public Pixel(int pixelX, int pixelY) {
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    public int getPixelX() {
        return pixelX;
    }

    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }
}
