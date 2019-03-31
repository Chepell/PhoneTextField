package sample;

import java.util.Map;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public class Test {
    public static void main(String[] args) {
        Map<String, PhonePattern> map = PhonePatternMap.getAllCountriesMap();


        PhonePattern by = map.get("Беларусь");
        PhonePattern ru = map.get("Россия");
        PhonePattern ua = map.get("Украина");


        System.out.println(ru);
//        System.out.println(by);

        System.out.println();


//        String formatPhone = PhonePatternMap.getFormatPhone("+79308217758");
//        String formatPhone1 = PhonePatternMap.getFormatPhone("+77074656856");
//        String formatPhone2 = PhonePatternMap.getFormatPhone("+497325612365");
//        System.out.println(formatPhone);
//        System.out.println(formatPhone1);
//        System.out.println(formatPhone2);

        String formatPhone = PhonePatternMap.getFormatPhone("+4156454275");
        System.out.println(formatPhone);

    }
}
