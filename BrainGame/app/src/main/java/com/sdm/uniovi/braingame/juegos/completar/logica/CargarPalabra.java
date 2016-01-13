package com.sdm.uniovi.braingame.juegos.completar.logica;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.sdm.uniovi.braingame.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CargarPalabra {

    private static CargarPalabra instancia = null;

    private CargarPalabra() {

    }

    public static CargarPalabra getInstancia() {
        if(instancia == null) {
            instancia = new CargarPalabra();
        }
        return instancia;
    }

    public ArrayList<String> cargarPalabras(Context context) {
        String archivoPalabras = leerXML(context, R.raw.palabras);
        ArrayList<String> palabras = cargarPalabrasXML(archivoPalabras);

        return palabras;
    }

    private String leerXML(Context context, int archivoPalabras) {
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(archivoPalabras);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String linea = bufferedReader.readLine();
            while (linea != null) {
                textoFicheroNivel += linea;
                linea = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception ex) {
        }
        return textoFicheroNivel;
    }

    public ArrayList<String> cargarPalabrasXML(String archivoPalabras) {
        ParseXML parser = new ParseXML();

        Document doc = parser.getDom(archivoPalabras);

        ArrayList<String> palabras = new ArrayList<String>();

        NodeList nodos = doc.getElementsByTagName("palabra");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String palabra = parser.getValor(elementoActual);

            palabras.add(palabra);
        }

        return palabras;
    }



}
