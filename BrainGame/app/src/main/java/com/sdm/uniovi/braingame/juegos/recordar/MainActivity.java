package com.sdm.uniovi.braingame.juegos.recordar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.LogingActivity;
import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.servicioWeb.ActualizarPuntuaciones;
import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.util.ArrayList;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class MainActivity extends AppCompatActivity implements OnResultadoListener<Boolean> {

    private TextView tVInformacion;
    private TextView tVPunctuacion;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private ArrayList<Button> buttonList;

    private int dificultad;
    boolean even;
    int counterTimer;
    int counterOrder;

    private Integer points;
    private CountDownTimer countdown;
    private int[] order;

    private CorrectTouchListener cTL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recordar_activity_main);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD",6);
        switch (dificultad){
            case 6 : points = 150;
                break;
            case 8 : points = 250;
                break;
            case 10 : points = 350;
        }

        cTL = new CorrectTouchListener();

        tVInformacion = (TextView) findViewById(R.id.lblInformacionREC);
        tVPunctuacion = (TextView) findViewById(R.id.lblPunctuacionREC);
        tVPunctuacion.setText(this.getString(R.string.ordenar_Puntos) + points.toString());
        counterOrder = 0;

        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);

        buttonList = new ArrayList<>();
        buttonList.add(btn1);
        buttonList.add(btn2);
        buttonList.add(btn3);
        buttonList.add(btn4);
        buttonList.add(btn5);
        buttonList.add(btn6);
        buttonList.add(btn7);
        buttonList.add(btn8);
        buttonList.add(btn9);

        for (int i = 0; i < buttonList.size();i++){
            buttonList.get(i).setTag(i);
        }

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        tVInformacion.setTypeface(estiloLetra);
        tVPunctuacion.setTypeface(estiloLetra);
        for (Button b : buttonList){
            b.setTypeface(estiloLetra);
        }

        order = makeRandomOrder(dificultad);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(R.string.recordar_actDif_Titulo);
        alertDialog.setMessage(getString(R.string.recordar_descripcion));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ordenar_ok_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startTimer();
                    }
                });
        alertDialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_juegos, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_ayuda:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(R.string.ayuda);
                alertDialog.setMessage(this.getString(R.string.ayuda_recordar));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            case R.id.menu_estadisticas:
                EstadisticasActivity.iniciar(this, TipoJuego.RECORDAR);
                return true;
            case R.id.menu_cerrar_sesion:
                Login.getInstancia(getApplicationContext()).desloguear();
                Intent intent = new Intent(this, LogingActivity.class);
                Toast.makeText(this, R.string.cerrado_sesion, Toast.LENGTH_LONG).show();
                this.finish();

                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResultado(Boolean resultado) {

    }

    private final class CorrectTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Button pushedButton = (Button) v;
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    pushedButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recordar_btn_highlighted));
                    return true;
                case MotionEvent.ACTION_UP:
                    pushedButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recordar_btn_normal));

                        int comp1 = (int)pushedButton.getTag();
                        int comp2 = order[counterOrder];

                        if (comp1 == comp2) {
                            if (counterOrder < dificultad-1) {
                                counterOrder++;
                            }
                            else{
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle(R.string.ordenar_finTitulo);
                                alertDialog.setMessage(getString(R.string.recordar_mensajeFin) + points.toString());
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ordenar_ok_button),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                closeActivity(true);
                                            }
                                        });
                                alertDialog.show();
                            }

                        }
                        else {
                            if (points > 75) {
                                points -= 75;
                                tVPunctuacion.setText(getApplicationContext().getString(R.string.ordenar_Puntos) + points.toString());
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle(R.string.recordar_reiniciar_titulo);
                                alertDialog.setMessage(getString(R.string.recordar_reiniciar));
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ordenar_ok_button),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                restartRepetition();
                                            }
                                        });
                                alertDialog.show();
                            }
                            else{
                                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle(R.string.ordenar_finTitulo);
                                alertDialog.setMessage(getString(R.string.ordenar_finPerdidoMensaje));
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ordenar_ok_button),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                closeActivity(false);
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    return true;
            }
            return false;
        }
    }


    private void restartRepetition() {
        toggleButtonListener(false);
        counterOrder = 0;
        startTimer();
    }


    public int[] makeRandomOrder(int amount){
        int[] numbers = new int[amount];
        for (int i = 0; i< amount;i++){
            numbers[i] = (int)(Math.random() * 9);
            Log.i("Banane"+i, String.valueOf(numbers[i]));
        }
        return numbers;
    }

    public void makeMove(int index, boolean even){
        if (even){
            buttonList.get(index).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recordar_btn_highlighted));
        }
        else{
            buttonList.get(index).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recordar_btn_normal));
            counterTimer++;
        }

    }

    private void startTimer(){
        counterTimer = 0;
        even = true;
        countdown = new CountDownTimer(dificultad*1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                makeMove(order[counterTimer],even);
                even = !even;
            }

            @Override
            public void onFinish() {
                buttonList.get(order[dificultad-1]).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recordar_btn_normal));
                toggleButtonListener(true);
            }
        };

        countdown.start();
    }

    public void toggleButtonListener(boolean active){
        if(active){
            for(Button b : buttonList){
                b.setOnTouchListener(cTL);
            }
        }
        else{
            for(Button b : buttonList){
                b.setOnTouchListener(null);
            }
        }
    }

    private void closeActivity(boolean won) {
        if (won) {
            new ActualizarPuntuaciones(this, Login.getInstancia(this.getApplicationContext()).getAutenticacion()
                    , Login.getInstancia(this.getApplicationContext()).getUsuario(), points, TipoJuego.RECORDAR.getIdServicio()).execute();
        }
        this.finish();
    }


}
