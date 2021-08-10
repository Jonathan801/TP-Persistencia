package TransactionRunner;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateDataDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImpl;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.Neo4jDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.Neo4jDAOimpl;
import ar.edu.unq.epers.bichomon.backend.service.ubicacion.UbicacionServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TransactionRunnerTestCase {
    private MapaService mapaServiceImpl;
    private UbicacionServiceImpl ubicacionService;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private EntrenadorServiceImpl entrenadorService;
    private DataServiceImpl dataService;
    private Neo4jDAO neo4JDAO;
    private HibernateDataDAO dataDAO;

    @Before
    public void setUp(){
        entrenadorDAO = new HibernateEntrenadorDAO();
        ubicacionDAO =  new HibernateUbicacionDAO();
        ubicacionService = new UbicacionServiceImpl(ubicacionDAO);
        mapaServiceImpl = new MapaServiceImpl(entrenadorDAO,ubicacionDAO);
        entrenadorService = new EntrenadorServiceImpl(entrenadorDAO);
        dataDAO = new HibernateDataDAO();
        dataService = new DataServiceImpl(dataDAO);
        dataService.crearSetDatosIniciales();
        neo4JDAO = new Neo4jDAOimpl();
    }

    @Test
    public void persistir_algo_que_ya_existe_a_la_segunda_no_guarda_en_ninguna_BD() {
        Ubicacion dojo = new Dojo("Dojimoon");
        Ubicacion pueblo = new Dojo("Pueblomon");
        Ubicacion guarderia = new Dojo("Guarderiamon");

        //Creo ubicaciones duplicadas.
        this.mapaServiceImpl.crearUbicacion(dojo);
        this.mapaServiceImpl.crearUbicacion(dojo);
        this.mapaServiceImpl.crearUbicacion(pueblo);
        this.mapaServiceImpl.crearUbicacion(pueblo);
        this.mapaServiceImpl.crearUbicacion(guarderia);
        this.mapaServiceImpl.crearUbicacion(guarderia);

        this.mapaServiceImpl.conectar("Dojimoon","Pueblomon","Terrestre");
        this.mapaServiceImpl.conectar("Dojimoon","Guarderiamon","Terrestre");

        List<Ubicacion> conectadas = this.mapaServiceImpl.conectados("Dojimoon","Terrestre");
        Assert.assertEquals(2,conectadas.size());
    }

    @After
    public void eliminarDatos(){

        this.dataService.eliminarDatos();


    }
}
