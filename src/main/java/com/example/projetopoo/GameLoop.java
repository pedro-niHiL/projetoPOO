package com.example.projetopoo;

import javafx.animation.AnimationTimer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLoop {
    private static final Logger LOGGER = Logger.getLogger(GameLoop.class.getName());
    private final Runnable gameUpdate;
    private AnimationTimer timer;

    public GameLoop(Runnable gameUpdate) {
        this.gameUpdate = gameUpdate;
    }

    public void start() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    gameUpdate.run();
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Exceção no loop do jogo", e);
                    stop();
                }
            }
        };
        timer.start();
        LOGGER.info("Loop do jogo iniciado");
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
            LOGGER.info("Loop do jogo parado");
        }
    }
}