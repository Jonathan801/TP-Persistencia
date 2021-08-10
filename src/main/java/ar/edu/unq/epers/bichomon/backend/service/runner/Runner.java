package ar.edu.unq.epers.bichomon.backend.service.runner;

public interface Runner {
    void begin();
    void commit();
    void close();
    void rollback();
}
