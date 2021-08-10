package ar.edu.unq.epers.bichomon.backend.service.leaderBoard;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.Leaderboard;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;


import java.util.List;

public class LeaderboardServiceImpl implements LeaderboardService {
    private Leaderboard leaderboard;

    public  LeaderboardServiceImpl(){ }

    public LeaderboardServiceImpl(Leaderboard leaderboard){
        this.leaderboard = leaderboard;
    }

    @Override
    public List<Entrenador> campeones() {
        return run(()->leaderboard.campeones());
    }

    @Override
    public Especie especieLider() {
        return run(()->leaderboard.especieLider());
    }

    @Override
    public List<Entrenador> lideres() {
        return run(()-> this.leaderboard.lideres());
    }
}
