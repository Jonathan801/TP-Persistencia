package ModelTestCases.Especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesEvolucion.CondBasadaEnNivel;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TestEspecie {

    private Especie pikachu;
    private Especie pichu;
    private Especie raichu;

    @Before
    public void setUp() {
        this.pikachu = new Especie("pikachu", TipoBicho.ELECTRICIDAD);
        this.pichu = new Especie("pichu", TipoBicho.ELECTRICIDAD);
        this.raichu = new Especie("raichu", TipoBicho.ELECTRICIDAD);

        this.pichu.setEspecieSiguiente(pikachu);
        this.pikachu.setEspecieSiguiente(raichu);
        this.pikachu.setEspecieAnterior(pichu);
        this.raichu.setEspecieAnterior(pikachu);

    }

    @Test
    public void pichu_es_especie_raiz() {
        Assert.assertEquals(this.pichu.especieRaiz().getNombre(), "pichu");
    }

    @Test
    public void pichu_es_la_especie_Raiz_de_pikachu() {
        Assert.assertEquals(this.pikachu.especieRaiz().getNombre(), "pichu");
    }

    @Test
    public void pichu_es_la_especie_raiz_de_raichu() {
        Assert.assertEquals(this.raichu.especieRaiz().getNombre(), "pichu");
    }

    @Test
    public void evoluciones_de_pichu_pikachu_raichu() {
        Set<String> evols = new HashSet<>();
        evols.add("pichu");
        evols.add("raichu");
        evols.add("pikachu");
        Assert.assertEquals(pichu.getEvoluciones(), evols);
        Assert.assertEquals(pikachu.getEvoluciones(), evols);
        Assert.assertEquals(raichu.getEvoluciones(), evols);

    }

    @Test
    public void agrego_condiciones_de_evolucion(){
        CondBasadaEnNivel nivelCond=new CondBasadaEnNivel(1);
        Assert.assertEquals(pikachu.getCantidadCondiciones(),0);
        pikachu.agregarCondEvolucion(nivelCond);
        Assert.assertEquals(pikachu.getCantidadCondiciones(),1);

    }
    @Test(expected = RuntimeException.class)
    public void raichu_NO_puede_evolucionar(){
        Entrenador ash = new Entrenador("ash",100,1);
        Bicho pika=new Bicho(raichu);

        this.raichu.evolucionar(pika,ash);
    }
    @Test
    public void pikachu_puede_evolucionar_si_el_bicho_tiene_energia_mayor_a_100(){
        CondBasadaEnNivel nivelCond=new CondBasadaEnNivel(1);
        pikachu.agregarCondEvolucion(nivelCond);
        Entrenador ash = new Entrenador("ash",1000,10);
        Bicho pika = new Bicho(pikachu);
        pika.setEnergia(1000);
        Assert.assertTrue(pikachu.puedeEvolucionar(pika,ash));
    }
}
