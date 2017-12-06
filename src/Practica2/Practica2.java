package Practica2;

import ExpresionRegularToAFN.ConstructorDeAutomatas;

/**
 * Created by criscastro on 06/12/17.
 */

/*
        Genera un automata finito no determinista
* */
public class Practica2 {
    public static void main(String[] args) {
        ConstructorDeAutomatas cA= new ConstructorDeAutomatas();
        System.out.println(cA.expresionRegularToAutomata("((b.c.d)*|f+.a).f"));
    }
}
