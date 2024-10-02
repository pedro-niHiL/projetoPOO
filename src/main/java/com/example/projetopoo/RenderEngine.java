package com.example.projetopoo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

    public void render(double gameTime) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();



        gc.drawImage(new Image(getClass().getResource("cenario.png").toString()),0,0,gameCanvas.getWidth(),gameCanvas.getHeight());

        core.draw(gc);


        for (Enemy enemy : enemyManager.getEnemies()) {
            enemy.draw(gc);
        }

        player.draw(gc);

        // Desenha a barra de vida do jogador
        drawHealthBar(gc);

        // Desenha o tempo de jogo
        drawGameTime(gc, gameTime);

        drawPlayerPoints(gc);
    }

    private void drawPlayerPoints(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(3,40,200,35);
        gc.setFont(new Font("Arial", 20));
        gc.setFill(Color.WHITE);
        gc.fillText(String.format("Pontuação: %02d", (int)player.getPoints()),10,70);
    }


    private void drawHealthBar(GraphicsContext gc) {
        double maxHealth = 100;
        double currentHealth = player.getHealth();
        double barWidth = 200;
        double barHeight = 15;
        double filledBarWidth = (currentHealth / maxHealth) * barWidth;

        // Desenha a barra de fundo (vida perdida)
        gc.setFill(Color.RED);
        gc.fillRect(10, 20, barWidth, barHeight);

        // Desenha a barra de vida preenchida (vida restante)
        gc.setFill(Color.GREEN);
        gc.fillRect(10, 20, filledBarWidth, barHeight);

        // Desenha a borda preta ao redor da barra
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2); // Define a largura da borda
        gc.strokeRect(10, 20, barWidth, barHeight); // Borda ao redor da barra
    }


    private void drawGameTime(GraphicsContext gc, double gameTime) {
        gc.setFont(new Font("Arial", 20));

        // Formata o tempo como minutos e segundos
        int minutes = (int) (gameTime / 60);
        int seconds = (int) (gameTime % 60);

        String timeText = String.format("%02d:%02d", minutes, seconds);

        // Defina as coordenadas para o texto (ajustando para que apareça no canto superior direito)
        double textX = gameCanvas.getWidth() - 80; // Posição X ajustada
        double textY = 20; // Posição Y

        // Desenha o texto do tempo em vermelho
        gc.setFill(Color.RED);
        gc.fillText(timeText, textX, textY);
    }

    public void updateGameObjects(Player player, Core core, EnemyManager enemyManager) {
        this.player = player;
        this.core = core;
        this.enemyManager = enemyManager;
    }

}
