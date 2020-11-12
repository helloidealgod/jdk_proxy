package com.example.nio;

import java.nio.ByteBuffer;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/12 19:58
 */
public class Utils {

    public static ByteBuffer byte2Byffer(byte[] byteArray) {
        //初始化一个和byte长度一样的buffer
        ByteBuffer buffer = ByteBuffer.allocate(byteArray.length);
        // 数组放到buffer中
        buffer.put(byteArray);
        //重置 limit 和postion 值 否则 buffer 读取数据不对
        buffer.flip();
        return buffer;
    }

    public static byte[] bytebuffer2ByteArray(ByteBuffer buffer) {
        //重置 limit 和postion 值
        buffer.flip();
        //获取buffer中有效大小
        int len = buffer.limit() - buffer.position();
        byte[] bytes = new byte[len];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get();
        }
        return bytes;
    }
}
