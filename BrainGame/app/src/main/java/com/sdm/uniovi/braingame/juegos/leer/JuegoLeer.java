package com.sdm.uniovi.braingame.juegos.leer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.uniovi.braingame.R;

import java.util.ArrayList;

/**
 * Created by JohanArif on 30/12/2015.
 */
public class JuegoLeer extends AppCompatActivity {

    private CountDownTimer crono;
    private SpeechRecognizer reconocedor;
    private TextView textViewTiempo;
    private TextView textViewPalabra;
    private static final String[] COLORESVALIDOS = { "amarillo", "azul", "rojo", "blanco", "negro",
            "verde", "morado", "naranja", "rosa"};

    private static final String TAG = JuegoLeer.class.getName();

    private static final int COLORESVALIDOS_LENGTH = COLORESVALIDOS.length;
    private Intent vozIntent;
    private PowerManager.WakeLock mWakeLock;
    private ArrayList<String> resultados;
    private boolean terminado = false;

    private Handler manejador = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leer_interfaz);

        textViewTiempo = (TextView)findViewById(R.id.textViewTiempoRestante);
        textViewPalabra = (TextView)findViewById(R.id.textViewPalabra);
        textViewPalabra.setText("Amarillo");
        textViewPalabra.setTextColor(0xFF00FF00);

    }

    @Override
    protected void onStart(){
        iniciarTemporizador();
        iniciarReconocimientoDeVoz();

        super.onStart();
    }

    public void iniciarTemporizador(){
        crono = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTiempo.setText("  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                textViewTiempo.setText("  0");
            }
        };

        crono.start();
    }

    public void iniciarReconocimientoDeVoz(){
        reconocedor = SpeechRecognizer.createSpeechRecognizer(JuegoLeer.this);
        ReceptorVoz receptor = new ReceptorVoz();
        reconocedor.setRecognitionListener(receptor);
        vozIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        vozIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.sdm.uniovi.braingame.juegos" +
                ".leer");

        vozIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        vozIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        vozIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
        this.mWakeLock.acquire();

        startActivityForResult(vozIntent, 0);

        //reconocedor.startListening(vozIntent);

    }

    private void tratarResultados(ArrayList<String> resultadosStrings) {
        Toast.makeText(getApplicationContext(), "Probando",
                Toast.LENGTH_SHORT).show();

        boolean encontrado = false;
        ArrayList<String> coloresValidosTemp = new ArrayList<String>();

        for(int i = 0; i < COLORESVALIDOS_LENGTH; i++ ){
            coloresValidosTemp.add(COLORESVALIDOS[i]);
        }

        Toast.makeText(getApplicationContext(), "Probando",
                Toast.LENGTH_SHORT).show();

        if(coloresValidosTemp.contains(resultadosStrings.get(0))){ //En la primera posición está
            //la respuesta con más prob...

        }else{
            Toast.makeText(getApplicationContext(), "Lo que ha dicho no es un color válido.",
                    Toast.LENGTH_SHORT).show();
        }

        manejador.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Probando", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause(){
        if(reconocedor != null){
            reconocedor.destroy();
            reconocedor = null;
        }
        this.mWakeLock.release();
        super.onPause();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    { if (resultCode == RESULT_OK) {
        ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        textViewPalabra.setText(matches.toString());
    }}


    class ReceptorVoz implements RecognitionListener{



        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "Listo para iniciar el reconocmiento");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "Inicio del reconocimiento");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "RMS");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "Buffer recibido");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "Inicio del reconocimiento");
        }

        @Override
        public void onError(int error) {

            if(error == SpeechRecognizer.ERROR_CLIENT || error ==
                                            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS){
                Log.d(TAG, "Error del usuario");
                terminado = true;
            }else{
                Log.d(TAG, "Otros errores");
                reconocedor.startListening(vozIntent);
            }
        }

        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "Resultados");
            resultados = null;
            if(results != null){
                resultados = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                final ArrayList<String> resultadosStrings = resultados;
                tratarResultados(resultadosStrings);

                if(!terminado){
                    reconocedor.startListening(vozIntent);
                }else {
                    finish();
                }
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "Resultados parciales");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "Evento");
        }
    }


}
