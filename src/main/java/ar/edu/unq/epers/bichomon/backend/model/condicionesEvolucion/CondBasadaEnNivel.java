package ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Entity;

@Entity
public class CondBasadaEnNivel extends CondicionEvolucion {

    private int nivelPedido;

    public CondBasadaEnNivel(int nivelPedido) {
        this.nivelPedido = nivelPedido;
    }

    @Override
    public boolean puedeEvolucionar(Bicho b, Entrenador e) {
        return e.getNivel() >= this.nivelPedido;
    }

}
