package com.sdm.uniovi.braingame.servicioWeb;


import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ActualizarPuntuaciones extends ConexionServidor<Boolean> {

    private String autorizacion;
    private String nameJuego;


    private List<TipoJuego> tipos;

    private String usuario;
    private int puntos;

    public ActualizarPuntuaciones(OnResultadoListener<Boolean> onResultadoListener, String autorizacion, String usuario, int puntos, String nameJuego) {
        super(onResultadoListener);
        this.autorizacion = autorizacion;
        this.usuario = usuario;
        this.puntos=puntos;
        this.nameJuego = nameJuego;




    }




    @Override
    protected Boolean procesarRetorno(String respuesta) throws JSONException {
        JSONObject jsonObject = new JSONObject(respuesta);
        return jsonObject.getBoolean("valido");
    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return new URL(getUrlEnUso() + "/puntuaciones/insertar-puntuacion");
    }

    @Override
    protected JSONObject getParametros() throws JSONException {
        JSONObject parametros = new JSONObject();
        parametros.put(Usuario.CAMPO_NOMBRE, usuario);
        parametros.put("puntuacion", puntos);

        int numero = TipoJuego.valueOf(nameJuego).ordinal();
        parametros.put("nameJuego", numero);

        return parametros;
    }
}
