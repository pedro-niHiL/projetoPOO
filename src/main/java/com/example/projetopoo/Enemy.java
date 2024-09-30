package com.example.projetopoo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Enemy {
    private double x;
    private double y;
    private double size;
    private double speed = 1;

    protected Image spriteSheet;
    private int currentFrame = 0;
    private int frameCount = 4;  // Assumindo 4 frames de animação
    private int spriteRow = 0;  // Primeira linha do spritesheet
    private int animationDelay = 10;
    private int animationTimer = 0;
    protected Canvas gameCanvas = new Canvas();

    private boolean facingLeft = false;

    public Enemy(double startX, double startY, double size) {
        this.x = startX;
        this.y = startY;
        this.size = size;

        // Carrega o spritesheet do inimigo
        this.spriteSheet = new Image(getClass().getResource("spriteSheetEnemy.png").toString());
    }

    public void shoot(){

    }

    public void updateProjectiles() {

    }

    public void moveTowards(double targetX, double targetY) {
        double dx = targetX - (x + size / 2);
        double dy = targetY - (y + size / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;

            // Atualiza a direção do inimigo
            facingLeft = dx < 0;
        }

        // Atualiza a animação
        animationTimer++;
        if (animationTimer >= animationDelay) {
            currentFrame = (currentFrame + 1) % frameCount;
            animationTimer = 0;
        }
    }

    public void draw(GraphicsContext gc) {
        // Se o inimigo estiver virado para a esquerda, espelhe a imagem
        if (facingLeft) {
            gc.save();
            gc.translate(x + size, y);  // Move para a posição do inimigo
            gc.scale(-1, 1);  // Inverte a escala no eixo X para espelhar a imagem
            gc.drawImage(spriteSheet, currentFrame * size, spriteRow * size, size, size, 0, 0, size, size);
            gc.restore();
        } else {
            // Desenha normalmente se o inimigo estiver virado para a direita
            gc.drawImage(spriteSheet, currentFrame * size, spriteRow * size, size, size, x, y, size, size);
        }
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

    public void shoot(double x, double y) {
    }

    public ArrayList<Projectile> getProjectiles() {
        return null;
    }
}
