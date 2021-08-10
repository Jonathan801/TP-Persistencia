package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;

public interface BichoService {


    Bicho buscar(String entrenador);
    void abandonar(String entrenador, int bicho);
    ResultadoCombate duelo(String entrenador, int bicho);
    Boolean puedeEvolucionar(String entrenador, int bicho);
    Bicho evolucionar(String entrenador, int bicho);




}