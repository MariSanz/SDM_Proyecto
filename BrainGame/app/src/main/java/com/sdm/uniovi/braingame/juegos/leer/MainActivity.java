package com.sdm.uniovi.braingame.juegos.leer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sdm.uniovi.braingame.R;


/**
 * Created by luism_000 on 11/11/2015.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leer_colores_activity_main);
    }

    public void irAEscogerDificultad(View view){
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.ActivityDificultad.class);

        startActivity(intent);
    }
}
