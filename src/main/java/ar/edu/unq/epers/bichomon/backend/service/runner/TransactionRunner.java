package ar.edu.unq.epers.bichomon.backend.service.runner;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TransactionRunner {

    public static void run(Runnable bloque) {
        run(()->{
            bloque.run();
            return null;
        });
    }

    public static <T> T run(Supplier<T> bloque) {

        List<Runner> trs = new ArrayList<>();

        Runner hibernate = new HibernateRunner();
        Runner neo4j = new Neo4jRunner();

        trs.add(hibernate);
        trs.add(neo4j);

        try {
            trs.forEach(tr->tr.begin());
            T resultado = bloque.get();
            trs.forEach(tr->tr.commit());

            return resultado;
        } catch (RuntimeException e) {
            trs.forEach(tr->tr.rollback());
        } finally {
            trs.forEach(tr->tr.close());
        }
        return null;
    }
}