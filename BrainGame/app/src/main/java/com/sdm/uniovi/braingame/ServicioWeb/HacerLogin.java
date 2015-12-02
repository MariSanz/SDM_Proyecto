package com.sdm.uniovi.braingame.ServicioWeb;

import com.sdm.uniovi.braingame.usuarios.Usuario;
import com.sdm.uniovi.braingame.usuarios.Usuarios;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by luism_000 on 27/11/2015.
 */
public class HacerLogin extends ConexionServidor {

    private Usuario usuario;

    public HacerLogin(){
        setMetodo("usuarios");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }


    @Override
    protected Usuarios procesarRetorno(String respuesta) throws JSONException {
        return new Usuarios(respuesta);

    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return new URL(getUrlEnUso() + "/login");
    }

    @Override
    protected JSONObject getParametros() throws JSONException {

        JSONObject parametros = new JSONObject();
        parametros.put(Usuario.CAMPO_NOMBRE, usuario.getNombre());
        parametros.put(Usuario.CAMPO_CLAVE, usuario.getClave());

        return parametros;
    }


}
