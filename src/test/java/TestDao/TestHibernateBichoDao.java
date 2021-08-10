package TestDao;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.BichoDAO;

import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHibernateBichoDao extends TestAbstract{
    private BichoDAO bichoDAO;
    private Bicho pika;
    private Especie raichu;

    @Before
    public void setUpBichoDao(){
        bichoDAO = new HibernateBichoDAO();
        serviceHibernate.setBichoDAO(bichoDAO);
        raichu = new Especie("Raichu", TipoBicho.ELECTRICIDAD);
        pika = new Bicho(raichu);

    }

    @Test
    public void guardo_recupero_y_actualizo_a_pika(){
        serviceHibernate.guardarBicho(pika);
        Bicho pikaRecuperado = serviceHibernate.recuperarBicho(new Long(1));
        Assert.assertEquals(pikaRecuperado.getEspecie().getNombre(),pika.getEspecie().getNombre());
        Assert.assertEquals(pikaRecuperado.getEnergia(),0);
        pikaRecuperado.setEnergia(100);
        serviceHibernate.actualizarBicho(pikaRecuperado);
        Bicho pikaActualizado = serviceHibernate.recuperarBicho(new Long(1));
        Assert.assertEquals(pikaActualizado.getEnergia(),100);
    }

}
