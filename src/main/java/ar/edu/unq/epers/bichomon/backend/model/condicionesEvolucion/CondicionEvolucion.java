package ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CondicionEvolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public abstract boolean puedeEvolucionar(Bicho bicho, Entrenador entrenador);
}

