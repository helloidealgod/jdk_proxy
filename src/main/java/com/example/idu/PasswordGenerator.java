package com.example.idu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description:
 * @Auther: xiankun.jiang
 * @Date: 2019/7/30 13:51
 */
public class PasswordGenerator {
    private int layer = -1;
    private int passwordLength = 6;
    private char[] password;
    private char[] charSet;
    private PasswordCallbackImpl passwordCallback;
    private final String NUMBER_STR = "0123456789";
    private final String LETTER_STR = "qwertyuiopasdfghjklzxcvbbnm";
    private final String SYMBOL_STR = "!@#$%^&*()_+-=[]{}`~|;':,./<>?";
    private FileOutputStream fos;

    public PasswordGenerator(char[] charSet, PasswordCallbackImpl passwordCallback) {
        this.charSet = charSet;
        this.passwordCallback = passwordCallback;
    }

    public PasswordGenerator(char[] charSet) {
        this.charSet = charSet;
    }

    public static void main(String[] argv) throws IOException {
        String s = "0123456789qwertyuiopasdfghjklzxcvbbnm!@#$%^&*()_+-=[]{}`~|;':,./<>?";
        PasswordGenerator passwordGenerator = new PasswordGenerator(s.toCharArray());
        passwordGenerator.generator(16, "E:/password.txt");
        System.out.println("");
    }

    public void generator(int passwordLength) throws IOException {
        this.passwordLength = passwordLength;
        this.password = new char[passwordLength];
        iterate(this.charSet);
    }

    public void randGenerator(int passwordLength) {
        this.password = new char[passwordLength];
        for (int i = 0; i < passwordLength; i++) {

        }

    }

    public void generator(int passwordLength, String filePath) throws IOException {
        this.passwordLength = passwordLength;
        this.password = new char[passwordLength];
        File file = new File(filePath);
        this.fos = new FileOutputStream(file);
        iterate(this.charSet);
        this.fos.flush();
        this.fos.close();
    }

    public void iterate(char[] data) throws IOException {
        layer++;
        if (layer < this.passwordLength) {
            for (int i = 0; i < data.length; i++) {
                password[layer] = data[i];
                iterate(data);
            }
        } else {
            System.out.println(password);
            if (null != fos) {
                for (char c : password) {
                    fos.write((byte) c);
                }
                fos.write("\r\n".getBytes());
            }

        }
        layer--;
    }

}
