package ModelTestCases.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.junit.Assert;
import org.junit.Test;

public class TestGuarderia extends TestUbicacion{
    @Test
    public void agregoBichosAGuarderia(){
        Assert.assertEquals(this.guarderia.getBichos().size(),0);
        guarderia.agregarBicho(this.dragon);
        guarderia.agregarBicho(this.pika);
        guarderia.agregarBicho(this.raichu);
        Assert.assertEquals(this.guarderia.getBichos().size(),3);

    }
    @Test
    public void abandonarBichoEnGuarderia(){
        this.guarderia.abandonar(pika,ash);
        Assert.assertEquals(guarderia.getBichos().size(),1);
    }

    @Test(expected = RuntimeException.class)
    public void dueloEnGuarderia(){
        this.guarderia.duelo(ash,pika);
    }

    @Test
    public void buscarBicho(){
        guarderia.agregarBicho(raichu);
        Bicho v=guarderia.buscar(ash);
        Assert.assertNotNull(v);
        Assert.assertEquals(0,guarderia.getBichos().size());

    }
}
