package ModelTestCases.CondicionEvolucion;

import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnEdad;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnNivel;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnVictoria;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TestCondicionEvolucion {
    private CondBasadaEnEnergia condicionEnergia;
    private CondBasadaEnEdad condicionBasadaEnEdad;
    private CondBasadaEnNivel condicionNivel;
    private CondBasadaEnVictoria condicionBasadaEnVictoria;
    private Entrenador ash;
    private Especie pikachu;
    private Bicho pika;

    @Before
    public void setUp(){
        this.ash = new Entrenador("ash",10000,10);
        this.ash.setCantVictorias(10);
        this.condicionBasadaEnEdad = new CondBasadaEnEdad(10);
        this.condicionBasadaEnVictoria = new CondBasadaEnVictoria(8);
        this.condicionNivel = new CondBasadaEnNivel(9);
        this.condicionEnergia = new CondBasadaEnEnergia(5000);
        this.pikachu = new Especie("pikachu", TipoBicho.ELECTRICIDAD);
        this.pika = new Bicho(pikachu);
    }

    @Test
    public void ash_cumple_condicion_de_edad(){
        LocalDate fechaDeCaptura = LocalDate.of(2000,12,10);
        pika.agregarFechaCaptura(fechaDeCaptura);
        Assert.assertTrue(this.condicionBasadaEnEdad.puedeEvolucionar(pika,ash));
    }
    @Test
    public void ash_NO_cumple_condicion_de_edad(){
        LocalDate fechaDeCaptura = LocalDate.of(2015,12,10);
        pika.agregarFechaCaptura(fechaDeCaptura);
        Assert.assertFalse(this.condicionBasadaEnEdad.puedeEvolucionar(pika,ash));

    }
    /*
    @Test
    public void pika_cumple_condicion_basada_en_victoria(){
        pika.setCantVictorias(10);
        Assert.assertTrue(this.condicionBasadaEnVictoria.puedeEvolucionar(pika,ash));
    }

    @Test
    public void pika_NO_cumple_condicion_basada_en_victoria(){
        pika.setCantVictorias(1);
        Assert.assertFalse(this.condicionBasadaEnVictoria.puedeEvolucionar(pika,ash));
    }

     */
    @Test
    public void pika_de_ash_cumple_condicion_basada_en_nivel(){
        Assert.assertTrue(this.condicionNivel.puedeEvolucionar(pika,ash));

    }
    @Test
    public void pika_de_ash_NO_cumple_condicion_basada_en_nivel(){
        this.ash.setNivel(1);
        Assert.assertFalse(this.condicionNivel.puedeEvolucionar(pika, ash));
    }

    @Test
    public void pika_cumple_condicion_basada_en_edad(){
        pika.setEnergia(100000);
        Assert.assertTrue(this.condicionEnergia.puedeEvolucionar(pika,ash));
    }
    @Test
    public void pika_NO_cumple_condicion_basada_en_edad() {

        Assert.assertFalse(this.condicionEnergia.puedeEvolucionar(pika, ash));
    }
}
