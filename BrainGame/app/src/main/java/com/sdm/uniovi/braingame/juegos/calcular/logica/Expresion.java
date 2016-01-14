package com.sdm.uniovi.braingame.juegos.calcular.logica;

import java.util.Random;

public interface Expresion {



     double valor();
    String mostrar();
    Expresion getIncorrecta(Random r);
}
