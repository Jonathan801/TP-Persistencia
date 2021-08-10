package ar.edu.unq.epers.bichomon.backend.Dao.dao;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface Neo4jDAO {
    void eliminarDatos();
    List<String> conectados(Ubicacion ubicacion, String tipoCamino);
    void crearUbicacion(Ubicacion Ubicacion);
    void conectar(Ubicacion origen, Ubicacion destino, String tipoCamino);
    int costoCamino(Ubicacion ubicacion1,Ubicacion ubicacion);
    int costoDeCaminoLargoMasBarato(Ubicacion ubicacionO,Ubicacion ubicacionD);
    List<String> caminosCortos (Ubicacion ubicacionO);
}
