package com.example.projetopoo;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class PlayerController {

    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void setupKeyHandling(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code.isLetterKey()) {
                player.setKeyPressed(code.getCode());
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code.isLetterKey()) {
                player.setKeyReleased(code.getCode());
            }
        });
    }
}
