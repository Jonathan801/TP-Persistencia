package ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Entity;

@Entity
public class CondBasadaEnVictoria extends CondicionEvolucion {

    private int victoriasPedidas;

    public CondBasadaEnVictoria(int victoriasPedidas) {
        this.victoriasPedidas = victoriasPedidas;
    }

    @Override
    public boolean puedeEvolucionar(Bicho b, Entrenador e) {
        return b.getCantVictorias() >= victoriasPedidas;
    }
}
