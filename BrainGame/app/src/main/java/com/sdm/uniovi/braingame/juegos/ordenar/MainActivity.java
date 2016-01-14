package com.sdm.uniovi.braingame.juegos.ordenar;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.LogingActivity;
import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.servicioWeb.ActualizarPuntuaciones;
import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class MainActivity extends AppCompatActivity  implements OnResultadoListener<Boolean> {

    private ImageView imgAzul;
    private ImageView imgAmarillo;
    private ImageView imgRojo;
    private ImageView imgVerde;
    private ImageView imgNaranja;
    private ImageView imgMagenta;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;

    private ArrayList<ImageView> imagesList;

    private TextView tVInfo;
    private TextView tVTimer;
    private TextView tVPoints;
    private Button btOkay;
    private CountDownTimer countdown;
    private int timerTime = 10000;

    private dragTouchListener dragL = new dragTouchListener();
    private dropListener dropL = new dropListener();

    OrdenarColor[] colors;

    private Integer points = 0;
    private int equivocar = 3;
    private int dificultad;

    @Override
    public void onResultado(Boolean resultado) {

    }


    //Listener for the colored images to be dragged
    private final class dragTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    //Listener for the to this point empty images on top
    //to set image to specific drawable:
    //      dropTarget.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inicio_calcular));
    private class dropListener implements View.OnDragListener {
        ImageView draggedView;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    draggedView = (ImageView) event.getLocalState();
                    draggedView.setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    ImageView dropTarget = (ImageView) v;
                    dropTarget.setImageDrawable(draggedView.getDrawable());
                    dropTarget.setTag(draggedView.getTag());
                    draggedView.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        dificultad = extras.getInt("EXTRA_DIFICULTAD",0);

        setContentView(R.layout.ordenar_imagenes_activity_main);
        //Connect views of colored images
        imgAzul =(ImageView)findViewById(R.id.imgAzul);
        imgAzul.setTag(OrdenarColor.AZUL);
        imgAmarillo =(ImageView)findViewById(R.id.imgAmarillo);
        imgAmarillo.setTag(OrdenarColor.AMARILLO);
        imgRojo =(ImageView)findViewById(R.id.imgRojo);
        imgRojo.setTag(OrdenarColor.ROJO);
        imgVerde =(ImageView)findViewById(R.id.imgVerde);
        imgVerde.setTag(OrdenarColor.VERDE);
        imgNaranja =(ImageView)findViewById(R.id.imgNaranja);
        imgNaranja.setTag(OrdenarColor.NARANJA);
        imgMagenta =(ImageView)findViewById(R.id.imgMagenta);
        imgMagenta.setTag(OrdenarColor.MAGENTA);

        imagesList = new ArrayList<>();

        img1 =(ImageView)findViewById(R.id.img1);
        img2 =(ImageView)findViewById(R.id.img2);
        img3 =(ImageView)findViewById(R.id.img3);
        img4 =(ImageView)findViewById(R.id.img4);
        img5 =(ImageView)findViewById(R.id.img5);
        img6 =(ImageView)findViewById(R.id.img6);

        imagesList.add(img1);
        imagesList.add(img2);
        imagesList.add(img3);
        imagesList.add(img4);
        imagesList.add(img5);
        imagesList.add(img6);

        placeColors(dificultad);

        tVTimer = (TextView) findViewById(R.id.lblTimer);
        tVInfo = (TextView) findViewById(R.id.lblInformacion);
        tVPoints = (TextView) findViewById(R.id.lblPuntuacion);
        btOkay = (Button) findViewById(R.id.btnAceptar);
        tVPoints.setText(this.getString(R.string.ordenar_Puntos) + points.toString());

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        tVTimer.setTypeface(estiloLetra);
        tVInfo.setTypeface(estiloLetra);
        tVPoints.setTypeface(estiloLetra);
        btOkay.setTypeface(estiloLetra);
        
        startTimer(timerTime);

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
                alertDialog.setMessage(this.getString(R.string.ayuda_ordenar));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            case R.id.menu_estadisticas:
                EstadisticasActivity.iniciar(this, TipoJuego.ORDENAR);
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

    private void startTimer(int millis){
        countdown = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tVTimer.setText("  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                tVTimer.setText("  0");
                deleteImages();
                tVInfo.setText(R.string.ordenar_InfoText2);
            }
        };

        countdown.start();
    }

    //Hides the images for the user and enables the Listener to drag and drop pictures on the views and the OK button
    private void deleteImages() {

        for (int i = 0; i < dificultad; i++){
            imagesList.get(i).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
            imagesList.get(i).setOnDragListener(dropL);
        }

        imgAzul.setOnTouchListener(dragL);
        imgAmarillo.setOnTouchListener(dragL);
        imgRojo.setOnTouchListener(dragL);
        imgVerde.setOnTouchListener(dragL);
        imgNaranja.setOnTouchListener(dragL);
        imgMagenta.setOnTouchListener(dragL);

        btOkay.setEnabled(true);

    }

    public int translateColorToImage(OrdenarColor color){

            switch(color) {
                case AZUL:
                    return R.drawable.ordenar_azul;
                case AMARILLO:
                    return R.drawable.ordenar_amarillo;
                case ROJO:
                    return R.drawable.ordenar_rojo;
                case VERDE:
                    return R.drawable.ordenar_verde;
                case NARANJA:
                    return R.drawable.ordenar_naranja;
                case MAGENTA:
                    return R.drawable.ordenar_magenta;
                default:
                    return R.drawable.ordenar_blanco;
        }
    }

    public void confirmOrder (View v){

        btOkay.setEnabled(false);

      if (isOrderCorrect() && timerTime > 4000){
          points += dificultad*10;
          tVPoints.setText(this.getString(R.string.ordenar_Puntos) + points.toString());
          AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
          alertDialog.setTitle(R.string.ordenar_correctoTitulo);
          alertDialog.setMessage(this.getString(R.string.ordenar_correctoMensaje));
          alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                          restartInterface(true);
                      }
                  });
          alertDialog.show();

      }
      else if (!isOrderCorrect()){

          if (equivocar > 0) {
              equivocar--;
              if (points >= 10) {
                  points -= 10;
                  tVPoints.setText(this.getString(R.string.ordenar_Puntos) + points.toString());
              }
              AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
              alertDialog.setTitle(R.string.ordenar_incorrectoTitulo);
              alertDialog.setMessage(this.getString(R.string.ordenar_incorrectoMensaje));
              alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                      new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                              restartInterface(false);
                          }
                      });
              alertDialog.show();
          }
          else{
              AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
              alertDialog.setTitle(R.string.ordenar_finTitulo);
              alertDialog.setMessage(this.getString(R.string.ordenar_finPerdidoMensaje));
              alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                      new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                              closeActivity(false);
                          }
                      });
              alertDialog.show();
          }

      }
      else{
          AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
          alertDialog.setTitle(R.string.ordenar_finTitulo);
          alertDialog.setMessage(this.getString(R.string.ordenar_finGanadoMensaje) + points.toString());
          alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                          closeActivity(true);
                      }
                  });
          alertDialog.show();

      }

    }

    public boolean isOrderCorrect(){
        boolean correct = true;

        for (int i = 0; i < colors.length; i++){
                OrdenarColor c1 = colors[i];
                OrdenarColor c2 = (OrdenarColor) imagesList.get(i).getTag();
                if (!(c1 == c2)){
                    return false;
                }

        }
        return correct;
    }

    public void placeColors(int dificultad){
        colors = generizeColors(dificultad);

        for (int i = 0; i< dificultad; i++){
            imagesList.get(i).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[i])));
        }

    }

    public void restartInterface(boolean won){
        placeColors(dificultad);
        tVInfo.setText(R.string.ordenar_InfoText1);

        for (ImageView i : imagesList){
            i.setOnDragListener(null);
        }

        imgAzul.setOnTouchListener(null);
        imgAmarillo.setOnTouchListener(null);
        imgRojo.setOnTouchListener(null);
        imgVerde.setOnTouchListener(null);
        imgNaranja.setOnTouchListener(null);
        imgMagenta.setOnTouchListener(null);

        if (won) {
            timerTime -= 2000;
            startTimer(timerTime);
        }
        else {
            startTimer(timerTime);
        }

    }

    public void closeActivity(boolean won){
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
                        , TipoJuego.ORDENAR.getIdServicio())
                        .execute();
            } else {

                onPause();
                Toast.makeText(this, R.string.fallo_conexion_estadisticas, Toast.LENGTH_LONG).show();
            }
        }
        this.finish();
    }

    public OrdenarColor[] generizeColors(int number){
        OrdenarColor[] colors = new OrdenarColor[number];
        for (int i = 0; i < number; i++){
            colors[i] = OrdenarColor.getRandom();
        }
        return colors;
    }

}


