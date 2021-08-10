package TestServices;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateDataDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateUbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.entrenador.EntrenadorServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
public class TestServiceMapaImpl {
    private MapaService mapaServiceImpl;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private DataServiceImpl dataService;
    private Ubicacion pueblo;

    private Ubicacion paleta;
    private EntrenadorServiceImpl entrenadorService;




    @Before
    public void setUp(){
        entrenadorDAO = new HibernateEntrenadorDAO();
        ubicacionDAO =  new HibernateUbicacionDAO();
        mapaServiceImpl = new MapaServiceImpl(entrenadorDAO,ubicacionDAO);
        dataService = new DataServiceImpl(new HibernateDataDAO());
        dataService.crearSetDatosIniciales();
        pueblo = new Dojo("Pueblo");
        paleta = new Dojo("Paleta");
        entrenadorService = new EntrenadorServiceImpl(entrenadorDAO);

    }

    //TEST CON NEO4J
    @Test
    public void creo_Ubicaciones_tanto_en_neo4j_como_en_Hibernate(){
        this.mapaServiceImpl.crearUbicacion(pueblo);
        this.mapaServiceImpl.crearUbicacion(paleta);


        this.mapaServiceImpl.conectar("Paleta","Pueblo","Terrestre");
        this.mapaServiceImpl.conectar("Pueblo","Guarderia","Terrestre");

        List<Ubicacion> conectadas = this.mapaServiceImpl.conectados("Pueblo","Terrestre");
        Assert.assertEquals(1,conectadas.size());
        List<Ubicacion> conectadasPaleta = this.mapaServiceImpl.conectados("Paleta","Terrestre");
        Assert.assertEquals(1,conectadasPaleta.size());

    }

    @Test
    public void brock_se_mueve_Por_el_camino_mas_corto_a_Pueblomon(){
        Ubicacion dojimoon = new Dojo("Dojimoon");
        Ubicacion pueblomon = new Dojo("Pueblomon");

        this.mapaServiceImpl.crearUbicacion(dojimoon);
        this.mapaServiceImpl.crearUbicacion(pueblomon);

        this.mapaServiceImpl.conectar("Dojimoon","Pueblomon","Aereo");
        this.mapaServiceImpl.conectar("Dojo","Pueblomon","Terrestre");
        this.mapaServiceImpl.moverMasCorto("Brock","Pueblomon");

        Entrenador brockRecuperado = entrenadorService.buscarEntrenador("Brock");
        Assert.assertEquals(199,brockRecuperado.getBilletera());

        Assert.assertEquals("Pueblomon",brockRecuperado.getUbicacionActual().getNombre());


    }

    @Test
    public void camino_muy_costoso(){
        Ubicacion pueblomon = new Dojo("Pueblomon");
        try {

            this.mapaServiceImpl.crearUbicacion(pueblomon);

            this.mapaServiceImpl.conectar("Dojo", "Pueblomon", "Aereo");

            this.mapaServiceImpl.moverMasCorto("Ash", "Pueblomon");

        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("CaminoMuyCostoso"));

        }


    }

    @Test()
    public void camino_muy_Lejano(){
        Ubicacion pueblomon = new Dojo("Pueblomon");
        try {
            this.mapaServiceImpl.crearUbicacion(pueblomon);


            this.mapaServiceImpl.moverMasCorto("Ash","Pueblomon");

        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("UbicacionMuyLejana"));

        }

    }

    @Test
    public void no_se_puede_mover_a_una_ubicacion_que_no_existe(){
        try {
            this.mapaServiceImpl.moverMasCorto("Ash","Alola");
        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("No esta persistido en la BD"));

        }


    }


    @Test
    public void ash_se_mueve_de_Guarderia_a_Dojo_y_elige_el_camino_mas_barato(){
        this.mapaServiceImpl.crearUbicacion(this.paleta);
        this.mapaServiceImpl.conectar("Guarderia","Paleta","Terrestre");
        this.mapaServiceImpl.conectar("Guarderia","Paleta","Aereo");
        this.mapaServiceImpl.conectar("Paleta","Dojo","Terrestre");
        this.mapaServiceImpl.mover("Ash","Dojo");
        Entrenador ashrecuperado = this.entrenadorService.buscarEntrenador("Ash");
        Assert.assertEquals("Dojo",ashrecuperado.getUbicacionActual().getNombre());
        Assert.assertEquals(6,ashrecuperado.getBilletera());
        Assert.assertEquals("Dojo",ashrecuperado.getUbicacionActual().getNombre());
    }

    @Test
    public void ash_no_se_puede_mover_de_dojo_a_Guarderia(){
        try{
            this.mapaServiceImpl.crearUbicacion(this.paleta);

            this.mapaServiceImpl.conectar("Dojo","Paleta","Aereo");
            this.mapaServiceImpl.conectar("Dojo","Paleta","Aereo");
            this.mapaServiceImpl.conectar("Paleta","Guarderia","Aereo");
            this.mapaServiceImpl.mover("Ash","Guarderia");


        }
        catch (RuntimeException e){
            assertThat(e.getMessage(),is("CaminoMuyCostoso"));

        }
    }


    @Test
    public void conectados(){
        this.mapaServiceImpl.crearUbicacion(this.paleta);

        List<Ubicacion> conectadosDojo = this.mapaServiceImpl.conectados("Dojo","Aereo");
        Assert.assertEquals(0,conectadosDojo.size());
        this.mapaServiceImpl.conectar("Dojo","Paleta","Aereo");
        this.mapaServiceImpl.conectar("Dojo","Guarderia","Aereo");
        List<Ubicacion> conectadosADojo = this.mapaServiceImpl.conectados("Dojo","Aereo");
        Assert.assertEquals(2,conectadosADojo.size());
        Assert.assertEquals("Guarderia",conectadosADojo.get(0).getNombre());
        Assert.assertEquals("Paleta",conectadosADojo.get(1).getNombre());

    }



    @After
    public void eliminarDatos(){

        dataService.eliminarDatos();


    }

}
