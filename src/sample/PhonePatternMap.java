package sample;

import java.util.HashMap;
import java.util.Map;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public class PhonePatternMap {

    public static Map<String, PhonePattern> getMap() {
        Map<String, PhonePattern> resultMap = new HashMap<>();

        PhonePattern ru = new PhonePattern("Россия", "Russia", "RU",
                "+7 ___ ___-__-__",
                "^(\\+(7(\\s(\\d(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?$");
        PhonePattern by = new PhonePattern("Беларусь", "Belarus", "BY",
                "+375 __ ___-__-__",
                "^(\\+(3(7(5(\\s(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?$");
        PhonePattern kz = new PhonePattern("Казахстан", "Kazakhstan", "KZ",
                "+77 __ ___-__-__",
                "^(\\+(7(7(\\s(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?$");
        PhonePattern ua = new PhonePattern("Украина", "Ukraine", "UA",
                "+380 __ ___-__-__",
                "^(\\+(3(8(0(\\s(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?$");
        PhonePattern pl = new PhonePattern("Польша", "Poland", "PL",
                "+48 __ ___-__-__",
                "^(\\+(4(8(\\s(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?$");
        PhonePattern de = new PhonePattern("Германия", "Germany", "DE",
                "+49 ___ ___-__-__",
                "^(\\+(4(9(\\s(\\d(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?$");

        resultMap.put(ru.getCountryRU(), ru);
        resultMap.put(by.getCountryRU(), by);
        resultMap.put(kz.getCountryRU(), kz);
        resultMap.put(ua.getCountryRU(), ua);
        resultMap.put(pl.getCountryRU(), pl);
        resultMap.put(de.getCountryRU(), de);

        return resultMap;
    }
}
