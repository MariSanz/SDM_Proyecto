package com.sdm.uniovi.braingame.juegos;

/**
 * Created by luism_000 on 25/11/2015.
 */
public enum TipoJuego {

    LEER,
    ORDENAR,
    CALCULAR,
    RECORDAR;

    public String getIdServicio() {
        return name().toLowerCase();
    }
}