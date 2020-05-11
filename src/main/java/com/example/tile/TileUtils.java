package com.example.tile;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/5/11 16:13
 */
public class TileUtils {
    public double _Math_sinh(double x) {
        return (Math.exp(x) - Math.exp(-x)) / 2;
    }

    /*
     * 某一瓦片等级下瓦片地图X轴(Y轴)上的瓦片数目
     */
    public double _getMapSize(double level) {
        return Math.pow(2, level);
    }

    /*
     * 分辨率，表示水平方向上一个像素点代表的真实距离(m)
     */
    public double getResolution(double latitude, int level) {
        double resolution = 6378137.0 * 2 * Math.PI * Math.cos(latitude) / 256 / this._getMapSize(level);
        return resolution;
    }

    private int _lngToTileX(double longitude, int level) {
        double x = (longitude + 180) / 360;
        int tileX = (int) Math.floor(x * this._getMapSize(level));
        return tileX;
    }

    private int _latToTileY(double latitude, double level) {
        double lat_rad = latitude * Math.PI / 180;
        double y = (1 - Math.log(Math.tan(lat_rad) + 1 / Math.cos(lat_rad)) / Math.PI) / 2;
        int tileY = (int) Math.floor(y * this._getMapSize(level));
        // 代替性算法,使用了一些三角变化，其实完全等价
        //let sinLatitude = Math.sin(latitude * Math.PI / 180);
        //let y = 0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI);
        //let tileY = Math.floor(y * this._getMapSize(level));
        return tileY;
    }

    private int _lngToPixelX(double longitude, int level) {
        double x = (longitude + 180) / 360;
        int pixelX = (int) Math.floor(x * this._getMapSize(level) * 256 % 256);
        return pixelX;
    }

    private int _latToPixelY(double latitude, int level) {
        double sinLatitude = Math.sin(latitude * Math.PI / 180);
        double y = 0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI);
        int pixelY = (int) Math.floor(y * this._getMapSize(level) * 256 % 256);
        return pixelY;
    }

    private double _pixelXTolng(int pixelX, int tileX, int level) {
        double pixelXToTileAddition = pixelX / 256.0;
        double lngitude = (tileX + pixelXToTileAddition) / this._getMapSize(level) * 360 - 180;
        return lngitude;
    }

    private double _pixelYToLat(int pixelY, int tileY, int level) {
        double pixelYToTileAddition = pixelY / 256.0;
        double latitude = Math.atan(_Math_sinh(Math.PI * (1 - 2 * (tileY + pixelYToTileAddition) / this._getMapSize(level)))) * 180.0 / Math.PI;
        return latitude;
    }

    /**
     * @description: 从某一瓦片的某一像素点到经纬度
     * @param:
     * @return: com.example.tile.Coordinate
     * @author: xiankun.jiang
     * @date: 2020/5/11 17:07
     */
    public Coordinate pixelToLnglat(int pixelX, int pixelY, int tileX, int tileY, int level) {

        double lng = this._pixelXTolng(pixelX, tileX, level);
        double lat = this._pixelYToLat(pixelY, tileY, level);
        return new Coordinate(lng, lat);
    }

    /**
     * @description: 从经纬度获取点在某一级别瓦片中的像素坐标
     * @param:
     * @return: com.example.tile.Pixel
     * @author: xiankun.jiang
     * @date: 2020/5/11 17:08
     */
    public Pixel lnglatToPixel(double longitude, double latitude, int level) {
        int pixelX = this._lngToPixelX(longitude, level);
        int pixelY = this._latToPixelY(latitude, level);
        return new Pixel(pixelX, pixelY);
    }

    /**
     * @description: 从经纬度获取某一级别瓦片坐标编号
     * @param:
     * @return: com.example.tile.Tile
     * @author: xiankun.jiang
     * @date: 2020/5/11 17:08
     */
    public Tile lnglatToTile(double longitude, double latitude, int level) {
        int tileX = this._lngToTileX(longitude, level);
        int tileY = this._latToTileY(latitude, level);
        return new Tile(tileX, tileY);
    }
}
