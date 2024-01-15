package com.example.morse;

import javax.sound.sampled.*;

public class Utils {
    /**
     * A B C D E F G H I J K L M N O P Q R S T U V W X Y Z [A-Z : ASCII 65 -90]
     * .- -... -.-. -.. . ..-. --. .... .. .--- -.- .-..  -- -. --- .--. --.- .-. ... - ..- ...- .-- -..- -.-- --..
     * 1 2 3 4 5 6 7 8 9 0 [0-9 : ascii 48 - 57]
     * .--- ..--- ...-- ....- ..... -.... --... ---.. ----. -----
     *
     * @return
     */
    public final String[] letterCode = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
    public final String[] numberCode = {"-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."};

    public String letterToMorseCode(char c) {
        String morseCode = "";
        if (32 == c) {
            morseCode = "#";
        } else if (65 <= c) {
            morseCode = letterCode[c - 65];
        } else {
            morseCode = numberCode[c - 48];
        }
        return morseCode;
    }

    public String stringToMorseCode(String msg) {
        StringBuffer code = new StringBuffer();
        for (char c : msg.toCharArray()) {
            String code1 = letterToMorseCode(c);
            code.append(code1);
            code.append("/");
        }
        return code.toString();
    }

    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        String msg = "CQCQ DE HZ9BYU 73 73 73 73";
        Utils utils = new Utils();
        String s = utils.stringToMorseCode(msg.toUpperCase());
        int t = 45;
        int hz = 800;
        for (char c : s.toCharArray()) {
            if ('-' == c) {
                //输出300ms、停顿 100ms
                Sound.tone(hz, 3 * t);
                Thread.sleep(t);
            } else if ('.' == c) {
                //输出100ms、停顿 100ms
                Sound.tone(hz, t);
                Thread.sleep(t);
            } else if ('/' == c) {
                //停顿 200ms
                Thread.sleep(3 * t);
            } else if ('#' == c) {
                //停顿 500ms
                Thread.sleep(7 * t);
            }
        }
        System.out.println(s);
    }
}
