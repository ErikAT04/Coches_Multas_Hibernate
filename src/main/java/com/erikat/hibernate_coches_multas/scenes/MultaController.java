package com.erikat.hibernate_coches_multas.scenes;

import com.erikat.hibernate_coches_multas.dao.MultaDAO;
import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.model.Multa;
import com.erikat.hibernate_coches_multas.util.FXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MultaController {

    private Coche cocheMultado;

    private MultaDAO multaDAO;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Multa, LocalDate> fechaTCol;

    @FXML
    private TableColumn<Multa, Integer> idTCol;

    @FXML
    private TextField idTField;

    @FXML
    private TextField matrTField;

    @FXML
    private TableView<Multa> multaTView;

    @FXML
    private TableColumn<Multa, Double> precioTCol;

    @FXML
    private TextField precioTField;

    @FXML
    void onActualizarClick(ActionEvent event) {
        if (idTField.getText().isEmpty() || precioTField.getText().isEmpty() || datePicker.getValue()==null){
            FXUtils.makeAlert(Alert.AlertType.ERROR, "Los campos deben estar rellenos", "Error de campos");
        } else {
            try {
                int id = Integer.parseInt(idTField.getText());
                double precio = Double.parseDouble(precioTField.getText());
                LocalDate fecha = datePicker.getValue();

                Multa multa = multaDAO.buscarMulta(id);
                if(multa!=null){
                    Multa multaNueva = new Multa(precio, fecha, cocheMultado);
                    if (multaDAO.modificarMulta(multaNueva)) {
                        FXUtils.makeAlert(Alert.AlertType.INFORMATION, "Multa actualizada correctamente", "Actualización");
                        tableRefresh();
                    } else {
                        FXUtils.makeAlert(Alert.AlertType.ERROR, "Ha habido un problema con la base de datos", "Error de actualización");
                    }
                }else{
                    FXUtils.makeAlert(Alert.AlertType.ERROR, "No existe la multa en la base de datos", "Error de actualización");
                }
            }catch (NumberFormatException e){
                FXUtils.makeAlert(Alert.AlertType.ERROR, "El identificador y el precio deben ser números", "Error de actualización");
            }
        }
    }

    @FXML
    void onBorrarClick(ActionEvent event) {
        if (idTField.getText().isEmpty()){
            FXUtils.makeAlert(Alert.AlertType.ERROR, "Debe haber información en el campo de id", "Error de campos");
        } else {
            try {
                int id = Integer.parseInt(idTField.getText());
                Multa multa = multaDAO.buscarMulta(id);
                if (multa!=null){
                    Optional<ButtonType> botonPulsado = FXUtils.makeAlert(Alert.AlertType.CONFIRMATION, "¿Quieres borrar esta multa?", "Borrar");
                    if(botonPulsado.isPresent() && botonPulsado.get() == ButtonType.OK){
                        if (multaDAO.borrarMulta(multa)){
                            FXUtils.makeAlert(Alert.AlertType.INFORMATION, "Coche borrado correctamente", "Multa borrada");
                            flush(); //Borra los datos
                            tableRefresh(); //Refresca la tabla
                        } else {
                            FXUtils.makeAlert(Alert.AlertType.ERROR, "Error insesperado, ha ocurrido algo con la base de datos mientras se borraba", "Error inesperado");
                        }
                    }
                } else {
                    FXUtils.makeAlert(Alert.AlertType.ERROR, "No se ha encontrado esa multa", "Error de multa");
                }
            }catch (NumberFormatException e){
                FXUtils.makeAlert(Alert.AlertType.ERROR, "El id tiene que ser un número", "Error de campos");
            }
        }
    }

    @FXML
    void onDateChosen(ActionEvent event) {
        try {
            LocalDate date = datePicker.getValue();
            if (date.isAfter(LocalDate.now())){
                FXUtils.makeAlert(Alert.AlertType.ERROR, "La fecha no puede ser futura", "Fecha");
                datePicker.setValue(LocalDate.now());
            }
        }catch (Exception e){
            FXUtils.makeAlert(Alert.AlertType.ERROR, "Fecha no introducida correctamente", "Fecha");
            datePicker.setValue(LocalDate.now());
        }
    }

    @FXML
    void onInsertarClick(ActionEvent event) {
        if (idTField.getText().isEmpty()){
            if (precioTField.getText().isEmpty() || datePicker.getValue()==null){
                FXUtils.makeAlert(Alert.AlertType.ERROR, "Los datos de precio y fecha deben tener contenido", "Error de campos");
            } else {
                try {
                    double precio = Double.parseDouble(precioTField.getText());
                    LocalDate fecha = datePicker.getValue();
                    Multa multa = new Multa(precio, fecha, cocheMultado);
                    if (multaDAO.insertarMulta(multa)){
                        FXUtils.makeAlert(Alert.AlertType.INFORMATION, "Multa insertada correctamente", "Inserción");
                        tableRefresh();
                    } else {
                        FXUtils.makeAlert(Alert.AlertType.ERROR, "Ha habido un problema con la base de datos", "Error de actualización");
                    }
                }catch (NumberFormatException e){
                    FXUtils.makeAlert(Alert.AlertType.ERROR, "El precio debe ser un valor numérico", "Error de datos");
                }
            }
        } else {
            FXUtils.makeAlert(Alert.AlertType.ERROR, "Para insertar, el identificador debe estar vacío", "Error de campos");
        }
    }

    @FXML
    void onItemPicked(ActionEvent event) {
        Multa multa = multaTView.getSelectionModel().getSelectedItem();
        if (multa!=null){
            idTField.setText(String.valueOf(multa.getId()));
            precioTField.setText(String.valueOf(multa.getPrecio()));
            datePicker.setValue(multa.getFecha());
        }
    }

    @FXML
    void onLimpiarClick(ActionEvent event) {
        flush();
    }

    public void flush(){
        idTField.setText("");
        precioTField.setText("");
        datePicker.setValue(null);
    }

    public void tableRefresh(){
        List<Multa> listaMultas = multaDAO.listarMultas(cocheMultado.getMatricula());
        ObservableList<Multa> listaMultasFX = FXCollections.observableArrayList(listaMultas);
        multaTView.setItems(listaMultasFX);
    }

    public void setItems(Coche coche){
        cocheMultado = coche;
        matrTField.setText(cocheMultado.getMatricula());

        multaDAO = new MultaDAO();

        idTCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fechaTCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        precioTCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        tableRefresh();
    }
}
