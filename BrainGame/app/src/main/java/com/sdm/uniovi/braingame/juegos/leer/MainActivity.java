package com.sdm.uniovi.braingame.juegos.leer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leer_colores_activity_main);

        TextView tituloJuego = (TextView) findViewById(R.id.tvTituloJuegoIni);
        TextView descripcion = (TextView) findViewById(R.id.tvDescripcionIni);
        Button btSiguiente = (Button) findViewById(R.id.btSiguienteIni);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        tituloJuego.setTypeface(estiloLetra);
        descripcion.setTypeface(estiloLetra);
        btSiguiente.setTypeface(estiloLetra);
        

    }

    public void irAEscogerDificultad(View view){
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.ActivityDificultad.class);

        startActivity(intent);
    }
}
