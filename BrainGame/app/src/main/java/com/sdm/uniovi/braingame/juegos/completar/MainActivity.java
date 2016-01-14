package com.sdm.uniovi.braingame.juegos.completar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
//        Intent intent = new Intent(this, JuegoCompletar.class);
//        startActivity(intent);
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Under construciotn");
        alertDialog.setMessage("Este juego todavía no está implementado.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ordenar_ok_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        closeApp();
                    }
                });
        alertDialog.show();

    }

    public void closeApp(){
        this.finish();
    }

}
