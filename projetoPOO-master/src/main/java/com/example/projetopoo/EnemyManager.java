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
    private double lastTimeUpdate;

    public EnemyManager(double screenWidth, double screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.enemies = new ArrayList<>();
        this.random = new Random();
        this.spawnInterval = 3;
        this.spawnTimer = 0;
        this.lastTimeUpdate = 0;
    }

    public void update(double deltaTime, Core core, Player player,double gameTime) {
        spawnTimer += deltaTime;
        if (spawnTimer >= spawnInterval) {
            enemies.add(createRandomEnemy());
            spawnTimer = 0;
        }

        if ((int)gameTime/120 > lastTimeUpdate){
            if (spawnTimer != 1) {
                this.lastTimeUpdate = (int)gameTime / 120;
                this.spawnInterval -= 1 * lastTimeUpdate;
            }
        }

        List<Enemy> enemiesToRemove = new ArrayList<>();
        List<Projectile> projectilesToRemove = new ArrayList<>();
        List<PlayerProjectile> playerProjectilesToRemove = new ArrayList<>();

        for (Enemy enemy : enemies) {
            enemy.moveTowards(core.getX() + core.getSize() / 2, core.getY() + core.getSize() / 2);
            enemy.updateProjectiles();

            // Verifica colisão com o núcleo
            double dxCore = (core.getX() + core.getSize() / 2) - (enemy.getX() + enemy.getSize() / 2);
            double dyCore = (core.getY() + core.getSize() / 2) - (enemy.getY() + enemy.getSize() / 2);
            double distanceToCore = Math.sqrt(dxCore * dxCore + dyCore * dyCore);
            if (distanceToCore == 200){enemy.shoot(core.getX(), core.getY());} // Para inimigos atiradores
            double coreCollisionDistance = (core.getSize() / 2) + (enemy.getSize() / 2);

            if (distanceToCore < coreCollisionDistance) {
                core.takeDamage(10);
                enemiesToRemove.add(enemy);
                continue;
            }

            if (enemy.getProjectiles() != null) {
                for (Projectile p : enemy.getProjectiles()) {
                    double dxProjCore = (core.getX() + core.getSize() / 2) - (p.getX() + p.getSize() / 2);
                    double dyProjCore = (core.getY() + core.getSize() / 2) - (p.getY() + p.getSize() / 2);
                    double distanceToCore2 = Math.sqrt(dxProjCore * dxProjCore + dyProjCore * dyProjCore);
                    double projCollisionDistance = (core.getSize() / 2) + (p.getSize() / 2);
                    if (distanceToCore2 < projCollisionDistance) {
                        core.takeDamage(10);
                        projectilesToRemove.add(p);
                    }
                }
            }

            for (PlayerProjectile p : player.getProjectiles()) {
                double dxProjPlayerEnemy = (enemy.getX() + enemy.getSize() / 2) - (p.getX() + p.getSize() / 2);
                double dyProjPlayerEnemy = (enemy.getY() + enemy.getSize() / 2) - (p.getY() + p.getSize() / 2);
                double distanceToEnemy = Math.sqrt(dxProjPlayerEnemy * dxProjPlayerEnemy + dyProjPlayerEnemy * dyProjPlayerEnemy);
                double projPlayerCollisionDistance = (enemy.getSize() / 2) + (p.getSize() / 2);
                if (distanceToEnemy < projPlayerCollisionDistance) {
                    enemiesToRemove.add(enemy);
                    playerProjectilesToRemove.add(p);
                    player.addingPoints();
                    break;
                }
            }

            if (enemy.getProjectiles() != null) {
                for (Projectile p : enemy.getProjectiles()) {
                    double dxProjPlayer = (player.getX() + player.getSize() / 2) - (p.getX() + p.getSize() / 2);
                    double dyProjPlayer = (player.getY() + player.getSize() / 2) - (p.getY() + p.getSize() / 2);
                    double distanceToPlayer2 = Math.sqrt(dxProjPlayer * dxProjPlayer + dyProjPlayer * dyProjPlayer);
                    double projPlayerCollisionDistance = (player.getSize() / 2) + (p.getSize() / 2);
                    if (distanceToPlayer2 < projPlayerCollisionDistance) {
                        player.takeDamage(10);
                        projectilesToRemove.add(p);
                    }
                }
            }

            // Verifica colisão com o jogador
            double dxPlayer = (player.getX() + player.getSize() / 2) - (enemy.getX() + enemy.getSize() / 2);
            double dyPlayer = (player.getY() + player.getSize() / 2) - (enemy.getY() + enemy.getSize() / 2);
            double distanceToPlayer = Math.sqrt(dxPlayer * dxPlayer + dyPlayer * dyPlayer);
            double playerCollisionDistance = (player.getSize() / 2) + (enemy.getSize() / 2);

            if (distanceToPlayer < playerCollisionDistance) {
                player.takeDamage(10);
                enemiesToRemove.add(enemy);
            }
        }

        // Remove inimigos e projéteis após a iteração
        enemies.removeAll(enemiesToRemove);
        for (Enemy enemy : enemies) {
            if (enemy.getProjectiles() != null) {
                enemy.getProjectiles().removeAll(projectilesToRemove);
            }
        }
        player.getProjectiles().removeAll(playerProjectilesToRemove);
    }

    private Enemy createRandomEnemy() {
        final double adjustY = 50;
        double startX = 0, startY = 0;
        double enemySize = 64;  // Tamanho do inimigo

        int door = random.nextInt(4);

        switch (door) {
            case 0: // Porta de cima
                startX = screenWidth / 2 - enemySize / 2;
                startY = 0 - enemySize + adjustY*3;
                break;
            case 1: // Porta de baixo
                startX = screenWidth / 2 - enemySize / 2;
                startY = screenHeight;
                break;
            case 2: // Porta da esquerda
                startX = 0 - enemySize;
                startY = screenHeight / 2 - enemySize / 2 + adjustY;
                break;
            case 3: // Porta da direita
                startX = screenWidth;
                startY = screenHeight / 2 - enemySize / 2 + adjustY;
                break;
        }

        int type = random.nextInt(2);

        if (type == 0) {
            return new Enemy(startX, startY, enemySize);
        } else {
            return new EnemyShooter(startX, startY, enemySize);
        }
    }


    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void clearEnemies() {
        enemies.clear();
    }
}
