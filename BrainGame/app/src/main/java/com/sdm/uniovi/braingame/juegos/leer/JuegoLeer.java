package com.sdm.uniovi.braingame.juegos.leer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.R;

import java.util.ArrayList;
import java.util.Random;

public class JuegoLeer extends AppCompatActivity {

    private CountDownTimer crono;
    private TextView tvTiempoRestante;
    private TextView tvPalabra;
    private TextView tvPuntos;
    private TextView tvDescripcion;
    private TextView tvTiempoInicial;
    private TextView tvPuntuacion;
    private Button btAtras;
    private static final String[] COLORESVALIDOS = { "amarillo", "azul", "rojo", "blanco",
            "verde", "morado", "naranja", "rosa"};

    private static final int COLORESVALIDOS_LENGTH = COLORESVALIDOS.length;
   // private Intent vozIntent;
    private int dificultad;
    private int tiempo;
    private int indexColor;
    private Integer puntos = 0;
    private int cont = 0;
    private int max_veces = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD", 0);

        setContentView(R.layout.leer_interfaz);

        tvDescripcion = (TextView)findViewById(R.id.tvDescripcionInt);
        tvPuntuacion = (TextView)findViewById(R.id.tvPuntuacionInt);
        tvTiempoInicial = (TextView)findViewById(R.id.tvTiempoInt);
        tvTiempoRestante = (TextView)findViewById(R.id.tvTiempoRestanteInt);
        tvPalabra = (TextView)findViewById(R.id.tvPalabraInt);
        tvPuntos = (TextView)findViewById(R.id.tvPuntosInt);
        btAtras = (Button) findViewById(R.id.btAtrasInt);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        tvDescripcion.setTypeface(estiloLetra);
        tvPuntuacion.setTypeface(estiloLetra);
        tvTiempoInicial.setTypeface(estiloLetra);
        tvTiempoRestante.setTypeface(estiloLetra);
        tvPalabra.setTypeface(estiloLetra);
        tvPuntos.setTypeface(estiloLetra);
        btAtras.setTypeface(estiloLetra);

        calcularNumeroPalabras();
        iniciarJuego();
    }

    private void generarPalabra(){
        Random r = new Random();
        int index = r.nextInt(COLORESVALIDOS_LENGTH - 1);
        indexColor = r.nextInt(COLORESVALIDOS_LENGTH - 1);
        tvPalabra.setText(COLORESVALIDOS[index]);
        int color = -1;

        switch (COLORESVALIDOS[indexColor]){
            case "amarillo":  color = Color.rgb(255,255,0);
                break;
            case "azul": color = Color.rgb(0,0,255);
                break;
            case "rojo": color = Color.rgb(255,0,0);
                break;
            case "blanco": color = Color.rgb(255,255,255);
                break;
            case "verde": color = Color.rgb(0,128,0);
                break;
            case "morado": color = Color.rgb(128,0,128);
                break;
            case "naranja": color = Color.rgb(255,165,0);
                break;
            case "rosa": color = Color.rgb(255,192,203);
        }

        tvPalabra.setTextColor(color);
    }

    public void calcularNumeroPalabras(){
        switch(dificultad){
            case 1:
                max_veces = 7;
                break;
            case 2:
                max_veces = 9;
                break;
            case 3:
                max_veces = 11;
                break;
        }
    }


    public void iniciarTemporizador(){

        switch(dificultad){
            case 1:
                tiempo = 3000;
                break;
            case 2:
                tiempo = 2000;
                break;
            case 3:
                tiempo = 1000;
                break;
        }


        crono = new CountDownTimer(tiempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTiempoRestante.setText("  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                tvTiempoRestante.setText("  0");
                iniciarReconocimientoDeVoz();
            }
        };

        crono.start();
    }

    public void iniciarReconocimientoDeVoz(){

        Intent vozIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        vozIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el color de la palabra");
        startActivityForResult(vozIntent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    { if (resultCode == RESULT_OK) {
        ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        tratarResultados(matches);
    }}

    private void tratarResultados(ArrayList<String> resultadosStrings) {


        ArrayList<String> coloresValidosTemp = new ArrayList<String>();

        for(int i = 0; i < COLORESVALIDOS_LENGTH; i++ ){
            coloresValidosTemp.add(COLORESVALIDOS[i]);
        }

        if(coloresValidosTemp.contains(resultadosStrings.get(0))){ //En la primera posición está
            if(resultadosStrings.get(0).equals(COLORESVALIDOS[indexColor])){
                Toast.makeText(getApplicationContext(), "¡Has acertado!",
                        Toast.LENGTH_LONG).show();
                puntos += dificultad*10;

            }else{
                if(puntos >= (10*dificultad) / 2){
                    puntos -= (10*dificultad) / 2 ;
                }else{
                    puntos = 0;
                }
            }
            tvPuntos.setText("" + puntos.toString());

        }else{
            Toast.makeText(getApplicationContext(), "Lo que ha dicho no es un color válido.",
                    Toast.LENGTH_SHORT).show();
        }
        iniciarJuego();
    }

    public void iniciarJuego(){
        cont++;
        if(cont < max_veces) {
            generarPalabra();
            iniciarTemporizador();
        }
    }

    public void irAtras(View view){
        this.finish();
    }

}
