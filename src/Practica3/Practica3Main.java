package Practica3;

import AFDtoAFDbySubsets.ContructorAFNtoAFD;
import ExpresionRegularToAFN.ConstructorDeAutomatas;
import Model.AutomataStructure.Automata;

/**
 * Created by criscastro on 06/12/17.
 */
 //genera  un automata finito determinista equivalente a un automata finito no determinista de entrada
// usando el algoritmo por subconjuntos
public class Practica3Main {
    public static void main(String[] args){
        //subconjunto.
        ContructorAFNtoAFD construye = new ContructorAFNtoAFD();

        Automata a=new ConstructorDeAutomatas().expresionRegularToAutomata("(a.b|a*.c)+");
        System.out.println("<><><><><><><><>Automata Finito NO DETERMINISTA de Entrada <><><><><><><><><><><><>");

        System.out.println(a);
        System.out.println("<><><><><><><><>Automata Finito Determinista Generado<><><><><><><><><><><><>");

        System.out.println(construye.convertAFNtoAFD( a));
    }
}
