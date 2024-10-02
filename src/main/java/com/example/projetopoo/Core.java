package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Core {
    private double x;
    private double y;
    private double size = 105;
    private int health = 100;
    private Image spritesheet;
    private int currentFrame = 0;
    private int frameCount = 6;
    private double frameWidth;
    private double frameHeight;
    private long lastFrameTime;
    private long frameDuration = 100_000_000; // 100ms

    public Core(double screenWidth, double screenHeight) {
        updatePosition(screenWidth, screenHeight);
        loadSpritesheet();
        lastFrameTime = System.nanoTime();
    }

    private void loadSpritesheet() {
        this.spritesheet = new Image(getClass().getResource("coreSpritesheet.png").toString());
        this.frameWidth = spritesheet.getWidth() / frameCount;
        this.frameHeight = size; // Use only the first row (64px height)
    }

    public void updatePosition(double screenWidth, double screenHeight) {
        this.x = (screenWidth - size) / 2;
        this.y = (screenHeight - size) / 2 + 50;
    }

    public void draw(GraphicsContext gc) {
        updateAnimation();

        double srcX = currentFrame * frameWidth;
        double srcY = 0; // Always use the first row

        gc.drawImage(spritesheet, srcX, srcY, frameWidth, frameHeight, x, y, size, size);

        drawHealthBar(gc);
    }

    private void updateAnimation() {
        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime > frameDuration) {
            currentFrame = (currentFrame + 1) % frameCount;
            lastFrameTime = currentTime;
        }
    }

    private void drawHealthBar(GraphicsContext gc) {
        double barWidth = size;
        double barHeight = 1.0/10 * size;
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

    public boolean isColliding(double enemyX, double enemyY, double enemySize) {
        return (enemyX + enemySize > x && enemyX < x + size &&
                enemyY + enemySize > y && enemyY < y + size);
    }

    // Getters and other methods remain the same

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

    public void resetHealth() {
        this.health = 100;
    }
}