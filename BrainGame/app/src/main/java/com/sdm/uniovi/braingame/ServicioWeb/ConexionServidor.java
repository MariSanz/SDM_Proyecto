package com.sdm.uniovi.braingame.ServicioWeb;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.sdm.uniovi.braingame.estadisticas.Puntuaciones;

import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by luism_000 on 26/11/2015.
 */
public abstract class ConexionServidor extends AsyncTask<Void, Void, Object> {
    private static final String TAG = ConexionServidor.class.getName();

    private static final String URL_SW_DESARROLLO = "http://10.0.2.2/SDM_PHP/index.php";
    private static final String URL_SW_PRODUCCION = "http://braingame.esy.es/index.php";

    protected static final String URL_EN_USO = URL_SW_DESARROLLO;

    private String metodo;

    protected String urlString;

    @Override
    protected Object doInBackground(Void... params) {

        HttpURLConnection conexion = null;
        try {

            URL url = construirURL();// new URL(URL_SW_DESARROLLO + "/puntuaciones/" + mIdJuego);

            conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("GET");
            conexion.setDoInput(true);
            conexion.setDoOutput(false);

            int codigo = conexion.getResponseCode();

            String respuesta = getResponseText(conexion.getInputStream());

            Log.v(TAG, respuesta);
            if (codigo == HttpURLConnection.HTTP_OK) {
                return procesarRetorno(respuesta);
            } else if (codigo == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.w(TAG, "El servicio devolvió acceso no autorizado");
            } else {
                Log.w(TAG, "El servicio devolvió un codigo de respuesta no válido: " + codigo);
            }

        } catch (Exception e) {
            Log.w(TAG, "No se pudo completar la llamada al servicio", e);
        } finally {
            if (conexion != null) {
                conexion.disconnect();
            }
        }
        return null;
    }

    protected abstract Object procesarRetorno(String respuesta) throws JSONException;

    protected abstract URL construirURL() throws MalformedURLException;

    @Override
    protected void onPostExecute(Object puntuaciones) {///////////////////////////VER
        //Callback ...para acceso a la activity, actualizar interfaz
    }

    private String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    protected void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    protected String getMetodo() {
        return metodo;
    }

    protected static String getUrlEnUso() {
        return URL_EN_USO;
    }
}
