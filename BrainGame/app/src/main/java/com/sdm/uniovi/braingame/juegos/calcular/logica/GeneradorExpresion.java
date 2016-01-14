package com.sdm.uniovi.braingame.juegos.calcular.logica;


import java.util.Random;

public class GeneradorExpresion {

    private static Random random = new Random();

    public static final int NIVEL_FACIL = 1;
    public static final int NIVEL_BASICO = 2;
    public static final int NIVEL_AVANZADO = 3;

    private Expresion principal;


    private int nivel;


    public GeneradorExpresion(int nivel) {
        this.nivel=nivel;

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



    private  Expresion operando(int maximo) {

        return new Constante(random.nextInt(maximo - 1) + 1);

    }

    private  Operacion operacion(int desde, int hasta){
        return Operacion.values()[random.nextInt(hasta - desde + 1) + desde];
    }

    private void generarNivel1() {


        Expresion raiz;

        Expresion hijo = new ExpresionBinaria(operando(10), operando(10), operacion(0, 2));


        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijo, operando(10), operacion(0, 2));

        }
        else {
            raiz = new ExpresionBinaria(operando(10), hijo, operacion(0, 2));

        }


        principal = raiz;
    }


    private void generarNivel2() {

        Expresion hijo = new ExpresionBinaria(operando(10), operando(10), operacion(2, 3));

        Expresion raiz;
        if (random.nextBoolean()) {
            raiz = new ExpresionBinaria(hijo, operando(10), operacion(0, 3));
        }
        else {
            raiz = new ExpresionBinaria(operando(10), hijo, operacion(0, 3));
        }

        principal = raiz;
    }

    private void generarNivel3() {
        Expresion hijo1 = new ExpresionBinaria(operando(10), operando(10), operacion(2, 3));
        Expresion hijo2 = new ExpresionBinaria(operando(10), operando(10), operacion(2, 3));

       principal = new ExpresionBinaria(hijo1, hijo2, operacion(0, 3));

    }


}
