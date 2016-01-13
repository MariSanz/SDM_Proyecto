package com.sdm.uniovi.braingame.juegos.completar.logica;

import java.util.ArrayList;
import java.util.Random;

public class GeneradorPalabras {

    public static String generarPalabra(ArrayList<String> palabras,
                                                   ArrayList<String> palabrasUsadas) {
        Random generador = new Random();
        boolean palabraUsada = true;
        while(palabraUsada) {
            int posicion = generador.nextInt(palabras.size());
            String palabra = palabras.get(posicion);
            if(!palabrasUsadas.contains(palabra)) {
                palabrasUsadas.add(palabra);
                return palabra;
            }

            if(palabras.size() == palabrasUsadas.size()) {
                palabraUsada = false;
            }
        }
        return "";
    }
}
