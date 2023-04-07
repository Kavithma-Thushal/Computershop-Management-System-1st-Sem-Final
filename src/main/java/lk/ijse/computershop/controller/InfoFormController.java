package lk.ijse.computershop.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.computershop.util.UILoader;

import java.io.IOException;

public class InfoFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private void closeOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.CloseStage(root);
    }
}
