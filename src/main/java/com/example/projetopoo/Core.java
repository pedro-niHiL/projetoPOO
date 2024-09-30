package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Core {
    private double x;
    private double y;
    private double size = 64;  // Tamanho do núcleo
    private int health = 100;   // Vida do núcleo
    private Image coreImage;

    public Core(double screenWidth, double screenHeight) {
        updatePosition(screenWidth, screenHeight);
        this.coreImage = new Image(getClass().getResource("core.png").toString());
    }

    public void updatePosition(double screenWidth, double screenHeight) {
        this.x = (screenWidth - size) / 2;
        this.y = (screenHeight - size) / 2;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(coreImage, x, y, size, size);
        drawHealthBar(gc);
    }

    private void drawHealthBar(GraphicsContext gc) {
        double barWidth = size;
        double barHeight = size/10;
        double healthPercentage = (double) health / 100.0;
        double barX = x;
        double barY = y - barHeight - 5;

        gc.setStroke(Color.BLACK);
        gc.strokeRect(barX, barY, barWidth, barHeight);

        gc.setFill(Color.RED);
        gc.fillRect(barX, barY, barWidth * healthPercentage, barHeight);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            System.out.println("Núcleo destruído!");
        }
    }

    public void resetHealth() {
        this.health = 100;  // Restaura a vida
    }

    public boolean isColliding(double enemyX, double enemyY, double enemySize) {
        return (enemyX + enemySize > x && enemyX < x + size &&
                enemyY + enemySize > y && enemyY < y + size);
    }

    public int getHealth() {
        return health;
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
