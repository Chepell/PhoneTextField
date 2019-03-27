package sample;

import java.util.regex.Pattern;

/**
 * Artem Voytenko
 * 27.03.2019
 */

public class Test2 {
    public static void main(String[] args) {

        System.out.println(setPhoneValidPattern(12));

    }

    public static String setPhoneValidPattern(int phoneLength) {
        return String.format("(?i)^\\\\+\\\\d{%d}$", phoneLength);
    }
}
