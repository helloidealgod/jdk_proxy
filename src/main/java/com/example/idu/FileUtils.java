package com.example.idu;

import sun.misc.BASE64Encoder;

import java.io.InputStream;
import java.net.URLEncoder;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/8/9 08:50
 */
public class FileUtils {
    public void getFileFromJar() {
        //resource下templateFile文件夹
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templateFile/雪亮平台摄像头信息导入模板.xls");
//        ServletOutputStream outputStream = response.getOutputStream();
//        //获得请求头中的User-Agent
//        String agent = request.getHeader("User-Agent");
//        String filename = "雪亮平台摄像头信息导入模板.xls";
//        //根据不同浏览器进行不同的编码
//        String filenameEncoder = "";
//        if (agent.contains("MSIE")) {
//            // IE浏览器
//            filenameEncoder = URLEncoder.encode(filename, "utf-8");
//            filenameEncoder = filenameEncoder.replace("+", " ");
//        } else if (agent.contains("Firefox")) {
//            // 火狐浏览器
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            filenameEncoder = "=?utf-8?B?"
//                    + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
//        } else {
//            // 其它浏览器
//            filenameEncoder = URLEncoder.encode(filename, "utf-8");
//        }
//
//        //告诉客户端该文件不是直接解析 而是以附件形式打开(下载)----filename="+filename 客户端默认对名字进行解码
//        response.setHeader("Content-Disposition", "attachment;filename=" + filenameEncoder);
//        response.setContentType("application/octet-stream;charset=UTF-8");
//
//        byte[] bytes = new byte[1024];
//        int length = 0;
//        while (-1 != (length = inputStream.read(bytes))) {
//            outputStream.write(bytes, 0, length);
//        }
//        outputStream.flush();
    }
}
