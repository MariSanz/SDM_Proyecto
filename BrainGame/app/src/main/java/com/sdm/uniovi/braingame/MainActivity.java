package com.sdm.uniovi.braingame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.sdm.uniovi.braingame.juegos.calcular.JuegoCalcular;
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
                    Intent intentAyuda = new Intent(this, AyudaPrincipalActivity.class);
                    startActivity(intentAyuda);

                    return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void irAPatrones(View view) {

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.recordar.ActivityInicio.class);
        startActivity(intent);
    }

    public void irACalcular(View view){
        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.calcular.MainActivity.class);
        startActivity(intent);
    }

    public void irAOrdenar(View view) {

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.ordenar.ActivityInicio.class);
        startActivity(intent);
    }

    public void irALeer(View view){

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.MainActivity.class);
        startActivity(intent);
    }


    public void irACorresponder(View view){

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.corresponder.ActivityInicio.class);
        startActivity(intent);
    }

    public void irACompletar(View view) {

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.completar.MainActivity.class);
        startActivity(intent);
    }

    public void irAConocer(View view) {

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.conocer.MainActivity.class);
        startActivity(intent);
    }




}
