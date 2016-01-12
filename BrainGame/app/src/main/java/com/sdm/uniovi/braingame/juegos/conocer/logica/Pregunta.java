package com.sdm.uniovi.braingame.juegos.conocer.logica;

import java.util.ArrayList;

public class Pregunta {

    private String categoria;
    private String nombre;
    private ArrayList<String> respuestasFalsas;
    private ArrayList<String> respuestasCorrectas;

    public Pregunta(String categoria, String nombre, ArrayList<String> respuestasFalsas,
                    ArrayList<String> respuestasCorrectas) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.respuestasFalsas = respuestasFalsas;
        this.respuestasCorrectas = respuestasCorrectas;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<String> getRespuestasFalsas() {
        return respuestasFalsas;
    }

    public ArrayList<String> getRespuestasCorrectas() {
        return respuestasCorrectas;
    }
}
