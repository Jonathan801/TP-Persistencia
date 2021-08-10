package ar.edu.unq.epers.bichomon.backend.Dao.dao;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.List;

public interface EspecieDAO {

    void guardar(Especie especie);

    Especie recuperar(Long id);

    Especie recuperarPorNombre(String nombre);

    void actualizar(Especie especie);

    List<Especie> populares();

    List<Especie> impopulares();
}
