package sample;

import java.util.HashMap;
import java.util.Map;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public class CountryMap {

    public static Map<String, CountryPhone> getMap() {
        Map<String, CountryPhone> resultMap = new HashMap<>();

        CountryPhone ru = new CountryPhone("Россия", "Russia", "RU", "+7 ___ ___-__-__");
        CountryPhone by = new CountryPhone("Беларусь", "Belarus", "BY", "+375 __ ___-__-__");
        CountryPhone kz = new CountryPhone("Казахстан", "Kazakhstan", "KZ", "+77 __ ___-__-__");
        CountryPhone ua = new CountryPhone("Украина", "Ukraine", "UA", "+380 __ ___-__-__");
        CountryPhone pl = new CountryPhone("Польша", "Poland", "PL", "+48 __ ___-__-__");
        CountryPhone de = new CountryPhone("Германия", "Germany", "DE", "+49 ___ ___-__-__");

        resultMap.put(ru.getCountryRU(), ru);
        resultMap.put(by.getCountryRU(), by);
        resultMap.put(kz.getCountryRU(), kz);
        resultMap.put(ua.getCountryRU(), ua);
        resultMap.put(pl.getCountryRU(), pl);
        resultMap.put(de.getCountryRU(), de);

        return resultMap;
    }
}
