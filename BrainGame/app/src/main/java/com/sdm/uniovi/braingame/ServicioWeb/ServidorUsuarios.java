package com.sdm.uniovi.braingame.ServicioWeb;

import com.sdm.uniovi.braingame.usuarios.Usuario;
import com.sdm.uniovi.braingame.usuarios.Usuarios;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by luism_000 on 27/11/2015.
 */
public class ServidorUsuarios extends ConexionServidor {

    private Usuario usuario;

    public ServidorUsuarios(){
        setMetodo("usuarios");
    }


    @Override
    protected Usuarios procesarRetorno(String respuesta) throws JSONException {
        return new Usuarios(respuesta);

    }

    @Override
    protected URL construirURL() throws MalformedURLException {
        return null;
    }

    public void buscarUsuario(String nombre, String clave){

        usuario = new Usuario(nombre, clave);

        setUrlString(getUrlEnUso() +"/" + getMetodo() + "/" + usuario.getNombre()+"/"+usuario.getClave());

        //TODO

    }


}
