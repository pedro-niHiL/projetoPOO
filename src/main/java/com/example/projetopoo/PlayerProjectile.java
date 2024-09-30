package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerProjectile {
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double speed = 5;  // Velocidade do projétil
    private double size = 16;   // Tamanho do projétil

    public PlayerProjectile(double startX, double startY, double directionX, double directionY) {
        this.x = startX;
        this.y = startY;
        this.dx = directionX;
        this.dy = directionY;
    }

    // Atualiza a posição do projétil
    public void update() {
        x += dx * speed;
        y += dy * speed;
    }

    // Desenha o projétil no canvas
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREEN);  // Cor do projétil
        gc.fillOval(x, y, size, size);  // Desenha um círculo como projétil
    }



    // Getters para a posição e o tamanho do projétil
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
