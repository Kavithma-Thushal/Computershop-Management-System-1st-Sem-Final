package lk.ijse.computershop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.computershop.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;

public class AdminFormController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private void adminLoginOnAction(MouseEvent mouseEvent) throws IOException, SQLException {
        if (txtUsername.getText().equals("") && txtPassword.getText().equals("")) {
            UILoader.LoginOnAction(root,"admindashboard_form");
        } else {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}
