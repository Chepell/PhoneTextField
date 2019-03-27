package sample;

/**
 * Artem Voytenko
 * 26.03.2019
 */

public class PhonePattern {
    private String countryRU;
    private String countryEN;
    private String isoCode;
    private String countryCode;
    private int phoneLength;
    private int fullPhoneLength;
    private String phoneMask;
    private int firstSpace;
    private int secondSpace;
    private int firstDash;
    private int secondDash;
    private String regex;

    public PhonePattern(String countryRU, String countryEN, String isoCode, String phoneMask, String regex) {

        this.countryRU = countryRU;
        this.countryEN = countryEN;
        this.isoCode = isoCode;
        this.fullPhoneLength = phoneMask.length();
        this.phoneLength = fullPhoneLength - 5; // минус количество всех знаков в отформатированной маске
        this.phoneMask = phoneMask;

        this.firstSpace = phoneMask.indexOf(" ");
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
