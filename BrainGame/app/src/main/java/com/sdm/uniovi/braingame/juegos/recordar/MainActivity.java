package com.sdm.uniovi.braingame.juegos.recordar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
 * This class implements the game Memorizar (Internally Recordar). All the game logic is contained inside the class.
 */
public class MainActivity extends AppCompatActivity implements OnResultadoListener<Boolean> {

    private TextView tVInformacion;
    private TextView tVPunctuacion;

    private ArrayList<Button> buttonList;

    private int dificultad;
    boolean even;
    //used to measure the ticks of the timer
    int counterTimer;
    //used to remember at which point of the sequence the user is in his repetition
    int counterOrder;

    private Integer points;
    private CountDownTimer countdown;

    //the array contains the order that is to be remembered by the player. As the buttons
    //are referenced by their number 1-9, an int array is sufficient
    private int[] order;

    private CorrectTouchListener cTL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recordar_activity_main);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD",6);
        //The difficulty determines the length of the sequence to remember. A higher
        //difficuly means more points in the highscore. Points will be subtracted and not added.
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

        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
        Button btn3 = (Button) findViewById(R.id.button3);
        Button btn4 = (Button) findViewById(R.id.button4);
        Button btn5 = (Button) findViewById(R.id.button5);
        Button btn6 = (Button) findViewById(R.id.button6);
        Button btn7 = (Button) findViewById(R.id.button7);
        Button btn8 = (Button) findViewById(R.id.button8);
        Button btn9 = (Button) findViewById(R.id.button9);

        //To handle buttons through an index
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

        //To address buttons more easily and compare them to the sequence in the order array
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

    @Override
    public void onResultado(Boolean resultado) {

    }

    //This Listener is set to the Buttons to check if they are pushed in the right order. Also the push is shown graphically
    //Through a change in color. If the right Button is pushed the counterOrder is incremented and the Listener waits for the next
    //Button pushed. If the wrong Button is pushed, point are subtracted. If the points fall under 0, the game is over.
    //If not, the right order is shown again.

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

    //Shows the right order to the player again and resets all components necessary
    private void restartRepetition() {
        toggleButtonListener(false);
        counterOrder = 0;
        startTimer();
    }

    //returns an int array with random numbers from 0-8
    public int[] makeRandomOrder(int amount){
        int[] numbers = new int[amount];
        for (int i = 0; i< amount;i++){
            numbers[i] = (int)(Math.random() * 9);
        }
        return numbers;
    }

    //Makes a move (highlights Button) for the button with the transmitted index
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

    //Method to (de)activate the Listener for all Buttons
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

    //The Server is called to submit the highscore in case the game was won. Then the
    //Activity is finished.
    private void closeActivity(boolean won) {
        if (won) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean conectado = activeNetwork != null && activeNetwork.isConnected();
            if (conectado) {

                Login login = Login.getInstancia(this.getApplicationContext());
                new ActualizarPuntuaciones(this
                        , login.getAutenticacion()
                        , login.getUsuario()
                        , points
                        , TipoJuego.RECORDAR)
                        .execute();
            } else {
                onPause();
                Toast.makeText(this, R.string.fallo_conexion_estadisticas, Toast.LENGTH_LONG).show();
            }
        }
        this.finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        countdown.cancel();
    }

    @Override
    public void onStop(){
        super.onStop();
        countdown.cancel();
    }

}
