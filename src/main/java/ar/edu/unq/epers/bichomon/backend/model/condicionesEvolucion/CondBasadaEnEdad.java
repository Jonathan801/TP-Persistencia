package ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.Entity;
import java.time.LocalDate;
@Entity
public class CondBasadaEnEdad extends CondicionEvolucion {

    private int edadRequerida;

    public CondBasadaEnEdad(int edadRequerida) {
        this.edadRequerida = edadRequerida;
    }

    private LocalDate fechaRequerida(LocalDate fechaCaptura) {
        // Devuelve la fecha en la que el bichomon podrá evolucionar basandose en cuando se capturó y sumando la edad que se requiere.

        LocalDate fechaEsperada =
                LocalDate.of(fechaCaptura.getYear() + this.edadRequerida, fechaCaptura.getMonth(), fechaCaptura.getDayOfMonth());

        return fechaEsperada;
    }

    @Override
    public boolean puedeEvolucionar(Bicho b, Entrenador e) {
        // Retorna true si la fecha actual es posterior a la fecha necesaria para la evolucion del bicho (basandose en la edad)
        LocalDate fechaRequerida = this.fechaRequerida(b.getFechaCaptura());

        return LocalDate.now().isAfter(fechaRequerida);
    }
}
