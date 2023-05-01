package lk.ijse.computershop.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
                    .hideAfter(Duration.seconds(2))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "please try again...!").show();
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.05);
            scaleT.setToY(1.05);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.BLACK);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }
}
