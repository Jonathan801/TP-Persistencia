package TestDao;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestHibernateUbicacionDao extends TestAbstract {
    private UbicacionDAO ubicacionDAO;
    private Ubicacion guarderia;
    private BichoDAO bichoDAO;


    @Before
    public void setUpUbicacionDao(){
        bichoDAO = new HibernateBichoDAO();
        ubicacionDAO = new HibernateUbicacionDAO();
        serviceHibernate.setUbicacionDAO(ubicacionDAO);
        serviceHibernate.setBichoDAO(bichoDAO);
        guarderia = new Guarderia("Guarderia");

    }

    @Test
    public void guardo_recupero_y_actualizo_a_Dojo(){

        Long n = new Long(1);
        Especie raichu = new Especie("Raichu", TipoBicho.ELECTRICIDAD);
        Bicho pikachu = new Bicho(raichu);
        guarderia.agregarBicho(pikachu);
        serviceHibernate.guardarUbicacion(guarderia);
        serviceHibernate.guardarBicho(pikachu);
        Ubicacion guarderiaRecuperada = serviceHibernate.recuperarUbicacionPorId(n);
        Assert.assertEquals(guarderiaRecuperada.getNombre(),guarderia.getNombre());
        Assert.assertEquals(1,guarderiaRecuperada.getBichos().size());
        guarderiaRecuperada.removerBichoPorId(n);
        serviceHibernate.actualizarUbicacion(guarderiaRecuperada);
        Ubicacion guarderiaActualizada = serviceHibernate.recuperarUbicacionPorId(n);
        Assert.assertEquals(guarderiaActualizada.getNombre(),guarderia.getNombre());
        Assert.assertEquals(0,guarderiaActualizada.getBichos().size());





    }



    @Test
    public void Cantidad_Entrenadores_Ubicacion(){
        dataServiceImpl.crearSetDatosIniciales();
        Assert.assertEquals(serviceHibernate.cantDeEntrenadores("Dojin"),1);

    }
    @Test
    public void Bicho_Campeon_Dojo(){
        dataServiceImpl.crearSetDatosIniciales();
        Assert.assertEquals(serviceHibernate.campeon("Dojo").getEspecie().getNombre(),"Meowth");

    }
    @Test
    public void Bicho_Campeon_Historico(){
        dataServiceImpl.crearSetDatosIniciales();
        Assert.assertEquals(serviceHibernate.campeonHistorico("Dojo").getEspecie().getNombre(),"Meowth");

    }


}
