package com.sdm.uniovi.braingame.ServicioWeb;

import com.sdm.uniovi.braingame.estadisticas.Puntuaciones;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by luism_000 on 26/11/2015.
 */
public class ServidorPuntuaciones extends ConexionServidor {

    private String mIdJuego;
    private int idUsuario = -1;

    private String metodo="puntuaciones";

    private String urlString;

    public ServidorPuntuaciones() {

    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }


    @Override
    protected Puntuaciones procesarRetorno(String respuesta) throws JSONException {
        return new Puntuaciones(respuesta);
    }

    @Override
    protected URL construirURL(String servicio) throws MalformedURLException {

            return new URL(urlString);

    }


    public void getPuntuacionesTodas(String juego) {

       this.urlString = URL_SW_DESARROLLO + "/" + metodo + "/" + mIdJuego;
    }

    public void getPuntuacionesUsuario(int idUsuario) {
        this.urlString = URL_SW_DESARROLLO + "/" + metodo + "/" + mIdJuego+"/"+idUsuario;
    }

    public void setPuntuacionesUsuario(String juego, int idUsuario){

    }
}
