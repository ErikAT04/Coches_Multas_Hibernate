package com.erikat.hibernate_coches_multas.dao;

import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.util.HibernateUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class CocheDAO implements CocheDAOInterface{
    private Session session;
    public CocheDAO(){
        this.session = HibernateUtils.getSession();
    }

    public boolean insertarCoche(Coche coche) {
        try {
            session.beginTransaction();
            session.save(coche);
            session.getTransaction().commit();
            session.clear();
            return true;
        }catch (Exception e){
            System.out.println("Error de BD");
            session.getTransaction().rollback();
            session.clear();
            return false;
        }
    }

    @Override
    public Coche buscarCoche(String matricula) {
        Coche coche = null;
        try{
            session.beginTransaction();
            coche = (Coche) session.createQuery("from Coche where matricula = '" + matricula +"'", Coche.class).uniqueResult();
            session.getTransaction().commit();
            session.clear();
        }catch (Exception e){
            System.out.println("Error de BD");
            System.out.println(e.getCause());
            session.getTransaction().rollback();
            session.clear();
        }
        return coche;
    }

    @Override
    public boolean modificarCoche(Coche coche) {
        try {
            session.beginTransaction();
            session.update(coche);
            session.getTransaction().commit();
            session.clear();
            return true;
        } catch (Exception e){
            System.out.println("Error de BD: " +e.getCause());
            session.getTransaction().rollback();
            session.clear();
            return false;
        }
    }

    @Override
    public boolean borrarCoche(Coche coche) {
        try {
            session.beginTransaction();
            session.delete(coche);
            session.getTransaction().commit();
            session.clear();
            return true;
        } catch (Exception e){
            System.out.println("Error de BD: " +e.getCause());
            session.getTransaction().rollback();
            session.clear();
            return false;
        }
    }

    @Override
    public List<Coche> listarCoches() {
        List<Coche> coches = new ArrayList<>();
        try{
            coches = (List<Coche>) session.createQuery(" from Coche", Coche.class).list();
        }catch (Exception e){
            System.out.println("Error de BD: " +e.getMessage());
            System.out.println(e.getMessage());
        }
        session.clear();
        return coches;
    }
}
