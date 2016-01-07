package com.sdm.uniovi.braingame.estadisticas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.sdm.uniovi.braingame.R;
import com.sdm.uniovi.braingame.ServicioWeb.ObtenerTodasLasPuntuaciones;
import com.sdm.uniovi.braingame.ServicioWeb.OnResultadoListener;
import com.sdm.uniovi.braingame.juegos.TipoJuego;
import com.sdm.uniovi.braingame.usuarios.Login;

public class EstadisticasActivity extends AppCompatActivity
    implements OnResultadoListener<Puntuaciones> {

    public static final String EXTRA_ID_SERVICIO_TIPO_JUEGO = "juego";

    private ListView mListViewPuntuaciones;

    public static void iniciar(Context contexto, TipoJuego juego) { //siempre llamar a metodo iniciar, contexto aplicacion
        Intent intent = new Intent(contexto, EstadisticasActivity.class);
        intent.putExtra(EXTRA_ID_SERVICIO_TIPO_JUEGO, juego.getIdServicio());
        contexto.startActivity(intent);
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.estadisticas_activity_main);

        mListViewPuntuaciones = (ListView)findViewById(R.id.lista_estadisticas);


        String juego = getIntent().getStringExtra(EXTRA_ID_SERVICIO_TIPO_JUEGO);
        if (juego == null) {
            throw new RuntimeException("El parámetro juego no se paso a la actividad");
        }

        new ObtenerTodasLasPuntuaciones(
                juego,
                Login.getInstancia(this.getApplicationContext()).getAutenticacion(),
                this
            ).execute();
    }




    @Override
    public void onResultado(Puntuaciones resultado) {
        //si problema en servicio resultado null
        if (resultado != null) {
            mListViewPuntuaciones.setAdapter(new PuntuacionesAdapter(this, resultado.getPuntuaciones()));
        }
    }
}
