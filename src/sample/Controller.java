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

        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[0-9,\\+,\\s,-]*")
//                    || newValue.length() > countryMap.get(choiceBox.getValue()).getFullPhoneLength()) {
//                phoneField.setText(oldValue);
//            }

            // "+7 ___ ___-__-__");


            if (!newValue.matches("^\\+?\\d*\\s?\\d*\\s?\\d*-?\\d*-?\\d*$")
                    || newValue.length() > countryMap.get(choiceBox.getValue()).getFullPhoneLength()
            ) {
                phoneField.setText(oldValue);
            }
        });


        phoneField.setOnKeyReleased(keyEvent -> {
            String text = phoneField.getText();
            int length = phoneField.getLength();

            CountryPhone countryPhone = countryMap.get(choiceBox.getValue());
            int secondSpace = countryPhone.getSecondSpace();
            int firstDash = countryPhone.getFirstDash();
            int secondDash = countryPhone.getSecondDash();
            int fullPhoneLength = countryPhone.getFullPhoneLength();
            String countryCode = countryPhone.getCountryCode();
            KeyCode key = keyEvent.getCode();

            System.out.println("len : " + length);
            System.out.println("key code: " + key.getChar());

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

            // если удаляю последний символ слева, то если нахожусь на месте расделителей маски их тоже удаляю
            if (key == KeyCode.BACK_SPACE) {
                if (length == secondSpace) {
                    onBackSpaceFormatField(text, secondSpace);
                } else if (length == firstDash) {
                    onBackSpaceFormatField(text, firstDash);
                } else if (length == secondDash) {
                    onBackSpaceFormatField(text, secondDash);
                }
            }

            // сразу удаляю введенные вручную не в нужных местах символы
//            if (length < fullPhoneLength && length != 0
//                    && (
//                    key == KeyCode.ADD ||
//                            key == KeyCode.SUBTRACT
//                            || key == KeyCode.MINUS
//                            || key == KeyCode.SPACE
//                            || key.isWhitespaceKey())) {
//                text = text.substring(0, length - 1);
//                length = text.length();
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(length);
//            }

            // если введена не цифра то удаляю ее
//            if (!key.isDigitKey() && !key.isArrowKey() && !key.isModifierKey() && !key.isFunctionKey()
//                    && !key.isNavigationKey() && !key.isMediaKey() && key != KeyCode.BACK_SPACE && !text.isEmpty()) {
//                text = text.substring(0, length - 1);
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(length - 1);
//            }

//            // ограничение на длину номера вместе со всеми символами
//            if (length > fullPhoneLength && key.isDigitKey()) {
//                text = text.substring(0, fullPhoneLength);
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(fullPhoneLength);
//            }

            // нажав вправо в пустом поле сразу перехожу к месту ввода кода оператора
            if (text.isEmpty() && key == KeyCode.RIGHT) {
                setCursorToStartInput("");
            }

            // после ввода в начале строки любого символа перепрыгиваю на ввод кода оператора
            if (length==1 && key != KeyCode.BACK_SPACE) {
                setCursorToStartInput("");
            }

            // todo тут что-то не так. не воспринимает цифры с цифровой панели нормально
            if (length==1 && key.isDigitKey()) {
                setCursorToStartInput(key.getChar());
            }

            // находяся в начале ввода кода оператора нажав влево возвращаюсь к демонстрации всей маски телефона
            if (phoneField.getText().equals(countryCode + " ") && key == KeyCode.LEFT) {
                phoneField.clear();
            }

            // находясь в начале ввода кода оператора если удаляю пробел разделяющий код страны и код оператора,
            // то удаляю все и возвращаюсь к показу маски ввода телефона
            if (phoneField.getText().equals(countryCode) && key == KeyCode.BACK_SPACE) {
                phoneField.clear();
            }

            // отчищаю номер в поле от всех знаков
            String number = text.replaceAll("\\+", "").replaceAll(" ", "")
                    .replaceAll("-", "");

            System.out.println("Number: " + number);
            System.out.println("Number length: " + number.length());
        });

//        phoneField.setOnKeyReleased(keyEvent -> {
//            String text = phoneField.getText();
//            int length = text.length();
//
//            CountryPhone countryPhone = countryMap.get(choiceBox.getValue());
//            int secondSpace = countryPhone.getSecondSpace();
//            int firstDash = countryPhone.getFirstDash();
//            int secondDash = countryPhone.getSecondDash();
//            int fullPhoneLength = countryPhone.getFullPhoneLength();
//            String countryCode = countryPhone.getCountryCode();
//
//            KeyCode key = keyEvent.getCode();
//
//            // если ввожу цифры, то форматирую строку в нужных местах дополняя пробелами и тире
//            if (key.isDigitKey() && !text.isEmpty()) {
//                if (length == secondSpace) {
//                    text = text.substring(0, secondSpace) + " " + text.substring(secondSpace);
//                    length = text.length();
//                    phoneField.clear();
//                    phoneField.setText(text);
//                    phoneField.positionCaret(length);
//                } else if (length == firstDash) {
//                    text = text.substring(0, firstDash) + "-" + text.substring(firstDash);
//                    length = text.length();
//                    phoneField.clear();
//                    phoneField.setText(text);
//                    phoneField.positionCaret(length);
//                } else if (length == secondDash) {
//                    text = text.substring(0, secondDash) + "-" + text.substring(secondDash);
//                    length = text.length();
//                    phoneField.clear();
//                    phoneField.setText(text);
//                    phoneField.positionCaret(length);
//                }
//            }
//
//            // если удаляю последний символ слева, то если нахожусь на месте расделителей маски их тоже удаляю
//            if (key == KeyCode.BACK_SPACE) {
//                if (length == secondSpace) {
//                    onBackSpaceFormatField(text, secondSpace);
//                } else if (length == firstDash) {
//                    onBackSpaceFormatField(text, firstDash);
//                } else if (length == secondDash) {
//                    onBackSpaceFormatField(text, secondDash);
//                }
//            }
//
//            // если введена не цифра то удаляю ее
//            if (!key.isDigitKey() && !key.isArrowKey() && !key.isModifierKey() && !key.isFunctionKey()
//                    && !key.isNavigationKey() && !key.isMediaKey() && key != KeyCode.BACK_SPACE && !text.isEmpty()) {
//                text = text.substring(0, length - 1);
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(length - 1);
//            }
//
//            // ограничение на длину номера вместе со всеми символами
//            if (length > fullPhoneLength && key.isDigitKey()) {
//                text = text.substring(0, fullPhoneLength);
//                phoneField.clear();
//                phoneField.setText(text);
//                phoneField.positionCaret(fullPhoneLength);
//            }
//
//            // нажав вправо в пустом поле сразу перехожу к месту ввода кода оператора
//            if (text.isEmpty() && key == KeyCode.RIGHT) {
//                setCursorToStartInput();
//            }
//
//            // находяся в начале ввода кода оператора нажав влево возвращаюсь к демонстрации всей маски телефона
//            if (phoneField.getText().equals(countryCode + " ") && key == KeyCode.LEFT) {
//                phoneField.clear();
//            }
//
//            // находясь в начале ввода кода оператора если удаляю пробел разделяющий код страны и код оператора,
//            // то удаляю все и возвращаюсь к показу маски ввода телефона
//            if (phoneField.getText().equals(countryCode) && key == KeyCode.BACK_SPACE) {
//                phoneField.clear();
//            }
//
//            // отчищаю номер в поле от всех знаков
//            String number = text.replaceAll("\\+", "").replaceAll(" ", "")
//                    .replaceAll("-", "");
//
//            System.out.println("Number: " + number);
//            System.out.println("Number length: " + number.length());
//        });

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

//    UnaryOperator<TextFormatter.Change> filter = change -> {
//        String text = change.getText();
//
//        if (text.matches("[0-9]*")) {
//            return change;
//        }
//
//        return null;
//    };
//    TextFormatter<String> textFormatter = new TextFormatter<>(filter);

}
