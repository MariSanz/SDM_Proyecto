package com.sdm.uniovi.braingame.estadisticas;

import java.util.Date;

public class Puntuacion {

    private String mUsuario;
    private Date mFecha;
    private int mPuntos;

    public Puntuacion(String usuario, Date fecha, int puntos) {
        mUsuario = usuario;
        mFecha = fecha;
        mPuntos = puntos;
    }

    public String getUsuario() {
        return mUsuario;
    }

    public Date getFecha() {
        return mFecha;
    }

    public int getPuntos() {
        return mPuntos;
    }

}