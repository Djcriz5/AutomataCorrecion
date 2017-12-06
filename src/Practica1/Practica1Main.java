package Practica1;

import Model.AutomataStructure.Automata;
import Model.AutomataStructure.Estado;
import Model.AutomataStructure.Transicion;

/**
 * Created by criscastro on 06/12/17.
 */

/** verifica una cadena apartir de un automata finito no determinista*/
public class Practica1Main {
    public static void main(String[] args) {
        Automata a =new Automata();
        Estado q1=new Estado("1");
        Estado q2=new Estado("2");
        Estado q3=new Estado("3",true);
        Estado q4=new Estado("4");
        Estado q5=new Estado("5");
        Estado q6=new Estado("6",true);

        q1.addTransicion(new Transicion(q1,q2,""));
        q1.addTransicion(new Transicion(q1,q4,""));

        q2.addTransicion(new Transicion(q2,q3,"1"));
        q3.addTransicion(new Transicion(q3,q2,"1"));

        q4.addTransicion(new Transicion(q4,q5,"0"));
        q5.addTransicion(new Transicion(q5,q6,"0"));
        q4.addTransicion(new Transicion(q4,q6,""));
        q6.addTransicion(new Transicion(q6,q4,""));//loops inf



        a.addEstado(q1);
        a.addEstado(q2);
        a.addEstado(q3);
        a.addEstado(q4);
        a.addEstado(q5);
        a.addEstado(q6);
        a.setEstadoInicial(q1);
        a.evaluar("11111","ejecucion 1");
    }
}