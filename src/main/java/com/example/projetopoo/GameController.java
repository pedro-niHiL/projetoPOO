package com.example.projetopoo;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameController {

    @FXML
    private Canvas gameCanvas;

    private Player player;
    private GameLoop gameLoop;
    private int fps = 60;  // Definindo o FPS aqui

    @FXML
    public void initialize() {
        player = new Player(100, 100);
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        // Define o loop de atualização com o FPS configurado
        gameLoop = new GameLoop(() -> {
            player.update();
            render(gc);
        }, fps);

        // Começa o game loop
        gameLoop.start();

        // Garante que o canvas tenha o foco para receber eventos de teclado
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
    }

    public void setupKeyHandling(Scene scene) {
        // Captura eventos de teclado diretamente na cena
        scene.setOnKeyPressed(event -> player.setKeyPressed(event.getCode().getCode()));
        scene.setOnKeyReleased(event -> player.setKeyReleased(event.getCode().getCode()));
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        player.draw(gc);
    }
}
