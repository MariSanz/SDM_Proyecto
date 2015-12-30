package com.sdm.uniovi.braingame.juegos.ordenar;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class MainActivity extends AppCompatActivity {

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

    private TextView tVInfo;
    private TextView tVTimer;
    private TextView tVPoints;
    private CountDownTimer countdown;
    private int timerTime = 10000;

    private dragTouchListener dragL = new dragTouchListener();
    private dropListener dropL = new dropListener();

    OrdenarColor[] colors;

    private Integer points = 200;




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
                    dropTarget.setTag(draggedView.getDrawable());
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

        setContentView(R.layout.ordenar_imagenes_activity_main);
        //Connect views of colored images
        imgAzul =(ImageView)findViewById(R.id.imgAzul);
        imgAmarillo =(ImageView)findViewById(R.id.imgAmarillo);
        imgRojo =(ImageView)findViewById(R.id.imgRojo);
        imgVerde =(ImageView)findViewById(R.id.imgVerde);
        imgNaranja =(ImageView)findViewById(R.id.imgNaranja);
        imgMagenta =(ImageView)findViewById(R.id.imgMagenta);

        img1 =(ImageView)findViewById(R.id.img1);
        img2 =(ImageView)findViewById(R.id.img2);
        img3 =(ImageView)findViewById(R.id.img3);
        img4 =(ImageView)findViewById(R.id.img4);
        img5 =(ImageView)findViewById(R.id.img5);
        img6 =(ImageView)findViewById(R.id.img6);

        placeColors();

        tVTimer = (TextView) findViewById(R.id.lblTimer);
        tVInfo = (TextView) findViewById(R.id.lblInformacion);
        tVPoints = (TextView) findViewById(R.id.lblPuntuacion);
        tVPoints.setText(this.getString(R.string.ordenar_Puntos) +  points.toString());

        startTimer(timerTime);

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

    //Hides the images for the user and enables the Listener to drag and drop pictures on the views
    private void deleteImages() {
        img1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
        img1.setOnDragListener(dropL);
        img2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
        img2.setOnDragListener(dropL);
        img3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
        img3.setOnDragListener(dropL);
        img4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
        img4.setOnDragListener(dropL);
        img5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
        img5.setOnDragListener(dropL);
        img6.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));
        img6.setOnDragListener(dropL);

        imgAzul.setOnTouchListener(dragL);
        imgAmarillo.setOnTouchListener(dragL);
        imgRojo.setOnTouchListener(dragL);
        imgVerde.setOnTouchListener(dragL);
        imgNaranja.setOnTouchListener(dragL);
        imgMagenta.setOnTouchListener(dragL);

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

      if (isOrderCorrect() && timerTime > 4000){
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
          points -= 50;
          tVPoints.setText(this.getString(R.string.ordenar_Puntos) + points.toString());

          if (points >= 50) {

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
                              closeActivity();
                          }
                      });
              alertDialog.show();
          }

      }
      else{
          AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
          alertDialog.setTitle(R.string.ordenar_finTitulo);
          alertDialog.setMessage(this.getString(R.string.ordenar_finGanadoMensaje));
          alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, this.getString(R.string.ordenar_ok_button),
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                          closeActivity();
                      }
                  });
          alertDialog.show();

      }

    }

    public boolean isOrderCorrect(){
        boolean correct = true;

        ImageView arrayImage = new ImageView(this);
        arrayImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[0])));

        ImageView blancImage = new ImageView(this);
        blancImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ordenar_blanco));

        if (!(((BitmapDrawable)arrayImage.getDrawable()).getBitmap().equals(((BitmapDrawable)img1.getDrawable()).getBitmap()))){
            correct = false;
        }

        arrayImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[1])));
        if (!(((BitmapDrawable)arrayImage.getDrawable()).getBitmap().equals(((BitmapDrawable)img2.getDrawable()).getBitmap()))){
            correct = false;
        }

        arrayImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[2])));
        if (!(((BitmapDrawable)arrayImage.getDrawable()).getBitmap().equals(((BitmapDrawable)img3.getDrawable()).getBitmap()))){
            correct = false;
        }

        arrayImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[3])));
        if (!(((BitmapDrawable)arrayImage.getDrawable()).getBitmap().equals(((BitmapDrawable)img4.getDrawable()).getBitmap()))){
            correct = false;
        }

        arrayImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[4])));
        if (!(((BitmapDrawable)arrayImage.getDrawable()).getBitmap().equals(((BitmapDrawable)img5.getDrawable()).getBitmap()))){
            correct = false;
        }

        arrayImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[5])));
        if (!(((BitmapDrawable)arrayImage.getDrawable()).getBitmap().equals(((BitmapDrawable)img6.getDrawable()).getBitmap()))){
            correct = false;
        }

        return correct;
    }

    public void placeColors(){
        colors = OrdenarLogica.generizeColors(6);
        img1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),translateColorToImage(colors[0])));
        img2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),translateColorToImage(colors[1])));
        img3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),translateColorToImage(colors[2])));
        img4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),translateColorToImage(colors[3])));
        img5.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[4])));
        img6.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), translateColorToImage(colors[5])));
    }

    public void restartInterface(boolean won){
        placeColors();
        tVInfo.setText(R.string.ordenar_InfoText1);

        img1.setOnDragListener(null);
        img2.setOnDragListener(null);
        img3.setOnDragListener(null);
        img4.setOnDragListener(null);
        img5.setOnDragListener(null);
        img6.setOnDragListener(null);

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


    private void closeActivity(){
        //TO DO: send points to server...
        this.finish();
    }


}


