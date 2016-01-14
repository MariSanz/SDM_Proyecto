package com.sdm.uniovi.braingame.juegos.ordenar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

public class ActivityInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordenar_activity_inicio);

        TextView tituloJuego = (TextView) findViewById(R.id.tvTituloJuegoIni);
        TextView descripcion = (TextView) findViewById(R.id.tvDescripcionIni);
        Button btSiguiente = (Button) findViewById(R.id.btSiguienteIni);
        Button btAtras = (Button) findViewById(R.id.btAtrasIni);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        tituloJuego.setTypeface(estiloLetra);
        descripcion.setTypeface(estiloLetra);
        btSiguiente.setTypeface(estiloLetra);
        btAtras.setTypeface(estiloLetra);
    }

    public void irAEscogerDificultad(View view) {
        Intent intent = new Intent(this,
                com.sdm.uniovi.braingame.juegos.ordenar.ActivityDificultad.class);
        startActivity(intent);
        this.finish();
    }

    public void irAtrasResIni(View view) {
        this.finish();
    }

}
