package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy {
    private double x;
    private double y;
    private double size;
    private double speed = 1;

    public Enemy(double startX, double startY, double size) {
        this.x = startX;
        this.y = startY;
        this.size = size;
    }

    public void moveTowards(double targetX, double targetY) {
        double dx = targetX - (x + size / 2);
        double dy = targetY - (y + size / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, size, size);  // Desenha o inimigo com as dimensões corretas
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
