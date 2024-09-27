package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {

    private double x;
    private double y;
    private final double size = 64; // Tamanho de cada quadro do sprite
    private final double speed = 5;
    private boolean[] keys = new boolean[256];

    private Image spriteSheet;
    private int currentFrame = 0;
    private int frameCount = 4; // Número de quadros em cada linha da animação
    private int idleFrame = 0; // Frame inicial para idle
    private boolean isMoving = false; // Controla se o jogador está se movendo
    private int spriteRow = 0; // Linha do sprite (0 = parado, 1 = andando, etc.)

    private int animationDelay = 5; // Controla a velocidade da animação
    private int animationTimer = 0;

    private double screenWidth; // Largura da tela
    private double screenHeight; // Altura da tela

    public Player(double startX, double startY, double screenWidth, double screenHeight) {
        this.x = startX;
        this.y = startY;

        // Dimensões da tela
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Carrega o spritesheet do jogador
        this.spriteSheet = new Image(getClass().getResource("playersprite.png").toString());
    }

    public void updateScreenSize(double newWidth, double newHeight) {
        this.screenWidth = newWidth;
        this.screenHeight = newHeight;
    }


    public void update(double deltaTime) {
        isMoving = false; // Reset no estado de movimento

        // Verifica teclas pressionadas e movimenta o jogador
        if (keys['W']) {
            y -= speed;
            isMoving = true;
        }
        if (keys['S']) {
            y += speed;
            isMoving = true;
        }
        if (keys['A']) {
            x -= speed;
            isMoving = true;
        }
        if (keys['D']) {
            x += speed;
            isMoving = true;
        }

        // Limita o movimento às bordas da tela
        if (x < 0) {
            x = 0;
        }
        if (x + size > screenWidth) {
            x = screenWidth - size;
        }
        if (y < 0) {
            y = 0;
        }
        // Ajuste no limite inferior, considerando a altura do sprite
        if (y + size > screenHeight) {
            y = screenHeight - size;
        }

        // Atualiza o estado da animação
        if (isMoving) {
            spriteRow = 1; // Linha 1 para movimento
            animationTimer++;
            if (animationTimer >= animationDelay) {
                currentFrame = (currentFrame + 1) % frameCount;
                animationTimer = 0;
            }
        } else {
            spriteRow = 0; // Linha 0 para parado (idle)
            currentFrame = idleFrame;
        }
    }


    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, screenWidth, screenHeight); // Limpa a área do canvas

        // Calcula a posição correta no spritesheet para o quadro atual
        gc.drawImage(spriteSheet,
                currentFrame * size, spriteRow * size, size, size,  // Fonte: (x, y, largura, altura)
                x, y, size, size  // Destino: (x, y, largura, altura)
        );
    }

    public void setKeyPressed(int keyCode) {
        keys[keyCode] = true;
    }

    public void setKeyReleased(int keyCode) {
        keys[keyCode] = false;
    }
}
