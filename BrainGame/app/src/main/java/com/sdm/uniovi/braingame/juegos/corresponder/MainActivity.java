package com.sdm.uniovi.braingame.juegos.corresponder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.servicioWeb.ActualizarPuntuaciones;
import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.util.ArrayList;
import java.util.Random;

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
    private int lastInt = 0;
    private int timertime;
    private ArrayList<Drawable> listaTodasImagenes;
    private ArrayList<ImageView> listaImageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corresponder_activity_main);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD",0);

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

        img1Comparar = (ImageView) findViewById(R.id.iVComparar1);
        img2Comparar = (ImageView) findViewById(R.id.iVComparar2);
        img3 = (ImageView) findViewById(R.id.iV3);
        img4 = (ImageView) findViewById(R.id.iV4);
        img5 = (ImageView) findViewById(R.id.iV5);

        listaTodasImagenes = new ArrayList();
        listaImageViews = new ArrayList();

        listaTodasImagenes.add(getResources().getDrawable(R.drawable.corresponder1));
        listaTodasImagenes.add(getResources().getDrawable(R.drawable.corresponder2));
        listaTodasImagenes.add(getResources().getDrawable(R.drawable.corresponder3));
        listaTodasImagenes.add(getResources().getDrawable(R.drawable.corresponder4));
        listaTodasImagenes.add(getResources().getDrawable(R.drawable.corresponder5));
        listaTodasImagenes.add(getResources().getDrawable(R.drawable.corresponder6));

        listaImageViews.add(img1Comparar);
        listaImageViews.add(img2Comparar);
        listaImageViews.add(img3);
        listaImageViews.add(img4);
        listaImageViews.add(img5);

        makeInitOrder();

        startTimer(timertime);
        makeInitOrder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                            }
                        });
                alertDialog.show();
                return true;
            case R.id.menu_estadisticas:
                EstadisticasActivity.iniciar(this, TipoJuego.CORRESPONDER);

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void startTimer(int millis){
        CountDownTimer countdown = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tVTimer.setText("  " + millisUntilFinished / 1000);
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


    private void closeActivity(){
        new ActualizarPuntuaciones(this, Login.getInstancia(this.getApplicationContext()).getAutenticacion()
                , Login.getInstancia(this.getApplicationContext()).getUsuario(), points, TipoJuego.ORDENAR.getIdServicio()).execute();
        this.finish();
    }

    @Override
    public void onResultado(Boolean resultado) {

    }

    public void makeInitOrder(){
        for (int i = 0; i < 5; i++) {
            Drawable randomDraw = getRandomDrawable();
            listaImageViews.get(i).setImageDrawable(randomDraw);
        }
    }

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

    public void popImagenes(){
            img1Comparar.setImageDrawable(img2Comparar.getDrawable());
            img2Comparar.setImageDrawable(img3.getDrawable());
            img3.setImageDrawable(img4.getDrawable());
            img4.setImageDrawable(img5.getDrawable());
            img5.setImageDrawable(getRandomDrawable());
    }

    public void clickCorres (View v){
        if (isMatching()){
            addPoints(dificultad);
        }
        else{
            deletePoints(dificultad);
        }
        popImagenes();
    }

    public void clickNoCorres (View v){
        if (!isMatching()){
            addPoints(dificultad);
        }
        else{
            deletePoints(dificultad);
        }
        popImagenes();
    }

    public boolean isMatching() {
        if ((((BitmapDrawable)img1Comparar.getDrawable()).getBitmap().equals(((BitmapDrawable)img2Comparar.getDrawable()).getBitmap()))){
            return true;
        }
        else return false;
    }

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
}
