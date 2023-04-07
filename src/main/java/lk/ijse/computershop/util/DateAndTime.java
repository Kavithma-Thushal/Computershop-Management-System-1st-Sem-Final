package lk.ijse.computershop.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DateAndTime {

    public static void loadDateAndTime(javafx.scene.control.Label lblDate, javafx.scene.control.Label lblTime) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        lblDate.setText(simpleDateFormat.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();

            String state;
            if (currentTime.getHour() >= 12) {
                state = "PM";
            } else {
                state = "AM";
            }

            int hour = Integer.parseInt(String.valueOf(currentTime.getHour()));
            if (hour >= 12) {
                hour -= 12;
            }

            lblTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", currentTime.getMinute()) + " " + state);

        }),
                new KeyFrame(Duration.seconds(60))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}
