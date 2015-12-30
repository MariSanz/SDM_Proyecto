package com.sdm.uniovi.braingame.juegos.leer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sdm.uniovi.braingame.R;

/**
 * Created by JohanArif on 30/12/2015.
 */
public class ActivityDificultad extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leer_escoger_dificultad);
    }

    public void irAJuegoLeer(View view){
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.JuegoLeer.class);
        startActivity(intent);
    }


}
