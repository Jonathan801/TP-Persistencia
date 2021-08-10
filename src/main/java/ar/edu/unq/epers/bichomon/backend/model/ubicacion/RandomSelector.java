package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSelector {

    private List<ProbabilidadDeEspecie> items;
    private double p = Math.random();
    private double cumulativeProbability = 0.0;
    private Random rand = new Random();

    public int numberRandom(){
        return rand.nextInt(301);
    }


    public RandomSelector(List<ProbabilidadDeEspecie> list) {
        this.items = list;
    }

    public Especie randomEspecie() {
        for (ProbabilidadDeEspecie item : items) {
            cumulativeProbability += item.getProbabilidad();
            if (p <= cumulativeProbability) {
                return item.getEspecie();
            }
        }
        return null; // es para que no llore la puta de intelijj
    }
}
