package ModelTestCases.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnNivel;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
//import ar.edu.unq.epers.bichomon.backend.model.entrenador.ManejadorDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ManejadorDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TestEntrenador {

    private Entrenador ash;
    private ManejadorDeExperiencia manejadorDeExperiencia;
    private Bicho bicho;
    private Especie pika;
    private Especie raichu;
    private Ubicacion guarderia;
    private CondBasadaEnEnergia condBasadaEnEnergia;
    private Pueblo pueblo;
    private Dojo dojo;
    private Entrenador brock;
    private Bicho bicho1;
    private Especie roca;
    private LocalDate fecha;


    @Before
    public void crearModelo() {
        Triplet<Integer,Integer,Integer> nivel1= Triplet.with(1,2,10);
        Triplet<Integer,Integer,Integer> nivel2= Triplet.with(2,4,20);
        manejadorDeExperiencia = new ManejadorDeExperiencia();
        manejadorDeExperiencia.agregarNivelConLimiteDeExperienciaYCantidadDeBichos(nivel1);
        manejadorDeExperiencia.agregarNivelConLimiteDeExperienciaYCantidadDeBichos(nivel2);
        raichu = new Especie();
        pika = new Especie();
        pika.setEnergiaIncial(10);
        pika.setEspecieSiguiente(raichu);
        condBasadaEnEnergia = new CondBasadaEnEnergia(100);
        pika.agregarCondEvolucion(condBasadaEnEnergia);
        bicho = new Bicho(pika);
        bicho.setEnergia(100000);
        guarderia = new Guarderia("Guarderia");
        ash = new Entrenador();
        ash.setManejadorDeExperiencia(this.manejadorDeExperiencia);
        ash.setNombre("Ash");
        ash.setNivel(1);
        ash.setPuntosExp(10);
        ash.setUbicacionActual(guarderia);
        ash.addBicho(bicho);

        //Brock
        roca = new Especie();
        bicho1 = new Bicho(roca);
        brock = new Entrenador("Brock", 3, 3);
        dojo = new Dojo("Dojo");
        brock.setUbicacionActual(dojo);
        brock.addBicho(bicho1);
        brock.setManejadorDeExperiencia(manejadorDeExperiencia);
        bicho1.setEnergia(1);
        LocalDate coronacionBicho1 = LocalDate.now();
        dojo.setCampeon(bicho1, coronacionBicho1);
        dojo.setEntrenadorCampeon(brock);
        dojo.setCampeon(bicho1,coronacionBicho1);
        fecha = LocalDate.now();
        pueblo = new Pueblo("Paleta");
        pueblo.agregarProbabilidadAEspecie(raichu,30);




    }

    @Test
    public void no_puede_combatir_porque_no_se_encuentra_en_Dojo(){
        try {
            ash.duelo(bicho);
        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("no se puede combatir"));
        }
    }
    @Test
    public void no_puede_evoluvionar_porque_cumple_condicion_de_energia(){
        try {
            ash.evolucionarBicho(bicho);
        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("Bicho no cumple las condiciones de evolucion")); //MIRAR TEST LUEGO
        }
    }

    @Test
    public void no_puede_abandonar_porque_tiene_un_solo_bicho(){
        try {
            ash.abandonar(bicho);
        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("No se puede abandonar el ultimo bicho"));
        }
    }

    @Test
    public void abandonoPikachu(){
        Especie pika = new Especie();
        Bicho bicho1 = new Bicho(pika);
        Bicho bicho2 = new Bicho(pika);
        guarderia = new Guarderia("Guarderia");
        ash.setUbicacionActual(guarderia);
        ash.addBicho(bicho1);
        ash.addBicho(bicho2);
        assertEquals(ash.cantBichos(),3);
        ash.abandonar(bicho1);
        assertEquals(ash.cantBichos(),2);
    }


    @Test
    public void buscarBicho(){
        Especie pika = new Especie();
        Bicho bicho1 = new Bicho(pika);
        Bicho bicho2 = new Bicho(pika);
        guarderia = new Guarderia("Guarderia");
        assertEquals(ash.cantBichos(),1);
        ash.setUbicacionActual(guarderia);
        guarderia.agregarBicho(bicho1);
        guarderia.agregarBicho(bicho2);
        Bicho bicho = ash.buscarBicho();
        assertEquals(2,ash.cantBichos());
        assertNotNull(bicho);
    }
    @Test
    public void puedeEvolucionarbicho(){
        CondicionEvolucion condicionBasadaEnNivel= new CondBasadaEnNivel(1);
        Especie pika = new Especie();
        assertEquals(0,pika.getCantidadCondiciones());
        pika.agregarCondEvolucion(condicionBasadaEnNivel);
        Bicho bicho1 = new Bicho(pika);

        assertTrue(pika.puedeEvolucionar(bicho1,ash));
    }

    @Test
    public void random_pueblo(){
        ash.setUbicacionActual(pueblo);
        assertEquals(ash.cantBichos(),1);
        ash.buscarBicho();
        assertEquals(ash.cantBichos(),2);
        System.out.println(ash.getBichosCapturados());
    }


}
