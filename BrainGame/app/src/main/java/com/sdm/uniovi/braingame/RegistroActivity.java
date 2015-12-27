package com.sdm.uniovi.braingame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdm.uniovi.braingame.ServicioWeb.ComprobarLogin;
import com.sdm.uniovi.braingame.ServicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.ServicioWeb.Registrar;
import com.sdm.uniovi.braingame.usuarios.Login;
import com.sdm.uniovi.braingame.usuarios.Usuario;

public class RegistroActivity extends AppCompatActivity
    implements OnResultadoListener<Boolean> {

    private EditText etUsuario;
    private EditText etClave;
    private Button btRegistro;
    private ProgressBar registrando;

    private boolean registrado= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        etUsuario = (EditText) findViewById(R.id.etUsuarioRegistro);
        etClave = (EditText) findViewById(R.id.etClaveRegistro);
        btRegistro = (Button) findViewById(R.id.btRegistro);
        btRegistro.setTypeface(estiloLetra);
        etUsuario.setTypeface(estiloLetra);
        etClave.setTypeface(estiloLetra);

        registrando = (ProgressBar) findViewById(R.id.pBRegistro);
    }

    private Usuario getUsuario() {
        String nombre = etUsuario.getText().toString();
        String clave = etClave.getText().toString();
        return new Usuario(nombre, clave);
    }

    public void registrar(View v){
        Usuario usuario = getUsuario();
        Registrar registrar = new Registrar(usuario, this);
        registrar.execute();
        while(registrar.getStatus()!= AsyncTask.Status.FINISHED){
            registrando.setVisibility(View.VISIBLE);
            btRegistro.setEnabled(false);

        }
        if(registrar.getStatus()== AsyncTask.Status.FINISHED) {
            if (registrado) {
                showSimplePopUp("Exito", "Registro satisfactorio");
                Intent intent = new Intent(this, LogingActivity.class); //lanzo actividad
                startActivity(intent);
            } else {
                showSimplePopUp("Error", "Usuario ocupado");
            }
        }else{
            showSimplePopUp("Error", "Error con el servidor");
        }

    }

    @Override
    public void onResultado(Boolean resultado) {
        registrado=resultado;
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