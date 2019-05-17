//package com.example.video;
//
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.Java2DFrameConverter;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//compile('org.bytedeco:javacv:1.4.3')
//        compile('org.bytedeco.javacpp-presets:ffmpeg-platform:4.0.2-1.4.3')
///**
// * @Description:
// * @Auther: xiankun.jiang
// * @Date: 2019/5/17 08:47
// */
//public class VideoFetchFrameUtils {
//    /**
//     * @description: 获取视频文件第5帧图片
//     * @param: videoFilePath 视频文件路径
//     * @param: videoFilePath 图片要保存的路径
//     * @return: void
//     * @auther: xiankun.jiang
//     * @date: 2019/5/17 9:04
//     */
//    public static void fetchFrame(String videoFilePath, String frameSavePath) {
//        try {
//            int flag = 0;
//            FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoFilePath);
//            fFmpegFrameGrabber.start();
//            //获取视频总帧数
//            int ftp = fFmpegFrameGrabber.getLengthInFrames();
//            while (flag <= ftp) {
//                Frame frame = fFmpegFrameGrabber.grabImage();
//                //对视频的第五帧进行处理
//                if (frame != null && flag == 5) {
//                    //文件储存对象
//                    File outPut = new File(frameSavePath);
//                    ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
//                    break;
//                }
//                flag++;
//            }
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static BufferedImage FrameToBufferedImage(Frame frame) {
//        //创建BufferedImage对象
//        Java2DFrameConverter converter = new Java2DFrameConverter();
//        BufferedImage bufferedImage = converter.getBufferedImage(frame);
//        return bufferedImage;
//    }
//
//    public static void main(String[] args) {
//        fetchFrame("e:/1.ts", "e:/123.jpg");
//    }
//}