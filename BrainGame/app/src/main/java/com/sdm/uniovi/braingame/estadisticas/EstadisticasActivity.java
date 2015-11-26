package com.sdm.uniovi.braingame.estadisticas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sdm.uniovi.braingame.ServicioWeb.ServidorPuntuaciones;
import com.sdm.uniovi.braingame.juegos.TipoJuego;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pintarEstadisticas();
        String juego = getIntent().getStringExtra(EXTRA_ID_SERVICIO_TIPO_JUEGO);
        if (juego == null) {
            throw new RuntimeException("El parámetro juego no se paso a la actividad");
        }

        ServidorPuntuaciones puntuacionesWeb = new ServidorPuntuaciones();
        puntuacionesWeb.getPuntuacionesTodas(juego);
        puntuacionesWeb.execute(); //se puede diferenciar entre estadisticas para cada usuario
    }

    private void pintarEstadisticas() {

    }


}
