package com.sdm.uniovi.braingame.juegos.calcular.logica;


public enum Operacion {

    SUMA("+"),
    RESTA("-"),
    MULTIPLICACION("x"),
    DIVISION("/");

    private final String operacion;


    Operacion(String operacion) {

        this.operacion = operacion;
    }

    @Override
    public String toString() {

        return this.operacion;
    }
}
