package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField phoneField;

    @FXML
    private Label resultLabel;

    private Map<String, CountryPhone> countryMap;

    @FXML
    void initialize() {
        countryMap = CountryMap.getMap();

        Set<String> countries = countryMap.keySet();
        ObservableList<String> countriesList = FXCollections.observableArrayList(countries);
        choiceBox.setItems(countriesList);

        // страна по умолчанию
        choiceBox.setValue("Россия");
        // запрет выбора страны
        choiceBox.setDisable(false);


        // первичное заполнение полей паттерном
        String initPhonePattern = countryMap.get(choiceBox.getValue()).getPhoneMask();
        phoneField.setPromptText(initPhonePattern);
        phoneField.positionCaret(initPhonePattern.length());

        resultLabel.setText(initPhonePattern);


        // разрешение на работу choiceBox и обработчик смены значения в textField
        choiceBox.setOnAction(actionEvent -> {
            CountryPhone countryPhone = countryMap.get(choiceBox.getValue());
            String phonePattern = countryPhone.getPhoneMask();

            phoneField.clear();
            phoneField.setPromptText(phonePattern);
            phoneField.positionCaret(phonePattern.length());

            resultLabel.setText(phonePattern);
        });

        CountryPhone countryPhone = countryMap.get(choiceBox.getValue());
        int firstSpace = countryPhone.getFirstSpace();
        int secondSpace = countryPhone.getSecondSpace();
        int firstDash = countryPhone.getFirstDash();
        int secondDash = countryPhone.getSecondDash();
        int fullPhoneLength = countryPhone.getFullPhoneLength();
        String countryCode = countryPhone.getCountryCode();

        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9,\\+,\\s,-]*")
//                    || newValue.length() > countryMap.get(choiceBox.getValue()).getFullPhoneLength()) {
//                phoneField.setText(oldValue);
//            }

            // "+7 ___ ___-__-__");


//            if (!newValue.matches("^\\+?\\d*\\s?\\d*\\s?\\d*-?\\d*-?\\d*$")
//            if (!newValue.matches("(^\\+\\d?\\s?(\\d{3})?(\\s?(\\d{3})?)?-?(\\d{2})?-?\\d{0,2}$)?")
//            if (!newValue.matches("^(\\+(\\d(\\s(\\d{0,3}(\\s(\\d{0,3}(-(\\d{0,2}(-(\\d{0,2})?)?)?)?)?)?)?)?)?)?$")
            if (!newValue
                    .matches("^(\\+(\\d(\\s(\\d(\\d(\\d(\\s(\\d(\\d(\\d(-(\\d(\\d(-(\\d(\\d)?)?)?)?)?)?)?)?)?)?)?)" +
                            "?)?)?)" +
                            "?)?$")
//                    || newValue.length() > countryMap.get(choiceBox.getValue()).getFullPhoneLength()
            ) {
                phoneField.setText(oldValue);
            }
        });


        phoneField.setOnKeyReleased(keyEvent -> {
            String text = phoneField.getText();
            int length = phoneField.getLength();

//            CountryPhone countryPhone = countryMap.get(choiceBox.getValue());
//            int secondSpace = countryPhone.getSecondSpace();
//            int firstDash = countryPhone.getFirstDash();
//            int secondDash = countryPhone.getSecondDash();
//            int fullPhoneLength = countryPhone.getFullPhoneLength();
//            String countryCode = countryPhone.getCountryCode();
            KeyCode key = keyEvent.getCode();

            System.out.println("len : " + length);
            System.out.println("key code: " + key.getName());
            System.out.println("secondSpace: " + secondSpace);

            // если ввожу цифры, то форматирую строку в нужных местах дополняя пробелами и тире
            if (key.isDigitKey() && !text.isEmpty()) {
                if (length == secondSpace) {
                    text = text.substring(0, secondSpace) + " " + text.substring(secondSpace);
                } else if (length == firstDash) {
                    text = text.substring(0, firstDash) + "-" + text.substring(firstDash);
                } else if (length == secondDash) {
                    text = text.substring(0, secondDash) + "-" + text.substring(secondDash);
                }
                length = text.length();
                phoneField.clear();
                phoneField.setText(text);
                phoneField.positionCaret(length);
            }


//            if (key != KeyCode.BACK_SPACE) {
//                if (length == secondSpace) {
//                    phoneField.appendText(" ");
//                } else if (length == firstDash || length == secondDash) {
//                    phoneField.appendText("-");
//                }
//                length = text.length();
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(length);
//            }


            // если удаляю последний символ слева, то если нахожусь на месте расделителей маски их тоже удаляю
//            if (key == KeyCode.BACK_SPACE) {
//                if (length == secondSpace) {
//                    onBackSpaceFormatField(text, secondSpace);
//                } else if (length == firstDash) {
//                    onBackSpaceFormatField(text, firstDash);
//                } else if (length == secondDash) {
//                    onBackSpaceFormatField(text, secondDash);
//                }
//            }

            // после ввода в начале строки любого символа перепрыгиваю на ввод кода оператора
//            if ((length == secondSpace + 1 || length == firstDash + 1 || length == secondDash + 1) && key == KeyCode.BACK_SPACE) {
//                text = phoneField.getText();
//                length = phoneField.getLength();
//                text = text.substring(0, length - 2);
//                length = text.length();
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(length);
//            }

            // todo чтото не так с удалением символов, начать со второго пробела
            if (length == secondSpace + 1 && key == KeyCode.BACK_SPACE) {
                text = phoneField.getText();
                length = phoneField.getLength();
                text = text.substring(0, length - 1);
                length = text.length();
                phoneField.clear();
                phoneField.setText(text);
                phoneField.positionCaret(length);
            }

            // нажав вправо в пустом поле сразу перехожу к месту ввода кода оператора
            if (text.isEmpty() && key == KeyCode.RIGHT) {
                setCursorToStartInput("");
            }

            // после ввода в начале строки любого символа перепрыгиваю на ввод кода оператора
            if (length == 1 && key != KeyCode.BACK_SPACE) {
                setCursorToStartInput("");
            }

            // при взятии фокуса в поле с помощью таба сразу перепрыгиваю на ввод кода оператора
            if (key == KeyCode.TAB) {
                setCursorToStartInput("");
            }

            // находяся в начале ввода кода оператора нажав влево возвращаюсь к демонстрации всей маски телефона
            if (phoneField.getText().equals(countryCode + " ") && key == KeyCode.LEFT) {
                phoneField.clear();
            }

            // находясь в начале ввода кода оператора если удаляю пробел разделяющий код страны и код оператора,
            // то удаляю все и возвращаюсь к показу маски ввода телефона
            if (length == firstSpace && key == KeyCode.BACK_SPACE) {
                phoneField.clear();
            }

            // отчищаю номер в поле от всех знаков
            String number = text.replaceAll("\\+", "").replaceAll(" ", "")
                    .replaceAll("-", "");

            System.out.println("Number: " + number);
            System.out.println("Number length: " + number.length());
        });

        // при клике на поле ввода мышью заменяю маску на код страны и ставлю курсор в место начала ввода кода оператора
        phoneField.setOnMouseClicked(actionEvent -> setCursorToStartInput(""));

        // убрать фокус с поля ввода
//        Platform.runLater(() -> resultLabel.requestFocus());

        resultLabel.setStyle("-fx-font: 15pt 'Open Sans'");
    }

    // ввод в поле кода страны и постановка курсора на дальнейший ввод
    private void setCursorToStartInput(String addSymbol) {
        String countryCode = countryMap.get(choiceBox.getValue()).getCountryCode() + " " + addSymbol;
        phoneField.clear();
        phoneField.setText(countryCode);
        phoneField.positionCaret(countryCode.length());
    }

    private void onBackSpaceFormatField(String text, int position) {
        text = text.substring(0, position - 1);
        int length = text.length();
        phoneField.clear();
        phoneField.setText(text);
        phoneField.positionCaret(length);
    }

    private String namPadKeyHeader(KeyCode key) {
        switch (key) {
            case NUMPAD0:
                return "0";
            case NUMPAD1:
                return "1";
            case NUMPAD2:
                return "2";
            case NUMPAD3:
                return "3";
            case NUMPAD4:
                return "4";
            case NUMPAD5:
                return "5";
            case NUMPAD6:
                return "6";
            case NUMPAD7:
                return "7";
            case NUMPAD8:
                return "8";
            case NUMPAD9:
                return "9";
            default:
                return "";
        }
    }

}
