package com.sdm.uniovi.braingame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sdm.uniovi.braingame.ServicioWeb.ComprobarLogin;
import com.sdm.uniovi.braingame.ServicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.usuarios.Login;
import com.sdm.uniovi.braingame.usuarios.Usuario;

public class LogingActivity  extends AppCompatActivity
    implements OnResultadoListener<Boolean> {

    private EditText etUsuario;
    private EditText etClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging_activity_main);


        //Recupero los elementos de la vista
        TextView tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        TextView tvClave = (TextView) findViewById(R.id.tvClave);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave = (EditText) findViewById(R.id.etClave);
        Button btLogin = (Button) findViewById(R.id.btLogin);
        TextView tvRegistro = (TextView) findViewById(R.id.tvRegistro);


        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
        tvUsuario.setTypeface(estiloLetra);
        tvClave.setTypeface(estiloLetra);
        tvRegistro.setTypeface(estiloLetra);
        etUsuario.setTypeface(estiloLetra);
        btLogin.setTypeface(estiloLetra);


    }

    private Usuario getUsuario() {
        String nombre = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        return new Usuario(nombre, clave);
    }

    public void hacerLogin(View v){
        Usuario usuario = getUsuario();
        new ComprobarLogin(usuario, this).execute();
    }

    public void hacerRegistro(View v){

    }

    @Override
    public void onResultado(Boolean resultado) {
        if (resultado) {
            Usuario usuario = getUsuario();
            Login.getInstancia(getApplicationContext()).loguear(usuario);
            Intent intent = new Intent(this, MainActivity.class ); //lanzo actividad
            startActivity(intent);
            finish();
        }
    }
}
