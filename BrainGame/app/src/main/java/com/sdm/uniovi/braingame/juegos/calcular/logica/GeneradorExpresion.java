package com.sdm.uniovi.braingame.juegos.calcular.logica;

import android.gesture.GestureOverlayView;

import java.util.Random;

public class GeneradorExpresion {

    private static Random random = new Random();

    public static final int NIVEL_FACIL = 1;
    public static final int NIVEL_BASICO = 2;
    public static final int NIVEL_AVANZADO = 3;



    private Expresion[] incorrectas;
    private Expresion constante;

    private Expresion principal;


    private int nivel;


    public GeneradorExpresion(int nivel, int numOpciones) {
        this.nivel=nivel;

        incorrectas= new Expresion[numOpciones];
    }

    public void generarExpresiones(){
        switch (nivel){
            case NIVEL_FACIL:
                generarNivel1();
                break;
            case NIVEL_BASICO:
                generarNivel2();
                break;
            case NIVEL_AVANZADO:
                generarNivel3();
                break;
        }
    }

    public Expresion getPrincipal() {
        return principal;
    }

    public Expresion getIncorreta(int i){

            return incorrectas[i];

    }

    private  Expresion operando(int maximo) {

        constante = new Constante(random.nextInt(maximo));
        return constante;
    }

    private  Operacion operacion(int hasta){
        return Operacion.values()[random.nextInt(hasta)];
    }

    private void generarNivel1() {

        Expresion raiz;

        Expresion hijo = new ExpresionBinaria(operando(10), operando(10), operacion(2));


        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijo, operando(10), operacion(2));
            incorrectas[0] = new ExpresionBinaria(operando(10), hijo, operacion(2));
        }
        else {
            raiz = new ExpresionBinaria(operando(10), hijo, operacion(2));
            incorrectas[0] = new ExpresionBinaria(hijo, operando(10), operacion(2));
        }





        principal = raiz;
        generarIncorrectas(10,2);
    }

    private void generarIncorrectas(int max, int hasta ) {
        incorrectas[1] = new ExpresionBinaria(operando(max), operando(max), operacion(hasta));
        incorrectas[2] = new ExpresionBinaria(operando(max), constante, operacion(hasta));
        incorrectas[3] = new ExpresionBinaria(operando(max), operando(max), operacion(hasta));
        for (int i=1; i<incorrectas.length; i++){
            if(incorrectas[i].valor()==0.0){
                incorrectas[i] = new ExpresionBinaria(operando(max), operando(max), operacion(hasta));
            }
            while(incorrectas[i].valor()==getPrincipal().valor()){
                incorrectas[i] = new ExpresionBinaria(operando(max), operando(max), operacion(hasta));
            }
        }


    }

    private void generarNivel2() {

        Expresion raiz;
        Expresion hijo;
        Operacion operacion = operacion(3);

        ExpresionBinaria op1 = new ExpresionBinaria(operando(10), operando(10), operacion(3));

        if(operacion.equals(Operacion.DIVISION) || operacion.equals(Operacion.MULTIPLICACION)){
            hijo = new ExpresionBinaria(op1.getOp2(), operando(10), operacion);
            raiz = new ExpresionBinaria(op1.getOp1(), hijo, op1.getOperacion());

            incorrectas[0] = new ExpresionBinaria(op1.getOp1(), hijo, operacion(2));
        }else{
            raiz = new ExpresionBinaria(op1, operando(10),operacion);
            incorrectas[0] = new ExpresionBinaria(operando(10), op1, operacion(2));
        }


        principal = raiz;
        generarIncorrectas(10,2);
    }

    private void generarNivel3() {

        Expresion raiz;

        Operacion operacion1 = operacion(3);
        Operacion operacion2 = operacion(3);


        Expresion hijo1;
        Expresion hijo2;

        ExpresionBinaria op1 = new ExpresionBinaria(operando(10), operando(10), operacion(3));

        if(operacion1.equals(Operacion.DIVISION) || operacion1.equals(Operacion.MULTIPLICACION)){
            hijo1 = new ExpresionBinaria(op1.getOp2(), operando(10), operacion1);
            hijo2 = new ExpresionBinaria(op1.getOp1(), hijo1, op1.getOperacion());
            raiz = new ExpresionBinaria(operando(10), hijo2, operacion2);
            incorrectas[0] = new ExpresionBinaria(hijo1, operando(10), operacion1);
        }else if(operacion2.equals(Operacion.DIVISION) || operacion2.equals(Operacion.MULTIPLICACION)){
            hijo1 = new ExpresionBinaria(op1.getOp2(), operando(10), operacion2);
            hijo2 = new ExpresionBinaria(op1.getOp1(), hijo1, op1.getOperacion());
            raiz = new ExpresionBinaria(operando(10), hijo2, operacion1);
            incorrectas[0] = new ExpresionBinaria(hijo1, operando(10), operacion2);
        }
        else {

            hijo1 = new ExpresionBinaria(operando(10), operando(10),operacion1);
            hijo2 = new ExpresionBinaria(operando(10), operando(10),operacion2);
            raiz = new ExpresionBinaria(hijo1, hijo2, operacion(2));
            incorrectas[0] = new ExpresionBinaria(hijo1, operando(10), operacion1);
        }


        principal = raiz;
        generarIncorrectas(10, 3);
    }


}
