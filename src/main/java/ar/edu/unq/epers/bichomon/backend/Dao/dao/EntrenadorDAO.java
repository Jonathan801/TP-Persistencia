package ar.edu.unq.epers.bichomon.backend.Dao.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public interface EntrenadorDAO {

    void guardar(Entrenador entrenador);

    Entrenador recuperarEntrenador(String nombre);

    void actualizar(Entrenador entrenador);




}
