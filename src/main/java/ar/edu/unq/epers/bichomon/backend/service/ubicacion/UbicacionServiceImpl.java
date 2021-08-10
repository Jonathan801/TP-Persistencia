package ar.edu.unq.epers.bichomon.backend.service.ubicacion;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class UbicacionServiceImpl {
    private UbicacionDAO ubicacionDAO;

    public UbicacionServiceImpl(UbicacionDAO ubicacionDAO){
        this.ubicacionDAO = ubicacionDAO;
    }

    public Ubicacion buscarUbicacion(Long ubicacion){
        return run(()->this.ubicacionDAO.recuperar(ubicacion));
    }

    public Ubicacion buscarUbicacionNombre(String ubicacion){
        return run(()->this.ubicacionDAO.recuperarUbicacionNombre(ubicacion));
    }
}
