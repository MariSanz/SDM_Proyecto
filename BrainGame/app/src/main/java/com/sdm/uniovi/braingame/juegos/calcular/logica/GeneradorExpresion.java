package com.sdm.uniovi.braingame.juegos.calcular.logica;

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
    private int numOpciones;

    public GeneradorExpresion(int nivel, int numOpciones) {
        this.nivel=nivel;
        this.numOpciones=numOpciones;
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

        Expresion hijoIzq = new ExpresionBinaria(operando(10), operando(10), operacion(3));
        Expresion hijoDer = new ExpresionBinaria(operando(10), operando(10), operacion(3));

        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijoIzq, operando(10), operacion(3));
        }
        else {
            raiz = new ExpresionBinaria(operando(10), hijoDer, operacion(3));
        }




        principal = raiz;
        generarIncorrectas(10, 3);
    }

    private void generarNivel3() {

        Expresion raiz;

        Expresion hijoIzq = new ExpresionBinaria(operando(100), operando(100), operacion(3));
        Expresion hijoCentro = new ExpresionBinaria(operando(100), operando(100), operacion(3));
        Expresion hijoDer = new ExpresionBinaria(operando(100), operando(100), operacion(3));

        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijoIzq, hijoCentro, operacion(3));
        }
        else {
            raiz = new ExpresionBinaria(hijoCentro, hijoDer, operacion(3));
        }






        principal = raiz;
        generarIncorrectas(100, 3);
    }


}
