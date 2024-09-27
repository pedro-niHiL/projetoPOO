package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy {
    private double x;
    private double y;
    private double size = 50;
    private double speed = 1;

    public Enemy(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }

    public void moveTowards(double targetX, double targetY) {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        x += (dx / distance) * speed;
        y += (dy / distance) * speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
}
