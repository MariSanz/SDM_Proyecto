package com.sdm.uniovi.braingame.juegos.ordenar;

/**
 * Created by Eva on 27.12.2015.
 */
public enum OrdenarColor {

    AZUL,
    AMARILLO,
    ROJO,
    VERDE,
    NARANJA,
    MAGENTA;

    public static OrdenarColor getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
