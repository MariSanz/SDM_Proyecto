package com.sdm.uniovi.braingame.ServicioWeb;


import com.sdm.uniovi.braingame.estadisticas.Puntuaciones;
import com.sdm.uniovi.braingame.usuarios.Usuario;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class ActualizarPuntuaciones extends ConexionServidor<Puntuaciones>{

    private String autorizacion;
    private String idJuego;

    private Usuario usuario;

    public ActualizarPuntuaciones(OnResultadoListener<Puntuaciones> onResultadoListener, String autorizacion, Usuario usuario, String idJuego) {
        super(onResultadoListener);
        this.autorizacion = autorizacion;
        this.usuario = usuario;
        this.idJuego = idJuego;
    }

    @Override
    protected Puntuaciones procesarRetorno(String respuesta) throws JSONException {
        return null;
    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return new URL(getUrlEnUso() + "/usuarios/comprobar-login");
    }
}
