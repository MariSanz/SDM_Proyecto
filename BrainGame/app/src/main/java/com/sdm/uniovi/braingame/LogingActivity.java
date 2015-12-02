package com.sdm.uniovi.braingame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdm.uniovi.braingame.estadisticas.EstadisticasActivity;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

import org.w3c.dom.Text;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class LogingActivity  extends AppCompatActivity {


    private TextView tvUsuario;
    private TextView tvClave;
    private EditText etUsuario;
    private EditText etClave;
    private Button btLogin;
    private TextView tvRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging_activity_main);


        //Recupero los elementos de la vista
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvClave = (TextView) findViewById(R.id.tvClave);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave = (EditText) findViewById(R.id.etClave);
        btLogin = (Button) findViewById(R.id.btLogin);
        tvRegistro = (TextView) findViewById(R.id.tvRegistro);


        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        tvUsuario.setTypeface(estiloLetra);
        tvClave.setTypeface(estiloLetra);
        tvRegistro.setTypeface(estiloLetra);
        etUsuario.setTypeface(estiloLetra);
        btLogin.setTypeface(estiloLetra);


    }

    public void hacerLogin(View v){

        String nombre = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        if( nombre != null && clave !=null){
            Login.getInstancia(getApplicationContext()).loguear(nombre, clave);
        }

        Intent intent = new Intent(this, MainActivity.class ); //lanzo actividad
        startActivity(intent);
        finish();
    }

    public void hacerRegistro(View v){

    }


}
