package com.example.projetopoo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Movimentação do Jogador");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Acessa o controlador e registra o tratamento de eventos de teclado
        GameController controller = loader.getController();
        controller.setupKeyHandling(scene);

        // Garante que a cena tenha o foco
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
