package ar.edu.unq.epers.bichomon.backend.Dao.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface BichoDAO {

    void guardar(Bicho bicho);

    Bicho recuperar(Long id);

    void actualizar(Bicho bicho);




}
