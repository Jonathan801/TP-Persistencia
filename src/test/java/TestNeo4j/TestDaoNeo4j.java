package TestNeo4j;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.Neo4jDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.Neo4jDAOimpl;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestDaoNeo4j {
    private Neo4jDAO neo4JDAO;

    @Before
    public void setUp() {
        this.neo4JDAO = new Neo4jDAOimpl();


    }

    @Test
    public void creo_Ubicaciones_() {
        //DOJO
        Ubicacion ubicacion = new Dojo("Dojo");
        this.neo4JDAO.crearUbicacion(ubicacion);
        //GUARDERIA
        Ubicacion ubicacion1 = new Dojo("Guarderia");
        this.neo4JDAO.crearUbicacion(ubicacion1);
        //PUEBLO
        Ubicacion ubicacion2 = new Dojo("Pueblo");
        this.neo4JDAO.crearUbicacion(ubicacion2);

    }

    @Test
    public void conecto_a_Dojo_Con_Guarderia_y_Pueblo_y_verifico_que_se_conecten() {
        Ubicacion dojo = new Dojo("Dojo");
        this.neo4JDAO.crearUbicacion(dojo);

        Ubicacion guarderia = new Dojo("Guarderia");
        this.neo4JDAO.crearUbicacion(guarderia);

        Ubicacion pueblo = new Dojo("Pueblo");
        this.neo4JDAO.crearUbicacion(pueblo);

        this.neo4JDAO.conectar(dojo, guarderia, "Terrestre");
        this.neo4JDAO.conectar(dojo, pueblo, "Terrestre");

        List<String> destinos = this.neo4JDAO.conectados(dojo,"Terrestre");
        Assert.assertEquals(2, destinos.size());

        Assert.assertEquals(pueblo.getNombre(), destinos.get(0));
        Assert.assertEquals(guarderia.getNombre(), destinos.get(1));
    }




    @Test
    public void costo_de_un_Camino(){
        Ubicacion dojo = new Dojo("Dojo");
        this.neo4JDAO.crearUbicacion(dojo);

        Ubicacion pueblo = new Dojo("Pueblo");
        this.neo4JDAO.crearUbicacion(pueblo);

        this.neo4JDAO.conectar(dojo,pueblo,"Terrestre");

        Assert.assertEquals(1,this.neo4JDAO.costoCamino(dojo,pueblo));
    }

    @Test
    public void no_hay_camino(){

        Ubicacion dojo = new Dojo("Dojimon");
        this.neo4JDAO.crearUbicacion(dojo);

        Ubicacion pueblo = new Dojo("Pueblomon");
        this.neo4JDAO.crearUbicacion(pueblo);

        this.neo4JDAO.conectar(dojo,pueblo,"Aereo");

        try {
            Assert.assertEquals(2,this.neo4JDAO.costoCamino(pueblo,dojo));
        }catch (RuntimeException e){
            assertThat(e.getMessage(),is("UbicacionMuyLejana"));
        }


    }
    @Test
    public void camino_terrestre_y_Aereo(){
        Ubicacion dojo = new Dojo("Dojo");
        this.neo4JDAO.crearUbicacion(dojo);

        Ubicacion pueblo = new Dojo("Pueblo");
        this.neo4JDAO.crearUbicacion(pueblo);


        Ubicacion guarderia = new Dojo("Guarderia");
        this.neo4JDAO.crearUbicacion(guarderia);


        this.neo4JDAO.conectar(dojo,pueblo,"Terrestre");
        this.neo4JDAO.conectar(pueblo,guarderia,"Aereo");
        Assert.assertEquals(6,this.neo4JDAO.costoCamino(dojo,guarderia));
    }
    //A veces puede romper, y no sabemos bien porque
    @Test
    public void precio_del_camino_mas_barato_entre_dojo_y_dojin(){
        Ubicacion paleta=new Pueblo("paleta");
        Ubicacion dojito=new Dojo("dojito");
        Ubicacion pueblito = new Pueblo("pueblito");
        this.neo4JDAO.crearUbicacion(paleta);
        this.neo4JDAO.crearUbicacion(dojito);
        this.neo4JDAO.crearUbicacion(pueblito);
        this.neo4JDAO.conectar(paleta,dojito,"Terrestre");
        this.neo4JDAO.conectar(paleta,dojito,"Maritimo");
        this.neo4JDAO.conectar(dojito,pueblito,"Maritimo");

      Assert.assertEquals(3,this.neo4JDAO.costoDeCaminoLargoMasBarato(paleta,pueblito));

    }


@After
public void eliminar_datos() {

    neo4JDAO.eliminarDatos();
}
}
