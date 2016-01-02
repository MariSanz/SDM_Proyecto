package com.sdm.uniovi.braingame.ServicioWeb;


import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.juegos.calcular.MainActivity;
import com.sdm.uniovi.braingame.usuarios.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ActualizarPuntuaciones extends ConexionServidor<Boolean> {

    private String autorizacion;
    private String idJuego;

    private String usuario;
    private int puntos;

    public ActualizarPuntuaciones(OnResultadoListener<Boolean> onResultadoListener, String autorizacion, String usuario, int puntos, String idJuego) {
        super(onResultadoListener);
        this.autorizacion = autorizacion;
        this.usuario = usuario;
        this.puntos=puntos;
        this.idJuego = idJuego;
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
        parametros.put("nameJuego", TipoJuego.CALCULAR.getIdServicio());

        return parametros;
    }
}
