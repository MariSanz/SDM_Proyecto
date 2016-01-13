package com.sdm.uniovi.braingame.juegos.completar;

import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.juegos.completar.logica.CargarPalabra;
import com.sdm.uniovi.braingame.juegos.completar.logica.GeneradorPalabras;

import java.util.ArrayList;
import java.util.Random;

public class JuegoCompletar extends AppCompatActivity {

    private TextView textViewPuntos;
    private TextView textViewPalabras;
    private TextView textViewTiempo;
    private TextView textViewPuntosObtenidos;
    private TextView textViewPalabrasNumero;
    private CountDownTimer countdown;

    private TextView textViewPalabra;

    private LinearLayout linearLayoutPalabra;


    private Button botonGenerar;

    private ArrayList<String> palabras;
    private ArrayList<String> palabrasUsadas = new ArrayList<String>();
    private int puntos = 0;

    private  Typeface estiloLetra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_completar);

        textViewPuntos = (TextView) findViewById(R.id.textViewPuntosCompletar);
        textViewPalabras = (TextView) findViewById(R.id.textViewPalabrasAcertadas);
        textViewTiempo = (TextView) findViewById(R.id.textViewTiempoCompletar);
        textViewPuntosObtenidos = (TextView) findViewById(R.id.textViewPuntosNumero);
        textViewPalabrasNumero = (TextView) findViewById(R.id.textViewPalabrasNumero);

        linearLayoutPalabra = (LinearLayout) findViewById(R.id.layoutPalabra);

        botonGenerar = (Button) findViewById(R.id.buttonGenerar);

        estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        textViewPuntos.setTypeface(estiloLetra);
        textViewPalabras.setTypeface(estiloLetra);
        textViewTiempo.setTypeface(estiloLetra);
        textViewPuntosObtenidos.setTypeface(estiloLetra);
        textViewPalabrasNumero.setTypeface(estiloLetra);
        textViewPalabra.setTypeface(estiloLetra);

        botonGenerar.setTypeface(estiloLetra);

        palabras = CargarPalabra.getInstancia().cargarPalabras(getApplicationContext());

        countdown = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished) {
                textViewTiempo.setText(String.valueOf(millisUntilFinished / 1000));
                puntos+=10;
                textViewPuntosObtenidos.setText(""+puntos);
            }

            public void onFinish() {
                textViewTiempo.setText("Se ha agotado el tiempo");
            }
        }.start();

        String palabra = generar();

        char[] caracteres = palabra.toCharArray();

        for(int i=1; i<=2; i++) {
            TextView tV = (TextView) findViewById(R.id.textView+i);
            linearLayoutPalabra.addView(tV);

        }


    }

    public String generar() {
        String palabra = GeneradorPalabras.generarPalabra(palabras, palabrasUsadas);

        if(!palabra.equals("")) {
            textViewPalabra.setText(palabra);
        }
        else
            botonGenerar.setEnabled(false);


        return palabra;
    }

}
