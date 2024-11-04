package com.erikat.hibernate_coches_multas.dao;

import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.model.Multa;

import java.util.List;

public interface MultaDAOInterface {
    boolean insertarMulta(Multa multa);
    Multa buscarMulta(int id);
    boolean modificarMulta(Multa multa);
    boolean borrarMulta(Multa multa);
    List<Multa> listarMultas();
}
