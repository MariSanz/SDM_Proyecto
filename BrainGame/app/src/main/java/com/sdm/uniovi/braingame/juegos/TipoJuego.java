package com.sdm.uniovi.braingame.juegos;

/**
 * Created by luism_000 on 25/11/2015.
 */
public enum TipoJuego {

    LEER,
    ORDENAR,
    CALCULAR,
    COMPLETAR,
    CONOCER,
    RECORDAR,
    CORRESPONDER;

    public String getIdServicio() {
        return name().toLowerCase();
    }
}
