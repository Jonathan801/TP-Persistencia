package ar.edu.unq.epers.bichomon.backend.Dao.impl;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.HibernateRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class HibernateUbicacionDAO extends HibernateDAO<Ubicacion> implements UbicacionDAO {

    public HibernateUbicacionDAO() {
        super(Ubicacion.class);
    }

    @Override
    public Ubicacion recuperarUbicacionNombre(String nombre) {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "from Ubicacion t " + "where t.nombre =  :nombre";
        Query<Ubicacion> query = session.createQuery(hql, Ubicacion.class);
        query.setParameter("nombre", nombre);

        return this.validacion(query.uniqueResult());
    }


    @Override
    public int cantDeEntrenadores(String ubicacion) {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "select count(e.ubicacionActual) "
                + "from Entrenador e  join e.ubicacionActual "
                + "where e.ubicacionActual.nombre = :ubicacion";

        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("ubicacion", ubicacion);
        long l = query.uniqueResult();
        return (int) l;

    }

    @Override
    public Bicho campeon(String dojo) {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "from Dojo d " + " where d.nombre = :dojoParam";
        Query<Dojo> query = session.createQuery(hql, Dojo.class);
        query.setParameter("dojoParam", dojo);
        return query.uniqueResult().getBichoCampeon();
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        Session session = HibernateRunner.getCurrentSession();

        String hql2 = "select r.bicho "
                + "from Dojo as d "
                + "join d.historialDeCampeones as r   "
                + "where d.nombre = :dojo "
                + "group by r.bicho "
                + "order by ( r.fechaDescoronacion - r.fechaCoronacion ) desc ";


        Query<Bicho> query = session.createQuery(hql2, Bicho.class);
        query.setParameter("dojo", dojo);
        return query.list().get(0);
    }
}
