package ModelTestCases.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPueblo extends TestUbicacion{
    private Pueblo pueblo;
    @Before
    public void setUpPueblo(){
        this.pueblo = new Pueblo("pueblo paleta");

    }
    @Test(expected = RuntimeException.class)
    public void abandonoEnPueblo(){
        this.pueblo.abandonar(this.pika,this.ash);
    }

    @Test(expected = RuntimeException.class)
    public void dueloEnPueblo(){
        this.pueblo.duelo(this.ash,this.pika);
    }

    @Test(expected = RuntimeException.class)
    public void busquedaFallida(){
        this.pueblo.buscar(this.ash);
    }

    @Test
    public void agregoProbabilidadAEspecie(){
        this.pueblo.agregarProbabilidadAEspecie(pikachu,10);
        Assert.assertEquals(this.pueblo.getProbabilidadesDeEspecies().size(),1);
        Assert.assertEquals(this.pueblo.getProbabilidadesDeEspecies().get(0).getEspecie().getNombre(),pikachu.getNombre());
        Assert.assertEquals(this.pueblo.getProbabilidadesDeEspecies().get(0).getProbabilidad(),10);
    }

}
