package com.sdm.uniovi.braingame.juegos.calcular;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.juegos.calcular.logica.GeneradorExpresion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public static int NIVEL_MAXIMO = 4;
    public static int NIVEL_MINIMO = 1;
    private int nivelActual = NIVEL_MINIMO;

    private Timer timer = new Timer();
    private Random random = new Random();
    private GeneradorExpresion generadorExpresion;


    private TextView textViewFormula = null;
    private TextView textViewTimer = null;
    private TextView textViewNivel = null;

    private RadioButton[] opciones = new RadioButton[4];
    private RadioButton opcion1;
    private RadioButton opcion2;
    private RadioButton opcion3;
    private RadioButton opcion4;
    private int level;
    private CountDownTimer countdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calcular_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");


        /*WebView webViewFormula = (WebView)findViewById(R.id.webViewFormula);
        webViewFormula.getSettings().setJavaScriptEnabled(true);
        webViewFormula.loadUrl("file:///android_assets/formula/formula.html");*/
        textViewFormula = (TextView) findViewById(R.id.textViewFormula);
        textViewFormula.setTypeface(estiloLetra);

        textViewTimer = (TextView) findViewById(R.id.tvTimer);
        textViewTimer.setTypeface(estiloLetra);

        textViewNivel = (TextView) findViewById(R.id.tvNivel);
        textViewNivel.setTypeface(estiloLetra);

        opcion1 = (RadioButton) findViewById(R.id.rBCalcularOp1);
        opcion1.setTypeface(estiloLetra);
        opciones[0] = opcion1;
        opcion2 = (RadioButton) findViewById(R.id.rBCalcularOp2);
        opcion2.setTypeface(estiloLetra);
        opciones[1] = opcion1;
        opcion3 = (RadioButton) findViewById(R.id.rBCalcularOp3);
        opcion3.setTypeface(estiloLetra);
        opciones[2] = opcion1;
        opcion4 = (RadioButton) findViewById(R.id.rBCalcularOp4);
        opcion4.setTypeface(estiloLetra);
        opciones[3] = opcion1;

        generadorExpresion= new GeneradorExpresion();


        jugarNivelFacil();

    }

    private void jugarNivelFacil() {

        int aciertosMinimos= 7;
        int aciertos=0;
        final int[] fallos = new int[1];
        int jugadas = 0;
        textViewNivel.setText(R.string.nivel +" "+ R.string.leer_facil);

        while(jugadas <10)

            //genero la expresion
            generadorExpresion.generarNivel1();
            textViewFormula.setText(generadorExpresion.getExpresion().mostrar());
            //designo el resultado correcto
            opciones[random.nextInt(opciones.length)].setText(String.valueOf(generadorExpresion.getExpresion().valor()));

            new CountDownTimer(20000, 1000) {

                public void onTick(long millisUntilFinished) {
                    textViewTimer.setText("seconds remaining: " + millisUntilFinished / 1000);

                }

                public void onFinish() {
                    textViewTimer.setText("done!");
                    fallos[0]++;
                }
            }.start();



        }



    private void showPopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Inicio");
        helpBuilder.setMessage(R.string.nivel + " " + R.string.leer_facil);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }










}
