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
import android.widget.Toast;

import com.sdm.uniovi.braingame.R;

public class ActivityDificultad extends AppCompatActivity{

    private RadioGroup rGroup;



    private int dificultad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.escoger_dificultad_activity);

        RadioButton rFacil = (RadioButton) findViewById(R.id.radioButtonFacil);
        RadioButton rNormal = (RadioButton) findViewById(R.id.radioButtonNormal);
        RadioButton rDificil = (RadioButton) findViewById(R.id.radioButtonDificil);
        rGroup = (RadioGroup) findViewById(R.id.radioGroupDificultades);

        TextView dificultadTV = (TextView)  findViewById(R.id.tvDificultadDif);
        Button btEmpezar = (Button) findViewById(R.id.btEmpezarDif);
        Button btAtras = (Button) findViewById(R.id.btAtrasDif);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        rFacil.setTypeface(estiloLetra);
        rNormal.setTypeface(estiloLetra);
        rDificil.setTypeface(estiloLetra);

        dificultadTV.setTypeface(estiloLetra);
        btEmpezar.setTypeface(estiloLetra);
        btAtras.setTypeface(estiloLetra);
    }

    public void irAJuego(View view){

        int elegido = rGroup.getCheckedRadioButtonId();
        switch (elegido){
            case R.id.radioButtonFacil : dificultad = 1;
                break;
            case R.id.radioButtonNormal : dificultad = 2;
                break;
            case R.id.radioButtonDificil : dificultad = 3;
                break;
        }

        if(elegido>0) {
            Intent intent = new Intent(this, com.sdm.uniovi.braingame.juegos.leer.JuegoLeer.class);
            intent.putExtra("EXTRA_DIFICULTAD", dificultad);
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.falta_seleccion, Toast.LENGTH_LONG).show();
        }
    }

    public void irAtrasDif(View view){
        this.finish();
    }


}
