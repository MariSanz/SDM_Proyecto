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
import com.sdm.uniovi.braingame.ServicioWeb.ActualizarPuntuaciones;
import com.sdm.uniovi.braingame.ServicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.juegos.calcular.logica.GeneradorExpresion;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements OnResultadoListener<Boolean> {

    public static int NIVEL_MAXIMO = 4;
    public static int NIVEL_MINIMO = 1;
    private int nivelActual = NIVEL_MINIMO;

    private Random random = new Random();
    private GeneradorExpresion generadorExpresion;


    private TextView textViewFormula = null;
    private TextView textViewTimer = null;
    private TextView textViewNivel = null;

    private RadioButton[] opciones = new RadioButton[4];
    private CountDownTimer countdown;

     private int jugadas;
    private int fallos;
    private int aciertos;

    private int jugadasMaximasNivel;
    private int aciertosMaximosNivel;
    private int puntos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calcular_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        textViewFormula = (TextView) findViewById(R.id.textViewFormula);
        textViewFormula.setTypeface(estiloLetra);

        textViewTimer = (TextView) findViewById(R.id.tvTimer);
        textViewTimer.setTypeface(estiloLetra);

        textViewNivel = (TextView) findViewById(R.id.tvNivel);
        textViewNivel.setTypeface(estiloLetra);

        RadioButton opcion1 = (RadioButton) findViewById(R.id.rBCalcularOp1);
        opcion1.setTypeface(estiloLetra);
        opciones[0] = opcion1;
        RadioButton opcion2 = (RadioButton) findViewById(R.id.rBCalcularOp2);
        opcion2.setTypeface(estiloLetra);
        opciones[1] = opcion2;
        RadioButton opcion3 = (RadioButton) findViewById(R.id.rBCalcularOp3);
        opcion3.setTypeface(estiloLetra);
        opciones[2] = opcion3;
        RadioButton opcion4 = (RadioButton) findViewById(R.id.rBCalcularOp4);
        opcion4.setTypeface(estiloLetra);
        opciones[3] = opcion4;

        countdown = new CountDownTimer(20000, 1000){

            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished / 1000));

            }

            public void onFinish() {
                showPopUpTiempo("Tiempo fuera", "Se ha agotado el tiempo");

                textViewTimer.setText("! Fin !");


            }


        };

        generadorExpresion= new GeneradorExpresion(NIVEL_MINIMO, opciones.length);

        jugadasMaximasNivel = 10;
        jugadas=0;
        jugarNivel();


    }



    private void jugarNivel() {

        countdown.cancel();
        countdown.start();
        if(jugadas<jugadasMaximasNivel) {


            String msgInicial = "Resuelve";
            textViewNivel.setText(msgInicial);

            //genero la expresion
            generadorExpresion.generarExpresiones();
            textViewFormula.setText(generadorExpresion.getPrincipal().mostrar());
            //designo el resultado correcto
            rellenarRadioButton();
        }

        }

    public void evaluar(View view){
        RadioButton seleccion = (RadioButton) view;
        seleccion.setChecked(false);
        jugadas++;
        if(seleccion.getText().equals(String.valueOf(generadorExpresion.getPrincipal().valor()))){

            aciertos++;
            puntos+=100;
            showPopUp("Acierto", "Respuesta correcta");


        }else{
            fallos++;
            showPopUp("Fallo", "Ups respuesta incorrecta");

        }

       // resultadoPartida();

    }

    private void resultadoPartida() {
        if(fallos>=4){
            showPopUp("Perdido", "Vuelve a intentarlo, has perdido la partida");
        }
        if(jugadas==jugadasMaximasNivel){
            jugadas=0;
            if(aciertos>=aciertosMaximosNivel){

                if(nivelActual==NIVEL_MAXIMO){
                    showPopUpFin("Enhorabuena", "Has ganado el máximo nivel \n Eres un gran matematico ");

                }else {
                    nivelActual++;
                    showPopUpFin("Ganado", "Enhorabuena, has ganado pasas al siguiente nivel");
                }
            }else{
                showPopUpFin("Ups", "Vuelve a intentar el nivel");
            }
        }

    }

    private void showPopUpFin(String titulo, String msg) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(titulo);
        helpBuilder.setMessage(msg);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        actualizarPuntuacion();
                        jugar();
                    }
                });


        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private void actualizarPuntuacion() {
        new ActualizarPuntuaciones(this, Login.getInstancia(this.getApplicationContext()).getAutenticacion()
                , Login.getInstancia(this.getApplicationContext()).getUsuario(), puntos, TipoJuego.CALCULAR.getIdServicio()).execute();
    }


    private void jugar() {

        switch (nivelActual){
            case GeneradorExpresion.NIVEL_FACIL:
                jugadasMaximasNivel=10;
                aciertosMaximosNivel =7;
                jugarNivel();
                break;
            case GeneradorExpresion.NIVEL_BASICO:
                jugadasMaximasNivel=15;
                aciertosMaximosNivel =12;
                jugarNivel();
                break;
            case GeneradorExpresion.NIVEL_AVANZADO:
                jugadasMaximasNivel=20;
                aciertosMaximosNivel = 18;
                jugarNivel();
                break;
        }
    }




    private void rellenarRadioButton() {

        for (int i=0; i<opciones.length; i++){
            opciones[i].setText(String.valueOf(generadorExpresion.getIncorreta(i).valor()));
        }
        opciones[random.nextInt(opciones.length)].setText(String.valueOf(generadorExpresion.getPrincipal().valor()));

    }


    private void showPopUp(String titulo, String msg) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(titulo);
        helpBuilder.setMessage(msg);



        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private void showPopUpTiempo(String titulo, String msg) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(titulo);
        helpBuilder.setMessage(msg);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        resultadoPartida();
                    }
                });


        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }


    @Override
    public void onResultado(Boolean resultado) {


    }
}
