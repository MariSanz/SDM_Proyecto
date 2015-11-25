package com.sdm.uniovi.braingame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irAPatrones(View view) {
        EstadisticasActivity.iniciar(this, TipoJuego.LEER);
        //Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.recordar.MainActivity.class);
        //startActivity(intent);
    }
}
