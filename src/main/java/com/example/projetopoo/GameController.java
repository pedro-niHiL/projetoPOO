package com.example.projetopoo;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {
    private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private GameLoop gameLoop;
    private PlayerController playerController;
    private EnemyManager enemyManager;
    private RenderEngine renderEngine;
    private double gameTime;

    public GameController(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void initialize(Scene scene) {
        try {
            double canvasWidth = gameCanvas.getWidth();
            double canvasHeight = gameCanvas.getHeight();

            gameCanvas.setOnMouseMoved(event -> {
                if (player != null) {
                    player.setMousePosition(event.getX(), event.getY());
                }
            });

            player = new Player(500, 500, canvasWidth, canvasHeight);
            core = new Core(canvasWidth, canvasHeight);
            enemyManager = new EnemyManager(canvasWidth, canvasHeight);
            renderEngine = new RenderEngine(gameCanvas, player, core, enemyManager);

            playerController = new PlayerController(player);
            playerController.setupKeyHandling(scene);

            gameTime = 0;

            gameLoop = new GameLoop(this::gameUpdateCycle);
            gameLoop.start();

            scene.getRoot().requestFocus();

            LOGGER.info("Jogo inicializado com sucesso");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar o jogo", e);
        }
    }

    private void gameUpdateCycle() {
        try {
            update(1.0 / 60);
            renderEngine.render(gameTime);
            checkGameOver();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro no ciclo de atualização do jogo", e);
            gameLoop.stop();
        }
    }

    private void update(double deltaTime) {
        try {
            if (player != null) player.update(deltaTime, core);
            if (enemyManager != null) enemyManager.update(deltaTime, core, player);
            gameTime += deltaTime;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro na atualização do estado do jogo", e);
        }
    }

    private void checkGameOver() {
        try {
            if ((player != null && player.getHealth() <= 0) || (core != null && core.getHealth() <= 0)) {
                gameLoop.stop();
                restartGame();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao verificar fim de jogo", e);
        }
    }

    public void restartGame() {
        try {
            if (player != null) player.resetHealth();
            if (core != null) core.resetHealth();
            if (enemyManager != null) enemyManager.clearEnemies();

            gameTime = 0;

            gameLoop.start();

            LOGGER.info("Jogo reiniciado");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao reiniciar o jogo", e);
        }
    }
}