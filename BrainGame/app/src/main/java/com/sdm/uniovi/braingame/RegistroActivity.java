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
import android.widget.Toast;

import com.sdm.uniovi.braingame.servicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.servicioWeb.Registrar;

import com.sdm.uniovi.braingame.usuarios.Usuario;

public class RegistroActivity extends AppCompatActivity
    implements OnResultadoListener<Boolean> {

    private EditText etUsuario;
    private EditText etClave;
    private EditText etRepetirClave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        etUsuario = (EditText) findViewById(R.id.etUsuarioRegistro);
        etClave = (EditText) findViewById(R.id.etClaveRegistro);
        etRepetirClave = (EditText) findViewById(R.id.etRepetirCLave);
        Button btRegistro = (Button) findViewById(R.id.btRegistro);
        btRegistro.setTypeface(estiloLetra);
        etUsuario.setTypeface(estiloLetra);
        etClave.setTypeface(estiloLetra);
        etRepetirClave.setTypeface(estiloLetra);


    }

    private Usuario getUsuario() {
        String nombre = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        String repeCLave = etRepetirClave.getText().toString();
        if(clave.equals(repeCLave)) {
            return new Usuario(nombre, clave);
        }
        return null;
    }

    public void registrar(View v){
        Usuario usuario = getUsuario();

        if(usuario!=null) {

            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean conectado = activeNetwork.isConnectedOrConnecting();

            if (conectado) {
                new Registrar(usuario, this).execute();
            } else {
                Toast.makeText(this, R.string.fallo_conexion, Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, R.string.fallo_clave, Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onResultado(Boolean resultado) {


            if (resultado) {
                showSimplePopUp("Exito", "Registro satisfactorio");
                Intent intent = new Intent(this, LogingActivity.class); //lanzo actividad
                startActivity(intent);
            } else {
                showSimplePopUp("Error", "Usuario ocupado");
            }

    }

    private void showSimplePopUp(String titulo, String mensaje) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(titulo);
        helpBuilder.setMessage(mensaje);
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
