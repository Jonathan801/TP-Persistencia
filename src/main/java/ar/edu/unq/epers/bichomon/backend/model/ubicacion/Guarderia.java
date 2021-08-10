package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;

import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Guarderia extends Ubicacion {

    public Guarderia(){
        super();
    }

    public Guarderia(String nombre) {
        super(nombre);
    }

    @Override
    public void abandonar(Bicho bicho, Entrenador entrenador){
        if(entrenador.cantBichos()> 1 ){
            this.agregarBicho(bicho);
            bicho.agregarExEntrenador(entrenador);
            entrenador.eliminarBicho(bicho);
        }else {
            throw new RuntimeException("No se puede abandonar el ultimo bicho");
        }
    }
    @Override
    public ResultadoCombate duelo(Entrenador entrenador,Bicho bicho){
        throw new RuntimeException("no se puede combatir");
    }



    @Override
    public Bicho bichoEncontrado(Entrenador e) {
        List<Bicho> bichos = this.getBichos().stream().filter(bicho -> !bicho.getExEntrenadores().contains(e.getNombre())).collect(Collectors.toList());
        int numRamdon=(int) (Math.random() * bichos.size()-1);
        Bicho bichoConseguido=bichos.get(numRamdon);
        this.bichos.remove(numRamdon);
        return bichoConseguido;
    }

    @Override
    public boolean esBusquedaExitosa() {
        return true;
    }
}
