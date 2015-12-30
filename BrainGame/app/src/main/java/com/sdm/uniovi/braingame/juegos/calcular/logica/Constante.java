package com.sdm.uniovi.braingame.juegos.calcular.logica;

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
}
