package ar.edu.unq.epers.bichomon.backend.Dao.impl;


import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.Neo4jDAO;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

public class Neo4jDAOimpl implements Neo4jDAO {
    private Driver driver;


    public Neo4jDAOimpl() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
    }

    @Override
    public void crearUbicacion(Ubicacion ubicacion) {
        Session session = this.driver.session();

        try {
            String query = "MERGE (n:Ubicacion:tipoUbicacionConcreta {nombre: {elNombre}}) " +
                    "SET n.nombre = {elNombre} ";
            session.run(query, Values.parameters("elNombre", ubicacion.getNombre(),
                    "elNombre", ubicacion.getNombre(), "tipoUbicacionConcreta", ubicacion.getClass().getSimpleName()));
        } finally {
            session.close();
        }
    }

    @Override
    public void eliminarDatos(){
        Session session = this.driver.session();
        try {
            String query = "MATCH(R) DETACH DELETE R ";

            session.run(query);

        }finally {
            session.close();
        }
    }


    @Override
    public void conectar(Ubicacion origen, Ubicacion destino, String tipoCamino) {
        Session session = this.driver.session();


        try {
            String query = "MATCH (origen:Ubicacion {nombre: {elNombreOrigen}}) " +
                    "MATCH (destino:Ubicacion {nombre: {elNombreDestino}}) " +
                    "MERGE (origen)-[camino:"+tipoCamino+"]->(destino)" +
                    "SET camino.costoCamino = {costo}";
            session.run(query, Values.parameters("elNombreOrigen", origen.getNombre(),
                    "elNombreDestino", destino.getNombre(),"costo",this.obtenerCostoDeCamino(tipoCamino)));


        } finally {
            session.close();
        }

    }

    public int obtenerCostoDeCamino(String tipoCamino){
        switch(tipoCamino) {
            case "Terrestre": return 1;

            case "Maritimo": return 2;

            case  "Aereo": return 5;

            default: return 0;
        }
    }



    @Override
    public  List<String> conectados(Ubicacion ubicacion, String tipoCamino) {
        Session session = this.driver.session();
        List<String > destinos = new ArrayList<>();

        try {
            String query = "MATCH (ubicacion:Ubicacion {nombre: {elNombreOrigen}}) " +
                    "MATCH (ubicacion)-[:"+tipoCamino+"]->(destino) " +
                    "RETURN destino";
            StatementResult result = session.run(query, Values.parameters("elNombreOrigen", ubicacion.getNombre()));

            result.list().forEach(record ->{
                Value destino = record.get(0);
                String nombre = destino.get("nombre").asString();
                destinos.add(nombre);
            });

            return destinos;

        } finally {
            session.close();
        }
    }



    @Override
    public int costoCamino(Ubicacion ubicacion1, Ubicacion ubicacion2){

        Session session = this.driver.session();
        try {
            String query =
                    " MATCH p=shortestPath((bacon:Ubicacion {nombre:{origen}})-[*]->(meg:Ubicacion {nombre:{destino}}))" +
                            " WITH relationships(p) as caminos" +
                            " RETURN reduce(s=0,camino in caminos|s+camino.costoCamino) ";


            StatementResult result = session.run(query, Values.parameters("origen", ubicacion1.getNombre(),
                    "destino", ubicacion2.getNombre()));



//                 int r = result.list(record -> {
//                     Value i = record.get(0);
//                     return i;
//                 }).get(0).asNumber().intValue();
            // Boolean n = result.list().isEmpty();
                     if(result.hasNext()) {
                         return result.single().get(0).asInt();
                     }else{
                         throw new RuntimeException("UbicacionMuyLejana");
                     }
        } finally {
            session.close();
        }
    }

    @Override
    public int costoDeCaminoLargoMasBarato(Ubicacion ubicacionO, Ubicacion ubicacionD) {
        Session session = this.driver.session();
        try {
            String query =
                    " MATCH (o:Ubicacion{nombre: {origen}}) " +
                            " MATCH (d:Ubicacion{nombre: {destino}}) " +
                            " MATCH (o)-[r*]->(d) " +
                            " RETURN reduce(s=0,camino in min(r)|s+camino.costoCamino) ";
            StatementResult result = session.run(query, Values.parameters("origen",ubicacionO.getNombre(),
                    "destino",ubicacionD.getNombre()));


           if(result.hasNext()) {
                return result.single().get(0).asInt();
            }else{
                throw new RuntimeException("UbicacionMuyLejana");
            }
        } finally {
            session.close();
        }
    }
    @Override
    public List<String> caminosCortos(Ubicacion ubicacionO) {
        List<String > destinosVinculados = new ArrayList<>();
        Session session = this.driver.session();
        try {
            String query =
                    " MATCH (j:Ubicacion{nombre:{origen}}) -[*0..1]->(B)" +
                            "RETURN B";
            StatementResult result = session.run(query, Values.parameters("origen",ubicacionO.getNombre()));

            result.list().forEach(record ->{
                Value destino = record.get(0);
                String nombre = destino.get("nombre").asString();
                destinosVinculados.add(nombre);
            });

            return destinosVinculados;

        } finally {
            session.close();
        }
    }

}
