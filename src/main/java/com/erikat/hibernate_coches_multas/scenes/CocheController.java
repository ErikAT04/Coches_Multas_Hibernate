package com.erikat.hibernate_coches_multas.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CocheController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}