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

        this.backgroundImage = new Image(getClass().getResource("cenarioMaior.png").toExternalForm());
    }

    public void render() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        gc.drawImage(backgroundImage, 0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        player.draw(gc);
        core.draw(gc);

        for (Enemy enemy : enemyManager.getEnemies()) {
            enemy.draw(gc);
        }

        drawHealthBar(gc);
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

    public void updateGameObjects(Player player, Core core, EnemyManager enemyManager) {
        this.player = player;
        this.core = core;
        this.enemyManager = enemyManager;
    }
}