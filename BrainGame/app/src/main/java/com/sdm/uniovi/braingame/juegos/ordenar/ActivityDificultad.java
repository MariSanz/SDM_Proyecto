package com.sdm.uniovi.braingame.juegos.ordenar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sdm.uniovi.braingame.R;

public class ActivityDificultad extends ActionBarActivity {

    private RadioButton rFacil;
    private RadioButton rNormal;
    private RadioButton rDificil;
    private RadioGroup rGroup;
    private int dificultad = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordenar_activity_dificultad);

        rFacil = (RadioButton) findViewById(R.id.radioButton_facil);
        rNormal = (RadioButton) findViewById(R.id.radioButton_normal);
        rDificil = (RadioButton) findViewById(R.id.radioButton_dificil);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup_Dificultad);

    }


    public void irAlJuego(View v){
        int elegido = rGroup.getCheckedRadioButtonId();
        switch (elegido){
            case R.id.radioButton_facil : dificultad = 3;
                break;
            case R.id.radioButton_normal : dificultad = 4;
                break;
            case R.id.radioButton_dificil : dificultad = 6;
                break;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EXTRA_DIFICULTAD", dificultad);
        startActivity(intent);

    }
}
