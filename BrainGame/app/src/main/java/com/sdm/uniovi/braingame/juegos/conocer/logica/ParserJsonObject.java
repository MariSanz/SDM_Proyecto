package com.sdm.uniovi.braingame.juegos.conocer.logica;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sdm.uniovi.braingame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParserJsonObject extends AppCompatActivity {

    private ArrayList<String> stringJson;
    private ArrayList<Pregunta> preguntas;


    public ParserJsonObject(){
        stringJson = new ArrayList<>();
        leerFichero();
        parserJsonObject();


    }

    private void leerFichero() {

        try
        {


            InputStream fraw =
                    getResources().openRawResource(R.raw.preguntasJuegoResponder);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            String linea = brin.readLine();
            stringJson.add(linea);

            fraw.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
    }

    private void parserJsonObject() {

        JSONObject obj = null;

        String categoria;
        String nombre;
        ArrayList<String> respuestasFalsas = new ArrayList<>();
        ArrayList<String> respuestasCorrectas = new ArrayList<>();

        Pregunta pregunta;

        try {

            for(int i=0; i<stringJson.size(); i++) {
                obj = new JSONObject(stringJson.get(i));



               // JSONArray array = obj.getJSONArray("category");
                categoria = obj.getString("category");
                nombre = obj.getString("text");
                JSONArray falsas = obj.getJSONArray("answersFalse");
                JSONArray correctas = obj.getJSONArray("answersTrue");
                for (int j = 0; i < falsas.length(); i++) {
                    respuestasFalsas.add(falsas.getJSONObject(i).toString());
                }
                for (int j = 0; i < correctas.length(); i++) {
                    respuestasCorrectas.add(correctas.getJSONObject(i).toString());
                }

                pregunta = new Pregunta(categoria,nombre, respuestasFalsas, respuestasCorrectas);
                preguntas.add(pregunta);


            }
    }catch (JSONException e) {
        e.printStackTrace();
    }
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }
}
