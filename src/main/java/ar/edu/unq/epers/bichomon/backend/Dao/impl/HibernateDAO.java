package ar.edu.unq.epers.bichomon.backend.Dao.impl;

import ar.edu.unq.epers.bichomon.backend.service.runner.HibernateRunner;
import org.hibernate.Session;

public class HibernateDAO<T> {

    private Class<T> entityType;

    public HibernateDAO(Class<T> entityType){
        this.entityType = entityType;
    }

    public void guardar(T item) {
        Session session = HibernateRunner.getCurrentSession();
        session.save(item);
    }

    public T recuperar(Long id) {
        Session session = HibernateRunner.getCurrentSession();
        return this.validacion(session.get(entityType, id));
    }

    public T validacion(T tipe){
        if(tipe==null){
            throw  new RuntimeException("No esta persistido en la BD");
        }else{
            return tipe;
        }

    }

    public void  actualizar(T item){
        Session session = HibernateRunner.getCurrentSession();
        session.update(item);
    }
}
