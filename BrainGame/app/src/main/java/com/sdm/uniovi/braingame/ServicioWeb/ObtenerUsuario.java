package com.sdm.uniovi.braingame.servicioWeb;


import com.sdm.uniovi.braingame.usuarios.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ObtenerUsuario extends ConexionServidor<String> {


    String id;

    public ObtenerUsuario(String id, OnResultadoListener<String> listener){
        super(listener);
        this.id = id;
    }

    @Override
    protected String procesarRetorno(String respuesta) throws JSONException {
        JSONObject jsonObject = new JSONObject(respuesta);
        return jsonObject.getString("nombre");
    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return new URL(getUrlEnUso() + "/usuarios/nombre");
    }

    @Override
    protected JSONObject getParametros() throws JSONException {

        JSONObject parametros = new JSONObject();
        parametros.put(Usuario.CAMPO_NOMBRE, id);


        return parametros;
    }
}
