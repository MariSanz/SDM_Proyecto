package com.sdm.uniovi.braingame.juegos.completar;

import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

public class JuegoCompletar extends AppCompatActivity {

    private TextView textViewPuntos;
    private TextView textViewPalabras;
    private TextView textViewTiempo;
    private TextView textViewPuntosObtenidos;
    private CountDownTimer countdown;

    private final String [] palabras = {"hola", "mundo" };
    private int puntos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_completar);

        textViewPuntos = (TextView) findViewById(R.id.textViewPuntosCompletar);
        textViewPalabras = (TextView) findViewById(R.id.textViewPalabrasAcertadas);
        textViewTiempo = (TextView) findViewById(R.id.textViewTiempoCompletar);
        textViewPuntosObtenidos = (TextView) findViewById(R.id.textViewPuntosNumero);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        textViewPuntos.setTypeface(estiloLetra);
        textViewPalabras.setTypeface(estiloLetra);
        textViewTiempo.setTypeface(estiloLetra);
        textViewPuntosObtenidos.setTypeface(estiloLetra);

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
    }

}
