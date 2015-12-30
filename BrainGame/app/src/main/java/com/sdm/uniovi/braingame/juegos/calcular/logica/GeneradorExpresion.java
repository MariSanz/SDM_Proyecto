package com.sdm.uniovi.braingame.juegos.calcular.logica;

import java.util.Random;

public class GeneradorExpresion {

    private static Random random = new Random();

    private Expresion expresion;

    private static Expresion operando(int maximo) {
        return new Constante(random.nextInt(maximo));
    }

    private static Operacion operacion(int hasta){
        return Operacion.values()[hasta];
    }

    public void generarNivel1() {

        Expresion raiz;

        Expresion hijo = new ExpresionBinaria(operando(10), operando(10), operacion(2));

        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijo, operando(10), operacion(2));
        }
        else {
            raiz = new ExpresionBinaria(operando(10), hijo, operacion(2));
        }


        expresion = raiz;
    }

    public void generarNivel2() {

        Expresion raiz;

        Expresion hijoIzq = new ExpresionBinaria(operando(100), operando(100), operacion(3));
        Expresion hijoDer = new ExpresionBinaria(operando(100), operando(100), operacion(3));

        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijoIzq, operando(100), operacion(3));
        }
        else {
            raiz = new ExpresionBinaria(operando(100), hijoDer, operacion(3));
        }


        expresion= raiz;
    }

    public void generarNivel3() {

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


        expresion= raiz;
    }

    public Expresion getExpresion() {
        return expresion;
    }
}
