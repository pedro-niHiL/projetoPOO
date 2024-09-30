//package com.example.projetopoo;
//
//
//
//import javafx.scene.canvas.GraphicsContext;
//
//public class EnemyBoss extends EnemyShooter {
//    private double orbitRadiusX;  // Raio da órbita no eixo X (horizontal)
//    private double orbitRadiusY;  // Raio da órbita no eixo Y (vertical)
//    private double angle = 0;     // Ângulo de rotação
//    private double rotationSpeed = 0.02; // Velocidade de rotação (mais lenta que o inimigo comum)
//
//    public EnemyBoss(double centerX, double centerY, double orbitRadiusX, double orbitRadiusY, double size) {
//        super(centerX, centerY, Math.max(orbitRadiusX, orbitRadiusY), size); // Usa o maior raio para o EnemyShooter
//        this.orbitRadiusX = orbitRadiusX;
//        this.orbitRadiusY = orbitRadiusY;
//    }
//
//    @Override
//    public void moveTowards(double targetX, double targetY) {
//        // Movimento em órbita elíptica ao redor do ponto central
//        angle += rotationSpeed;
//        if (angle >= 2 * Math.PI) {
//            angle = 0; // Reinicia o ângulo ao completar uma rotação
//        }
//
//        // Atualiza a posição do EnemyBoss para seguir uma órbita elíptica
//        double newX = getCenterX() + orbitRadiusX * Math.cos(angle);
//        double newY = getCenterY() + orbitRadiusY * Math.sin(angle);
//        setX(newX);
//        setY(newY);
//    }
//
//    @Override
//    public void draw(GraphicsContext gc) {
//        // Desenha o EnemyBoss (pode usar uma imagem maior ou mais elaborada)
//        super.draw(gc);  // Reutiliza o método de desenho do EnemyShooter
//
//        // Aqui você pode adicionar animações ou efeitos especiais para o chefe
//        // Por exemplo, uma aura ou sombra especial para indicar que é um chefe
//    }
//
//    // O método update será herdado da classe EnemyShooter
//    // Ele inclui o comportamento de disparar projéteis para o centro
//}

//Falta terminar
