package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.DataDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;

import java.time.Instant;
import java.util.Date;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class BichoServiceImpl implements BichoService {

    private EntrenadorDAO entrenadorDAO;
    private BichoDAO bichoDAO;
    private DataDAO dataDAO;


    public BichoServiceImpl(EntrenadorDAO entrenadorDAO, BichoDAO bichoDAO, DataDAO dataDAO) {
        this.entrenadorDAO = entrenadorDAO;
        this.bichoDAO = bichoDAO;
        this.dataDAO = dataDAO;

    }


    @Override
    public Bicho buscar(String entrenador) {
        return run(() -> {
            Entrenador ash = this.entrenadorDAO.recuperarEntrenador(entrenador);
            Bicho bichoEncontrado = ash.buscarBicho();
            this.entrenadorDAO.actualizar(ash);

            return bichoEncontrado;
        });
    }

    public Bicho buscarBicho(Long bicho) {

        return run(() -> this.bichoDAO.recuperar(bicho));
    }


    public void actualizarEntrenador(Entrenador entrenador) {
        run(() -> this.entrenadorDAO.actualizar(entrenador));
    }




    @Override
    public void abandonar(String entrenador, int bicho) {
        run(() -> {
            long id = bicho;
            //Ubicacion
            Entrenador ash = this.entrenadorDAO.recuperarEntrenador(entrenador);
            Bicho bicho1 = this.bichoDAO.recuperar(id);
            ash.abandonar(bicho1);
            this.entrenadorDAO.actualizar(ash);



        });

    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        return run(() -> {
            long id = bicho;
            Entrenador ash = this.entrenadorDAO.recuperarEntrenador(entrenador);
            Bicho bicho1 = this.bichoDAO.recuperar(id);


            ResultadoCombate resultado = ash.duelo(bicho1);
            this.entrenadorDAO.actualizar(ash);
            this.bichoDAO.actualizar(bicho1);

            return resultado;
        });
    }


    @Override
    public Boolean puedeEvolucionar(String entrenador, int bicho) {
        return run(() -> {
            long id = bicho;
            Entrenador ash = this.entrenadorDAO.recuperarEntrenador(entrenador);
            Bicho bicho1 = this.bichoDAO.recuperar(id);
            Boolean valor = bicho1.getEspecie().puedeEvolucionar(bicho1, ash);
            return valor;
        });
    }

    @Override
    public Bicho evolucionar(String entrenador, int bicho) {
        return run(() -> {
            long id = bicho;
            Entrenador ash = this.entrenadorDAO.recuperarEntrenador(entrenador);
            Bicho bicho1 = this.bichoDAO.recuperar(id);
            ash.evolucionarBicho(bicho1);
            this.entrenadorDAO.actualizar(ash);
            this.bichoDAO.actualizar(bicho1);
            return bicho1;
        });
    }

}
