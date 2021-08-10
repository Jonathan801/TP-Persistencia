package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.registro.Registro;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;
import org.javatuples.Pair;

import javax.persistence.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Dojo extends Ubicacion {

    @OneToOne
    private Bicho campeon;
    @OneToOne
    private Entrenador entrenadorCampeon;


    @OneToMany(cascade= CascadeType.ALL)
    private List<Registro> historialDeCampeones;

    //<Chabon,funcion tiempo()>

    private Boolean esBusquedaExitosa;

    public Dojo() {
      super();
    }

    public Dojo(String nombre) {
        super(nombre);
        this.historialDeCampeones = new ArrayList<>();
    }

    public void setCampeon(Bicho bicho,LocalDate coronacion){
        this.campeon = bicho;
        this.agregarFechaDeCoronacion(bicho,coronacion);
        this.clearBichos(bicho);
    }

    public void clearBichos(Bicho bicho){
        if(!this.bichos.isEmpty()&& !(this.bichos.get(0).getEspecie().getNombre()==bicho.getEspecie().getNombre())){
          this.bichos = new ArrayList<Bicho>();
        }

    }
    public Bicho getCampeon(){return this.campeon;}

    public void setEntrenadorCampeon(Entrenador entrenadorCampeon){
        this.entrenadorCampeon = entrenadorCampeon;
    }

    public void setHistorialDeCampeones(Registro registro){
        this.historialDeCampeones.add(registro);
    }



    public List<Registro>getHistorialDeCampeones(){return this.historialDeCampeones;}


    public void agregarFechaDeCoronacion(Bicho bicho,LocalDate fecha){
        Registro registro = new Registro();
        registro.setBicho(bicho);
        registro.setFechaCoronacion(fecha);
        this.historialDeCampeones.add(registro);
    }

    public void agregarFechaDeDespuesto(Bicho bicho, LocalDate fecha){
        int n = 0;
        Iterator<Registro> iterator = historialDeCampeones.iterator();
        while (iterator.hasNext()){
            Registro registro = iterator.next();
            if(!registro.getBicho().getId().equals(bicho.getId())) {
                n++;
            }
        }
        Registro registro = this.historialDeCampeones.get(n);
        this.historialDeCampeones.remove(registro);
        registro.setFechaDescoronacion(fecha);
        this.historialDeCampeones.add(registro);
    }

    @Override
    public void abandonar(Bicho bicho, Entrenador entrenador){
        throw new RuntimeException("No se encuentra en guarderia");
    }



    @Override
    public boolean esBusquedaExitosa() {
        return this.esBusquedaExitosa;
    }

    public void setEsBusquedaExitosa(Boolean busquedaExitosa){this.esBusquedaExitosa = busquedaExitosa;}

    public void agregarBicho(Bicho bicho){
        if(this.campeon.getEspecie().especieRaiz().getNombre()==bicho.getEspecie().getNombre()){
            this.bichos.add(bicho);
        }
        else {
            throw new RuntimeException("No se puede agregar bicho");
        }
    }

    @Override
    public ResultadoCombate duelo(Entrenador entrenador,Bicho bicho){
        if(entrenador.getNombre()==this.entrenadorCampeon.getNombre()){
            throw  new RuntimeException("el entrenador no puede enfrentarse a si mismo");
        }
        return this.pelea(bicho,this.campeon);
    }

    public ResultadoCombate pelea(Bicho bichoRetador,Bicho campeon) {
        ResultadoCombate resultadoCombate = new ResultadoCombate();
        //el danio que produce el retador al campeon al atacar
        int danioTotalRetador = 0;
        int danioRetadorPorTurno=0;
        //el danio que produce el campeon al retador al atacar
        int danioTotalCampeon = 0;
        int danioCampeonPorTurno=0;
        int turnos = 1;
        boolean turnoRetador = true;
        while (bichoRetador.getEnergia() > danioTotalCampeon && campeon.getEnergia() > danioTotalRetador && turnos < 10) {
            if (turnoRetador) {
                danioRetadorPorTurno = this.danioDelBicho(bichoRetador);
                resultadoCombate.agregarAtaqueDeRetador(danioRetadorPorTurno);
                danioTotalRetador += danioRetadorPorTurno;

                turnoRetador = false;
                turnos++;
            }
            danioCampeonPorTurno= this.danioDelBicho(campeon);
            resultadoCombate.agregarAtaqueDelCampeon(danioCampeonPorTurno);
            danioTotalCampeon += danioCampeonPorTurno;
            turnoRetador = true;
        }
        if(bichoRetador.getEnergia() < danioTotalCampeon || turnos==10) {
           resultadoCombate.setGanador(campeon);
        }
        else {
            resultadoCombate.setGanador(bichoRetador);
            LocalDate fechaActual = LocalDate.now();
            this.agregarFechaDeCoronacion(bichoRetador,fechaActual);
            this.agregarFechaDeDespuesto(campeon,fechaActual);

            /*
            this.agregarFechaDeDespuesto(campeon,fechaActual);
            this.campeon = bichoRetador;
            this.agregarFechaDeCoronacion(bichoRetador,fechaActual);

             */
        }

        return resultadoCombate;

    }
        public int danioDelBicho(Bicho bicho){
            return (int) (bicho.getEnergia()*Math.random()*0.5 + 0.5);

        }

        public Bicho getBichoCampeon(){
           return this.campeon;
        }



    public Entrenador getEntrenadorCampeon(){
        return this.entrenadorCampeon;
    }

    public void agregarRegistroCampeon(Registro reg){
        this.historialDeCampeones.add(reg);
    }


    @Override
    public Bicho bichoEncontrado(Entrenador e) {
        Especie especie = this.campeon.getEspecie().especieRaiz();
        Random r = new Random();
        Bicho bichoEncontrado  = this.bichos.stream().skip(r.nextInt(bichos.size())).findFirst().get();
        this.removeBicho(bichoEncontrado);
        bichoEncontrado.cambiarEspecie(especie);
        return bichoEncontrado;

    }






}

