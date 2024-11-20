package com.example.kademlia.sha256;

import org.springframework.util.DigestUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String getFileSHA(File file) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md.digest());
    }

    public static byte[] getFileSHABytes(File file) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md.update(buffer, 0, numRead);
        }
        fis.close();
        return md.digest();
    }

    public static void main(String args[]) {
        try {
            System.out.println("HashCode Generated by SHA-256 for:");

            String s1 = "GeeksForGeeks";
            System.out.println("\n" + s1 + ":" + toHexString(getSHA(s1)));

            String s2 = "hello world";
            System.out.println("\n" + s2 + ":" + toHexString(getSHA(s2)));

            File file = new File("/Users/jiangxiankun/workspace/wd.zip");
            System.out.println("\nwd.zip fileSHA:" + getFileSHA(file));
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Exception thrown for incorrect algorithm:" + e);
        }
    }
}