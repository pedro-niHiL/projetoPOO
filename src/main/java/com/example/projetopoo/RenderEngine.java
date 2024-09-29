package com.example.projetopoo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

        // Carregar a imagem de fundo
        this.backgroundImage = new Image(getClass().getResource("cenario_600x375.png").toExternalForm());

    }

    public void render() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        // Desenha a imagem de fundo
        gc.drawImage(backgroundImage, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Desenha os elementos do jogo
        player.draw(gc);
        core.draw(gc);

        // Desenha os inimigos
        for (Enemy enemy : enemyManager.getEnemies()) {
            enemy.draw(gc);
        }

        // Desenha a barra de vida do jogador
        drawHealthBar(gc);
    }

    private void drawHealthBar(GraphicsContext gc) {
        double maxHealth = 100;
        double currentHealth = player.getHealth();
        double barWidth = 200;
        double barHeight = 20;
        double filledBarWidth = (currentHealth / maxHealth) * barWidth;

        // Desenha a barra de vida
        gc.setFill(Color.RED); // Fundo da barra (vida perdida)
        gc.fillRect(10, 10, barWidth, barHeight);

        gc.setFill(Color.GREEN); // Parte preenchida (vida restante)
        gc.fillRect(10, 10, filledBarWidth, barHeight);
    }
}
