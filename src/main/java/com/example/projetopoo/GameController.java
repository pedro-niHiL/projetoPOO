package com.example.projetopoo;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameController {

    private Canvas gameCanvas;
    private Player player;
    private Core core;
    private GameLoop gameLoop;
    private List<Enemy> enemies;
    private int fps = 60;
    private Random random = new Random();
    private double spawnInterval = 2;
    private double spawnTimer = 0;

    // Construtor para inicializar com o Canvas
    public GameController(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        initialize();
    }

    public void initialize() {
        double canvasWidth = gameCanvas.getWidth();
        double canvasHeight = gameCanvas.getHeight();

        // Inicializa o jogador e o núcleo
        player = new Player(100, 100, canvasWidth, canvasHeight);
        core = new Core(canvasWidth, canvasHeight);

        enemies = new ArrayList<>();
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        // Define o loop de atualização com o FPS configurado
        gameLoop = new GameLoop(() -> {
            update(1.0 / fps); // Atualiza com base no deltaTime (tempo entre frames)
            render(gc);
        }, fps);

        // Começa o game loop
        gameLoop.start();
    }

    public void setupKeyHandling(Scene scene) {
        // Captura eventos de teclado diretamente na cena para movimentar o jogador
        scene.setOnKeyPressed(event -> player.setKeyPressed(event.getCode().getCode()));
        scene.setOnKeyReleased(event -> player.setKeyReleased(event.getCode().getCode()));
    }

    private void update(double deltaTime) {
        // Atualiza a posição do jogador
        player.update(deltaTime);

        // Atualiza o spawn de inimigos
        spawnTimer += deltaTime;
        if (spawnTimer >= spawnInterval) {
            enemies.add(createRandomEnemy(gameCanvas.getWidth(), gameCanvas.getHeight()));
            spawnTimer = 0;
        }

        // Atualiza a posição dos inimigos para que se movam em direção ao núcleo
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            enemy.moveTowards(core.getX() + core.getSize() / 2, core.getY() + core.getSize() / 2);

            // Verifica colisão entre o inimigo e o núcleo
            if (core.isColliding(enemy.getX(), enemy.getY(), enemy.getSize())) {
                core.takeDamage(10);  // Dano ao núcleo quando um inimigo colide
                System.out.println("O núcleo foi atingido!");

                // Remove o inimigo da lista após a colisão
                it.remove();
            }
        }
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Desenha o jogador, o núcleo e todos os inimigos
        player.draw(gc);
        core.draw(gc);
        for (Enemy enemy : enemies) {
            enemy.draw(gc);
        }
    }

    // Cria um inimigo em uma das quatro portas (norte, sul, leste, oeste)
    private Enemy createRandomEnemy(double screenWidth, double screenHeight) {
        double startX = 0, startY = 0;

        int door = random.nextInt(4);  // Escolhe uma das quatro portas

        switch (door) {
            case 0: // Norte (centro do topo)
                startX = screenWidth / 2;
                startY = 0;  // Topo da tela
                break;
            case 1: // Sul (centro da parte inferior)
                startX = screenWidth / 2;
                startY = screenHeight;  // Fundo da tela
                break;
            case 2: // Oeste (centro da esquerda)
                startX = 0;  // Lado esquerdo
                startY = screenHeight / 2;
                break;
            case 3: // Leste (centro da direita)
                startX = screenWidth;
                startY = screenHeight / 2;
                break;
        }

        return new Enemy(startX, startY);  // Retorna um novo inimigo da "porta"
    }
}
