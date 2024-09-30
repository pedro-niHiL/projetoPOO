package com.example.projetopoo;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class GameController {

    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private GameLoop gameLoop;
    private PlayerController playerController;
    private EnemyManager enemyManager;
    private RenderEngine renderEngine;


    private double gameTime; // Adiciona o campo de tempo de jogo

    public GameController(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void initialize(Scene scene) {
        double canvasWidth = gameCanvas.getWidth();
        double canvasHeight = gameCanvas.getHeight();

        gameCanvas.setOnMouseMoved(event -> {
            player.setMousePosition(event.getX(), event.getY());
        });

        player = new Player(500, 500, canvasWidth, canvasHeight);
        core = new Core(canvasWidth, canvasHeight);
        enemyManager = new EnemyManager(canvasWidth, canvasHeight);
        renderEngine = new RenderEngine(gameCanvas, player, core, enemyManager);

        // Setup para controle do jogador
        playerController = new PlayerController(player);
        playerController.setupKeyHandling(scene);


        
        gameTime = 0; // Inicia o tempo de jogo

        // Inicia o loop do jogo
        gameLoop = new GameLoop(() -> {
            update(1.0 / 60);
            renderEngine.render(gameTime); // Passa o tempo para ser renderizado
            checkGameOver();
        });
        gameLoop.start();

        // Garante que o Canvas tenha o foco
        scene.getRoot().requestFocus();
    }



    private void update(double deltaTime) {
        player.update(deltaTime, core);
        enemyManager.update(deltaTime, core, player);
        gameTime += deltaTime; // Incrementa o tempo de jogo
    }

    private void checkGameOver() {
        // Verifica se o jogador ou núcleo perdeu toda a vida
        if (player.getHealth() <= 0 || core.getHealth() <= 0) {
            gameLoop.stop();
            restartGame();  // Reinicia o jogo sem alterar a cena
        }
    }

    public void restartGame() {

        player.resetHealth();
        core.resetHealth();
        enemyManager.clearEnemies();

        gameTime = 0; // Reinicia o tempo de jogo

        // Reinicia o loop de jogo
        gameLoop.start();

    }
}
