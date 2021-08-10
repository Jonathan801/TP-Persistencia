package ar.edu.unq.epers.bichomon.backend.Dao.impl;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.Leaderboard;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.leaderBoard.LeaderboardService;
import ar.edu.unq.epers.bichomon.backend.service.runner.HibernateRunner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class HibernateLeaderboardDAO extends HibernateDAO<LeaderboardService> implements Leaderboard {

    public HibernateLeaderboardDAO() {
        super(LeaderboardService.class);
    }

    @Override
    public List<Entrenador> campeones() {
        Session session = HibernateRunner.getCurrentSession();

        String hql = "select d.entrenadorCampeon "
                + "from Dojo as d "
                + "join d.historialDeCampeones as hc "
                + "order by hc.fechaCoronacion asc";

        Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);
        query.setMaxResults(10);

        return query.getResultList();
    }

    @Override
    public Especie especieLider() throws NoResultException {
        Session session = HibernateRunner.getCurrentSession();

        String hql = "select distinct be   "
                + "from Registro  as r "
                + "join r.bicho.especie as be "
                + "group by be "
                + "order by count (be) desc ";

        Query<Especie> query = session.createQuery(hql,  Especie.class);
        query.setMaxResults(1);
        return query.getSingleResult();

    }

    @Override
    public List<Entrenador> lideres() {
        Session session = HibernateRunner.getCurrentSession();

        String hql = "select e "
                + "from Entrenador as e "
                + "join e.bichosCapturados as bc "
                + "group by e "
                + "order by sum(bc.energia) desc";

        Query<Entrenador> query = session.createQuery(hql,  Entrenador.class);
        query.setMaxResults(10);

        return query.getResultList();
    }
}
