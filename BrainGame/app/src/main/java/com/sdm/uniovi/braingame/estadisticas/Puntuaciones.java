package com.sdm.uniovi.braingame.estadisticas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by luism_000 on 25/11/2015.
 */
public class Puntuaciones {

    private static final DateFormat DF_JSON = new SimpleDateFormat("yyyy-MM-dd");

    private List<Puntuacion> puntuaciones;

    public Puntuaciones(String json) throws JSONException {

        puntuaciones = new ArrayList<>();

        JSONArray jsonArrayEstadisticas = new JSONArray(json);
        for (int i = 0; i < jsonArrayEstadisticas.length(); i++) {
            try {
                JSONObject jsonObjectEstadistica = jsonArrayEstadisticas.getJSONObject(i);
                String usuario = jsonObjectEstadistica.getString("id_usuario");
                Date fecha = DF_JSON.parse(jsonObjectEstadistica.getString("fecha"));
                int puntos = jsonObjectEstadistica.getInt("valor");

                puntuaciones.add(new Puntuacion(usuario, fecha, puntos));

            } catch (ParseException e) {
                throw new JSONException("Fecha no tiene el formato correcto");
            }
        }
    }

    public List<Puntuacion> getPuntuaciones() {
        return puntuaciones;
    }
}
