package com.sdm.uniovi.braingame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                case R.id.menu_cerrar_sesion:
                    Login.getInstancia(getApplicationContext()).desloguear();
                    Intent intent = new Intent(this, LogingActivity.class);
                    Toast.makeText(this, R.string.cerrado_sesion, Toast.LENGTH_LONG).show();
                    this.finish();

                    startActivity(intent);

                    return true;
                case R.id.menu_ayuda:

                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void irAPatrones(View view) {
        EstadisticasActivity.iniciar(this, TipoJuego.RECORDAR);
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.recordar.MainActivity.class);
        startActivity(intent);
    }

    public void irACalcular(View view){
        EstadisticasActivity.iniciar(this, TipoJuego.CALCULAR);
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.calcular.MainActivity.class);
        startActivity(intent);
    }

    public void irAOrdenar(View view) {
        EstadisticasActivity.iniciar(this, TipoJuego.ORDENAR);
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.ordenar.ActivityDificultad.class);
        startActivity(intent);
    }

    public void irALeer(View view){
        EstadisticasActivity.iniciar(this, TipoJuego.LEER);
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.MainActivity.class);
        startActivity(intent);
    }

    public void irACorresponder(View view){
        EstadisticasActivity.iniciar(this, TipoJuego.CORRESPONDER);
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.corresponder.ActivityDificultad.class);
        startActivity(intent);
    }


}
