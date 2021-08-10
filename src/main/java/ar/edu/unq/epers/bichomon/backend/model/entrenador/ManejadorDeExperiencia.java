package ar.edu.unq.epers.bichomon.backend.model.entrenador;



import org.javatuples.Pair;
import org.javatuples.Triplet;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class ManejadorDeExperiencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "MANEJADOR_EXP_ID")
    private Long id;

    //El par tiene el limite de experiencia(value0) y la cantidad de bichos(value1) que puede tener el entrenador
    //en la primer posicion el nivel, en el segundo cant de bichos que puede tener y en el tercero la exp
    @ElementCollection
    @Column(length = 10000)
    private List<Triplet<Integer,Integer,Integer>> infoDeNivel;



    public  ManejadorDeExperiencia(){
        this.infoDeNivel = new ArrayList<>();
    }

    public void agregarNivelConLimiteDeExperienciaYCantidadDeBichos(Triplet info){
        this.infoDeNivel.add(info);
    }


    public void reciboExperiencia(Entrenador entrenador,Integer experienciaRecibida){
        int nivelEntrenador = entrenador.getNivel() -1 ;
        int limiteDeExpDelNivelDelEntrenador = this.infoDeNivel.get(nivelEntrenador).getValue2();
        int experienciaNueva= entrenador.getPuntosExp() + experienciaRecibida;

        if(experienciaNueva >= limiteDeExpDelNivelDelEntrenador){
            if(!(this.infoDeNivel.size() == entrenador.getNivel())) {
                entrenador.subirNivel();
                entrenador.setPuntosExp(experienciaNueva);
            }
        }
    }




    public Boolean puedoCapturar(Entrenador entrenador){
        int nivelEntrenador = entrenador.getNivel();
        int posicionDelNivel= nivelEntrenador -1;
        Triplet <Integer, Integer,Integer> infoDeNiveles = this.infoDeNivel.get(posicionDelNivel);
        return entrenador.cantBichos() < infoDeNiveles.getValue1();

    }

}
