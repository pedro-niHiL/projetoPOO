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
            Projectile projectile = new Projectile(getX() + getSize() / 2, getY() + getSize() / 2, dx, dy);
            projectiles.add(projectile);

            // Atualiza o tempo do último disparo
            lastShotTime = System.currentTimeMillis();
        }
    }

    public void updateProjectiles() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            p.update();
        }
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // Desenha o inimigo como antes
        super.draw(gc);

        // Desenha os projéteis disparados pelo inimigo
        for (Projectile p : projectiles) {
            p.draw(gc);
        }
    }
}
