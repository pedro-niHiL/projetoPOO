package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {

    private double x;
    private double y;
    private final double size = 64; // Tamanho do jogador
    private final double speed = 2;
    private boolean[] keys = new boolean[256];
    private double health; // Vida do jogador
    private ArrayList<PlayerProjectile> playerProjectiles;

    private Image spriteSheet;
    private int currentFrame = 0;
    private int frameCount = 6; // Número de quadros da animação
    private int idleFrame = 0; // Frame quando parado
    private boolean isMoving = false;
    private int spriteRow = 0; // Linha do spritesheet (para diferentes direções)
    private int direction = 0; // 0 = Down, 1 = Right, 2 = Up
    private boolean facingLeft = false; // Flag para saber se está virado para a esquerda
    private long lastShotTime = 0;
    private long shotCooldown = 1000;

    private int animationDelay = 10; // Ajuste de tempo da animação
    private int animationTimer = 0;

    private double screenWidth;
    private double screenHeight;
    private double mouseX,mouseY;

    public Player(double startX, double startY, double screenWidth, double screenHeight) {
        this.x = startX;
        this.y = startY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.health = 100; // Vida inicial
        this.playerProjectiles = new ArrayList<>();
        this.spriteSheet = new Image(getClass().getResource("spriteSheetPlayer.png").toString()); // Novo spritesheet
    }

    public void update(double deltaTime, Core core) {
        isMoving = false;
        double nextX = x;
        double nextY = y;

        shoot(mouseX,mouseY);
        updateProjectiles();

        // Verifica teclas pressionadas e calcula a nova posição e direção
        if (keys['W']) {
            nextY -= speed;
            direction = 2; // Up
            isMoving = true;
        }
        if (keys['S']) {
            nextY += speed;
            direction = 0; // Down
            isMoving = true;
        }
        if (keys['A']) {
            nextX -= speed;
            direction = 1; // Left/Right (compartilha linha)
            facingLeft = true; // Virado para a esquerda
            isMoving = true;
        }
        if (keys['D']) {
            nextX += speed;
            direction = 1; // Left/Right
            facingLeft = false; // Virado para a direita
            isMoving = true;
        }



        // Limita o movimento às bordas da tela
        if (nextX < 0) nextX = 0;
        if (nextX + size > screenWidth) nextX = screenWidth - size;
        if (nextY < 0) nextY = 0;
        if (nextY + size > screenHeight) nextY = screenHeight - size;

        // Verifica colisão com o núcleo
        if (!isCollidingWithCore(nextX, nextY, core)) {
            x = nextX;
            y = nextY;
        }

        // Atualiza a animação
        if (isMoving) {
            spriteRow = 3 + direction; // Linhas 3, 4, 5 para movimentação
            animationTimer++;
            if (animationTimer >= animationDelay) {
                currentFrame = (currentFrame + 1) % frameCount;
                animationTimer = 0;
            }
        } else {
            spriteRow = direction; // Linhas 0, 1, 2 para idle
            currentFrame = idleFrame;
        }
    }

    private boolean isCollidingWithCore(double nextX, double nextY, Core core) {
        double coreX = core.getX();
        double coreY = core.getY();
        double coreSize = core.getSize();

        boolean collidingX = nextX + size > coreX && nextX < coreX + coreSize;
        boolean collidingY = nextY + size > coreY && nextY < coreY + coreSize;

        return collidingX && collidingY;
    }

    public void draw(GraphicsContext gc) {
        // Se o personagem estiver virado para a esquerda, desenha invertido
        if (facingLeft) {
            gc.save(); // Salva o estado atual do GraphicsContext
            gc.translate(x + size, y); // Move para a posição do personagem
            gc.scale(-1, 1); // Inverte a escala no eixo X para espelhar a imagem
            gc.drawImage(spriteSheet, currentFrame * size, spriteRow * size, size, size, 0, 0, size, size);
            gc.restore(); // Restaura o estado original do GraphicsContext
        } else {
            // Desenha normalmente para a direita
            gc.drawImage(spriteSheet, currentFrame * size, spriteRow * size, size, size, x, y, size, size);
        }

        for (PlayerProjectile p : playerProjectiles) {
            p.draw(gc);
        }
    }




    public void shoot(double coreX,double coreY) {
        if (System.currentTimeMillis() - lastShotTime >= shotCooldown) {
            // Cria um novo projétil em direção ao jogador
            double dx =  coreX - (getX() + getSize() / 2);
            double dy =  coreY - (getY() + getSize() / 2);
            double magnitude = Math.sqrt(dx * dx + dy * dy);

            // Normaliza a direção
            dx /= magnitude;
            dy /= magnitude;

            // Cria o projétil
            PlayerProjectile projectile = new PlayerProjectile(getX() + getSize() / 2, getY() + getSize() / 2, dx, dy);
            playerProjectiles.add(projectile);

            // Atualiza o tempo do último disparo
            lastShotTime = System.currentTimeMillis();
        }
    }

    public void updateProjectiles() {
        Iterator<PlayerProjectile> iterator = playerProjectiles.iterator();
        while (iterator.hasNext()) {
            PlayerProjectile p = iterator.next();
            p.update();
        }
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



    public void resetHealth() {
        this.health = 100;
    }

    public ArrayList<PlayerProjectile> getProjectiles() {
        return playerProjectiles;
    }


    public void setMousePosition(double x, double y) {
        this.mouseX = x;
        this.mouseY = y;
    }
}
