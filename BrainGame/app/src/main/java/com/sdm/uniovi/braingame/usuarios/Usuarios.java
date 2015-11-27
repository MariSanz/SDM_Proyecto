package com.sdm.uniovi.braingame.usuarios;

import android.util.Log;

import com.sdm.uniovi.braingame.estadisticas.Puntuacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luism_000 on 27/11/2015.
 */
public class Usuarios {

    private List<Usuario> usuarios;

    public Usuarios(String json) {

        usuarios = new ArrayList<>();

        JSONArray jsonArrayUsuario = null;
        try {
            jsonArrayUsuario = new JSONArray(json);

            for (int i = 0; i < jsonArrayUsuario.length(); i++) {
                JSONObject jsonObjectUsuario = jsonArrayUsuario.getJSONObject(i);
                String nombre = jsonObjectUsuario.getString("nombre");

                String clave = jsonObjectUsuario.getString("clave");

                usuarios.add(new Usuario(nombre, clave));
            }


        } catch (JSONException e) {
            Log.v("ERROR", "Error al parsear");
        }
    }
}
