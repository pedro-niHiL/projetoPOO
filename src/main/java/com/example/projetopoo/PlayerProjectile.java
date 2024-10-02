package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerProjectile extends Projectile {

    public PlayerProjectile(double startX, double startY, double directionX, double directionY) {
        // Chama o construtor da classe base (Projectile)
        super(startX, startY, directionX, directionY);
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Sobrescreve o metodo draw para modificar a cor do projétil
        gc.setFill(Color.GREEN);  // Cor específica do projétil do jogador
        gc.fillOval(getX(), getY(), getSize(), getSize());  // Desenha um círculo como projétil
    }
}
