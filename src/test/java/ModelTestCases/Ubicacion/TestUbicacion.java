package ModelTestCases.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.junit.Before;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

public class TestUbicacion {
    protected Guarderia guarderia;
    protected Especie pikachu;
    protected Especie charizard;
    protected Bicho pika;
    protected Bicho raichu;
    protected Bicho dragon;
    protected Entrenador ash;

    @Before
    public void setUp(){
        this.ash = new Entrenador("nombre",100,1);
        this.pikachu = new Especie("pikachu", TipoBicho.ELECTRICIDAD);
        this.charizard= new Especie("charizard",TipoBicho.FUEGO);
        this.pika=new Bicho(pikachu);
        this.dragon= new Bicho(charizard);
        this.raichu = new Bicho(pikachu);
        this.guarderia=new Guarderia("los abandonados");
        ash.addBicho(pika);
        ash.addBicho(raichu);
        ash.addBicho(dragon);
    }
}
