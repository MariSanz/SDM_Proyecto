package com.sdm.uniovi.braingame.juegos.calcular.logica;

import java.util.Random;

public class Constante implements Expresion {

    private int valor;

    public Constante(int valor) {
        this.valor = valor;
    }

    @Override
    public double valor() {
        return valor;
    }

    @Override
    public String mostrar() {

        return String.valueOf(valor);
    }

    @Override
    public Expresion getIncorrecta(Random r) {
        if (r.nextBoolean()) {
            return new Constante(valor + 1);
        }
        else {
            return new Constante(valor - 1);
        }
    }
}
