package com.example.projetopoo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemyShooter extends Enemy {
    private ArrayList<Projectile> projectiles;
    private long lastShotTime = 0;
    private long shotCooldown = 2000; // Cooldown de 2 segundos para atirar

    public EnemyShooter(double startX, double startY, double size) {
        super(startX, startY, size);
        projectiles = new ArrayList<>();
        this.spriteSheet = new Image(getClass().getResource("spriteSheetEnemy.png").toString());
    }

    @Override
    public void moveTowards(double targetX, double targetY) {
        double dx = targetX - (x + size / 2);
        double dy = targetY - (y + size / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance == 200) speed = 0;

        if (distance > 0) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;

            // Atualiza a direção do inimigo
            facingLeft = dx < 0;
        }

        // Atualiza a animação
        animationTimer++;
        if (animationTimer >= animationDelay) {
            currentFrame = (currentFrame + 1) % frameCount;
            animationTimer = 0;
        }
    }

    @Override
    public void shoot(double coreX, double coreY) {
        if (System.currentTimeMillis() - lastShotTime >= shotCooldown) {
            // Cria um novo projétil em direção ao core
            double dx = coreX - (getX() + getSize() / 2);
            double dy = coreY - (getY() + getSize() / 2);
            double magnitude = Math.sqrt(dx * dx + dy * dy);

            // Normaliza a direção
            dx /= magnitude;
            dy /= magnitude;

            // Cria o projétil
            Projectile projectile = new Projectile(getX() + getSize() / 2, getY() + getSize() / 2, dx, dy);
            projectiles.add(projectile);

            // Atualiza o tempo do último disparo
            lastShotTime = System.currentTimeMillis();
        }
    }

    @Override
    public void updateProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            p.update();
        }
    }

    @Override
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Desenha o inimigo
        super.draw(gc);

        // Desenha os projéteis disparados pelo inimigo
        for (Projectile p : projectiles) {
            p.draw(gc);
        }
    }
}