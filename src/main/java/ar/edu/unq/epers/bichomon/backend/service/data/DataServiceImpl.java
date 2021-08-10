package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.*;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ManejadorDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImpl;
import org.javatuples.Triplet;

import java.time.LocalDate;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;


public class DataServiceImpl implements DataService {

    private EntrenadorDAO daoEntrenador;
    private BichoDAO daoBicho;
    private DataDAO daoData;
    private BichoService bichoService;
    private Entrenador ash;
    private Ubicacion guarderia;
    private Bicho bicho2;
    private Bicho bicho1 ;
    private Especie pika ;
    private Bicho raichu1;
    private Bicho bolbazor;
    private Especie raichu;
    //BROCK
    private Entrenador brock;
    private Dojo dojo;
    private Bicho bicho3;
    private Bicho bicho4;
    private Especie terrakion;
    //Entrenador en Dojo MISTY
    private Entrenador misty;
    private Bicho bicho5;
    private Especie meowth;
    private Especie meo;
    private Dojo dojin;
    private UbicacionDAO ubicacionDAO;
    private Entrenador joni;
    private Bicho pichoto;
    private Especie avion;
    private Dojo dojimon;
    //Bichos Para la Guarderia
    private Bicho venonat;
    private Bicho paras;
    private Bicho vulpix;
    private Bicho zubat;
    private ManejadorDeExperiencia manejadorDeExperiencia;
    //Neo4j
    private Neo4jDAO neo4jDAO;


    public DataServiceImpl(DataDAO dataDAO){
        this.daoData = dataDAO;
        this.neo4jDAO = new Neo4jDAOimpl();
    }
    public DataServiceImpl(){
        this.neo4jDAO = new Neo4jDAOimpl();

    }


    @Override
    public void eliminarDatos() {
        run(()->
                this.daoData.clear());
        this.neo4jDAO.eliminarDatos();
    }

    @Override
    public void crearSetDatosIniciales() {
        run(() -> {
            //Manejador
            Triplet<Integer,Integer,Integer> nivel1= Triplet.with(1,10,1000);
            Triplet<Integer,Integer,Integer> nivel2= Triplet.with(2,40,2000);
            manejadorDeExperiencia = new ManejadorDeExperiencia();
            manejadorDeExperiencia.agregarNivelConLimiteDeExperienciaYCantidadDeBichos(nivel1);
            manejadorDeExperiencia.agregarNivelConLimiteDeExperienciaYCantidadDeBichos(nivel2);
            //DAOS
            daoData = new HibernateDataDAO();
            daoBicho = new HibernateBichoDAO();
            daoEntrenador = new HibernateEntrenadorDAO();
            ubicacionDAO = new HibernateUbicacionDAO();
            //SERVICE
            bichoService = new BichoServiceImpl(daoEntrenador,daoBicho,daoData);
            //ESPECIES
            pika = new Especie("Pikachu",TipoBicho.ELECTRICIDAD);

            raichu = new Especie("Raichu",TipoBicho.ELECTRICIDAD);
            terrakion = new Especie("Terrakion",TipoBicho.TIERRA);

            meowth = new Especie("Meowth",TipoBicho.AIRE);
            pika.setEspecieSiguiente(raichu);

            avion = new Especie("Avion",TipoBicho.AIRE);
//Dojin esta Joni
//Dojo est
            //BICHOS
            bicho1 = new Bicho(pika);
            bicho2 = new Bicho(avion);
            bolbazor= new Bicho(pika);
            raichu1 = new Bicho(pika);
            bicho3 = new Bicho(terrakion);
            bicho4 = new Bicho(terrakion);
            bicho5 = new Bicho(meowth);
            pichoto = new Bicho(avion);
            paras = new Bicho(terrakion);
            venonat = new Bicho(avion);
            vulpix = new Bicho(terrakion);
            zubat = new Bicho(terrakion);
            //UBICACIONES

            guarderia = new Guarderia("Guarderia");
            guarderia.agregarBicho(bolbazor);//Pikachu
            guarderia.agregarBicho(raichu1);//Pikachu
            guarderia.agregarBicho(paras); //Terrakion
            guarderia.agregarBicho(venonat);//Avion
            guarderia.agregarBicho(vulpix);//Terrakion
            guarderia.agregarBicho(zubat);
            neo4jDAO.crearUbicacion(guarderia);
            dojo = new Dojo("Dojo");
            neo4jDAO.crearUbicacion(dojo);
            dojin = new Dojo("Dojin");
            neo4jDAO.crearUbicacion(dojin);
            dojimon = new Dojo("Dojimon");
            LocalDate coronacionBicho2 = LocalDate.now();
            dojimon.setCampeon(bicho2,coronacionBicho2);
            neo4jDAO.crearUbicacion(dojimon);

            //ENTRENADORES
            //Ash
            ash = new Entrenador("Ash",1,2);
            ash.setUbicacionActual(guarderia);
            ash.addBicho(bicho1);
            ash.addBicho(bicho2);
            ash.setBilletera(8);
            ash.setManejadorDeExperiencia(manejadorDeExperiencia);
            dojimon.setEntrenadorCampeon(ash);
            //Joni
            joni = new Entrenador("Joni",2,2);
            joni.setUbicacionActual(dojin);
            joni.addBicho(pichoto);
            joni.setManejadorDeExperiencia(manejadorDeExperiencia);
            dojin.setEntrenadorCampeon(joni);
            LocalDate coronacionDePichoto = LocalDate.of(2015,6,10);
            dojin.setCampeon(pichoto,coronacionDePichoto);

            //Misty
            misty = new Entrenador("Misty",2,2);
            misty.addBicho(bicho5);
            misty.setManejadorDeExperiencia(manejadorDeExperiencia);
            misty.setBilletera(10);
            bicho5.setEnergia(1);
            bicho2.setEnergia(100);
            bicho1.setEnergia(9);
            bicho3.setEnergia(22);
            LocalDate coronacionDeBicho5 = LocalDate.of(2019,6,10);
            dojo.setCampeon(bicho5,coronacionDeBicho5);
            dojo.setEntrenadorCampeon(misty);
            //Brock
            brock = new Entrenador("Brock",3,2);
            brock.addBicho(bicho3);
            brock.addBicho(bicho4);
            brock.setBilletera(200);
            brock.setManejadorDeExperiencia(manejadorDeExperiencia);
            bicho4.setEnergia(10000000);
            brock.setUbicacionActual(dojo);
            //PERSISTENCIA
            daoBicho.guardar(bicho1);
            daoBicho.guardar(bicho2);
            daoBicho.guardar(bicho3);
            daoBicho.guardar(bicho4);
            daoBicho.guardar(bicho5);
            daoEntrenador.guardar(ash);
            daoEntrenador.guardar(brock);
            daoEntrenador.guardar(misty);
            daoEntrenador.guardar(joni);
            ubicacionDAO.guardar(dojin);
            ubicacionDAO.guardar(dojimon);
            ubicacionDAO.guardar(guarderia);

        });
    }


}

