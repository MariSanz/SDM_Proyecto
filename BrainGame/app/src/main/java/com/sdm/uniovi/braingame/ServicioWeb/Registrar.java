package com.sdm.uniovi.braingame.servicioWeb;

import com.sdm.uniovi.braingame.usuarios.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class Registrar extends ConexionServidor<Boolean> {

    private Usuario usuario;


    public Registrar(Usuario usuario, OnResultadoListener<Boolean> listener){
        super(listener);
        this.usuario = usuario;
    }

    @Override
    protected Boolean procesarRetorno(String respuesta) throws JSONException {
        JSONObject jsonObject = new JSONObject(respuesta);
        return jsonObject.getBoolean("valido");
    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return new URL(getUrlEnUso() + "/usuarios/registrar");
    }

    @Override
    protected JSONObject getParametros() throws JSONException {

        JSONObject parametros = new JSONObject();
        parametros.put(Usuario.CAMPO_NOMBRE, usuario.getNombre());
        parametros.put(Usuario.CAMPO_CLAVE, usuario.getClave());

        return parametros;
    }


}
