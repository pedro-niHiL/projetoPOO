package com.example.projetopoo;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class GameController {

    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private GameLoop gameLoop;
    private PlayerController playerController;
    private EnemyManager enemyManager;
    private RenderEngine renderEngine;


    public GameController(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void initialize(Scene scene) {
        double canvasWidth = gameCanvas.getWidth();
        double canvasHeight = gameCanvas.getHeight();

        player = new Player(100, 100, canvasWidth, canvasHeight);
        core = new Core(canvasWidth, canvasHeight);
        enemyManager = new EnemyManager(canvasWidth, canvasHeight);
        renderEngine = new RenderEngine(gameCanvas, player, core, enemyManager);

        // Setup para controle do jogador
        playerController = new PlayerController(player);
        playerController.setupKeyHandling(scene);


        // Inicializa o loop do jogo
        gameLoop = new GameLoop(() -> {
            update(1.0 / 60); // FPS 60
            renderEngine.render();
        });
        gameLoop.start();
    }

    private void  update(double deltaTime) {
        player.update(deltaTime, core);  // Agora o jogador verifica a colisão com o núcleo
        enemyManager.update(deltaTime, core, player);  // Atualiza inimigos com base no núcleo e no jogado
    }
}
