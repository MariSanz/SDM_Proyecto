package com.sdm.uniovi.braingame.juegos.ordenar;

import android.app.AlertDialog;
import android.content.ClipData;
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

/**
 * This class implements the game Ordenar. All the game logic is contained inside the class.
 * The class uses the enumeration OrdenarColor that contains the different colors important fot the game
 */
public class MainActivity extends AppCompatActivity  implements OnResultadoListener<Boolean> {

    private ImageView imgAzul;
    private ImageView imgAmarillo;
    private ImageView imgRojo;
    private ImageView imgVerde;
    private ImageView imgNaranja;
    private ImageView imgMagenta;

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

        //dificultad= 3,4,6; correponding to the number of pictures to remember
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

        //to acces the ImageViews through their index
        imagesList = new ArrayList<>();

        ImageView img1 =(ImageView)findViewById(R.id.img1);
        ImageView img2 =(ImageView)findViewById(R.id.img2);
        ImageView img3 =(ImageView)findViewById(R.id.img3);
        ImageView img4 =(ImageView)findViewById(R.id.img4);
        ImageView img5 =(ImageView)findViewById(R.id.img5);
        ImageView img6 =(ImageView)findViewById(R.id.img6);

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

    //Necessary to Translate the OrdenarColors from the generated array into
    //Drawables to insert into the ImageViews
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

    //If the timer is under 4 seconds, the game is over (4 rounds). The points added to the highscore
    //are given according to the difficulty. If the game is not over yet, it is checked if the order is correct
    //If it is, the game is restarted with less time. If no, it is checked if the player has given the wrong order
    //enough times to loose (3 times). If not, the game is restarted with the same amount of time as before
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

    //Through the Tags the colore ImgeViews have and the tagging into the dropped ImageView, the
    //color in the top array is determined. If this color is equal to the corresponding in the
    //order array, the match is correct. If all matches are correct, true is returned, else false.
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

    //generizeColors returns a random OrdenarColor array of the size of the difficulty.
    //Here the colores are set into the ImageViews to show to the player
    public void placeColors(int dificultad){
        colors = generizeColors(dificultad);
        for (int i = 0; i< dificultad; i++){
            imagesList.get(i).setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[i])));
        }

    }

    //Sets the interface to the beginning. The Listeners are unset to deny the dragging of images during the
    //remembering process. Then the timer is started again, with a tim depending on the correctness of the last round
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

    //generizes a random array of OrdenarColors using the random function of the enum
    public OrdenarColor[] generizeColors(int number){
        OrdenarColor[] colors = new OrdenarColor[number];
        for (int i = 0; i < number; i++){
            colors[i] = OrdenarColor.getRandom();
        }
        return colors;
    }

    //The Server is called to submit the highscore in case the game was won. Then the
    //Activity is finished.
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
                        , TipoJuego.ORDENAR)
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


