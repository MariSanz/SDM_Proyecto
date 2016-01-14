package com.sdm.uniovi.braingame.juegos.calcular;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.juegos.calcular.logica.Expresion;
import com.sdm.uniovi.braingame.servicioWeb.ActualizarPuntuaciones;
import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.juegos.calcular.logica.GeneradorExpresion;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;


public class JuegoCalcular extends AppCompatActivity implements OnResultadoListener<Boolean> {

    public static int NIVEL_MAXIMO = 4;
    public static int NIVEL_MINIMO = 1;
    private int nivelActual = NIVEL_MINIMO;

    private Random random = new Random();
    private GeneradorExpresion generadorExpresion;


    private TextView textViewFormula = null;
    private TextView textViewTimer = null;
    private TextView textViewNumAciertos = null;
    private TextView textViewNumPuntos;

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

        Bundle extras = getIntent().getExtras();
        nivelActual = extras.getInt("EXTRA_DIFICULTAD", 0);

        setContentView(R.layout.calcular_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        textViewFormula = (TextView) findViewById(R.id.textViewFormula);
        textViewFormula.setTypeface(estiloLetra);

        textViewTimer = (TextView) findViewById(R.id.tvTimer);
        textViewTimer.setTypeface(estiloLetra);

        textViewNumAciertos = (TextView) findViewById(R.id.tvNumAciertos);
        textViewNumAciertos.setTypeface(estiloLetra);
        textViewNumAciertos.setText(String.valueOf(0));

        textViewNumPuntos =(TextView) findViewById(R.id.tvNumPuntos);
        textViewNumPuntos.setTypeface(estiloLetra);
        textViewNumPuntos.setText(String.valueOf(0));

        TextView textViewPuntos = (TextView) findViewById(R.id.tvPuntos);
        textViewPuntos.setTypeface(estiloLetra);
       

        TextView acierto =(TextView) findViewById(R.id.tvAciertos);
        acierto.setTypeface(estiloLetra);

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



        generadorExpresion= new GeneradorExpresion(nivelActual);

        jugar();


    }

    private void iniarTemporizador(int tiempo) {
        if(countdown!=null){

            countdown=null;
        }

        countdown = new CountDownTimer(tiempo, 1000){

            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished / 1000));

            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), R.string.tiempo_fuera, Toast.LENGTH_LONG).show();
                resultadoPartida();



            }


        };
    }


    private void jugarNivel() {

        countdown.start();
        if(jugadas<jugadasMaximasNivel) {


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
        DecimalFormat formateador = new DecimalFormat("#.##");

        if(seleccion.getText().equals(String.valueOf(formateador.format(generadorExpresion.getPrincipal().valor())))){


            aciertos++;
            textViewNumAciertos.setText(String.valueOf(aciertos));
            puntos+=100;
            textViewNumPuntos.setText(String.valueOf(puntos));
            countdown.cancel();
            Toast.makeText(this, R.string.acierto, Toast.LENGTH_LONG).show();



        }else{
            fallos++;
            puntos-=30;
            textViewNumPuntos.setText(String.valueOf(puntos));
            countdown.cancel();
            Toast.makeText(this, R.string.fallo,Toast.LENGTH_LONG).show();


        }

        resultadoPartida();

    }

    private void resultadoPartida() {


        if(jugadas==jugadasMaximasNivel){
            jugadas=0;
            if(aciertos>=aciertosMaximosNivel){

                if(nivelActual==NIVEL_MAXIMO){

                    onPause();
                    showPopUpFin("Enhorabuena", "Has ganado el máximo nivel \n Eres un gran matematico \n\n ¿Quieres volver a jugar este nivel?", true);

                }else {
                    nivelActual++;

                    onPause();
                   showPopUpFin("Ganado", "Enhorabuena, has ganado. \n\n ¿Quieres jugar el siguiente nivel?", true);
                }
            }else{

                showPopUpFin("Ups", "Has perdido este nivel. \n \n ¿Quieres volver a jugarlo?", false);
            }
        }

        if (fallos>=4){

            showPopUpFin("Perdido", "Vuelve a intentarlo, has perdido la partida", false);
        }else{
            jugar();
        }

    }

    public void showPopUpFin(String titulo, String msg, final boolean fin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        AlertDialog.Builder builder1 = builder.setTitle(titulo)
                .setMessage(msg)
                .setPositiveButton(R.string.positivo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (fin) {
                                    actualizarPuntuacion();

                                }
                                onResume();
                                reiniciarActivity();
                                //reinicio las expresiones
                                generadorExpresion= new GeneradorExpresion(nivelActual);
                                jugar();
                            }
                        })
                .setNegativeButton(R.string.negativo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(fin){
                                    actualizarPuntuacion();
                                }
                                volverAjugar();
                            }
                        });

        AlertDialog dialog = builder1.create();
        dialog.show();
    }

    private void reiniciarActivity() {
        textViewNumPuntos.setText("0");
        textViewNumAciertos.setText("0");
        aciertos=0;
        fallos=0;
        jugadas=0;
    }

    private void volverAjugar(){
        Intent intent = new Intent(this, DificultadActivity.class);
        startActivity(intent);
    }



    private void actualizarPuntuacion() {
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean conectado = activeNetwork.isConnectedOrConnecting();
        if(conectado) {
            new ActualizarPuntuaciones(this, Login.getInstancia(this.getApplicationContext()).getAutenticacion()
                    , Login.getInstancia(this.getApplicationContext()).getUsuario(), puntos, TipoJuego.CALCULAR.getIdServicio()).execute();
        }else{


            Toast.makeText(this, R.string.fallo_conexion_estadisticas, Toast.LENGTH_LONG).show();


        }
    }


    private void jugar() {

        switch (nivelActual){
            case GeneradorExpresion.NIVEL_FACIL:
                jugadasMaximasNivel=10;
                aciertosMaximosNivel =7;

                iniarTemporizador(20000);
                jugarNivel();
                break;
            case GeneradorExpresion.NIVEL_BASICO:
                jugadasMaximasNivel=15;
                aciertosMaximosNivel =12;

                iniarTemporizador(30000);
                jugarNivel();
                break;
            case GeneradorExpresion.NIVEL_AVANZADO:
                jugadasMaximasNivel=20;
                aciertosMaximosNivel = 18;

                iniarTemporizador(50000);
                jugarNivel();
                break;
        }
    }

    private void rellenarRadioButton() {
        int posCorrecta= random.nextInt(opciones.length);

        Expresion correcta = generadorExpresion.getPrincipal();
        Expresion incorrecta;
        ArrayList<Double> opIncorrectas = new ArrayList<>();

        DecimalFormat formateador = new DecimalFormat("#.##");

        for (RadioButton opcione : opciones) {
            incorrecta = correcta.getIncorrecta(random);
            while (correcta.valor() == incorrecta.valor() && opIncorrectas.contains(incorrecta.valor())) {
                incorrecta = correcta.getIncorrecta(random);

            }
            opIncorrectas.add(incorrecta.valor());

            opcione.setText(String.valueOf(formateador.format(incorrecta.valor())));

        }

        opciones[posCorrecta].setText(String.valueOf(formateador.format(correcta.valor())));


    }

    @Override
    public void onResultado(Boolean resultado) {


    }
}
