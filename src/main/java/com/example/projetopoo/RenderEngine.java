package com.example.projetopoo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

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
        gc.drawImage(new Image(getClass().getResource("cenario maior.png").toString()),0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        core.draw(gc);



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
