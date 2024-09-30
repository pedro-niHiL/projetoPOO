package com.example.projetopoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        AnchorPane root = new AnchorPane();
        Canvas gameCanvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(gameCanvas);

        Scene scene = new Scene(root, screenWidth, screenHeight);

        primaryStage.setTitle("Nucleo Defense");
        primaryStage.setScene(scene);
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Inicializa o controlador principal do jogo
        GameController controller = new GameController(gameCanvas);
        controller.initialize(scene);

        // Garante que a cena tenha o foco
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
