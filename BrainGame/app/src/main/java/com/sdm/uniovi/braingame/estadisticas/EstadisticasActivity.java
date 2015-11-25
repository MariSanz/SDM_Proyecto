package com.sdm.uniovi.braingame.estadisticas;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sdm.uniovi.braingame.juegos.TipoJuego;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class EstadisticasActivity extends AppCompatActivity {

    public static final String EXTRA_ID_SERVICIO_TIPO_JUEGO = "juego";

    public static void iniciar(Context contexto, TipoJuego juego) {
        Intent intent = new Intent(contexto, EstadisticasActivity.class);
        intent.putExtra(EXTRA_ID_SERVICIO_TIPO_JUEGO, juego.getIdServicio());
        contexto.startActivity(intent);
    }

    private static class GetPuntuaciones extends AsyncTask<Void, Void, Puntuaciones> {

        private static final String TAG = GetPuntuaciones.class.getName();

        private static final String URL_SW_DESARROLLO = "http://10.0.2.2/SDM_PHP/index.php";
        private static final String URL_SW_PRODUCCION = "http://braingame.esy.es/index.php";

        private String mIdJuego;
        private Handler mHandlerResultado;

        private GetPuntuaciones(String idJuego) {
            mIdJuego = idJuego;
            mHandlerResultado = new Handler(Looper.getMainLooper());
        }

        @Override
        protected Puntuaciones doInBackground(Void... params) {

            HttpURLConnection conexion = null;
            try {

                URL url = new URL(URL_SW_DESARROLLO + "/puntuaciones/" + mIdJuego);

                conexion = (HttpURLConnection)url.openConnection();

                conexion.setRequestMethod("GET");
                conexion.setDoInput(true);
                conexion.setDoOutput(false);

                int codigo = conexion.getResponseCode();

                String respuesta = getResponseText(conexion.getInputStream());

                Log.v(TAG, respuesta);
                if (codigo == HttpURLConnection.HTTP_OK){
                    return new Puntuaciones(respuesta);
                }
                else if (codigo == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    Log.w(TAG, "El servicio devolvió acceso no autorizado");
                }
                else {
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

        @Override
        protected void onPostExecute(Puntuaciones puntuaciones) {
                //Callback ...para acceso a la activity, actualizar interfaz
        }

        private String getResponseText(InputStream inStream) {
             return new Scanner(inStream).useDelimiter("\\A").next();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String juego = getIntent().getStringExtra(EXTRA_ID_SERVICIO_TIPO_JUEGO);
        if (juego == null) {
            throw new RuntimeException("El parámetro juego no se paso a la actividad");
        }

        new GetPuntuaciones(juego).execute();
    }
}
