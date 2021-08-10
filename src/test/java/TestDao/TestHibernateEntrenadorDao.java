package TestDao;


import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.serviceHibernate.ServiceHibernate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHibernateEntrenadorDao extends TestAbstract{
    private EntrenadorDAO entrenadorDAO;
    private Entrenador ash;


    @Before
    public void setUpEntrenadorDao(){
        entrenadorDAO = new HibernateEntrenadorDAO();
        serviceHibernate.setEntrenadorDAO(entrenadorDAO);
        ash = new Entrenador();
        ash.setNombre("Ash");
        ash.setNivel(1);
    }

    @Test
    public void guardo_recupero_y_actualizo_a_Ash(){
        serviceHibernate.guardarEntrenador(ash);
        Entrenador ashRecuperado = serviceHibernate.recuperarEntrenador("Ash");
        Assert.assertEquals(ashRecuperado.getNombre(),ash.getNombre());
        Assert.assertEquals(ashRecuperado.getNivel(),ash.getNivel());
        Assert.assertEquals(ashRecuperado.getBichosCapturados().size(),0);
        Especie raichu = new Especie("Raichu", TipoBicho.ELECTRICIDAD);
        Bicho pikachu = new Bicho(raichu);
        ashRecuperado.addBicho(pikachu);
        Guarderia guarderia = new Guarderia("Guarderia");
        ashRecuperado.setUbicacionActual(guarderia);
        serviceHibernate.actualizarEntrenador(ashRecuperado);
        Entrenador ashActualizado = serviceHibernate.recuperarEntrenador("Ash");
        Assert.assertEquals(ashActualizado.getUbicacionActual().getNombre(),"Guarderia");
        Assert.assertEquals(ashActualizado.getBichosCapturados().size(),1);

    }
}
