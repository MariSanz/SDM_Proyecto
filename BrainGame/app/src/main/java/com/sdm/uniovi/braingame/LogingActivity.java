package com.sdm.uniovi.braingame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.servicioWeb.ComprobarLogin;
import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
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

        Login login = Login.getInstancia(this.getApplicationContext());
        if(login.isLogueado()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {

            //Recupero los elementos de la vista
            TextView tvUsuario = (TextView) findViewById(R.id.tvUsuarioRegistro);
            TextView tvClave = (TextView) findViewById(R.id.tvClaveRegistro);
            etUsuario = (EditText) findViewById(R.id.etUsuarioRegistro);
            etClave = (EditText) findViewById(R.id.etClave);
            Button btLogin = (Button) findViewById(R.id.btLogin);
            TextView tvRegistro = (TextView) findViewById(R.id.btRegistro);


            Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");
            tvUsuario.setTypeface(estiloLetra);
            tvClave.setTypeface(estiloLetra);
            tvRegistro.setTypeface(estiloLetra);
            etUsuario.setTypeface(estiloLetra);
            btLogin.setTypeface(estiloLetra);
        }

    }

    private Usuario getUsuario() {
        String nombre = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        return new Usuario(nombre, clave);
    }

    public void hacerLogin(View v){

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean conectado = activeNetwork.isConnectedOrConnecting();

        if (conectado) {
            Usuario usuario = getUsuario();
            new ComprobarLogin(usuario, this).execute();
        }
        else {
            Toast.makeText(this, R.string.fallo_conexion, Toast.LENGTH_LONG).show();
        }

    }

    public void hacerRegistro(View v){
        Intent intent = new Intent(this, RegistroActivity.class ); //lanzo actividad
        startActivity(intent);
    }

    @Override
    public void onResultado(Boolean resultado) {
        Usuario usuario = getUsuario();
        if (resultado) {

            Login.getInstancia(getApplicationContext()).loguear(usuario);
            Intent intent = new Intent(this, MainActivity.class ); //lanzo actividad
            startActivity(intent);
            this.finish();
        }else{
            showSimplePopUp();
        }

    }

    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Error");
        helpBuilder.setMessage("Usuario o clave incorrectas");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}
