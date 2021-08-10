package ar.edu.unq.epers.bichomon.backend.Dao.impl;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.DataDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.HibernateRunner;
import org.hibernate.Session;

import java.util.List;

public class HibernateDataDAO implements DataDAO {

    public void clear(){
        Session session = HibernateRunner.getCurrentSession();
        List<String> nombreDeTablas = session.createNativeQuery("show tables").getResultList();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
        nombreDeTablas.forEach(tabla->{
            session.createNativeQuery("truncate table " + tabla).executeUpdate();
        });
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();
    }


}
