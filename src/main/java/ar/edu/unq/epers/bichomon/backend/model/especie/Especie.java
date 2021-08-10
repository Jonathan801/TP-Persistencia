package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Especie {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String nombre;
	private int altura;
	private int peso;
	private int cantidadBichos;
	private int energiaInicial;
	private TipoBicho tipo;
	@OneToOne(cascade = CascadeType.ALL)
	private Especie especieAnterior;
	@OneToOne(cascade = CascadeType.ALL)
	private Especie especieSiguiente;
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CondicionEvolucion> condEvolucionEspecie;

	private String urlFoto;

	public Especie() {
		this.condEvolucionEspecie = new ArrayList<CondicionEvolucion>();

	}


	public Especie(String nombre, TipoBicho tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.condEvolucionEspecie = new ArrayList<CondicionEvolucion>();
		this.altura =0;
		this.peso=0;
		this.cantidadBichos=0;
		this.energiaInicial=0;

	}
	public void setEspecieSiguiente(Especie e){
		this.especieSiguiente=e;
	}

	public void setEspecieAnterior(Especie especieAnterior) {
		this.especieAnterior = especieAnterior;
	}

	public Especie getEspecieAnterior() {
		return especieAnterior;
	}

	public Especie getEspecieSiguiente() {
		return especieSiguiente;
	}

	/**
	 * @return el nombre de la especie (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return la altura de todos los bichos de esta especie
	 */
	public int getAltura() {
		return this.altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public int getPeso() {
		return this.peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del bichomon por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta especie.
	 */
	public int getEnergiaInicial() {
		return this.energiaInicial;
	}

	public void setEnergiaIncial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	/**
	 * @return el tipo de todos los bichos de esta especie
	 */
	public TipoBicho getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoBicho tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * especie.
	 */
	public int getCantidadBichos() {
		return this.cantidadBichos;
	}

	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}

	public Bicho crearBicho() {
		this.cantidadBichos++;
		return new Bicho(this);
	}

	public Set<String> getEvoluciones() {
		List<String> evolucionActual= new ArrayList<String>();
		evolucionActual.add(this.nombre);
		List<String> evolAnterioresYPosteriores= Stream.concat(this.evolucionesAnteriores().stream(), this.evolucionesPosteriores().stream())
				.collect(Collectors.toList());
		List<String> list=Stream.concat(evolucionActual.stream(), evolAnterioresYPosteriores.stream())
				.collect(Collectors.toList());

		return list.stream().collect(Collectors.toSet());
	}

	private List<String> evolucionesAnteriores(){
		List<String> evolucionA= new ArrayList<String>();
		evolucionA.add(this.nombre);
		if (this.especieAnterior ==null){
			return evolucionA;
		}
		else{
			return Stream.concat(evolucionA.stream(), this.especieAnterior.evolucionesAnteriores().stream())
					.collect(Collectors.toList());
		}
	}
	private List<String> evolucionesPosteriores(){
		List<String> evolucionP= new ArrayList<String>();
		evolucionP.add(this.nombre);
		if (this.especieSiguiente ==null){
			return evolucionP;
		}
		else{
			return Stream.concat(evolucionP.stream(), this.especieSiguiente.evolucionesPosteriores().stream())
					.collect(Collectors.toList());
		}
	}




	public void agregarCondEvolucion(CondicionEvolucion condicion) {
		this.condEvolucionEspecie.add(condicion);
	}

	public int getCantidadCondiciones() {
		return this.condEvolucionEspecie.size();
	}


	public void evolucionar(Bicho bicho, Entrenador entrenador) {
		if (this.puedeEvolucionar(bicho,entrenador)) {
			bicho.cambiarEspecie(this.getSiguienteEvolucion());
		} else {
			throw new RuntimeException("Este Bicho no puede evolucionar ya que no se cumplen todas las condiciones");
		}
	}

	public boolean puedeEvolucionar(Bicho bicho, Entrenador entrenador) {
		return this.condEvolucionEspecie.stream().allMatch(cond -> cond.puedeEvolucionar(bicho,entrenador));
	}

	private Especie getSiguienteEvolucion()   {

		if (this.especieSiguiente == null){
			throw new RuntimeException("ya no se puede evolucionar pro que la especie actual es la ultima evolucion");
		}
		else{
			return this.especieSiguiente;
		}
	}
	public Especie especieRaiz(){
		if(this.especieAnterior==null){
			return this;
		}
		else{
			return this.especieAnterior.especieRaiz();
		}
	}


}
