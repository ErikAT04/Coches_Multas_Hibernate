package com.erikat.hibernate_coches_multas.util;

import com.erikat.hibernate_coches_multas.model.Coche;
import com.erikat.hibernate_coches_multas.model.Multa;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    static SessionFactory factory = null;
    static Session session = null;
    static {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        // Se registran las clases que hay que MAPEAR con cada tabla de la base de datos
        cfg.addAnnotatedClass(Coche.class);
        cfg.addAnnotatedClass(Multa.class);

        factory = cfg.buildSessionFactory();
        session = factory.openSession();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
    public static Session getSession(){return session;}

}
