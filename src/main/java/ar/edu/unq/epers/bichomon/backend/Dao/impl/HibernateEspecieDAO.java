package ar.edu.unq.epers.bichomon.backend.Dao.impl;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.HibernateRunner;
import org.hibernate.Session;


import java.util.List;
import org.hibernate.query.Query;


public class HibernateEspecieDAO extends HibernateDAO<Especie> implements EspecieDAO {

    public HibernateEspecieDAO() {
        super(Especie.class);
    }


    @Override
    public Especie recuperarPorNombre(String nombre) {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "from Especie e " + "where e.nombre =  :nombre";
        Query<Especie> query = session.createQuery(hql,  Especie.class);
        query.setParameter("nombre", nombre);

        return this.validacion(query.uniqueResult());
    }

    @Override
    public List<Especie> populares() {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "select es "
                + "from Entrenador  as e "
                + "join e.bichosCapturados as be "
                + "join be.especie as es "
                + "group by es "
                + "order by count (es) desc ";

        Query<Especie> query = session.createQuery(hql,  Especie.class);
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public List<Especie> impopulares() {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "select gs "
                + "from Guarderia  as g "
                + "join g.bichos as gb "
                + "join gb.especie as gs "
                + "group by gs "
                + "order by count (gs) desc ";

        Query<Especie> query = session.createQuery(hql,  Especie.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
