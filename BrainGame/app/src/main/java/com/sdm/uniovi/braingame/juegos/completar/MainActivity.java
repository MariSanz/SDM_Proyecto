package com.sdm.uniovi.braingame.juegos.completar;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

public class MainActivity extends AppCompatActivity {

    TextView resumenTextView;
    Button buttonAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completar_activity_main);

        resumenTextView = (TextView)findViewById(R.id.textViewResumenCompletar);
        buttonAceptar = (Button) findViewById(R.id.buttonSiguienteCompletar);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        resumenTextView.setTypeface(estiloLetra);
        buttonAceptar.setTypeface(estiloLetra);
    }

    public void iniciarCompletar(View view) {
        Intent intent = new Intent(this, JuegoCompletar.class);
        startActivity(intent);
        this.finish();
    }

}
