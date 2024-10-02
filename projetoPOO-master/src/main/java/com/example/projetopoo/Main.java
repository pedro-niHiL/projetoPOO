package com.example.projetopoo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
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

            GameController controller = new GameController(gameCanvas);
            controller.initialize(scene);

            scene.getRoot().requestFocus();

            LOGGER.info("Aplicação iniciada com sucesso");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao iniciar a aplicação", e);
        }
    }

    public static void main(String[] args) {
        setupLogger();
        launch(args);
    }

    private static void setupLogger() {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        rootLogger.addHandler(handler);
    }
}