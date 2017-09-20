package AFDtoAFDbySubsets;


import Model.AutomataStructure.Automata;
import Model.AutomataStructure.Estado;
import Model.AutomataStructure.Transicion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

/**
 * Created by criscastro on 12/09/17.
 */
public class ContructorAFNtoAFD {
    /*
    *
    * */

    public  ContructorAFNtoAFD(){

    }


    public  Automata convertAFNtoAFD(Automata srcAutomata){
        /*
        * Donde
        * */
        int contadorEstadosAutoR=0;
        LinkedList<Subconjunto> estadosPorProcesarStack=new LinkedList<>();
        LinkedList<Subconjunto> estadosProcesados=new LinkedList<>();

        Automata automataResultante=new Automata();
        /**
         * ir haciendo iteraciones  recordar siempre
         * el estado inicial del automata de entrada sera el estado inicial del resultante
         *
         * al ir procesandolos subconjuntos suponemos la sig situacion
         *
         * (a),a :{4*,8*,7,9} = (b) add la transicion  de el estado a al (b)  si b existe se añade si no se crea y se añade
         *
         */
        Subconjunto inicial=cerraduraEpsilon(Integer.toString(++contadorEstadosAutoR),srcAutomata.getEstadoInicial().getClave(),srcAutomata);

        automataResultante.addEstadoInicial(inicial.getEstado());
        inicial.getCerradura().add(srcAutomata.getEstadoInicial().getClave());
        estadosPorProcesarStack.addFirst(inicial);
        Subconjunto procesandose;
        Subconjunto iteracion;
        /**
         * Ya esta arreglkado el metodo cerradura y el metodo mover solo falta arreglar lo que esta dentor del while
         *
         */
        while(!estadosPorProcesarStack.isEmpty()){
            procesandose=estadosPorProcesarStack.removeLast();
            estadosProcesados.add(procesandose);
             for (String simbolo:srcAutomata.getAlfabetoSinEpsilon()) {
                 iteracion=cerraduraEpsilon(mover(Integer.toString(++contadorEstadosAutoR),procesandose,simbolo,srcAutomata),srcAutomata);
                 if(!estadosPorProcesarStack.contains(iteracion)&&!estadosProcesados.contains(iteracion)&&!iteracion.getKernel().isEmpty()){
                     estadosPorProcesarStack.addFirst(iteracion);
                     automataResultante.addEstado(iteracion.getEstado());
                     automataResultante.addTransicion(procesandose.getEstado(),iteracion.getEstado(),simbolo);
                 }else if(!iteracion.getKernel().isEmpty()){
                        if(estadosPorProcesarStack.contains(iteracion)){
                            automataResultante.addTransicion(procesandose.getEstado(),estadosPorProcesarStack.get(estadosPorProcesarStack.indexOf(iteracion)).getEstado(),simbolo);
                        }else if(estadosProcesados.contains(iteracion)){
                            if(iteracion.equals(procesandose)){

                                automataResultante.addTransicion(procesandose.getEstado(), procesandose.getEstado(), simbolo);
                            }else {

                                automataResultante.addTransicion(procesandose.getEstado(),estadosProcesados.get(estadosProcesados.indexOf(iteracion)).getEstado(), simbolo);
                            }
                        }
                        contadorEstadosAutoR--;
                 }
             }
         }


        return automataResultante;
    }


    public Subconjunto mover(String claveValor,Subconjunto subconjunto,String valor,Automata srcAutomata){
        Subconjunto resultante= new Subconjunto(claveValor);
        mover(subconjunto,resultante,subconjunto.getKernel(),srcAutomata,valor);
        mover(subconjunto,resultante,subconjunto.getCerradura(),srcAutomata,valor);
        return  resultante;
    }

    private void  mover(Subconjunto srcSubcojunto,Subconjunto dstSubconjunto,Set<String> setAProcesar,Automata srcAutomata,String valor){
        for (String estado:setAProcesar) {
            if(estado.equals(srcAutomata.getEstadoFinal(srcAutomata).getClave())){
                srcSubcojunto.getEstado().setEsFinal(true);
            }
            for (Transicion transicion:srcAutomata.getEstado(estado).getTransiciones()) {
                if(transicion.getValorAceptado().equals(valor)){
                    dstSubconjunto.getKernel().add(transicion.getEstadoDeDestino().getClave());
                }
            }
        }
    }


    ///////////////////////////Trabajan en conjunto/////////////////////////////////////
    public Subconjunto cerraduraEpsilon(Subconjunto subconjunto,Automata srcAutomata){
        for(String estado:subconjunto.getKernel()){
            if(estado.equals(srcAutomata.getEstadoFinal(srcAutomata).getClave())){
                subconjunto.getEstado().setEsFinal(true);
            }
            cerraduraEpsilon(estado,srcAutomata,subconjunto.getCerradura());
        }
        return subconjunto;
    }


    public void cerraduraEpsilon(String estado, Automata srcAutomata, Set<String> cerraduraDestino){
        for(Transicion transicion:srcAutomata.getEstado(estado).getTransiciones()){
            if(transicion.getValorAceptado().equals("")){
                cerraduraDestino.add(transicion.getEstadoDeDestino().getClave());
                cerraduraEpsilon(transicion.getEstadoDeDestino().getClave(),srcAutomata,cerraduraDestino);
            }
        }
    }


    /////////////////////////////////////////////////////////////////
    public Subconjunto cerraduraEpsilon(String claveEstadonuevo,String estado,Automata srcAutomata){
        Subconjunto resultante= new Subconjunto(claveEstadonuevo);
            this.cerraduraEpsilon(estado,srcAutomata,resultante.getCerradura());
        return resultante;
    }
}
