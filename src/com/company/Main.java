package com.company;


import ExpresionRegularToAFN.ConstructorDeAutomatas;
import ExpresionRegularToAFN.ShuntingYard;
import Model.AutomataStructure.Automata;
import Model.AutomataStructure.Estado;
import Model.AutomataStructure.Transicion;

public class Main {

    public static void main(String[] args) {
       /* String l="a";
        String ca="aba7";
       // System.out.println(ca.substring(0,1).equals(l));

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
        a.evaluar("11","ejecucion 2");
        a.evaluar("00","ejecucion 3");

       // System.out.println("el numero de estados es "+a.getNumeroEstados());
       // a.deleteEstado(q1);
        //System.out.println("el numero de estados es "+a.getNumeroEstados());

        System.out.println(a);

        //System.out.println("ejecucion 2");

        //a.evaluar("1111");

        */
       Automata b=new Automata();
        Estado q3=new Estado("3");
        Estado q4=new Estado("4");
        Estado q5=new Estado("5",true);

        q3.addTransicion(q4,"");
        q4.addTransicion(q5,"1");
        q5.addTransicion(q4,"1");
        b.addEstadoInicial(q3);
        b.addEstado(q4);
        b.addEstado(q5);

        Automata c=new Automata();
        Estado q6=new Estado("6");
        Estado q7=new Estado("7");
        Estado q8=new Estado("8",true);

        q6.addTransicion(q7,"");
        q7.addTransicion(q8,"1");
        q8.addTransicion(q7,"1");
        c.addEstadoInicial(q6);
        c.addEstado(q7);
        c.addEstado(q8);
        //System.out.println(b);
        //System.out.println("_-------------------_");

       // System.out.println(c);
        ConstructorDeAutomatas cA=new ConstructorDeAutomatas();

       // System.out.println("_-------------------_");
        //System.out.println(cA.aPlus(b));

       // System.out.println(cA.aConcatenacionFusionDeEstados(cA.contruccionbasica("a"),cA.contruccionbasica("b")));

     //System.out.println(cA.expresionRegularToAutomata("((b.c.d)*|f+.a).f"));
     System.out.println(cA.expresionRegularToAutomata("((a.a+)|(c+)|(b.c*))+"));
 //     System.out.println(cA.expresionRegularToAutomata("a.b"));

     //cA.expresionRegularToAutomata("a.b+|c*");
    }
}
