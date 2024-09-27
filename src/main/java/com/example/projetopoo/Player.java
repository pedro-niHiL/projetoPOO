package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {

    private double x;
    private double y;
    private final double size = 64; // Tamanho do jogador
    private final double speed = 2;
    private boolean[] keys = new boolean[256];
    private double health; // Vida do jogador

    private Image spriteSheet;
    private int currentFrame = 0;
    private int frameCount = 4; // Número de quadros da animação
    private int idleFrame = 0; // Frame quando parado
    private boolean isMoving = false;
    private int spriteRow = 0;

    private int animationDelay = 2; // Velocidade da animação
    private int animationTimer = 0;

    private double screenWidth;
    private double screenHeight;

    public Player(double startX, double startY, double screenWidth, double screenHeight) {
        this.x = startX;
        this.y = startY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.health = 100; // Vida inicial

        this.spriteSheet = new Image(getClass().getResource("playersprite.png").toString());
    }

    public void update(double deltaTime, Core core) {
        isMoving = false;

        double nextX = x;
        double nextY = y;

        // Verifica teclas pressionadas e calcula a nova posição
        if (keys['W']) {
            nextY -= speed;
            isMoving = true;
        }
        if (keys['S']) {
            nextY += speed;
            isMoving = true;
        }
        if (keys['A']) {
            nextX -= speed;
            isMoving = true;
        }
        if (keys['D']) {
            nextX += speed;
            isMoving = true;
        }

        // Limita o movimento às bordas da tela
        if (nextX < 0) nextX = 0;
        if (nextX + size > screenWidth) nextX = screenWidth - size;
        if (nextY < 0) nextY = 0;
        if (nextY + size > screenHeight) nextY = screenHeight - size;

        // Verifica colisão com o núcleo (impede o jogador de atravessar o núcleo)
        if (!isCollidingWithCore(nextX, nextY, core)) {
            x = nextX;
            y = nextY;
        }

        // Atualiza a animação
        if (isMoving) {
            spriteRow = 1; // Linha de movimento
            animationTimer++;
            if (animationTimer >= animationDelay) {
                currentFrame = (currentFrame + 1) % frameCount;
                animationTimer = 0;
            }
        } else {
            spriteRow = 0; // Linha idle
            currentFrame = idleFrame;
        }
    }

    private boolean isCollidingWithCore(double nextX, double nextY, Core core) {
        double coreX = core.getX();
        double coreY = core.getY();
        double coreSize = core.getSize();

        // Verifica se o jogador está colidindo com o núcleo
        boolean collidingX = nextX + size > coreX && nextX < coreX + coreSize;
        boolean collidingY = nextY + size > coreY && nextY < coreY + coreSize;

        return collidingX && collidingY;
    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, screenWidth, screenHeight);
        gc.drawImage(spriteSheet, currentFrame * size, spriteRow * size, size, size, x, y, size, size);
    }

    public void takeDamage(double damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public double getHealth() {
        return health;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }

    public void setKeyPressed(int keyCode) {
        keys[keyCode] = true;
    }

    public void setKeyReleased(int keyCode) {
        keys[keyCode] = false;
    }
}
