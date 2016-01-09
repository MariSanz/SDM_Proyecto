package com.sdm.uniovi.braingame;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AyudaPrincipalActivity extends AppCompatActivity {

    private TextView textoPrincipal;
    private String texto="BrainGame es una aplicación que busca probar y entrenar la agilidad " +
            "mental del usuario con 7 juegos que te resultaran entretenidos, utiles y competitivos. El primero " +
            "de los juegos es \"Memorizar\" donde se reta la capacidad de memoria para los patrones de desbloqueo " +
            "movil que tiene el usuario. El segundo de los juegos es \"Ordenar\" donde por orden logico de secuencia " +
            "tendra el usuario se recordar y ordenar las imagenes que se le muestran. El tercer juego es \"Resolver\" " +
            "donde por nivel de dificultad tendras que calcular las operaciones matematicas que se te presenten. El " +
            "cuarto juego es \"Leer\" donde se pone a prueba tu agilidad mental para leer el color con el que está " +
            "pintada el nombre del color. El quinto juego es \"Responder\" con un amplio nuemero de preguntas de " +
            "cultura general a las que tendras que responder para ganar a los demás competidores. El sexto juego es \"Completar\" " +
            "donde cual juego clásico del 'Horcado' tendras que completar la palabra a la que corresponde a informacion dada. Por último " +
            "no menos interesante está el juego de \"Emparejar\" donde tendras que responder por logica la pareja de la imagen que se te muestra.\n" +
            "Pon a prueba tu agilidad mental con estos 7 juegos y diles a tus amigos cuan mejor eres que ellos.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");

        textoPrincipal = (TextView) findViewById(R.id.tvAyudaPrincipal);
        textoPrincipal.setTypeface(estiloLetra);

        textoPrincipal.setText(texto);



    }

}
