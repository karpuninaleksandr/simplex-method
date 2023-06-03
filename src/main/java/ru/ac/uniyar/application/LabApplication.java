package ru.ac.uniyar.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LabApplication extends Application {
    public Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("/main-page.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        scene = new Scene(loader.load(), 1300, 800);
        stage.setTitle("Simplex Method Lab");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
