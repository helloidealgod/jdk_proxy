package com.example.morse;

public class Test {
    public final String[] letterCode = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
    public final String[] numberCode = {"-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."};

    public static final byte[] morse_table = {
            (byte) 0x48,
            (byte) 0x90,
            (byte) 0x94,
            (byte) 0x70,
            (byte) 0x20,
            (byte) 0x84,
            (byte) 0x78,
            (byte) 0x80,
            (byte) 0x40,
            (byte) 0x8E,
            (byte) 0x74,
            (byte) 0x88,
            (byte) 0x58,
            (byte) 0x50,
            (byte) 0x7C,
            (byte) 0x8C,
            (byte) 0x9A,
            (byte) 0x68,
            (byte) 0x60,
            (byte) 0x30,
            (byte) 0x64,
            (byte) 0x82,
            (byte) 0x6C,
            (byte) 0x92,
            (byte) 0x96,
            (byte) 0x98,
            (byte) 0xBF,
            (byte) 0xAF,
            (byte) 0xA7,
            (byte) 0xA3,
            (byte) 0xA1,
            (byte) 0xA0,
            (byte) 0xB0,
            (byte) 0xB8,
            (byte) 0xBC,
            (byte) 0xBE
    };

    public static final byte[] short_morse_table = {
            (byte) 0x30,
            (byte) 0x48,
            (byte) 0x64,
            (byte) 0x82,
            (byte) 0xA1,
            (byte) 0x20,
            (byte) 0xB0,
            (byte) 0x90,
            (byte) 0x70,
            (byte) 0x50
    };

    public static void main(String[] args) {
        for (int i = 0; i < morse_table.length; i++) {
            byte b = morse_table[i];
            int i1 = b & 0xFF;
            String bin = String.format("%8s", Integer.toBinaryString(i1)).replace(' ', '0');
            System.out.print(bin);

            int len = (b & 0xFF) >> 5;
            System.out.print(" len=" + len);
            System.out.print(" " + String.format("%3s", Integer.toBinaryString(len)).replace(' ', '0'));

            int code = (b & 0x1F) << 3;
            System.out.print(" " + String.format("%5s", Integer.toBinaryString(code)).replace(' ', '0'));
            System.out.print(" ");

            for (; len > 0; len--) {
                // code & 10000000
                if ((code & 0x80) > 0) {
                    System.out.print("— ");
                } else {
                    System.out.print(". ");
                }
                code = code << 1;
            }
            System.out.println("");
        }
        System.out.println("===============short_morse_table====================");
        for (int i = 0; i < short_morse_table.length; i++) {
            byte b = short_morse_table[i];
            System.out.print(i);
            System.out.print(" ");

            int len = (b & 0xFF) >> 5;
            int code = (b & 0x1F) << 3;

            for (; len > 0; len--) {
                // code & 10000000
                if ((code & 0x80) > 0) {
                    System.out.print("— ");
                } else {
                    System.out.print(". ");
                }
                code = code << 1;
            }
            System.out.println("");
        }
    }
}
