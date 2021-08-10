package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ubicacion implements BusquedaExitosa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(unique = true)
    protected String nombre;

    @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    protected List<Bicho> bichos;


    public Ubicacion(){

    }

    public Ubicacion(String nombre){
        this.bichos=new ArrayList<>();
        this.nombre = nombre;
    }

    public String getNombre() {return this.nombre;}
    public void setNombre(String nombre){this.nombre= nombre;}

    public List<Bicho> getBichos(){return this.bichos;}

    public void agregarBicho(Bicho bicho){
        this.bichos.add(bicho);
    }

    public void removeBicho(Bicho bicho){
        this.bichos.remove(bicho);
    }

    public void abandonar(Bicho bicho, Entrenador entrenador){}
    public ResultadoCombate duelo(Entrenador entrenador,Bicho bicho){return null;}

    public Bicho buscar(Entrenador entrenador){
     if(this.esBusquedaExitosa()){
         Bicho bichoE=this.bichoEncontrado(entrenador);
         return bichoE;
     }
     else{
         throw new RuntimeException("no fue busqueda exitosa ");
     }
    }

    public abstract Bicho bichoEncontrado(Entrenador e);

    //Metodo creado para probar Ubicacion dao
    //El bicho esta en la lista

    public void removerBichoPorId(Long id){
        int n = 0;
        while (!(this.bichos.get(n).getId().equals(id))){
            n++;
        }
        this.bichos.remove(n);

    }

}