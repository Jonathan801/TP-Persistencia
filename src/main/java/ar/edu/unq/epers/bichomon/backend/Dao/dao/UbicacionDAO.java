package ar.edu.unq.epers.bichomon.backend.Dao.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public interface UbicacionDAO {

    void guardar(Ubicacion ubicacion);

    Ubicacion recuperar(Long id);

    Ubicacion recuperarUbicacionNombre(String nombre);

    void actualizar(Ubicacion ubicacion);

    int cantDeEntrenadores (String ubicacion);

    Bicho campeon(String dojo);

    Bicho campeonHistorico(String dojo);

}
