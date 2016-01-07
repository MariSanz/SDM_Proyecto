package com.sdm.uniovi.braingame.juegos.completar.logica;

import android.content.Context;

import com.sdm.uniovi.braingame.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public String[] cargarPalabras(Context context) {
        String archivoPalabras = leerXML(context, R.raw.palabras);
        String [] palabras = cargarPalabrasXML(context, archivoPalabras);

        return palabras;
    }

    private String leerXML(Context context, int recursoNivel) {
        String textoFicheroNivel = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    recursoNivel);
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

    public String[] cargarPalabrasXML(Context context, String archivoPalabras) {
        ParseXML parser = new ParseXML();

        Document doc = parser.getDom(archivoPalabras);

        String [] palabras = {};

        NodeList nodos = doc.getElementsByTagName("palabras");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elementoActual = (Element) nodos.item(i);
            String palabra = parser.getValor(elementoActual, "palabra");

            palabras[i] = palabra;
        }

        return palabras;
    }



}
