package com.example.train;

import java.util.BitSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        //全国停靠站点最多的高铁车次：目前公开信息中，‌G335次‌（16站）是北京到深圳方向停靠站点最多的车次‌
        //高铁单次载客量：以G81次为例，采用16节编组的复兴号CR400AF-A型列车，总载客量约1600人
        BitSet[] bitSets = new BitSet[1600];
        for (int i = 0; i < bitSets.length; i++) {
            bitSets[i] = new BitSet(16);
            bitSets[i].set(0, 15);//区间置1
        }
        //找第3站到第5站的空余位置
        Integer start = 3, end = 5;
        Integer count = 2000;
        while (count-- > 0) {
            start = 3;
            end = 5;
            for (int i = 0; i < bitSets.length; i++) {
                //查找符合条件的
                int nexted = bitSets[i].nextClearBit(start);
                if (nexted < end) {
                    //区间内无0，则该区间内无符合条件
                   // System.out.println("用户" + count + " " + (i + 1) + "位置：" + "占座失败，重新找空余位置！");
                } else {
                    //对应区间置0并结束，否则继续下一个
                    bitSets[i].clear(start, end);
                    System.out.println("用户" + count + " " + (i + 1) + "位置：" + "占座成功，结束！");
                    break;
                }
            }
        }
        System.out.println("");
    }
}
