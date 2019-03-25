package sample;

import java.net.URL;
import java.util.*;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    private Map<String, String> countryPhonePattern;
    @FXML
    void initialize() {
        countryPhonePattern = new HashMap<>();
        countryPhonePattern.put("Россия", "+7 ___ ___-__-__");
        countryPhonePattern.put("Беларусь", "+375 __ ___-__-__");
        countryPhonePattern.put("Украина", "+380 __ ___-__-__");
        countryPhonePattern.put("Казахстан", "+77 __ ___-__-__");
        countryPhonePattern.put("Польша", "+48 __ ___-__-__");

//        choiceBox.getItems().add("Россия");
//        choiceBox.getItems().add("Казахстан");
//        choiceBox.getItems().add("Беларусь");
//        choiceBox.getItems().add("Украина");
//        choiceBox.getItems().add("Польша");


        Set<String> countriesSet = countryPhonePattern.keySet();

        ObservableList<String> countriesList = FXCollections.observableArrayList(countriesSet);

        choiceBox.setItems(countriesList);


//        resultLabel.textProperty().bind(choiceBox.valueProperty());

        choiceBox.setOnAction(actionEvent -> {
            String phonePattern = countryPhonePattern.get(choiceBox.getValue());
            phoneField.setPromptText(phonePattern);
        });

        resultLabel.setText("+7 ___ ___-__-__");
        resultLabel.setStyle("-fx-font: 15pt 'Open Sans'");
    }
}