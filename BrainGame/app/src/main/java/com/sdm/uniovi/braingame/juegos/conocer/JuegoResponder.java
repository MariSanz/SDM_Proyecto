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
import java.util.Random;

/**
 * Created by JohanArif on 13/01/2016.
 */
public class JuegoResponder extends AppCompatActivity {

    private ArrayList<Pregunta> preguntas;
    TextView titulo;
    TextView pregunta;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    Button btAtras;
    Button btParar;
    private static final int NUM_PREGUNTAS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.responder_interfaz);

        titulo = (TextView) findViewById(R.id.tvTituloResInter);
        pregunta = (TextView) findViewById(R.id.tvPregunta);
        rb1 = (RadioButton) findViewById(R.id.rBOpcion1);
        rb2 = (RadioButton) findViewById(R.id.rBOpcion2);
        rb3 = (RadioButton) findViewById(R.id.rBOpcion3);
        rb4 = (RadioButton) findViewById(R.id.rBOpcion4);
        btAtras = (Button) findViewById(R.id.btAtrasResInter);
        btParar = (Button) findViewById(R.id.btPararResInt);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        titulo.setTypeface(estiloLetra);
        pregunta.setTypeface(estiloLetra);
        rb1.setTypeface(estiloLetra);
        rb2.setTypeface(estiloLetra);
        rb3.setTypeface(estiloLetra);
        rb4.setTypeface(estiloLetra);
        btAtras.setTypeface(estiloLetra);
        btParar.setTypeface(estiloLetra);

        obtenerPreguntas();
        //pregunta.setText(preguntas.get(0).getNombre());
        generarPregunta();


    }

    public void obtenerPreguntas(){
        ParserJsonObject parser = new ParserJsonObject(this);
        preguntas = parser.getPreguntas();
    }

    public void generarPregunta(){
        Random r = new Random();
        int index = r.nextInt(1);
        Pregunta p = preguntas.get(0);
        pregunta.setText(p.getNombre());

        int cont = 0;

        RadioButton rbSeleccionado = seleccionarRbAleatorio();
        String respuestaSeleccionada = "";
        ArrayList<RadioButton> aux = new ArrayList<>();
        ArrayList<String> auxRespuestas = new ArrayList<>();

        while(cont < 4) {
            rbSeleccionado = seleccionarRbAleatorio();
            if (!aux.contains(rbSeleccionado)) {
                aux.add(rbSeleccionado);
                int cont2 = 0;
                while(cont2 < 3) {
                    respuestaSeleccionada = p.getRespuestasFalsas().get(indiceRespuesta());
                    if (!auxRespuestas.contains(respuestaSeleccionada)) {
                        auxRespuestas.add(respuestaSeleccionada);
                        rbSeleccionado.setText(respuestaSeleccionada);
                        cont2++;
                        cont++;
                    }
                }

                boolean parar = false;
                while(!parar){
                    rbSeleccionado = seleccionarRbAleatorio();
                    if(!aux.contains(rbSeleccionado)){
                        rbSeleccionado.setText(p.getRespuestasCorrectas().get(0));
                        parar = true;
                        cont++;
                    }
                }
            }
        }



    }

    public int indiceRespuesta(){
        Random r = new Random();
        return r.nextInt(3);
    }

    public RadioButton seleccionarRbAleatorio(){
        Random r = new Random();
        int index = r.nextInt(4 - 1);
        switch (index){
            case 0:
                return rb1;
            case 1:
                return rb2;
            case 2:
                return rb3;
            case 3:
                return rb4;
        }
        return null;
    }

    public void irAtrasResInter(View view){
        this.finish();
    }


}
