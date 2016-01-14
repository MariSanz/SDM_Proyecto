package com.sdm.uniovi.braingame.juegos.recordar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

public class ActivityDificultad extends AppCompatActivity {

    private TextView tVTitulo;
    private TextView tVSeleccion;
    private RadioButton rFacil;
    private RadioButton rNormal;
    private RadioButton rDificil;
    private RadioGroup rGroup;
    private int dificultad = 0;
    private Button btEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordar_activity_dificultad);

        rFacil = (RadioButton) findViewById(R.id.radioButton_facil);
        rNormal = (RadioButton) findViewById(R.id.radioButton_normal);
        rDificil = (RadioButton) findViewById(R.id.radioButton_dificil);
        tVTitulo = (TextView) findViewById(R.id.lbTitulo);
        tVSeleccion = (TextView) findViewById(R.id.lbSeleccion);
        btEmpezar = (Button) findViewById(R.id.btEmpezar);
        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        rFacil.setTypeface(estiloLetra);
        rNormal.setTypeface(estiloLetra);
        rDificil.setTypeface(estiloLetra);
        tVTitulo.setTypeface(estiloLetra);
        tVSeleccion.setTypeface(estiloLetra);
        btEmpezar.setTypeface(estiloLetra);

        rGroup = (RadioGroup) findViewById(R.id.radioGroup_Dificultad);
    }

    public void irAlJuego (View v){
        int elegido = rGroup.getCheckedRadioButtonId();
        switch (elegido){
            case R.id.radioButton_facil : dificultad = 6;
                break;
            case R.id.radioButton_normal : dificultad = 8;
                break;
            case R.id.radioButton_dificil : dificultad = 10;
                break;
        }

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.recordar.MainActivity.class);
        intent.putExtra("EXTRA_DIFICULTAD", dificultad);
        startActivity(intent);
        this.finish();
    }
}
