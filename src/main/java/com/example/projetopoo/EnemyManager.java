package com.example.projetopoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EnemyManager {
    private List<Enemy> enemies;
    private Random random;
    private double spawnInterval;
    private double spawnTimer;
    private double screenWidth;
    private double screenHeight;

    public EnemyManager(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.enemies = new ArrayList<>();
        this.random = new Random();
        this.spawnInterval = 5;
        this.spawnTimer = 0;
    }

    public void update(double deltaTime, Core core, Player player) {
        spawnTimer += deltaTime;
        if (spawnTimer >= spawnInterval) {
            enemies.add(createRandomEnemy());
            spawnTimer = 0;
        }

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            // Movimenta o inimigo em direção ao núcleo
            enemy.moveTowards(core.getX() + core.getSize() / 2, core.getY() + core.getSize() / 2);

            // Verifica colisão com o núcleo
            double dxCore = (core.getX() + core.getSize() / 2) - (enemy.getX() + enemy.getSize() / 2);
            double dyCore = (core.getY() + core.getSize() / 2) - (enemy.getY() + enemy.getSize() / 2);
            double distanceToCore = Math.sqrt(dxCore * dxCore + dyCore * dyCore);
            double coreCollisionDistance = (core.getSize() / 2) + (enemy.getSize() / 2);

            if (distanceToCore < coreCollisionDistance) {
                core.takeDamage(10);  // Núcleo sofre dano
                it.remove();  // Remove o inimigo após a colisão com o núcleo
                continue; // Passa para o próximo inimigo
            }

            // Verifica colisão com o jogador
            double dxPlayer = (player.getX() + player.getSize() / 2) - (enemy.getX() + enemy.getSize() / 2);
            double dyPlayer = (player.getY() + player.getSize() / 2) - (enemy.getY() + enemy.getSize() / 2);
            double distanceToPlayer = Math.sqrt(dxPlayer * dxPlayer + dyPlayer * dyPlayer);
            double playerCollisionDistance = (player.getSize() / 2) + (enemy.getSize() / 2);

            if (distanceToPlayer < playerCollisionDistance) {
                player.takeDamage(10);  // Jogador perde 10 de vida
                it.remove();  // Remove o inimigo após a colisão com o jogador
            }
        }
    }

    private Enemy createRandomEnemy() {
        double startX = 0, startY = 0;
        double enemySize = 64;  // Tamanho do inimigo

        int door = random.nextInt(4);

        switch (door) {
            case 0: // Porta de cima
                startX = screenWidth / 2 - enemySize / 2;
                startY = 0 - enemySize / 2;
                break;
            case 1: // Porta de baixo
                startX = screenWidth / 2 - enemySize / 2;
                startY = screenHeight - enemySize / 2;
                break;
            case 2: // Porta da esquerda
                startX = 0 - enemySize / 2;
                startY = screenHeight / 2 - enemySize / 2;
                break;
            case 3: // Porta da direita
                startX = screenWidth - enemySize / 2;
                startY = screenHeight / 2 - enemySize / 2;
                break;
        }

        return new Enemy(startX, startY, enemySize);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
