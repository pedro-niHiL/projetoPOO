package com.example.projetopoo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {

    private final Timeline timeline;

    public GameLoop(Runnable updateMethod, int fps) {
        double interval = 1000.0 / fps; // Calcula o intervalo com base no FPS
        timeline = new Timeline(new KeyFrame(Duration.millis(interval), e -> updateMethod.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
