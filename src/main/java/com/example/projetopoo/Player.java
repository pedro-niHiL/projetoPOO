package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {

    private double x;
    private double y;
    private final double size = 32;
    private final double speed = 5;
    private boolean[] keys = new boolean[256];

    public Player(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        if (keys['W']) y -= speed;
        if (keys['S']) y += speed;
        if (keys['A']) x -= speed;
        if (keys['D']) x += speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size);
    }

    public void setKeyPressed(int keyCode) {
        keys[keyCode] = true;
    }

    public void setKeyReleased(int keyCode) {
        keys[keyCode] = false;
    }
}
