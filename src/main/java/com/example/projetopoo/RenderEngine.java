package com.example.projetopoo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class RenderEngine {
    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private EnemyManager enemyManager;

    public RenderEngine(Canvas gameCanvas, Player player, Core core, EnemyManager enemyManager) {
        this.gameCanvas = gameCanvas;
        this.player = player;
        this.core = core;
        this.enemyManager = enemyManager;
    }

    public void render() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        player.draw(gc);
        core.draw(gc);

        for (Enemy enemy : enemyManager.getEnemies()) {
            enemy.draw(gc);
        }
    }
}
