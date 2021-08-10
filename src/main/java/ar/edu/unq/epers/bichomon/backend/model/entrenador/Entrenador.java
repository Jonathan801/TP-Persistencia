package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "ENTRENADOR_ID")
    private Long id;


    private String nombre;

    private int puntosExp;

    private int nivel;
    private int billetera;

    @OneToOne(cascade = CascadeType.ALL)
    private ManejadorDeExperiencia manejadorDeExperiencia;

    @OneToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacionActual;

    //Revisar el cascade y el fetch
    //@JoinColumn(name = "ENTRENADOR_ID")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichosCapturados;

    private Integer cantidadDeVictorias;


    public void setCantVictorias(Integer cantidadDeVictorias){this.cantidadDeVictorias = cantidadDeVictorias;}

    public Integer getCantidadDeVictorias(){return this.cantidadDeVictorias;}


    public Entrenador() {

        this.manejadorDeExperiencia = new ManejadorDeExperiencia();
        this.bichosCapturados = new ArrayList<>();
    }

    public Entrenador(String nombre, int puntos, int nivel) {
        this.manejadorDeExperiencia = new ManejadorDeExperiencia();
        this.nombre = nombre;
        this.puntosExp = puntos;
        this.nivel = nivel;
        this.bichosCapturados = new ArrayList<>();

    }

    public void setBilletera(int billetera) {
        this.billetera = billetera;
    }

    public int getBilletera() {
        return billetera;
    }

    public List<Bicho> getBichosCapturados() {
        return this.bichosCapturados;
    }

    public void eliminarBicho(Bicho bicho) {
        this.bichosCapturados.remove(bicho);
    }

    public ManejadorDeExperiencia getManejadorDeExperiencia() {
        return this.manejadorDeExperiencia;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosExp() {
        return this.puntosExp;
    }

    public void setPuntosExp(int puntos) {
        this.puntosExp = puntos;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Ubicacion getUbicacionActual() {
        return this.ubicacionActual;
    }


    public void setUbicacionActual(Ubicacion ubicacion) {
        this.ubicacionActual = ubicacion;
    }


    public void subirNivel() {
        this.nivel++;
    }

    public int cantBichos() {
        return this.bichosCapturados.size();
    }


    public void capturarBicho(Bicho bicho) {
        if (this.manejadorDeExperiencia.puedoCapturar(this)) {
            LocalDate fechaDeCaptura = LocalDate.now();
            bicho.agregarFechaCaptura(fechaDeCaptura);
            this.bichosCapturados.add(bicho);
        }

    }

    public Bicho buscarBicho() {
        Bicho bichoEncontrado = this.ubicacionActual.buscar(this);
        this.capturarBicho(bichoEncontrado);
        this.recibirExp(10);
        return bichoEncontrado;


    }



        public void recibirExp(Integer n) {
            this.manejadorDeExperiencia.reciboExperiencia(this, n);
        }


    public ResultadoCombate duelo(Bicho bicho) {
           this.recibirExp(10);
            return this.ubicacionActual.duelo(this, bicho);
    }

    public void abandonar(Bicho bicho) {
            this.ubicacionActual.abandonar(bicho, this);
    }

    public void evolucionarBicho(Bicho bicho) {
        bicho.evolucionar(this);
        this.recibirExp(5);
    }

    public Bicho ultimoObtenido() {
        return this.bichosCapturados.get(this.bichosCapturados.size() - 1);
    }

    public void addBicho(Bicho bicho1) {
        this.bichosCapturados.add(bicho1);
    }

    public void setManejadorDeExperiencia(ManejadorDeExperiencia manejadorDeExperiencia) {
        this.manejadorDeExperiencia = manejadorDeExperiencia;
    }
}