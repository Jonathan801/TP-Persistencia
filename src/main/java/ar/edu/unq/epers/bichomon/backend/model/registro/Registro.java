package ar.edu.unq.epers.bichomon.backend.model.registro;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "REGISTRO_ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Bicho bicho;


    @Column(name = "fechaCoronacion")
    private LocalDate fechaCoronacion ;

    @Column(name = "fechaDescoronacion")
    private LocalDate fechaDescoronacion;

    public Registro(){
        this.fechaCoronacion = LocalDate.now();
        this.fechaDescoronacion = null;

    }

    public void setBicho(Bicho bicho){
        this.bicho = bicho;
    }

    public Bicho getBicho(){return this.bicho;}

    public void setFechaCoronacion(LocalDate fechaCoronacion){ this.fechaCoronacion = fechaCoronacion;}

    public void setFechaDescoronacion(LocalDate fechaDescoronacion){ this.fechaDescoronacion = fechaDescoronacion; }


    public int tiempoQuefueCampeon() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaInicial = dateFormat.parse(this.fechaString(fechaCoronacion));
        Date fechaFinal = dateFormat.parse(this.fechaString(fechaDescoronacion));

        int dias = (int) ((fechaFinal.getTime() - fechaInicial.getTime()) / 86400000);
        return dias;
    }


    public String fechaString(LocalDate localDate){
        Integer dia = localDate.getDayOfMonth();
        Integer mes = localDate.getMonth().getValue();
        Integer anio = localDate.getYear();
        String fecha = anio.toString()+"-"+mes.toString()+"-"+dia.toString();
        return fecha;
    }

}
