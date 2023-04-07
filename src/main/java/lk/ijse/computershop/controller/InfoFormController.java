package lk.ijse.computershop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class InfoFormController {

    @FXML
    private void closeOnAction(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
