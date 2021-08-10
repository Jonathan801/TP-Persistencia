package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */

@Entity
public class Bicho {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Especie especie;
	private int energia;

	private LocalDate fechaCaptura;

	@ElementCollection
	@CollectionTable ( name = "Exentrenadores" )
	private List<String> exEntrenadores;

	private int cantVictorias;

    public Bicho(Especie especie) {

		this.especie = especie;
		this.exEntrenadores = new ArrayList<String>();
		this.cantVictorias = 0;

	}

	public  Bicho(){
		this.exEntrenadores = new ArrayList<String>();
	}



	public Especie getEspecie() {
		return this.especie;
	}
	
    public Long getId(){
    	return this.id;
	}
	public int getEnergia() {
		return this.energia;
	}
	public void setEnergia(int energia) {
		this.energia = energia;
	}
	public void agregarFechaCaptura(LocalDate fecha) { this.fechaCaptura = fecha; }
	public LocalDate getFechaCaptura() { return this.fechaCaptura; }
	public void agregarExEntrenador(Entrenador e) {
    	this.exEntrenadores.add(e.getNombre());
    }



	public void cambiarEspecie(Especie espArg) {
		this.especie = espArg;
	}

    public Set<String> especiesDelBicho() {
         return this.especie.getEvoluciones();
    }

    public void evolucionar(Entrenador entrenador) {
	       this.especie.evolucionar(this, entrenador);
    }

    public List<String> getExEntrenadores() {
	    return this.exEntrenadores;
    }

    public void sumarVictoria() {
	    this.cantVictorias += 1;
    }

    public int getCantVictorias() {
	    return this.cantVictorias;
    }
}
