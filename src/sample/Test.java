package sample;

import java.util.Map;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public class Test {
    public static void main(String[] args) {
        Map<String, CountryPhone> map = CountryMap.getMap();


        CountryPhone ru = map.get("Украина");

        int fullPhoneLength = ru.getFullPhoneLength();

        System.out.println(ru);

    }
}
