package com.erikat.hibernate_coches_multas.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "coches")
public class Coche {
    /*
  TABLE coches (
  id integer NOT NULL AUTO_INCREMENT,
  matricula varchar(50) DEFAULT NULL,
  marca varchar(50) DEFAULT NULL,
  modelo varchar(50) DEFAULT NULL,
  tipo varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "tipo")
    private String tipo;

    @OneToMany(mappedBy = "multas", cascade = CascadeType.ALL)
    private List<Multa> multas; //Un coche puede tener varias multas

    public Coche(String matricula, String marca, String modelo, String tipo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public Coche(int id, String matricula, String marca, String modelo, String tipo, List<Multa> multas) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.multas = multas;
    }

    public Coche() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }
}
