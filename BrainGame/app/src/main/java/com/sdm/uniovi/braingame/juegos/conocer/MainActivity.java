package com.sdm.uniovi.braingame.juegos.conocer;


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
        setContentView(R.layout.responder_activity_main);


        TextView tituloJuego = (TextView) findViewById(R.id.tvTituloResIni);
        TextView descripcion = (TextView) findViewById(R.id.tvDescripcionRes);
        Button btSiguiente = (Button) findViewById(R.id.btSiguienteResIni);
        Button btAtras = (Button) findViewById(R.id.btAtrasResIni);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        tituloJuego.setTypeface(estiloLetra);
        descripcion.setTypeface(estiloLetra);
        btSiguiente.setTypeface(estiloLetra);
        btAtras.setTypeface(estiloLetra);
    }

    public void irAEscogerDificultadRes(View view) {
        Intent intent = new Intent(this,
                com.sdm.uniovi.braingame.juegos.conocer.ActivityDificultad.class);

        startActivity(intent);
    }

    public void irAtrasResIni(View view) {
        this.finish();
    }

}


