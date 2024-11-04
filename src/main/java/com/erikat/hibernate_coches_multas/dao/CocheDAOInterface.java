package com.erikat.hibernate_coches_multas.dao;

import com.erikat.hibernate_coches_multas.model.Coche;

import java.util.List;

public interface CocheDAOInterface {
    boolean insertarCoche(Coche coche);
    Coche buscarCoche(String matricula);
    boolean modificarCoche(Coche coche);
    boolean borrarCoche(Coche coche);
    List<Coche> listarCoches();
}
