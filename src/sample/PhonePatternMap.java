package sample;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public final class PhonePatternMap {
    private static Map<String, PhonePattern> resultMap;

    private PhonePatternMap() {}

    public static Map<String, PhonePattern> getAllCountriesMap() {
        // Отложенная инициализация мэпа в статичном фабричном методе и кэширование
        if (resultMap == null) {
            resultMap = new LinkedHashMap<>();

            PhonePattern ru = new PhonePattern("Россия", "Russia", "RU",
                    "+7  ___ ___-__-__",
                    "^(\\+(7(\\s{0,2}(\\d{0,3}\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?$");
            PhonePattern by = new PhonePattern("Беларусь", "Belarus", "BY",
                    "+375  __ ___-__-__",
                    "^(\\+(3(7(5(\\s{0,2}(\\d{0,2}\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?)?)?$");
            PhonePattern kz = new PhonePattern("Казахстан", "Kazakhstan", "KZ",
                    "+77  __ ___-__-__",
                    "^(\\+(7{0,2}(\\s{0,2}(\\d{0,2}\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?$");
            PhonePattern ua = new PhonePattern("Украина", "Ukraine", "UA",
                    "+380  __ ___-__-__",
                    "^(\\+(3(8(0(\\s{0,2}(\\d{0,2}\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?)?)?$");
            PhonePattern pl = new PhonePattern("Польша", "Poland", "PL",
                    "+48  __ ___-__-__",
                    "^(\\+(4(8(\\s{0,2}(\\d{0,2}\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?)?$");
            PhonePattern de = new PhonePattern("Германия", "Germany", "DE",
                    "+49  ___ ___-__-__",
                    "^(\\+(4(9(\\s{0,2}(\\d{0,3}\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?)?$");

            resultMap.put(ru.getCountryRU(), ru);
            resultMap.put(by.getCountryRU(), by);
            resultMap.put(kz.getCountryRU(), kz);
            resultMap.put(ua.getCountryRU(), ua);
            resultMap.put(pl.getCountryRU(), pl);
            resultMap.put(de.getCountryRU(), de);
        }

        // возврат неизменяемой копии мэпа
        return Collections.unmodifiableMap(resultMap);
    }


    /**
     * метод пытается отформатировать номер пройдя по всему списку значений мэпа
     *
     * @param simplePhoneNumber принимает номер начинающийся с + и состоящий только из цифр без каких-либо разделителей
     * @return возвращает первый не null результат
     */
    public static String getFormatPhone(String simplePhoneNumber) {
        for (PhonePattern pattern : getAllCountriesMap().values()) {
            String formatPhoneNumber = pattern.getFormatPhoneNumber(simplePhoneNumber);
            if (formatPhoneNumber != null) {
                return formatPhoneNumber;
            }
        }
        return simplePhoneNumber;
    }
}
