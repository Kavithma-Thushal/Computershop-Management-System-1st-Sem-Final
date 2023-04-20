package lk.ijse.computershop.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.computershop.util.UILoader;
import org.controlsfx.control.Notifications;

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
            UILoader.LoginOnAction(root, "admindashboard_form");

            Image img = new Image("/assets/icons/tick.gif", 90, 90, false, false);
            Notifications notificationBuilder = Notifications.create()
                    .title("Admin login ")
                    .text("Admin login Successful")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }
}
