package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;
import org.javatuples.Pair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Pueblo extends Ubicacion {

    @OneToMany
    //@JoinColumn(name = "")
    private List<ProbabilidadDeEspecie> probabilidadesPueblo;

    private RandomSelector random;

    public Pueblo(String nombre) {
        super(nombre);
        this.probabilidadesPueblo = new ArrayList<ProbabilidadDeEspecie>();
        this.random = new RandomSelector(probabilidadesPueblo);
    }

    public List<ProbabilidadDeEspecie> getProbabilidadesDeEspecies(){return this.probabilidadesPueblo;}

    @Override
    public void abandonar(Bicho bicho, Entrenador entrenador) {
        throw new RuntimeException("no se puede abandonar bichos");
    }

    @Override
    public ResultadoCombate duelo(Entrenador entrenador, Bicho bicho) {
        throw new RuntimeException("no se puede combatir");
    }


    @Override
    public Bicho bichoEncontrado(Entrenador e) {
        Bicho esp = random.randomEspecie().crearBicho();
        esp.setEnergia(random.numberRandom()); //TODO esto fue agregado y hace que la energia inicial al ser creado sea un numero random
        return esp;

    }


    public void agregarProbabilidadAEspecie(Especie especie, Integer n) {
        ProbabilidadDeEspecie proEspecie = new ProbabilidadDeEspecie(especie, n);
        this.probabilidadesPueblo.add(proEspecie);
    }


    @Override
    public boolean esBusquedaExitosa() {
        return true;
    }
}


