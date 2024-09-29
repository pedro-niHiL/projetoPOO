package com.example.projetopoo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RenderEngine {
    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private EnemyManager enemyManager;
    private Image backgroundImage;

    public RenderEngine(Canvas gameCanvas, Player player, Core core, EnemyManager enemyManager) {
        this.gameCanvas = gameCanvas;
        this.player = player;
        this.core = core;
        this.enemyManager = enemyManager;

        this.backgroundImage = new Image(getClass().getResource("cenarioMaior.png").toExternalForm());
    }

    public void render(double gameTime) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        // Desenha a imagem de fundo
        gc.drawImage(backgroundImage, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Desenha o jogador, núcleo e inimigos
        player.draw(gc);
        core.draw(gc);

        for (Enemy enemy : enemyManager.getEnemies()) {
            enemy.draw(gc);
        }

        // Desenha a barra de vida do jogador
        drawHealthBar(gc);

        // Desenha o tempo de jogo
        drawGameTime(gc, gameTime);
    }

    private void drawHealthBar(GraphicsContext gc) {
        double maxHealth = 100;
        double currentHealth = player.getHealth();
        double barWidth = 200;
        double barHeight = 20;
        double filledBarWidth = (currentHealth / maxHealth) * barWidth;

        gc.setFill(Color.RED);
        gc.fillRect(10, 10, barWidth, barHeight);

        gc.setFill(Color.GREEN);
        gc.fillRect(10, 10, filledBarWidth, barHeight);
    }

    private void drawGameTime(GraphicsContext gc, double gameTime) {
        gc.setFont(new Font("Arial", 20));

        // Formata o tempo como minutos e segundos
        int minutes = (int) (gameTime / 60);
        int seconds = (int) (gameTime % 60);

        String timeText = String.format("%02d:%02d", minutes, seconds);

        // Defina as coordenadas para o texto (ajustando para que apareça no canto superior direito)
        double textX = gameCanvas.getWidth() - 80; // Posição X ajustada
        double textY = 40; // Posição Y

        // Desenha o texto do tempo em branco
        gc.setFill(Color.RED);
        gc.fillText(timeText, textX, textY);
    }

    public void updateGameObjects(Player player, Core core, EnemyManager enemyManager) {
        this.player = player;
        this.core = core;
        this.enemyManager = enemyManager;
    }
}
