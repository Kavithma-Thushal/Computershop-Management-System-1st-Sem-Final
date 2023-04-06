package lk.ijse.computershop.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashscreenFormController {

    @FXML
    private AnchorPane splashScreen;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressIndicator;

    public void initialize() {
        new ShowSplashScreen().start();
    }

    class ShowSplashScreen extends Thread {
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    double x = i * (0.1);
                    progressBar.setProgress(x);
                    progressIndicator.setProgress(x);

                    if (i * 10 == 100) {
                        progressIndicator.setVisible(true);
                        progressIndicator.setProgress(1);
                    }

                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "please try again...!").show();
                    }
                }

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
                    } catch (Exception e) {
                        Logger.getLogger(SplashscreenFormController.class.getName()).log(Level.SEVERE, null, e);
                    }

                    assert root != null;
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    splashScreen.getScene().getWindow().hide();
                });
            } catch (Exception e) {
                Logger.getLogger(SplashscreenFormController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
