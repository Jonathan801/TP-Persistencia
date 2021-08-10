package ModelTestCases.registro;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.registro.Registro;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;

public class TestRegistro {
    private Registro registro;
    private Bicho pikachu;


    @Before
    public void setUp(){
        registro = new Registro();
        pikachu = new Bicho();
    }
    @Test
    public void pikachu_fue_campeon_diez_dias() throws ParseException {
        registro.setBicho(pikachu);
        LocalDate inicio = LocalDate.of(2019,10,4);
        registro.setFechaCoronacion(inicio);
        LocalDate descoronacion = LocalDate.of(2019,10,14);
        registro.setFechaDescoronacion(descoronacion);
        Assert.assertEquals(registro.tiempoQuefueCampeon(),10);
    }
}
