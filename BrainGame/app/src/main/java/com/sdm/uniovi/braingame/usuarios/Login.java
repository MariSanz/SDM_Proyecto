package com.sdm.uniovi.braingame.usuarios;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

public class Login {

    public static final String CAMPO_AUTENTICACION = "autenticacion";
    private static Login instancia = null;

    private String autenticacion;
    private String usuario;
    private SharedPreferences preferencias;

    private Login(Context context) {

        preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        autenticacion = preferencias.getString(CAMPO_AUTENTICACION, null);
    }

    public static Login getInstancia(Context context) {
        if (instancia == null) {
            instancia = new Login(context);
        }
        return instancia;
    }

    public boolean isLogueado() {
        return autenticacion != null;
    }

    public String getAutenticacion() {//cadena autenticación para firmar servicios
        return autenticacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void loguear(Usuario usuario) {
        String cadena = usuario.getNombre() + ":" + usuario.getClave();
        cadena = Base64.encodeToString(cadena.getBytes(), Base64.DEFAULT);
        preferencias.edit()
                .putString(CAMPO_AUTENTICACION, cadena)
                .apply();
        this.usuario=usuario.getNombre();
    }

    public void desloguear() {
        autenticacion = null;
        preferencias.edit()
                .remove(CAMPO_AUTENTICACION)
                .apply();
    }
}
