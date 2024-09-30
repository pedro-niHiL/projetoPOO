package com.example.projetopoo;

import javafx.scene.Scene;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void setupKeyHandling(Scene scene) {
        scene.setOnKeyPressed(event -> player.setKeyPressed(event.getCode().getCode()));
        scene.setOnKeyReleased(event -> player.setKeyReleased(event.getCode().getCode()));
    }
}
