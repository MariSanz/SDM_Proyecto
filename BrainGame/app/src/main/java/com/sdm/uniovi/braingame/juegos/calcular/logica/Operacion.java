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

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : operacion.equals(otherName);
    }
    @Override
    public String toString() {

        return this.operacion;
    }
}
