package lk.ijse.computershop.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.computershop.util.DateAndTime;
import lk.ijse.computershop.util.UILoader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private AnchorPane mainRoot;
    @FXML
    private AnchorPane root;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateAndTime.loadDateAndTime(lblDate, lblTime);
        fadeScreen();
    }

    private void fadeScreen() {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), mainRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    @FXML
    private void cashierLoginOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(root, "cashier_form");
    }

    @FXML
    private void adminLoginOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.loadUiHalfDashBoard(root, "admin_form");
    }

    @FXML
    private void infoOnAction(MouseEvent mouseEvent) throws IOException {
        UILoader.aboutOnAction("info_form");
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

    @FXML
    private void playMouseEnterAnimationlogo(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.07);
            scaleT.setToY(1.07);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.BLACK);
            glow.setWidth(5);
            glow.setHeight(5);
            glow.setRadius(5);
            icon.setEffect(glow);
        }
    }

    @FXML
    private void playMouseExitAnimationlogo(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    @FXML
    private void minimizeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    private void goToFacebookOnAction(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://web.facebook.com/chamacomputers.lk"));
    }

    @FXML
    private void goToGoogleOnAction(MouseEvent mouseEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.chamacomputers.lk/"));
    }
}
