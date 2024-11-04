package com.erikat.hibernate_coches_multas.dao;

import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.model.Multa;
import com.erikat.hibernate_coches_multas.util.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class MultaDAO implements MultaDAOInterface{
    private Session session;
    public MultaDAO(){
        this.session = HibernateUtils.getSession();
    }

    @Override
    public boolean insertarMulta(Multa multa) {
        try {
            session.beginTransaction();
            session.save(multa);
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
    public Multa buscarMulta(int id) {
        Multa multa = null;
        try {
            session.beginTransaction();
            multa = (Multa) session.get(Multa.class, id);
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Error de BD");
            session.getTransaction().rollback();
        }
        session.flush();
        return multa;
    }

    @Override
    public boolean modificarMulta(Multa multa) {
        try {
            session.beginTransaction();
            session.update(multa);
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
    public boolean borrarMulta(Multa multa) {
        try {
            session.beginTransaction();
            session.delete(multa);
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
    public List<Multa> listarMultas() {
        List<Multa> multas = null;
        try{
            multas = session.createQuery("from multas", Multa.class).getResultList();
        }catch (Exception e){
            System.out.println("Error de BD");
        }
        return multas;
    }
}
