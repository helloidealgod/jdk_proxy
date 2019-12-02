package com.example.jni;

public class TestJni {
    public static void main(String[] argv){
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.printf("123456");
        System.out.println(helloWorld.init(1));

        HelloWorld1 helloWorld1 = new HelloWorld1();
        helloWorld1.printf("123456");
        System.out.println(helloWorld1.init(1));
    }
}
