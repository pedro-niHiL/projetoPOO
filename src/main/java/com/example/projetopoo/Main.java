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
    public void start(Stage primaryStage) {
        // Obtém as dimensões da tela
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Cria um Canvas e AnchorPane diretamente, sem FXML
        Canvas gameCanvas = new Canvas(screenWidth, screenHeight);
        AnchorPane root = new AnchorPane(gameCanvas);

        // Cria uma cena e ajusta as dimensões da janela
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setTitle("Movimentação do Jogador");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Inicializa o controlador manualmente
        GameController controller = new GameController(gameCanvas);
        controller.setupKeyHandling(scene);

        // Garante que o Canvas receba o foco para capturar os eventos de teclado
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
