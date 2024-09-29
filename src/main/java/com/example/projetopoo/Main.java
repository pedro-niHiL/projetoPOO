package com.example.projetopoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

public class Main extends Application {

    private static final double SCENE_WIDTH = 800;
    private static final double SCENE_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Define o tamanho fixo do Canvas
        AnchorPane root = new AnchorPane();
        Canvas gameCanvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
        root.getChildren().add(gameCanvas);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle("Nucleo Defense");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);  // Desabilita o redimensionamento
        primaryStage.show();

        // Inicializa o controlador principal
        GameController controller = new GameController(gameCanvas, primaryStage);
        controller.initialize(scene);

        // Garante que a cena tenha o foco
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
