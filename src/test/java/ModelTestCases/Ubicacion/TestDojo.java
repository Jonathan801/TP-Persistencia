package ModelTestCases.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.resultadoCombate.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TestDojo extends TestUbicacion{
    private Dojo dojo;
    private Bicho rocaman;
    private Especie piedramon;

    @Before
    public void setUpDojo(){
        this.dojo = new Dojo("karate kid");
        this.piedramon= new Especie("piedramon", TipoBicho.TIERRA);
        this.rocaman = new Bicho(piedramon);
        LocalDate inicio = LocalDate.now();
        this.dojo.setCampeon(this.pika,inicio);
        this.dojo.setEntrenadorCampeon(this.ash);

    }

    @Test
    public void agregoACampeonYSuEntrenador(){
        Assert.assertNotNull(this.dojo.getCampeon());
        Assert.assertNotNull(this.dojo.getEntrenadorCampeon());
        Assert.assertEquals(this.dojo.getHistorialDeCampeones().size(),1);
    }

    @Test(expected = RuntimeException.class)
    public void abandonoEnDojo(){
        this.dojo.abandonar(pika,ash);
    }

    @Test(expected = RuntimeException.class)
    public void agregoBichoQueNoEsDeltipoRaizDelCampeon(){
        this.dojo.agregarBicho(rocaman);
    }

    @Test
    public void agregoBichoDelTipoDelCampeon() {
        Bicho pichotPika = new Bicho(this.pikachu);
        this.dojo.agregarBicho(pichotPika);
        Assert.assertEquals(this.dojo.getBichos().size(),1);
    }



    @Test
    public void busquedaExitosaEnDojo(){
        this.dojo.setEsBusquedaExitosa(true);
        Bicho pichotPika = new Bicho(this.pikachu);
        Bicho electrin= new Bicho(this.pikachu);
        this.dojo.agregarBicho(pichotPika);
        this.dojo.agregarBicho(electrin);
        Assert.assertEquals(2,this.dojo.getBichos().size());
        Bicho encontrado = this.dojo.buscar(ash);
        Assert.assertNotNull(encontrado);
        Assert.assertEquals(1,this.dojo.getBichos().size());

    }



    @Test(expected = RuntimeException.class)
    public void no_Puede_Pelear_El_Entrenador_Consigo_Mismo(){
        this.dojo.duelo(ash,pika);
    }

    @Test
    public  void duelo_en_el_dojo(){
        Entrenador retador = new Entrenador();
        pika.setEnergia(1000);
        raichu.setEnergia(10);
        ResultadoCombate r= this.dojo.duelo(retador,raichu);
        Assert.assertEquals(r.getGanador().getEspecie().getNombre(),pika.getEspecie().getNombre());
        Assert.assertTrue(r.getRegistroDeAtaquesDelCampeon().size()>0);
        Assert.assertTrue(r.getRegistroDeAtaquesDelRetador().size()>0);
    }
}
