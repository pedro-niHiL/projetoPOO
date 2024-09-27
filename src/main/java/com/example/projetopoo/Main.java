package com.example.projetopoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene scene = new Scene(loader.load());

        // Obtém as dimensões da tela
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Define o tamanho da janela com base na resolução do monitor
        primaryStage.setTitle("Movimentação do Jogador");
        primaryStage.setScene(scene);
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Acessa o controlador e registra o tratamento de eventos de teclado
        GameController controller = loader.getController();
        controller.setupKeyHandling(scene);

        // Redimensiona o Canvas para o tamanho da tela
        controller.resizeCanvas(screenWidth, screenHeight);

        // Garante que a cena tenha o foco
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
