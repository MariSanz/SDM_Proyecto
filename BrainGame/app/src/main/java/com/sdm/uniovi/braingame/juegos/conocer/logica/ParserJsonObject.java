package com.sdm.uniovi.braingame.juegos.conocer.logica;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sdm.uniovi.braingame.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParserJsonObject {

    private final Context contexto;
    private ArrayList<String> stringJson;
    private ArrayList<Pregunta> preguntas = new ArrayList<>();



    public ParserJsonObject(Context contexto){
        this.contexto = contexto;
        stringJson = new ArrayList<>();
        leerFichero();
        parserJsonObject();
    }

    private void leerFichero() {

        try
        {
            InputStream fraw = //openFileInput("preguntas_juego_responder.txt");
                    contexto.getResources().openRawResource(R.raw.preguntas_juego_responder);


            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            String linea;
            while((linea = brin.readLine()) != null){
                stringJson.add(linea);
            }


            fraw.close();

        }
        catch (Exception ex)
        {
            //"Ficheros", "Error al leer fichero desde recurso raw"
            ex.printStackTrace();
            //Log.e(ex.printStackTrace());
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
                respuestasFalsas.clear();
                respuestasCorrectas.clear();


               // JSONArray array = obj.getJSONArray("category");
                categoria = obj.getString("category");
                nombre = obj.getString("text");
                JSONArray falsas = obj.getJSONArray("answersFalse");
                JSONArray correctas = obj.getJSONArray("answersTrue");
                for (int j = 0; j < falsas.length(); j++) {
                    //respuestasFalsas.add(falsas.getJSONObject(i).toString());
                    respuestasFalsas.add(falsas.get(j).toString());
                }
                for (int k = 0; k < correctas.length(); k++) {
                    respuestasCorrectas.add(correctas.get(k).toString());
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
