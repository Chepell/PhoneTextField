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

    private Map<String, PhonePattern> countryMap;

    @FXML
    void initialize() {
        countryMap = PhonePatternMap.getMap();

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

        resultLabel.setText(initPhonePattern);


        // разрешение на работу choiceBox и обработчик смены значения в textField
        choiceBox.setOnAction(actionEvent -> {
            PhonePattern countryPhonePattern = countryMap.get(choiceBox.getValue());
            String phonePattern = countryPhonePattern.getPhoneMask();
            phoneField.clear();
            phoneField.setPromptText(phonePattern);

            resultLabel.setText(phonePattern);
        });

        // слушатель изменений при вводе в текстовом поле
        phoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            PhonePattern phonePattern = countryMap.get(choiceBox.getValue());
            String regex = phonePattern.getRegex();
            int fullPhoneLength = phonePattern.getFullPhoneLength();

            if (!newValue.matches(regex) || newValue.length() > fullPhoneLength) {
                phoneField.setText(oldValue);
            }
        });

        phoneField.setOnKeyReleased(keyEvent -> {
            PhonePattern phonePattern = countryMap.get(choiceBox.getValue());
            int firstSpace = phonePattern.getFirstSpace();
            int secondSpace = phonePattern.getSecondSpace();
            int firstDash = phonePattern.getFirstDash();
            int secondDash = phonePattern.getSecondDash();
//            String countryCode = phonePattern.getCountryCode();

            String textInField = phoneField.getText();
            int length = phoneField.getLength();
            KeyCode key = keyEvent.getCode();

            System.out.println("len : " + length);
            System.out.println("key code: " + key.getName());
//            System.out.println("secondSpace: " + secondSpace);

            // автоматически форматирую строку при вводе в нужных местах дополняя пробелами и тире
            if (key.isDigitKey()) {
                if (length == secondSpace) {
                    textInField = textInField + " ";
                } else if (length == firstDash || length == secondDash) {
                    textInField = textInField + "-";
                }
                length = textInField.length();
                phoneField.clear();
                phoneField.setText(textInField);
                phoneField.positionCaret(length);
            }

            // обработчик передвижения по области кода страны, тут всяческий ввод запрещен,
            // либо возврат к полной маске, либо заполнение кода по шаблону
            if (length <= firstSpace + 1) {
                if (key == KeyCode.RIGHT || key == KeyCode.ADD) {
                    setCursorToStartInput();
                } else if (key == KeyCode.LEFT || key == KeyCode.BACK_SPACE) {
                    phoneField.clear();
                } else {
                    setCursorToStartInput();
                }
            }

            // отчищаю номер в поле от всех знаков
            String number = textInField.replaceAll("\\+", "").replaceAll(" ", "")
                    .replaceAll("-", "");

            System.out.println("Number: " + number);
            System.out.println("Number length: " + number.length());
        });

        // при взятии фокуса в поле с помощью таба сразу перепрыгиваю на ввод кода оператора
//        if (key == KeyCode.TAB) {
//            setCursorToStartInput();
//        }

        // при клике на поле ввода мышью заменяю маску на код страны и ставлю курсор в место начала ввода кода оператора
//        phoneField.setOnMouseClicked(actionEvent -> setCursorToStartInput());


        // убрать фокус с поля ввода
//        Platform.runLater(() -> resultLabel.requestFocus());

        resultLabel.setStyle("-fx-font: 15pt 'Open Sans'");
    }

    // ввод в поле кода страны и постановка курсора на дальнейший ввод
    private void setCursorToStartInput() {
        String countryCode = countryMap.get(choiceBox.getValue()).getCountryCode() + " ";
        phoneField.clear();
        phoneField.setText(countryCode);
        phoneField.positionCaret(countryCode.length());
    }
}
