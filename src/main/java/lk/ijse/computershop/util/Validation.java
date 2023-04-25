package lk.ijse.computershop.util;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validation {

    public static Object validate(LinkedHashMap<TextField, Pattern> map, Button btnSave) {
        for (TextField textField : map.keySet()) {
            Pattern pattern = map.get(textField);
            if (!pattern.matcher(textField.getText()).matches()) {
                if (!textField.getText().isEmpty()) {
                    textField.setStyle("-fx-border-color: red");
                    btnSave.setDisable(true);
                }
                return textField;
            }
            textField.setStyle("-fx-border-color: green");
        }
        btnSave.setDisable(false);
        return true;
    }
}
