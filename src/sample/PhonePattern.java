package sample;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public final class PhonePattern {
    private final String countryRU;
    private final String countryEN;
    private final String isoCode;
    private final String countryCode;
    private final int phoneLength;
    private final int fullPhoneLength;
    private final String phoneMask;
    private final int firstSpace;
    private final int secondSpace;
    private final int firstDash;
    private final int secondDash;
    private final String regex;

    public PhonePattern(String countryRU, String countryEN, String isoCode, String phoneMask, String regex) {

        this.countryRU = countryRU;
        this.countryEN = countryEN;
        this.isoCode = isoCode;
        this.fullPhoneLength = phoneMask.length();
        this.phoneLength = fullPhoneLength - 6; // минус количество всех знаков в отформатированной маске
        this.phoneMask = phoneMask;

        this.firstSpace = phoneMask.indexOf(" ") + 2;
        this.secondSpace = phoneMask.indexOf(" ", firstSpace + 1);

        this.countryCode = phoneMask.substring(0, firstSpace);

        this.firstDash = phoneMask.indexOf("-");
        this.secondDash = phoneMask.indexOf("-", firstDash + 1);

        this.regex = regex;
    }

    public String getCountryRU() {
        return countryRU;
    }

    public String getCountryEN() {
        return countryEN;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getPhoneLength() {
        return phoneLength;
    }

    public int getFullPhoneLength() {
        return fullPhoneLength;
    }

    public String getPhoneMask() {
        return phoneMask;
    }

    public int getFirstSpace() {
        return firstSpace;
    }

    public int getSecondSpace() {
        return secondSpace;
    }

    public int getFirstDash() {
        return firstDash;
    }

    public int getSecondDash() {
        return secondDash;
    }

    public String getRegex() {
        return regex;
    }

    /**
     * форматирует полученный номер телефона
     *
     * @param simplePhoneNumber принимает номер начинающийся с + и цифр без разделителей и пробелов
     * @return возвращает отформатированный номер, если переданный в параметре номер не соответствует маске, то null
     */
    public String getFormatPhoneNumber(String simplePhoneNumber) {
        // проверка по длине
        if (simplePhoneNumber.length() - 1 != phoneLength) {
            return null;
        }
        // проверка по коду страны
        if (!simplePhoneNumber.contains(countryCode.trim())) {
            return null;
        }

        var operatorCode = simplePhoneNumber.substring(firstSpace - 2, secondSpace - 2);
        var firstNumberPart = simplePhoneNumber.substring(secondSpace - 2, firstDash - 3);
        var secondNumberPart = simplePhoneNumber.substring(firstDash - 3, secondDash - 4);
        var thirdNumberPart = simplePhoneNumber.substring(secondDash - 4);

        var formatNumber = new StringBuilder(countryCode);
        formatNumber.append(operatorCode)
                .append(" ").append(firstNumberPart)
                .append("-").append(secondNumberPart)
                .append("-").append(thirdNumberPart);

        var result = formatNumber.toString();

        if (!result.matches(regex)) {
            return null;
        }

        return result;
    }

    @Override
    public String toString() {
        return "PhonePattern{" +
                "isoCode='" + isoCode + '\'' +
                ", countryCode=" + countryCode +
                ", phoneLength=" + phoneLength +
                ", fullPhoneLength=" + fullPhoneLength +
                ", phoneMask='" + phoneMask + '\'' +
                ", firstSpace=" + firstSpace +
                ", secondSpace=" + secondSpace +
                ", firstDash=" + firstDash +
                ", secondDash=" + secondDash +
                '}';
    }
}
