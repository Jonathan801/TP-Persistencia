package ar.edu.unq.epers.bichomon.backend.service.runner;

import org.neo4j.driver.v1.*;

public class Neo4jRunner implements Runner {
    private Driver driver;
    private Session session;
    private Transaction tx;


    public Neo4jRunner() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
    }

    @Override
    public void begin() {
        session = this.driver.session();
        tx = session.beginTransaction();
    }

    @Override
    public void commit() {
        tx.success();
    }

    @Override
    public void close() {
        session.close();
        tx.close();
    }

    @Override
    public void rollback() {
        tx.failure();
    }
}
