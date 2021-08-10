package TestServices;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestEspecieService {
    private EspecieDAO especieDAO;
    private EspecieService especieService;
    private DataService dataService;


    @Before
    public void setUp(){
        especieDAO = new HibernateEspecieDAO();
        especieService = new EspecieServiceImpl(especieDAO);
        dataService = new DataServiceImpl();
        dataService.crearSetDatosIniciales();
    }

    @Test
    public void pupulares(){
        List<Especie> populares = especieService.populares();
        Assert.assertEquals(4,populares.size());
        Assert.assertEquals("Avion",populares.get(0).getNombre());
        Assert.assertEquals("Terrakion",populares.get(1).getNombre());
        Assert.assertEquals("Pikachu",populares.get(2).getNombre());
        Assert.assertEquals("Meowth",populares.get(3).getNombre());
    }

    @Test
    public void no_hay_populares_y_devuelve_lista_vacia() {
        dataService.eliminarDatos();
        List<Especie> populares = especieService.populares();
        Assert.assertTrue(populares.isEmpty());
    }

    @Test
    public void impopulares(){
        //En la Guarderia hay 6 bichos, 3 de especie Terrakion, 2 de especie Pikachu , 1 de especie Avion
        List<Especie> impopulares = especieService.impopulares();
        Assert.assertEquals(3,impopulares.size());
        Assert.assertEquals("Terrakion",impopulares.get(0).getNombre());
        Assert.assertEquals("Pikachu",impopulares.get(1).getNombre());
        Assert.assertEquals("Avion",impopulares.get(2).getNombre());
    }

    @Test
    public void nadie_es_impopular_y_devuelve_lista_vacia() {
        // Que utopico.
        dataService.eliminarDatos();
        List<Especie> impopulares = especieService.impopulares();
        Assert.assertTrue(impopulares.isEmpty());
    }

    @After
    public void eliminarDatos(){
        dataService.eliminarDatos();
    }












}
