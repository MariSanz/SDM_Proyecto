package com.sdm.uniovi.braingame.juegos.calcular.logica;


public class ExpresionBinaria implements Expresion {


    private Expresion op1;
    private Expresion op2;
    private Operacion operacion;



    public ExpresionBinaria(Expresion op1, Expresion op2, Operacion operacion) {
        super();
        this.op1 = op1;
        this.op2 = op2;
        this.operacion = operacion;

    }

    public Operacion getOperacion() {
        return operacion;
    }

    public Expresion getOp1() {
        return op1;
    }

    public Expresion getOp2() {
        return op2;
    }

    @Override
    public int valor() {

            switch (operacion) {
                case MULTIPLICACION:
                    return op1.valor() * op2.valor();
                case DIVISION:
                    return op1.valor() / op2.valor();
                case SUMA:
                   return op1.valor() + op2.valor();
                case RESTA:
                    return op1.valor() - op2.valor();


            }

       throw new RuntimeException();
    }

    @Override
    public String mostrar() {

        return op1.mostrar() + " " + operacion.toString() + " " + op2.mostrar() + " ";
    }

}
