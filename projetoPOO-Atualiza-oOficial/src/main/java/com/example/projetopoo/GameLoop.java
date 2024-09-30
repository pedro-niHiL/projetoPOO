package com.example.projetopoo;

import javafx.animation.AnimationTimer;

public class GameLoop {
    private final Runnable gameUpdate;
    private AnimationTimer timer;

    public GameLoop(Runnable gameUpdate) {
        this.gameUpdate = gameUpdate;
    }

    public void start() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate.run();
            }
        };
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}
