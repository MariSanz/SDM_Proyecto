package com.sdm.uniovi.braingame.servicioWeb;

import com.sdm.uniovi.braingame.estadisticas.Puntuaciones;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class ObtenerTodasLasPuntuaciones extends ConexionServidor<Puntuaciones> {

    private String autorizacion;
    private String idJuego;

    public ObtenerTodasLasPuntuaciones(String idJuego, String autorizacion, OnResultadoListener<Puntuaciones> listener) {
        super(listener);
        this.idJuego = idJuego;
        this.autorizacion = autorizacion;
    }

    @Override
    protected Puntuaciones procesarRetorno(String respuesta) throws JSONException {
        return new Puntuaciones(respuesta);
    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return new URL(getUrlEnUso() + "/puntuaciones/"+ idJuego);
    }

    @Override
    public String getAutorizacion() {
        return autorizacion;
    }
}
