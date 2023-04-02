package lk.ijse.computershop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminloginFormController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private void loginOnAction(ActionEvent event) throws IOException {

        if (txtUsername.getText().equals("admin") && txtPassword.getText().equals("1234")) {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/admindashboard_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Admin Dashboard");
            stage.setResizable(false);
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }

    }
}
