package ar.edu.unq.epers.bichomon.backend.service.especie;

import java.util.List;

//import ar.edu.unq.epers.bichomon.backend.jdbc.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import static ar.edu.unq.epers.bichomon.backend.service.runner.TransactionRunner.run;


public class EspecieServiceImpl implements EspecieService {

	private EspecieDAO especieDAO;

	public EspecieServiceImpl(EspecieDAO dao){
		this.especieDAO = dao;
	}



	@Override
	public void crearEspecie(Especie especie){

		especieDAO.guardar(especie);
	}


	

	@Override
	public Especie getEspecie(String nombreEspecie){
		Especie especie = especieDAO.recuperarPorNombre(nombreEspecie);
		if(especie == null){
			throw new EspecieNoExistente(nombreEspecie);
		}
		return especie;
	}


/*
	@Override
	public List<Especie> getAllEspecies(){
		return especieDAO.recuperarTodos();
	}

 */




	@Override
	public Bicho crearBicho(String nombreEspecie){
		Especie especie = especieDAO.recuperarPorNombre(nombreEspecie);
		Bicho bicho = especie.crearBicho();
		especieDAO.actualizar(especie);
		return bicho;
	}

    @Override
    public List<Especie> populares() {
        return run(()->this.especieDAO.populares());
    }

    @Override
    public List<Especie> impopulares() {
        return run(()-> this.especieDAO.impopulares());
    }
}

