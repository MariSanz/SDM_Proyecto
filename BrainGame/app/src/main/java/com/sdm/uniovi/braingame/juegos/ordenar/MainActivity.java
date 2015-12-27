package com.sdm.uniovi.braingame.juegos.ordenar;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

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

    private dragTouchListener dragL = new dragTouchListener();
    private dropListener dropL = new dropListener();


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
        //Connect views of colored images and set dragListeners for them
        imgAzul =(ImageView)findViewById(R.id.imgAzul);
        imgAzul.setOnTouchListener(dragL);
        imgAmarillo =(ImageView)findViewById(R.id.imgAmarillo);
        imgAmarillo.setOnTouchListener(dragL);
        imgRojo =(ImageView)findViewById(R.id.imgRojo);
        imgRojo.setOnTouchListener(dragL);
        imgVerde =(ImageView)findViewById(R.id.imgVerde);
        imgVerde.setOnTouchListener(dragL);
        imgNaranja =(ImageView)findViewById(R.id.imgNaranja);
        imgNaranja.setOnTouchListener(dragL);
        imgMagenta =(ImageView)findViewById(R.id.imgMagenta);
        imgMagenta.setOnTouchListener(dragL);
        //Connect views of emtpy images and set dropListeners for them
        img1 =(ImageView)findViewById(R.id.img1);
        img1.setOnDragListener(dropL);
        img2 =(ImageView)findViewById(R.id.img2);
        img2.setOnDragListener(dropL);
        img3 =(ImageView)findViewById(R.id.img3);
        img3.setOnDragListener(dropL);
        img4 =(ImageView)findViewById(R.id.img4);
        img4.setOnDragListener(dropL);
        img5 =(ImageView)findViewById(R.id.img5);
        img5.setOnDragListener(dropL);
        img6 =(ImageView)findViewById(R.id.img6);
        img6.setOnDragListener(dropL);



    }
}
