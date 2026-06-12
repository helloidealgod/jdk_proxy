package com.example.morse;

public class Test {
    public final String[] letterCode = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
    public final String[] numberCode = {"-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."};

    public static final byte[] morse_table = {
            (byte) 0x48, // A
            (byte) 0x90, // B
            (byte) 0x94, // C
            (byte) 0x70, // D
            (byte) 0x20, // E
            (byte) 0x84, // F
            (byte) 0x78, // G
            (byte) 0x80, // H
            (byte) 0x40, // I
            (byte) 0x8E, // J
            (byte) 0x74, // K
            (byte) 0x88, // L
            (byte) 0x58, // M
            (byte) 0x50, // N
            (byte) 0x7C, // O
            (byte) 0x8C, // P
            (byte) 0x9A, // Q
            (byte) 0x68, // R
            (byte) 0x60, // S
            (byte) 0x30, // T
            (byte) 0x64, // U
            (byte) 0x82, // V
            (byte) 0x6C, // W
            (byte) 0x92, // X
            (byte) 0x96, // Y
            (byte) 0x98, // Z
            (byte) 0xBF, // 0
            (byte) 0xAF, // 1
            (byte) 0xA7, // 2
            (byte) 0xA3, // 3
            (byte) 0xA1, // 4
            (byte) 0xA0, // 5
            (byte) 0xB0, // 6
            (byte) 0xB8, // 7
            (byte) 0xBC, // 8
            (byte) 0xBE  // 9
    };

    public static final byte[] short_morse_table = {
            (byte) 0x30, // 0
            (byte) 0x48, // 1
            (byte) 0x64, // 2
            (byte) 0x82, // 3
            (byte) 0xA1, // 4
            (byte) 0x20, // 5
            (byte) 0xB0, // 6
            (byte) 0x90, // 7
            (byte) 0x70, // 8
            (byte) 0x50  // 9
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
            System.out.print(" " + String.format("%8s", Integer.toBinaryString(code)).replace(' ', '0'));
            System.out.print(" ");

            System.out.print(" " + String.format("%5s", Integer.toBinaryString((b & 0x1F))).replace(' ', '0'));
            System.out.print(" ");

            for (; len > 0; len--) {
                // code & 10000000
                if ((code & 0x80) > 0) {
                    System.out.print("_ ");
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
                    System.out.print("_ ");
                } else {
                    System.out.print(". ");
                }
                code = code << 1;
            }
            System.out.println("");
        }
    }
}
