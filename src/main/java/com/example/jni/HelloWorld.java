package com.example.jni;

public class HelloWorld {
    static {
        System.loadLibrary("HelloWorld");
    }

    public HelloWorld() {

    }

    public native int init(int lport);

    public native void printf(String str);

    /**
     * JNI  jdk1.8以上 javac -h . HelloWorld.java 生成 .h 文件，把jdk 的jni.h,jni_md.h和这个生成的.h文件放到c环境下生成dll文件
     * 文件要注意 是32位还是64位的机子
     * 现在这HelloWorld.dll文件只能是 com.example.jni.HelloWorld类引用，这个HelloWorld换个位置都引用不到dll
     * -Djava.library.path=F:\workspace\jdk_proxy\mylibs    dll文件路径配置
     * 即在哪个路径下生成.h文件，就只能在哪个路径下的.java文件引用，且只能谁生成谁引用
     *
     * 字符乱码问题
     */
}
