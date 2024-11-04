package com.erikat.hibernate_coches_multas.dao;

import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.util.HibernateUtils;
import org.hibernate.Session;

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
            session.flush();
            return true;
        }catch (Exception e){
            System.out.println("Error de BD");
            session.getTransaction().rollback();
            session.flush();
            return false;
        }
    }

    @Override
    public Coche buscarCoche(String matricula) {
        Coche coche = null;
        try{
            session.beginTransaction();
            coche = (Coche) session.createQuery("from coche where matricula = " + matricula, Coche.class).uniqueResult();
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error de BD");
            session.getTransaction().rollback();
        }
        session.flush();
        return coche;
    }

    @Override
    public boolean modificarCoche(Coche coche) {
        try {
            session.beginTransaction();
            session.update(coche);
            session.getTransaction().commit();
            session.flush();
            return true;
        } catch (Exception e){
            System.out.println("Error de BD");
            session.getTransaction().rollback();
            session.flush();
            return false;
        }
    }

    @Override
    public boolean borrarCoche(Coche coche) {
        try {
            session.beginTransaction();
            session.delete(coche);
            session.getTransaction().commit();
            session.flush();
            return true;
        } catch (Exception e){
            System.out.println("Error de BD");
            session.getTransaction().rollback();
            session.flush();
            return false;
        }
    }

    @Override
    public List<Coche> listarCoches() {
        List<Coche> coches = null;
        try{
            coches = session.createQuery("from coches", Coche.class).getResultList();
        }catch (Exception e){
            System.out.println("Error de BD");
        }
        return coches;
    }
}
