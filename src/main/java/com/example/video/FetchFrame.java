package com.example.video;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/5/15 16:26
 */
public class FetchFrame {
    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videofile 源视频文件路径
     * @param framefile 截取帧的图片存放路径
     * @throws Exception
     */

    public static void fetchFrame(String videofile, String framefile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(framefile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //	ff.flush();
        ff.stop();
        System.out.println(System.currentTimeMillis() - start);

    }

    public static void main(String[] args) {
        try {
            FetchFrame.fetchFrame("E:/1.ts", "e:/test1.jpg");
            FetchFrame.fetchFrame("https://jsjoke.net/upload-1529285839603-touristappid.o6zAJswEDExvrLeuQTdnRSIz87Vo.f0fd65c07d78b8fc831c97888e4d7a55.mp4", "e:/test2.jpg");
            System.out.println("跑完了");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("异常了");
        }
    }
}
