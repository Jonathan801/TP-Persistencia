package ar.edu.unq.epers.bichomon.backend.Dao.impl;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.HibernateRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class HibernateEntrenadorDAO extends HibernateDAO<Entrenador> implements EntrenadorDAO {

    public HibernateEntrenadorDAO() {
        super(Entrenador.class);
    }


    public Entrenador recuperarEntrenador(String nombre) {
        Session session = HibernateRunner.getCurrentSession();
        String hql = "from Entrenador t " + "where t.nombre =  :nombre";
        Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);
        query.setParameter("nombre", nombre);

        return this.validacion(query.uniqueResult());//uniqueResult() devuelve null
    }

    public Entrenador validacion(Entrenador entrenador){
        if(entrenador==null){
            throw new RuntimeException("El entrenador no existe en la BD");
        }else{
            return entrenador;
        }
    }

    /*public Bicho obtenerBicho(Entrenador entrenador){
        Entrenador ash =this.entrenadorDAO.recuperarEntrenador(entrenador);
        ash.buscarBicho();
        this.entrenadorDAO.actualizar(ash);
    }*/

    /*@Override
    public Entrenador recuperar(Long id) {
        return null;
    }*/

}
