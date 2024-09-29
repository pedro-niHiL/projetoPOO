package com.example.projetopoo;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class GameController {

    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private GameLoop gameLoop;
    private PlayerController playerController;
    private EnemyManager enemyManager;
    private RenderEngine renderEngine;
    private Stage primaryStage;

    public GameController(Canvas gameCanvas, Stage primaryStage) {
        this.gameCanvas = gameCanvas;
        this.primaryStage = primaryStage;
    }

    public void initialize(Scene scene) {
        double canvasWidth = gameCanvas.getWidth();
        double canvasHeight = gameCanvas.getHeight();

        // Inicializa o jogo
        player = new Player(100, 100, canvasWidth, canvasHeight);
        core = new Core(canvasWidth, canvasHeight);
        enemyManager = new EnemyManager(canvasWidth, canvasHeight);
        renderEngine = new RenderEngine(gameCanvas, player, core, enemyManager);

        // Configura eventos de tecla
        playerController = new PlayerController(player);
        playerController.setupKeyHandling(scene);

        // Inicia o loop do jogo
        gameLoop = new GameLoop(() -> {
            update(1.0 / 60);
            renderEngine.render();
            checkGameOver();
        });
        gameLoop.start();

        // Garante que o Canvas tenha o foco
        scene.getRoot().requestFocus();
    }

    private void update(double deltaTime) {
        player.update(deltaTime, core);
        enemyManager.update(deltaTime, core, player);
    }

    private void checkGameOver() {
        // Verifica se o jogador ou núcleo perdeu toda a vida
        if (player.getHealth() <= 0 || core.getHealth() <= 0) {
            gameLoop.stop();
            restartGame();  // Reinicia o jogo sem alterar a cena
        }
    }

    // Método de reinício do jogo sem alterar a cena ou o foco
    public void restartGame() {
        // Reinicializa o estado do jogo
        player.resetHealth();
        core.resetHealth();
        enemyManager.clearEnemies();

        // Reinicia o loop de jogo
        gameLoop.start();
    }
}
