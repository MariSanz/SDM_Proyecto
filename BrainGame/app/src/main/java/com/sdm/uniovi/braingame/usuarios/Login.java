package com.sdm.uniovi.braingame.usuarios;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

/**
 * Created by luism_000 on 02/12/2015.
 */
public class Login {

    public static final String CAMPO_AUTENTICACION = "autenticacion";
    private static Login instancia = null;

    private String autenticacion;
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

    public void loguear(String usuario, String clave) {
        String cadena = usuario + ":" + clave;
        cadena = Base64.encodeToString(cadena.getBytes(), Base64.DEFAULT);
        preferencias.edit()
                .putString(CAMPO_AUTENTICACION, cadena)
                .apply();
    }

    public void desloguear() {
        autenticacion = null;
        preferencias.edit()
                .remove(CAMPO_AUTENTICACION)
                .apply();
    }
}
