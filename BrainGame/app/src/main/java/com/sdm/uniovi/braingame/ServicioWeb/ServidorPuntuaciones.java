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

    public ServidorPuntuaciones() {
            setMetodo("puntuaciones");
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
    protected URL construirURL() throws MalformedURLException {

            return new URL(urlString);

    }


    public void getPuntuacionesTodas(String juego) {

       setUrlString(getUrlEnUso() + "/" + getMetodo() + "/" + mIdJuego);
    }

    public void getPuntuacionesUsuario(int idUsuario) {
       setUrlString(getUrlEnUso() +"/" + getMetodo() + "/" + mIdJuego+"/"+idUsuario);
    }

    public void setPuntuacionesUsuario(String juego, int idUsuario){
        //TODO
    }
}
