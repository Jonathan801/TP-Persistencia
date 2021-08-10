package ar.edu.unq.epers.bichomon.backend.service.runner;

import org.hibernate.Transaction;
import org.hibernate.Session;

public class HibernateRunner implements Runner {
    private static Session session;
    private Transaction tx = null;

    @Override
    public void begin() {
        try {
            session = SessionFactoryProvider.getInstance().createSession();
            tx = session.beginTransaction();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                this.rollback();
            }
        }
    }

    @Override
    public void commit() {
        tx.commit();
    }

    @Override
    public void close() {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    @Override
    public void rollback() {
        tx.rollback();
    }

    public static Session getCurrentSession() {
        if (session == null) {
            throw new RuntimeException("No hay ninguna session en el contexto");
        }
        return session;
    }
}
