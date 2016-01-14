package com.sdm.uniovi.braingame.juegos.corresponder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.LogingActivity;
import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.servicioWeb.ActualizarPuntuaciones;
import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.util.ArrayList;
import java.util.Random;


/**
 * This class implements the game Emparejar (Internally Corresponder). All the game logic is contained inside the class.
 */
public class MainActivity extends AppCompatActivity implements OnResultadoListener<Boolean> {

    private TextView tVInformacion;
    private TextView tVPunctuacion;
    private TextView tVTimer;
    private Button bnCorres;
    private Button bnNoCorres;
    private ImageView img1Comparar;
    private ImageView img2Comparar;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;

    private Integer points = 0;
    private int dificultad;
    //Auxiliary variable that needs to be saved outside the method getRandomDrawable()
    private int lastInt = 0;
    private int timertime;
    //Contains the six Drawables of the game
    private ArrayList<Drawable> listaTodasImagenes;
    //Contains the 5 ImageViews of the queue
    private ArrayList<ImageView> listaImageViews;
    private CountDownTimer countdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corresponder_activity_main);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD",0);

        //The difficulty is simply encoded as 1,2,3. It determines if the countdown is set to 40,30 or 20 seconds
        switch(dificultad){
            case 1 : timertime = 40000;
                break;
            case 2 : timertime = 30000;
                break;
            case 3 : timertime = 20000;
                break;
            default : timertime = 40000;

        }

        tVInformacion = (TextView) findViewById(R.id.lblInformacionCOR);
        tVPunctuacion = (TextView) findViewById(R.id.lblPunctuacionCOR);
        tVPunctuacion.setText(this.getString(R.string.ordenar_Puntos) + points.toString());
        tVTimer = (TextView) findViewById(R.id.lblTimerCOR);
        bnCorres = (Button) findViewById(R.id.bnCorres);
        bnNoCorres = (Button) findViewById(R.id.bnNoCorres);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        tVInformacion.setTypeface(estiloLetra);
        tVPunctuacion.setTypeface(estiloLetra);
        tVTimer.setTypeface(estiloLetra);
        bnCorres.setTypeface(estiloLetra);
        bnNoCorres.setTypeface(estiloLetra);

        //images 1 and 2 are a bit bigger and are the ones compared in each step
        img1Comparar = (ImageView) findViewById(R.id.iVComparar1);
        img2Comparar = (ImageView) findViewById(R.id.iVComparar2);
        img3 = (ImageView) findViewById(R.id.iV3);
        img4 = (ImageView) findViewById(R.id.iV4);
        img5 = (ImageView) findViewById(R.id.iV5);

        listaTodasImagenes = new ArrayList<>();
        listaImageViews = new ArrayList<>();


        listaTodasImagenes.add(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corresponder1));
        listaTodasImagenes.add(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corresponder2));
        listaTodasImagenes.add(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corresponder3));
        listaTodasImagenes.add(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corresponder4));
        listaTodasImagenes.add(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corresponder5));
        listaTodasImagenes.add(ContextCompat.getDrawable(getApplicationContext(), R.drawable.corresponder6));

        listaImageViews.add(img1Comparar);
        listaImageViews.add(img2Comparar);
        listaImageViews.add(img3);
        listaImageViews.add(img4);
        listaImageViews.add(img5);

        makeInitOrder();
        startTimer(timertime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juegos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.menu_ayuda:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(R.string.ayuda);
                alertDialog.setMessage(this.getString(R.string.ayuda_corresponder));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //The time of the countdown is stopped during the display of the help
                                startTimer(timertime + 1000);
                            }
                        });
                alertDialog.show();
                countdown.cancel();
                return true;
            case R.id.menu_estadisticas:
                EstadisticasActivity.iniciar(this, TipoJuego.CORRESPONDER);
                return true;
            case R.id.menu_cerrar_sesion:
                Login.getInstancia(getApplicationContext()).desloguear();
                Intent intent = new Intent(this, LogingActivity.class);
                Toast.makeText(this, R.string.cerrado_sesion, Toast.LENGTH_LONG).show();
                this.finish();

                startActivity(intent);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //Timer is set according to difficulty. onFinish() the game is over
    private void startTimer(int millis){
            countdown = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tVTimer.setText("  " + millisUntilFinished / 1000);
                timertime -= 1000;
            }

            @Override
            public void onFinish() {
                tVTimer.setText("  0");
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(R.string.ordenar_finTitulo);
                alertDialog.setMessage(getString(R.string.corresponder_mensajeFin) + points.toString());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ordenar_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                closeActivity();
                            }
                        });
                alertDialog.show();
            }
        };

        countdown.start();
    }

    @Override
    public void onResultado(Boolean resultado) {

    }

    //Five random Drawables are chosen and set as the Drawables of the 5 ImageViews
    public void makeInitOrder(){
        for (int i = 0; i < 5; i++) {
            Drawable randomDraw = getRandomDrawable();
            listaImageViews.get(i).setImageDrawable(randomDraw);
        }
    }

    //A random Drawable is selected from the 6 available in this game. They are accessed through
    //the ArrayList listaTodasImagenes. The index of the currently chosen Drawable is saved in the auxiliary variable
    //currInt. It is retrieved in the next call and with a probability of 30% returned again.
    //This serves to make the amount of times to push each of the 2 Buttons in the game more equal.
    public Drawable getRandomDrawable(){
        Random random = new Random();
        int currInt = random.nextInt(listaTodasImagenes.size());

        //to insure that the same pic comes two times in a row more often
        boolean sameImage = (random.nextFloat() < 0.3);
        if(sameImage){
            return listaTodasImagenes.get(lastInt);
        }
        else{
            lastInt = currInt;
            return listaTodasImagenes.get(currInt);
        }

    }

    //Simulates behavoir of queue. Each ImageView gets the Drawable of its successor, the last ImageView a new random Drawable
    public void popImagenes(){
            img1Comparar.setImageDrawable(img2Comparar.getDrawable());
            img2Comparar.setImageDrawable(img3.getDrawable());
            img3.setImageDrawable(img4.getDrawable());
            img4.setImageDrawable(img5.getDrawable());
            img5.setImageDrawable(getRandomDrawable());
    }

    //Give points if the images match, takes points if not
    public void clickCorres (View v){
        if (isMatching()){
            addPoints(dificultad);
        }
        else{
            deletePoints(dificultad);
        }
        popImagenes();
    }

    //Give points if the images don't match, takes points if they do
    public void clickNoCorres (View v){
        if (!isMatching()){
            addPoints(dificultad);
        }
        else{
            deletePoints(dificultad);
        }
        popImagenes();
    }

    //Returns true if Drawables for ImageView 1 and 2 match, false if not
    public boolean isMatching() {
        return (((BitmapDrawable) img1Comparar.getDrawable()).getBitmap().equals(((BitmapDrawable) img2Comparar.getDrawable()).getBitmap()));
    }

    //Adds points to the highscore depending on the set difficulty
    public void addPoints (int dificultad){
        switch(dificultad){
            case 1 : points += 2;
                break;
            case 2 : points += 4;
                break;
            case 3 : points += 6;
                break;
            default : points += 2;
        }
        tVPunctuacion.setText(this.getString(R.string.ordenar_Puntos) + points.toString());
    }

    //Deletes points from the highscore depending on the set difficulty. Points cannot fall under zero
    public void deletePoints (int dificultad){
        switch(dificultad){
            case 1 : if (points - 2 >= 0){
                points -= 2;}
                break;
            case 2 : if (points - 4 >= 0){
                points -= 4;}
                break;
            case 3 : if (points - 6 >= 0){
                points -= 6;}
                break;
            default :if (points - 2 >= 0){
                points -= 2;}
        }
        tVPunctuacion.setText(this.getString(R.string.ordenar_Puntos) + points.toString());
    }

    //The Server is called to submit the highscore. Then the Activity is finished.
    private void closeActivity(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean conectado = activeNetwork != null && activeNetwork.isConnected();

        if (conectado) {
            Login login = Login.getInstancia(this.getApplicationContext());
            new ActualizarPuntuaciones(this
                    , login.getAutenticacion()
                    , login.getUsuario()
                    , points
                    , TipoJuego.CORRESPONDER.getIdServicio())
                    .execute();
        } else {

            onPause();
            Toast.makeText(this, R.string.fallo_conexion_estadisticas, Toast.LENGTH_LONG).show();

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
