package com.sdm.uniovi.braingame.servicioWeb;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by luism_000 on 26/11/2015.
 */
public abstract class ConexionServidor<T> extends AsyncTask<Void, Void, T> {
    private static final String TAG = ConexionServidor.class.getName();

    private static final String URL_SW_DESARROLLO = "http://10.0.2.2/SDM_PHP/index.php";
    private static final String URL_SW_PRODUCCION = "http://braingame.esy.es/index.php";

    protected static final String URL_EN_USO = URL_SW_PRODUCCION;

    private String metodo;

    protected String urlString;

    private OnResultadoListener<T> onResultadoListener;

    public ConexionServidor(OnResultadoListener<T> onResultadoListener) {
        this.onResultadoListener = onResultadoListener;
    }

    @Override
    protected T doInBackground(Void... params) {

        HttpURLConnection conexion = null;
        try {

            URL url = construirURL();// new URL(URL_SW_DESARROLLO + "/puntuaciones/" + mIdJuego);

            conexion = (HttpURLConnection) url.openConnection();

            JSONObject parametros = getParametros();

            if (parametros != null) {
                conexion.setRequestMethod("POST");
            }
            else {
                conexion.setRequestMethod("GET");
            }
            conexion.setDoInput(true);
            conexion.setDoOutput(parametros != null);

            String authorization = getAutorizacion();
            if (authorization != null) {
                conexion.setRequestProperty("Authorization", "Basic " + authorization);
            }

            if (parametros != null) {
                conexion.setRequestProperty("Content-Type", "application/json");
                Writer salida = new OutputStreamWriter(conexion.getOutputStream());
                salida.write(parametros.toString());
                salida.close();
            }

            int codigo = conexion.getResponseCode();

            if (codigo == HttpURLConnection.HTTP_OK) {
                String respuesta = getResponseText(conexion.getInputStream());
                return procesarRetorno(respuesta);
            } else if (codigo == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.w(TAG, "El servicio devolvió acceso no autorizado");
            } else {
                String respuesta = getResponseText(conexion.getErrorStream());
                Log.w(TAG, "El servicio devolvió un codigo de respuesta no válido: " + codigo);
                Log.d(TAG, "Respuesta del servidor: " + respuesta);
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

    protected abstract T procesarRetorno(String respuesta) throws JSONException;

    protected abstract URL construirURL() throws MalformedURLException;

    protected JSONObject getParametros() throws JSONException {
        return null;
    }

    protected String getAutorizacion() {
        return null;
    }

    @Override
    protected void onPostExecute(T resultado) {///////////////////////////VER
        onResultadoListener.onResultado(resultado);
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
