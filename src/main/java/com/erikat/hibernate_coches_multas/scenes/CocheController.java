package com.erikat.hibernate_coches_multas.scenes;

import com.erikat.hibernate_coches_multas.dao.CocheDAO;
import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.model.Multa;
import com.erikat.hibernate_coches_multas.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CocheController implements Initializable {

    private CocheDAO cocheDAO;

    @FXML
    private TableView<Coche> cochesTView;

    @FXML
    private TableColumn<Coche, Integer> idTCol;

    @FXML
    private TableColumn<Coche, String> marcaTCol;

    @FXML
    private TextField marcaTField;

    @FXML
    private TableColumn<Coche, String>  matrTCol;

    @FXML
    private TextField matrTField;

    @FXML
    private TableColumn<Coche, String>  modeloTCol;

    @FXML
    private TextField modeloTField;

    @FXML
    private Button multaBtt;

    @FXML
    private ComboBox<String> tipoCBox;

    @FXML
    private TableColumn<Coche, String> tipoTCol;

    List<TextField> textFields;

    /*--------------------------------------------------------------------------------------------*/

    @FXML
    void onActualizarClick(ActionEvent event) { //Función que salta al dar a "Actualizar"
        /*
        Requisitos para que se actualice:
            1. Todos los campos estén rellenos
            2. Existe la matrícula en la BBDD
            3. Los datos del coche no sean exactamente los mismos
         */
        if (FXUtils.anyContentIsNull(textFields, List.of(tipoCBox))){ //Si cualquier campo es nulo
            FXUtils.makeAlert(Alert.AlertType.ERROR, "No puede haber campos vacíos", "Error de formulario");
        } else {
            Coche cocheMatr = cocheDAO.buscarCoche(matrTField.getText());
            if (cocheMatr!=null){
                Coche nuevoCoche = new Coche(matrTField.getText(), marcaTField.getText(), modeloTField.getText(), tipoCBox.getValue());
                if (nuevoCoche.equals(cocheMatr)){
                    FXUtils.makeAlert(Alert.AlertType.ERROR, "¿Por qué querrías actualizar un coche si no ha cambiado nada?", "Error de actualización");
                } else {
                    nuevoCoche.setId(cocheMatr.getId()); //Le pongo al coche el ID del anterior (Ya que no tiene ninguna forma de llegar a este)
                    nuevoCoche.setMultas(cocheMatr.getMultas()); //Le pongo la lista de multas
                    if (cocheDAO.modificarCoche(nuevoCoche)){
                        FXUtils.makeAlert(Alert.AlertType.INFORMATION, "Coche actualizado correctamente", "Actualización");
                        tableRefresh();
                    } else {
                        FXUtils.makeAlert(Alert.AlertType.ERROR, "Error en la base de datos", "Error de BD");
                    }
                }
            } else {
                FXUtils.makeAlert(Alert.AlertType.ERROR, "No hemos encontrado ese coche en la base de datos",  "Error de actualización");
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/

    @FXML
    void onBorrarClick(ActionEvent event) { //Función que salta al dar a "Borrar"
        /*
        Requisitos para que se borre:
            1. El campo de la matrícula tiene información
            2. Existe esa matrícula en la BBDD
         */
        if (matrTField.getText().isEmpty()){ //Si no encuentra texto:
            FXUtils.makeAlert(Alert.AlertType.ERROR, "El campo de matrícula no puede estar vacío", "Error de borrado");
        } else {
            Coche coche = cocheDAO.buscarCoche(matrTField.getText());
            if (coche==null){
                FXUtils.makeAlert(Alert.AlertType.ERROR, "No se ha encontrado el coche que buscas", "Error de borrado");
            } else {
                Optional<ButtonType> botonPulsado = FXUtils.makeAlert(Alert.AlertType.CONFIRMATION, "¿Quieres borrar este coche?", "Borrar");
                if(botonPulsado.isPresent() && botonPulsado.get() == ButtonType.OK){
                    if (cocheDAO.borrarCoche(coche)){
                        FXUtils.makeAlert(Alert.AlertType.INFORMATION, "Coche borrado correctamente", "Coche borrado");
                        flush(); //Borra los datos
                        tableRefresh(); //Refresca la tabla
                    } else {
                        FXUtils.makeAlert(Alert.AlertType.ERROR, "Error insesperado, ha ocurrido algo con la base de datos mientras se borraba", "Error inesperado");
                    }
                }
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/

    @FXML
    void onContentClicked(MouseEvent event) { //Función que salta al pulsar un elemento de la tabla
        Coche coche = cochesTView.getSelectionModel().getSelectedItem();
        if (coche!=null){
            matrTField.setText(coche.getMatricula());
            marcaTField.setText(coche.getMarca());
            modeloTField.setText(coche.getModelo());
            tipoCBox.setValue(coche.getTipo());
        }
    }

    /*--------------------------------------------------------------------------------------------*/

    @FXML
    void onInsertarClick(ActionEvent event) { //Función que salta al dar a "Insertar"
        /*
        Requisitos para que se inserte:
            1. Todos los campos estén rellenos
            2. No exista esa matrícula en la BBDD
         */
        if (FXUtils.anyContentIsNull(textFields, List.of(tipoCBox))){ //Si cualquier campo es nulo
            FXUtils.makeAlert(Alert.AlertType.ERROR, "No puede haber campos vacíos", "Error de formulario");
        } else {
            Coche cocheMatr = cocheDAO.buscarCoche(matrTField.getText());
            if (cocheMatr==null){
                Coche nuevoCoche = new Coche(matrTField.getText(), marcaTField.getText(), modeloTField.getText(), tipoCBox.getValue());
                if (cocheDAO.insertarCoche(nuevoCoche)){
                    FXUtils.makeAlert(Alert.AlertType.INFORMATION, "Coche insertado correctamente", "Inserción de coche");
                    tableRefresh();
                } else {
                    FXUtils.makeAlert(Alert.AlertType.ERROR, "Error insesperado, ha ocurrido algo con la base de datos mientras se borraba", "Error inesperado");
                }
            } else {
                FXUtils.makeAlert(Alert.AlertType.ERROR, "Se ha encontrado un coche con esa matrícula en la base de datos", "Error de inserción");
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/

    @FXML
    void onLimpiarClick(ActionEvent event) { //Función que salta al dar a "Limpiar"
        flush(); //Llama a la función de limpieza de formulario
    }

    /*--------------------------------------------------------------------------------------------*/

    @FXML
    void onMultasClick(ActionEvent event) {
        if (matrTField.getText().isEmpty()){
            FXUtils.makeAlert(Alert.AlertType.ERROR, "Rellena el campo de matrícula o selecciona un coche de la tabla", "Error");
        } else {
            Coche coche = cocheDAO.buscarCoche(matrTField.getText());
            if (coche==null){
                FXUtils.makeAlert(Alert.AlertType.ERROR, "No se ha encontrado el coche", "Error de coche");
            } else {
                MultaController controller = FXUtils.newSceneInNewStage("Multas_View.fxml", "Multas");
                controller.setItems(coche);
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/

    void flush(){ //Función de borrado de elementos del formulario
        marcaTField.setText("");
        matrTField.setText("");
        modeloTField.setText("");
        tipoCBox.setValue(null);
    }

    /*--------------------------------------------------------------------------------------------*/

    void tableRefresh() { //Función de carga de datos en la tabla
        List<Coche> coches = cocheDAO.listarCoches();
        ObservableList<Coche> listaCoches = FXCollections.observableArrayList(coches);
        cochesTView.setItems(listaCoches);
    }

    /*--------------------------------------------------------------------------------------------*/

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Inicializador de elementos
        cocheDAO = new CocheDAO();

        //Tabla:
        idTCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoTCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        matrTCol.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        marcaTCol.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloTCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableRefresh();

        //ComboBox:
        List<String> listaTipos = List.of("SUV", "Monovolumen", "Familiar", "Coupé", "Descapotable");
        tipoCBox.getItems().addAll(listaTipos);

        textFields = List.of(marcaTField, matrTField, modeloTField); //Guardo todos los textField en una lista que usaré más tarde para hacer verificaciones
    }

}
