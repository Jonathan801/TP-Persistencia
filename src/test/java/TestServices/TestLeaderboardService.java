package TestServices;

import ar.edu.unq.epers.bichomon.backend.Dao.dao.Leaderboard;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateLeaderboardDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.leaderBoard.LeaderboardService;
import ar.edu.unq.epers.bichomon.backend.service.leaderBoard.LeaderboardServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.util.List;

public class TestLeaderboardService {
    private LeaderboardService leaderboardService;
    private DataService dataService;
    private Leaderboard daoLeaderboard;

    @Before
    public void setUp(){
        daoLeaderboard = new HibernateLeaderboardDAO();
        leaderboardService = new LeaderboardServiceImpl(daoLeaderboard);
        dataService = new DataServiceImpl();
        dataService.crearSetDatosIniciales();
    }
    @Test
    public void entrenadores_campeones(){
        List<Entrenador> entrenadores = leaderboardService.campeones();
        Assert.assertEquals(entrenadores.size(),3);
        Assert.assertEquals("Joni",entrenadores.get(0).getNombre());
        Assert.assertEquals("Misty",entrenadores.get(1).getNombre());
        Assert.assertEquals("Ash",entrenadores.get(2).getNombre());

    }

    @Test
    public void si_no_hay_campeones_devuelve_lista_vacia() {
        dataService.eliminarDatos();
        List<Entrenador> entrenadores = leaderboardService.campeones();
        Assert.assertTrue(entrenadores.isEmpty());
    }

    @Test
    public void especie_lider() {
        Especie especieLider = leaderboardService.especieLider();
        Assert.assertEquals("Avion",especieLider.getNombre());
    }

    @Test
    public void no_hay_especie_lider() {
        dataService.eliminarDatos();
        Especie especie = leaderboardService.especieLider();
        Assert.assertNull(especie);
    }

    @Test
    public void lideres(){
        List<Entrenador> lideres = leaderboardService.lideres();
        Assert.assertEquals(4,lideres.size());
    }

    @Test
    public void no_hay_lideres_y_devuelve_una_lista_vacia() {
        dataService.eliminarDatos();
        List<Entrenador> entrenadoresLideres = leaderboardService.lideres();
        Assert.assertTrue(entrenadoresLideres.isEmpty());
    }

    @Test
    public void los_lideres_en_orden_correcto(){
        List<Entrenador> lideres = leaderboardService.lideres();
        Assert.assertEquals("Brock",lideres.get(0).getNombre());
        Assert.assertEquals("Ash",lideres.get(1).getNombre());
        Assert.assertEquals("Misty",lideres.get(2).getNombre());
        Assert.assertEquals("Joni",lideres.get(3).getNombre());
    }
    @After
    public void limpiar(){

        dataService.eliminarDatos();
    }
}
