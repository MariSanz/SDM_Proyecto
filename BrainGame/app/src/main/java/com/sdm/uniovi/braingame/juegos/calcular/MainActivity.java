package com.sdm.uniovi.braingame.juegos.calcular;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcular_inicio);

        TextView tituloJuego = (TextView) findViewById(R.id.tvTituloJuego);
        TextView descripcion = (TextView) findViewById(R.id.tvDescripcion);
        Button btSiguiente = (Button) findViewById(R.id.btSiguiente);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        tituloJuego.setTypeface(estiloLetra);
        descripcion.setTypeface(estiloLetra);
        btSiguiente.setTypeface(estiloLetra);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_juegos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.menu_estadisticas:
                EstadisticasActivity.iniciar(this, TipoJuego.LEER);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void irAEscogerDificultad(View view){
        Intent intent = new Intent(this, DificultadActivity.class);
        startActivity(intent);
    }
}
