package com.sdm.uniovi.braingame.juegos.conocer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.juegos.conocer.logica.ParserJsonObject;
import com.sdm.uniovi.braingame.juegos.conocer.logica.Pregunta;

import java.util.ArrayList;

/**
 * Created by JohanArif on 13/01/2016.
 */
public class JuegoResponder extends AppCompatActivity {

    private ArrayList<Pregunta> preguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.responder_interfaz);

        TextView titulo = (TextView) findViewById(R.id.tvTituloResInter);
        TextView pregunta = (TextView) findViewById(R.id.tvPregunta);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rBOpcion1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rBOpcion2);
        RadioButton rb3 = (RadioButton) findViewById(R.id.rBOpcion3);
        RadioButton rb4 = (RadioButton) findViewById(R.id.rBOpcion4);
        Button btAtras = (Button) findViewById(R.id.btAtrasResInter);
        Button btParar = (Button) findViewById(R.id.btPararResInt);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        titulo.setTypeface(estiloLetra);
        pregunta.setTypeface(estiloLetra);
        rb1.setTypeface(estiloLetra);
        rb2.setTypeface(estiloLetra);
        rb3.setTypeface(estiloLetra);
        rb4.setTypeface(estiloLetra);
        btAtras.setTypeface(estiloLetra);
        btParar.setTypeface(estiloLetra);
//
        obtenerPreguntas();
//        pregunta.setText(preguntas.get(0).getNombre());

    }

    public void obtenerPreguntas(){
        ParserJsonObject parser = new ParserJsonObject();
        preguntas = parser.getPreguntas();
    }

    public void irAtrasResInter(View view){
        this.finish();
    }


}
