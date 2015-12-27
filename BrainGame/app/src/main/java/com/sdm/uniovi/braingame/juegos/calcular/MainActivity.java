package com.sdm.uniovi.braingame.juegos.calcular;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import com.sdm.uniovi.braingame.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int NIVEL_MAXIMO = 4;
    public static int NIVEL_MINIMO = 1;
    private int nivelActual = NIVEL_MINIMO;


    private TextView textViewFormula = null;

    private RadioButton[] opciones = new RadioButton[4];
    private RadioButton opcion1;
    private RadioButton opcion2;
    private RadioButton opcion3;
    private RadioButton opcion4;

    private double[] resultados = new double[12];
    private String[] operaciones = new String[12];
    private Map<Integer, List<Double>> erroresPosibles = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calcular_activity_main);

        Typeface estiloLetra = Typeface.createFromAsset(getAssets(), "fonts/daville.ttf");


        /*WebView webViewFormula = (WebView)findViewById(R.id.webViewFormula);
        webViewFormula.getSettings().setJavaScriptEnabled(true);
        webViewFormula.loadUrl("file:///android_assets/formula/formula.html");*/
        textViewFormula = (TextView) findViewById(R.id.textViewFormula);
        textViewFormula.setTypeface(estiloLetra);

        opcion1 = (RadioButton) findViewById(R.id.rBCalcularOp1);
        opcion1.setTypeface(estiloLetra);
        opciones[0] = opcion1;
        opcion2 = (RadioButton) findViewById(R.id.rBCalcularOp2);
        opcion2.setTypeface(estiloLetra);
        opciones[1] = opcion1;
        opcion3 = (RadioButton) findViewById(R.id.rBCalcularOp3);
        opcion3.setTypeface(estiloLetra);
        opciones[2] = opcion1;
        opcion4 = (RadioButton) findViewById(R.id.rBCalcularOp4);
        opcion4.setTypeface(estiloLetra);
        opciones[3] = opcion1;



        jugar();


    }

    private void jugar() {
        if (nivelActual == 1) {
            jugarNivel1();
        }
    }


    private void jugarNivel1() {

        cargarOperaciones(10);

        Random random = new Random();
        int operacionNum = random.nextInt(11);
        textViewFormula.setText(operaciones[operacionNum]);

        int opcionCorrecta = random.nextInt(3);


        opciones[opcionCorrecta].setText(String.valueOf(resultados[operacionNum]));

        List<Double> errores = erroresPosibles.get(operacionNum);

        /*int j=0;
        for(int i=0; i<opciones.length;i++){

            if(i!=opcionCorrecta){
                opciones[opcionCorrecta].setText(String.valueOf(errores.get(j)));
                j++;
            }
        }*/
    }



    private void cargarOperaciones(int hasta) {
        Random random = new Random();

        int num1 = random.nextInt(hasta);
        int num2 = random.nextInt(hasta);
        int num3 = random.nextInt(hasta);

        double sumaTodo = num1+num2+num3;  String sumaTodoT = num1+" + "+num2+" + "+num3;
        double combi1 = num1-num2+num3;    String combi1T = num1+" - "+num2+" + "+num3;
        double combi2 = num1*num2+num3;    String combi2T = num1+" x "+num2+" + "+num3;
        double restaTodo = num1-num2-num3; String restaTodoT = num1+" - "+num2+" - "+num3;
        double combi3 = num1+num2-num3;    String combi3T = num1+" + "+num2+" - "+num3;
        double combi4 = num1*num2-num3;    String combi4T = num1+" x "+num2+" -"+num3;
        double combi5 = num1*num2/num3;    String combi5T = num1+" x "+num2+" / "+num3;
        double combi6 = num1+num2/num3;    String combi6T = num1+" + "+num2+" / "+num3;
        double combi7 = num1-num2/num3;    String combi7T = num1+" - "+num2+" / "+num3;
        double multiTodo = num1*num2*num3; String multiTodoT = num1+" x "+num2+" x "+num3;
        double combi8 = num1+num2*num3;    String combi8T = num1+" + "+num2+" x "+num3;
        double combi9 = num1-num2*num3;    String combi9T = num1+" - "+num2+" x "+num3;


        List<Double> malasSoluciones = new ArrayList<>();

        malasSoluciones.add(combi1); malasSoluciones.add(combi2); malasSoluciones.add(combi3); malasSoluciones.clear();
        erroresPosibles.put(0, malasSoluciones);

        malasSoluciones.add(sumaTodo);malasSoluciones.add(restaTodo); malasSoluciones.add(combi3); malasSoluciones.clear();
        erroresPosibles.put(1, malasSoluciones);

        malasSoluciones.add(combi1);malasSoluciones.add(sumaTodo); malasSoluciones.add(combi3); malasSoluciones.clear();
        erroresPosibles.put(2, malasSoluciones);

        malasSoluciones.add(combi1);malasSoluciones.add(combi3); malasSoluciones.add(combi2); malasSoluciones.clear();
        erroresPosibles.put(3, malasSoluciones); //restaTodo

        malasSoluciones.add(combi1);malasSoluciones.add(sumaTodo); malasSoluciones.add(combi2); malasSoluciones.clear();
        erroresPosibles.put(4, malasSoluciones);//combi3

        malasSoluciones.add(combi2);malasSoluciones.add(combi5); malasSoluciones.add(multiTodo); malasSoluciones.clear();
        erroresPosibles.put(5, malasSoluciones);//combi4

        malasSoluciones.add(combi4);malasSoluciones.add(combi5); malasSoluciones.add(combi2); malasSoluciones.clear();
        erroresPosibles.put(6, malasSoluciones);//combi5

        malasSoluciones.add(combi7);malasSoluciones.add(combi5); malasSoluciones.add(combi8); malasSoluciones.clear();
        erroresPosibles.put(7, malasSoluciones);//combi6

        malasSoluciones.add(combi6);malasSoluciones.add(combi5); malasSoluciones.add(restaTodo); malasSoluciones.clear();
        erroresPosibles.put(8, malasSoluciones);//combi7

        malasSoluciones.add(combi5);malasSoluciones.add(combi4); malasSoluciones.add(combi8); malasSoluciones.clear();
        erroresPosibles.put(9, malasSoluciones);//multiTodo

        malasSoluciones.add(combi4);malasSoluciones.add(combi2); malasSoluciones.add(combi5); malasSoluciones.clear();
        erroresPosibles.put(10, malasSoluciones);//combi8

        malasSoluciones.add(combi4);malasSoluciones.add(multiTodo); malasSoluciones.add(combi8); malasSoluciones.clear();
        erroresPosibles.put(11, malasSoluciones);//combi9



        resultados[0]=sumaTodo;
        resultados[1]=combi1;
        resultados[2]= combi2;
        resultados[3]=restaTodo;
        resultados[4]=combi3;
        resultados[5]=combi4;
        resultados[6]=combi5;
        resultados[7]=combi6;
        resultados[8]=combi7;
        resultados[9]= multiTodo;
        resultados[10]= combi8;
        resultados[11]=combi9;

        operaciones[0]=sumaTodoT;
        operaciones[1]=combi1T;
        operaciones[2]=combi2T;
        operaciones[3]=restaTodoT;
        operaciones[4]= combi3T;
        operaciones[5]=combi4T;
        operaciones[6]=combi5T;
        operaciones[7]=combi6T;
        operaciones[8]=combi7T;
        operaciones[9]=multiTodoT;
        operaciones[10]=combi8T;
        operaciones[11]=combi9T;
    }


}
