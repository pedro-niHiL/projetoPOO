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

    public void update(double deltaTime, Core core) {
        spawnTimer += deltaTime;
        if (spawnTimer >= spawnInterval) {
            enemies.add(createRandomEnemy());
            spawnTimer = 0;
        }

        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            enemy.moveTowards(core.getX() + core.getSize() / 2, core.getY() + core.getSize() / 2);

            if (core.isColliding(enemy.getX(), enemy.getY(), enemy.getSize())) {
                core.takeDamage(10);
                it.remove();
            }
        }
    }

    private Enemy createRandomEnemy() {
        double startX = 0, startY = 0;
        int door = random.nextInt(4);

        switch (door) {
            case 0: startX = screenWidth / 2; startY = 0; break;
            case 1: startX = screenWidth / 2; startY = screenHeight; break;
            case 2: startX = 0; startY = screenHeight / 2; break;
            case 3: startX = screenWidth; startY = screenHeight / 2; break;
        }

        return new Enemy(startX, startY);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
