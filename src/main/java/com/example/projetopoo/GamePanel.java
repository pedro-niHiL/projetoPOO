package com.example.projetopoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //POSIÇÃO INICIAL DO JOGADOR
    private int playerX = 100;
    private int playerY = 100;
    private final int playerSize = 32;
    private boolean[] keys = new boolean[256]; // Para armazenar o estado das teclas
    private Timer timer;

    public GamePanel() {
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        timer = new Timer(20, this); // Atualiza a cada 20 ms
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(playerX, playerY, playerSize, playerSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int spd = 5;
        if (keys[KeyEvent.VK_UP]) playerY -= spd;
        if (keys[KeyEvent.VK_DOWN]) playerY += spd;
        if (keys[KeyEvent.VK_LEFT]) playerX -= spd;
        if (keys[KeyEvent.VK_RIGHT]) playerX += spd;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true; // Marca a tecla como pressionada
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false; // Marca a tecla como liberada
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Movimentação do Jogador");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}