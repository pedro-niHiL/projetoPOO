package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Core {
    private double x;
    private double y;
    private double size = 100;  // Tamanho do núcleo
    private int health = 100;   // Vida do núcleo
    private Image coreImage;

    public Core(double screenWidth, double screenHeight) {
        updatePosition(screenWidth, screenHeight);
        // Carrega a imagem do núcleo
        this.coreImage = new Image(getClass().getResource("core.png").toString());
    }

    public void updatePosition(double screenWidth, double screenHeight) {
        // Posiciona o núcleo no centro da tela
        this.x = (screenWidth - size) / 2;
        this.y = (screenHeight - size) / 2;
    }

    public void draw(GraphicsContext gc) {
        // Desenha o núcleo
        gc.drawImage(coreImage, x, y, size, size);

        // Desenha a barra de vida acima do núcleo
        drawHealthBar(gc);
    }

    private void drawHealthBar(GraphicsContext gc) {
        double barWidth = size;  // A largura da barra de vida será igual ao tamanho do núcleo
        double barHeight = 10;   // Altura da barra de vida
        double healthPercentage = (double) health / 100.0;

        // Posição da barra de vida (acima do núcleo)
        double barX = x;
        double barY = y - barHeight - 5;  // 5px acima do núcleo

        // Desenha o contorno da barra de vida
        gc.setStroke(Color.BLACK);
        gc.strokeRect(barX, barY, barWidth, barHeight);

        // Desenha a barra de vida preenchida
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
