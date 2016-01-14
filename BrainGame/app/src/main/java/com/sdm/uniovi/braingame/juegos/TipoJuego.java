package com.sdm.uniovi.braingame.juegos;

/**
 * Created by luism_000 on 25/11/2015.
 */
public enum TipoJuego {

    RECORDAR,
    ORDENAR,
    LEER,
    CALCULAR,
    COMPLETAR,
    CONOCER,
    CORRESPONDER;

    public String getIdServicio() {
        return name().toLowerCase();
    }
}
