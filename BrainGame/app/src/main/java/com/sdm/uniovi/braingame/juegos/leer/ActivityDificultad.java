package com.sdm.uniovi.braingame.juegos.leer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sdm.uniovi.braingame.R;

/**
 * Created by JohanArif on 30/12/2015.
 */
public class ActivityDificultad extends AppCompatActivity{

    private RadioButton rFacil;
    private RadioButton rNormal;
    private RadioButton rDificil;
    private RadioGroup rGroup;
    private TextView descripcion;
    private TextView dificultadTV;
    private Button btEmpezar;

    private int dificultad = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leer_escoger_dificultad);

        rFacil = (RadioButton) findViewById(R.id.radioButtonFacil);
        rNormal = (RadioButton) findViewById(R.id.radioButtonNormal);
        rDificil = (RadioButton) findViewById(R.id.radioButtonDificil);
        rGroup = (RadioGroup) findViewById(R.id.radioGroupDificultades);
        descripcion = (TextView) findViewById(R.id.tvDescripcionDif);
        dificultadTV = (TextView)  findViewById(R.id.tvDificultadDif);
        btEmpezar = (Button) findViewById(R.id.btEmpezarDif);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        rFacil.setTypeface(estiloLetra);
        rNormal.setTypeface(estiloLetra);
        rDificil.setTypeface(estiloLetra);
        descripcion.setTypeface(estiloLetra);
        dificultadTV.setTypeface(estiloLetra);
        btEmpezar.setTypeface(estiloLetra);
    }

    public void irAJuegoLeer(View view){

        int elegido = rGroup.getCheckedRadioButtonId();
        switch (elegido){
            case R.id.radioButtonFacil : dificultad = 3;
                break;
            case R.id.radioButtonNormal : dificultad = 4;
                break;
            case R.id.radioButtonDificil : dificultad = 6;
                break;
        }

        Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.JuegoLeer.class);
        intent.putExtra("EXTRA_DIFICULTAD", dificultad);
        startActivity(intent);
    }


}
