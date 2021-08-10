package ar.edu.unq.epers.bichomon.backend.service.entrenador;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class EntrenadorServiceImpl {
    private EntrenadorDAO entrenadorDAO;

    public EntrenadorServiceImpl(EntrenadorDAO entrenadorDAO) {
         this.entrenadorDAO = entrenadorDAO;
    }

    public EntrenadorServiceImpl() {

    }

    public Entrenador buscarEntrenador(String entrenador){
        return run (() -> this.entrenadorDAO.recuperarEntrenador(entrenador));
    }

}
