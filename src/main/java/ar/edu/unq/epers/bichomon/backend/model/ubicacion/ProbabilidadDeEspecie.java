package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class ProbabilidadDeEspecie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Especie especie;

    private int probabilidad;

    public ProbabilidadDeEspecie(){}

    public ProbabilidadDeEspecie(Especie esp,Integer pro){
        this.especie=esp;
        this.probabilidad=pro;
    }

    public int getProbabilidad(){
        return  probabilidad;
    }

    public Especie getEspecie(){
        return especie;
    }

}
