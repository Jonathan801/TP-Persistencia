package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.Neo4jDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.Neo4jDAOimpl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;

public class MapaServiceImpl implements MapaService {
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private Neo4jDAO neo4jUbicacionDAO;


    public MapaServiceImpl(){

    }


    public MapaServiceImpl(EntrenadorDAO entrenadorDAO, UbicacionDAO ubicacionDAO){
        this.entrenadorDAO = entrenadorDAO;
        this.ubicacionDAO = ubicacionDAO;
        this.neo4jUbicacionDAO = new Neo4jDAOimpl();


    }
    @Override
    public void mover(String entrenador, String ubicacion) {
        run(() ->{
            Entrenador entrenadorRecuperado = entrenadorDAO.recuperarEntrenador(entrenador);
            Ubicacion destino = ubicacionDAO.recuperarUbicacionNombre(ubicacion);
            Ubicacion origen=entrenadorRecuperado.getUbicacionActual();
            int dineroDeEntrenador=entrenadorRecuperado.getBilletera();
            int costoDeCamino=this.neo4jUbicacionDAO.costoDeCaminoLargoMasBarato(origen,destino);
            if(dineroDeEntrenador >= costoDeCamino){
                entrenadorRecuperado.setUbicacionActual(destino);
                entrenadorRecuperado.setBilletera(dineroDeEntrenador-costoDeCamino);
                entrenadorDAO.actualizar(entrenadorRecuperado);

            }else {
                throw new RuntimeException("CaminoMuyCostoso");
            }


        });

    }


    @Override
    public int cantidadEntrenadores(String ubicacion) {
       return run(() -> this.ubicacionDAO.cantDeEntrenadores(ubicacion));
    }

    @Override
    public Bicho campeon(String dojo) {
       return  run(() -> this.ubicacionDAO.campeon(dojo));
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        return run(()-> this.ubicacionDAO.campeonHistorico(dojo));
    }


    //NEO4J
    @Override
    public void moverMasCorto(String entrenador, String ubicacion) {
        run(() ->{
            Entrenador entrenador1 = entrenadorDAO.recuperarEntrenador(entrenador);
            Ubicacion origen = entrenador1.getUbicacionActual();
            Ubicacion destino = ubicacionDAO.recuperarUbicacionNombre(ubicacion);
            int costo  = this.neo4jUbicacionDAO.costoCamino(origen,destino);
            int dineroDeEntrenador = entrenador1.getBilletera();
            if(dineroDeEntrenador>= costo){
                entrenador1.setUbicacionActual(destino);
                dineroDeEntrenador = dineroDeEntrenador - costo;
                entrenador1.setBilletera(dineroDeEntrenador);
                entrenadorDAO.actualizar(entrenador1);
            }
            else {
                throw new RuntimeException("CaminoMuyCostoso");
            }
        });
    }


    @Override
    public void crearUbicacion(Ubicacion ubicacion) {
        run(() ->{
            this.ubicacionDAO.guardar(ubicacion);
            this.neo4jUbicacionDAO.crearUbicacion(ubicacion);
        });

    }

    @Override
    public void conectar(String ubicacion1, String ubicacion2, String tipoCamino) {
        run(()->{
            Ubicacion origen = this.ubicacionDAO.recuperarUbicacionNombre(ubicacion1);
            Ubicacion destino = this.ubicacionDAO.recuperarUbicacionNombre(ubicacion2);
            this.neo4jUbicacionDAO.conectar(origen,destino,tipoCamino);

        });

    }
    @Override
    public List<Ubicacion> conectados(String ubicacion, String tipoCamino) {
        return run(()->{
            Ubicacion ubicacion1 = this.ubicacionDAO.recuperarUbicacionNombre(ubicacion);
            List<String> ubicacionesConectadas = this.neo4jUbicacionDAO.conectados(ubicacion1,tipoCamino);
            return recuperarUbicaciones(ubicacionesConectadas);
        });

    }

    //O esta funcion podria estar dentro de conectados
    public List<Ubicacion>recuperarUbicaciones(List<String> ubicaciones){
            List<Ubicacion> ubicacionesRecuperadas = new ArrayList<>();
            for(String nombre : ubicaciones){
                ubicacionesRecuperadas.add(ubicacionDAO.recuperarUbicacionNombre(nombre));

            }
            return ubicacionesRecuperadas;


    }
}
