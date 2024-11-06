package com.erikat.hibernate_coches_multas;

import com.erikat.hibernate_coches_multas.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(R.getScene("Coches_View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Coches");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}