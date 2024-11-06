package com.erikat.hibernate_coches_multas.util;

import com.erikat.hibernate_coches_multas.scenes.MultaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class FXUtils {
    public static Optional<ButtonType> makeAlert(Alert.AlertType alertType, String mensaje, String titulo){
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.setHeaderText(null);
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add(R.getScene("styles/mainAppStyle.css").toString());
        return alert.showAndWait(); //Devuelve el botón que pulse el usuario
    }
    public static boolean anyContentIsNull(List<TextField> texts, List<ComboBox<String>> cbList){
        boolean res = false;
        for (TextField tfield:texts){
            if (tfield.getText().isEmpty()){
                res = true;
            }
        }
        for (ComboBox<String> cb:cbList){
            if (cb.getValue()==null){
                res = true;
            }
        }
        return res;
    }
    public static MultaController newSceneInNewStage(String path, String title){
        MultaController controller = null;
        try {
            Stage st = new Stage();
            FXMLLoader loader = new FXMLLoader(R.getScene(path));
            Scene scene = new Scene(loader.load());
            st.setTitle(title);
            st.setScene(scene);
            st.initModality(Modality.APPLICATION_MODAL); //Le da prioridad y bloquea los eventos de las demás ventanas hasta que esta desaparezca
            st.show();
            controller = loader.getController();
        }catch (Exception e){
            FXUtils.makeAlert(Alert.AlertType.ERROR, "Ha habido un error cargando la vista.", "Error de ruta");
        }
        return controller;
    }
}
