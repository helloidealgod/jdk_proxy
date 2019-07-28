package com.example.idu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/3/22 08:57
 */
public class ImageUtils {
//    public static void main(String[] args) throws Exception {
//        //读取图片到BufferImage
////        BufferedImage bf = readImage("D:\\easy-shopping\\WebContent\\upload\\image\\ad_login.jpg");//读取绝对路径+文件名
////        //Image转化二维数组
////        int[][] rgbArray1 = convertImageToArray(bf);
//        FileUtils fileUtils = new FileUtils();
//        byte[][][] bytes = fileUtils.read_idx3_ubyte("E:\\t10k-images.idx3-ubyte");
//        int[][] image = null;
//        for (int i = 0; i < bytes.length; i++) {
//            image = fileUtils.byte2int(bytes[i]);
//            //输出图片到指定文件
//            writeImageFromArray("E:\\images\\"+i+".bmp", "bmp", image);//输出绝对路径+文件名
//        }
//        System.out.println("图片输出完毕！");
//    }

    private static BufferedImage readImage(String imageFile) {
        File file = new File(imageFile);
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bf;
    }

    public static int[][] convertImageToArray(BufferedImage bf) {
        // 获取图片的宽度和高度
        int width = bf.getWidth();
        int height = bf.getHeight();
        //将图片sRGB数据写入一维数组中
        int[] data = new int[width * height];
        bf.getRGB(0, 0, width, height, data, 0, width);
        //将一维数组转换为二维数组
        int[][] rgbArray = new int[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                rgbArray[i][j] = data[i * width + j];
        return rgbArray;
    }

    public static void writeImageFromArray(String imageFile, String type, int[][] rgbArray) {
        //获取数组宽度和高度
        int width = rgbArray[0].length;
        int height = rgbArray.length;
        //将二维数组转换一维数组
        int[] data = new int[width * height];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < height; j++)
                data[i * width + j] = rgbArray[i][j];
        //将数据写入BufferImage
        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        bf.setRGB(0, 0, width, height, data, 0, width);
        //输出图片
        try {
            File file = new File(imageFile);
            ImageIO.write((BufferedImage) bf, type, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
