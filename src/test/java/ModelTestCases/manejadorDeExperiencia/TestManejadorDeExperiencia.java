package ModelTestCases.manejadorDeExperiencia;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ManejadorDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestManejadorDeExperiencia {
    private ManejadorDeExperiencia manejadorDeExperiencia;
    private Entrenador ash;
    private Bicho pika;
    private Bicho bolba;
    private Especie pikachu;
    private Especie bolbazor;



    @Before
    public void setUp(){
        Triplet<Integer,Integer,Integer> nivel1= Triplet.with(1,2,10);
        Triplet<Integer,Integer,Integer> nivel2= Triplet.with(2,4,20);
        manejadorDeExperiencia = new ManejadorDeExperiencia();
        manejadorDeExperiencia.agregarNivelConLimiteDeExperienciaYCantidadDeBichos(nivel1);
        manejadorDeExperiencia.agregarNivelConLimiteDeExperienciaYCantidadDeBichos(nivel2);
        ash = new Entrenador();
        ash.setNivel(1);
        ash.setNombre("Ash");
        ash.setPuntosExp(10);
        pikachu = new Especie("Pikachu", TipoBicho.ELECTRICIDAD);
        bolbazor= new Especie("Bolbazor",TipoBicho.AGUA);
        pika = new Bicho(pikachu);
        bolba = new Bicho(bolbazor);
        ash.addBicho(pika);
        ash.addBicho(bolba);
    }


    @Test
    public void ash_no_puede_capturar(){
        Assert.assertFalse(manejadorDeExperiencia.puedoCapturar(ash));
    }

    @Test
    public void ash_puede_capturar_porque_recibe_experiencia_y_ademas_sube_de_nivel(){
        Assert.assertEquals(1,ash.getNivel());
        manejadorDeExperiencia.reciboExperiencia(ash,1000);
        Assert.assertEquals(2,ash.getNivel());
        Assert.assertTrue(manejadorDeExperiencia.puedoCapturar(ash));
    }


}
