package sample;

import java.util.Map;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public class Test {
    public static void main(String[] args) {
        Map<String, PhonePattern> map = PhonePatternMap.getMap();


        PhonePattern by = map.get("Беларусь");
        PhonePattern ru = map.get("Россия");


        System.out.println(ru);
        System.out.println(by);

        String str = "+7 9307";
        int length = str.length();
        str = str.substring(0, length - 1) + " " + str.substring(length - 1);
        System.out.println(str);

    }
}
