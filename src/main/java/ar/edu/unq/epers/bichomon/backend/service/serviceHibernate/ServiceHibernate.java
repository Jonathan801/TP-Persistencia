package ar.edu.unq.epers.bichomon.backend.service.serviceHibernate;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class ServiceHibernate<T> {
    private BichoDAO bichoDAO;
    private EntrenadorDAO entrenadorDAO;
    private EspecieDAO especieDAO;
    private UbicacionDAO ubicacionDAO;


    public ServiceHibernate() {

    }

    public void setBichoDAO(BichoDAO bichoDAO) {
        this.bichoDAO = bichoDAO;
    }

    public void setEntrenadorDAO(EntrenadorDAO entrenadorDAO) {
        this.entrenadorDAO = entrenadorDAO;
    }

    public void setEspecieDAO(EspecieDAO especieDAO) {
        this.especieDAO = especieDAO;
    }

    public void setUbicacionDAO(UbicacionDAO ubicacionDAO) {
        this.ubicacionDAO = ubicacionDAO;
    }
    //BICHO DAO

    public Bicho recuperarBicho(Long n) {
        return run(() -> this.bichoDAO.recuperar(n));
    }

    public void actualizarBicho(Bicho bicho) {
        run(() -> {
            this.bichoDAO.actualizar(bicho);
        });
    }

    public void guardarBicho(Bicho bicho) {
        run(() -> {
            this.bichoDAO.guardar(bicho);
        });
    }

    //ENTRENADOR DAO
    public Entrenador recuperarEntrenador(String n) {
        return run(() -> this.entrenadorDAO.recuperarEntrenador(n));
    }


    public void actualizarEntrenador(Entrenador entrenador) {
        run(() -> {
            this.entrenadorDAO.actualizar(entrenador);
        });
    }

    public void guardarEntrenador(Entrenador entrenador) {
        run(() -> {
            this.entrenadorDAO.guardar(entrenador);
        });
    }

    //ESPECIE DAP
    public Especie recuperarEspeciePorId(Long id) {
        return run(() -> this.especieDAO.recuperar(id));
    }
    public Especie recuperarEspeciePorNombre(String nombre) {
        return run(() -> this.especieDAO.recuperarPorNombre(nombre));
    }

    public void actualizarEspecie(Especie especie) {
        run(() -> {
            this.especieDAO.actualizar(especie);
        });
    }

    public void guardarEspecie(Especie especie) {
        run(() -> {
            this.especieDAO.guardar(especie);
        });
    }

    public List<Especie> especiesPopulares(){
       return run(() -> this.especieDAO.populares());
    }

    public List<Especie> especiesImpopulares(){
        return run(() ->  this.especieDAO.impopulares());
    }

    //UBICACION DAO
    public Ubicacion recuperarUbicacionPorId(Long id) {
        return run(() -> this.ubicacionDAO.recuperar(id));
    }

    public Ubicacion recuperarUbicacionPorNombre(String nombre) {
        return run(() -> this.ubicacionDAO.recuperarUbicacionNombre(nombre));
    }



    public void actualizarUbicacion(Ubicacion ubicacion) {
        run(() -> {
            this.ubicacionDAO.actualizar(ubicacion);
        });
    }

    public void guardarUbicacion(Ubicacion ubicacion) {
        run(() -> {
            this.ubicacionDAO.guardar(ubicacion);
        });
    }

    public int cantDeEntrenadores (String ubicacion){
        return run(()->this.ubicacionDAO.cantDeEntrenadores(ubicacion));
    }

    public Bicho campeon(String ubicacion){
        return run(()->this.ubicacionDAO.campeon(ubicacion));
    }

    public Bicho campeonHistorico(String ubicacion){
        return run(()->this.ubicacionDAO.campeonHistorico(ubicacion));
    }




}