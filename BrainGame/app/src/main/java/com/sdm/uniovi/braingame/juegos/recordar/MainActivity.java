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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.LogingActivity;
import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.util.ArrayList;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class MainActivity extends AppCompatActivity {

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
    boolean even = true;
    int counter;

    private Integer points = 300;
    private CountDownTimer countdown;
    private int[] order;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recordar_activity_main);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD",6);

        tVInformacion = (TextView) findViewById(R.id.lblInformacionREC);
        tVPunctuacion = (TextView) findViewById(R.id.lblPunctuacionREC);
        tVPunctuacion.setText(this.getString(R.string.ordenar_Puntos) + points.toString());

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

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        tVInformacion.setTypeface(estiloLetra);
        tVPunctuacion.setTypeface(estiloLetra);
        for (Button b : buttonList){
            b.setTypeface(estiloLetra);
        }

        order = makeRandomOrder(dificultad);
        startTimer();

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
            counter++;
        }

    }

    private void startTimer(){
        counter = 0;
        countdown = new CountDownTimer(dificultad*1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                makeMove(order[counter],even);
                even = !even;
            }

            @Override
            public void onFinish() {
                buttonList.get(order[dificultad-1]).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recordar_btn_normal));
            }
        };

        countdown.start();
    }
}
