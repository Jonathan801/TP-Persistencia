package ar.edu.unq.epers.bichomon.backend.model.resultadoCombate;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class ResultadoCombate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private  Bicho ganador;
    @ElementCollection
    private List<Integer> registroDeAtaquesDelRetador;
    @ElementCollection
    private List<Integer> registroDeAtaquesDelCampeon;

    //como registrar los ataques de las batallas

    public ResultadoCombate(){
        this.registroDeAtaquesDelRetador = new ArrayList<>();
        this.registroDeAtaquesDelCampeon = new ArrayList<>();
    }

    public Bicho getGanador(){return this.ganador;}

    public void setGanador(Bicho ganador){this.ganador = ganador;}

    public void agregarAtaqueDeRetador(Integer n){
        this.registroDeAtaquesDelRetador.add(n);
    }
    public List<Integer>getRegistroDeAtaquesDelRetador(){return this.registroDeAtaquesDelRetador;}

    public void agregarAtaqueDelCampeon(Integer m){
        this.registroDeAtaquesDelCampeon.add(m);
    }
    public List<Integer>getRegistroDeAtaquesDelCampeon(){return this.registroDeAtaquesDelCampeon;}
}
