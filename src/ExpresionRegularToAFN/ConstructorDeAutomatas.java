package ExpresionRegularToAFN;

import Model.AutomataStructure.Automata;
import Model.AutomataStructure.Estado;
import Model.AutomataStructure.Transicion;

import java.util.Stack;

/**
 * Created by criscastro on 25/08/17.
 * Utiliza contrucciones de thompson para generar automatas
 *
 *
 */
public class ConstructorDeAutomatas {
    private int contador;

    public ConstructorDeAutomatas (){
          this.contador=0;
    }

    public Automata expresionRegularToAutomata(String expresionREgular){
        Automata resultante=null;
        Automata auxA=null;
        Automata auxB=null;

        Stack<Automata> pilaAuxiliar=new Stack();
        for (String token:ShuntingYard.postfix(expresionREgular).split("")) {
            // if(Character.isLetterOrDigit(token.charAt(0)))
            //System.out.println("realizando operacion: "+token);
            switch (token){
                case "*":
                    pilaAuxiliar.push(this.aCerraduraKleene(pilaAuxiliar.pop()));
                    break;
                case "+":
                    pilaAuxiliar.push(this.aPlus(pilaAuxiliar.pop()));
                    break;
                case".":
                    auxB=pilaAuxiliar.pop();
                    auxA=pilaAuxiliar.pop();
                    //pilaAuxiliar.push(this.aConcatenacion(auxA,auxB));  //variante de construcciones de thompson
                    pilaAuxiliar.push(this.aConcatenacionFusionDeEstados(auxA,auxB));
                    break;
                case"|":
                    auxB=pilaAuxiliar.pop();
                    auxA=pilaAuxiliar.pop();
                    pilaAuxiliar.push(this.aUnion(auxA,auxB));
                    break;
                default:
                    //System.out.println("poniendo : "+token);
                    pilaAuxiliar.push(this.contruccionbasica(token));
                    break;
            }
        }

        resultante=pilaAuxiliar.pop();
         auxA=null;
         auxB=null;
         System.gc();
        return resultante;
    }
    //TODO implementar epsilon elijir un caracter que lo represente , despues en cada construccion poner que si el simbolo es el que representa a epsilon , al crear las transiciones que sean con ""

    /**
     * Entrada:
     *  String caracter: recibe el caracter que se pondra en la tupla en la contruccion basica de thompson
     * Int contador :recibe el contador de donde esta construllendo el automata para no repetir nombres de estados
     *
     * Salida: una contruccion basica de thompson de 1 caracter
     ***/
    public Automata contruccionbasica(String caracter){
        Automata automataResultante=new Automata();

            contador++;//incrementando el numero de estados para no repetir nombres
            Estado eInicial=new Estado(Integer.toString(contador));
            automataResultante.addEstado(eInicial);
            contador++;//incrementando el numero de estados para no repetir nombres
            Estado eFinal=new Estado(Integer.toString(contador),true);
            automataResultante.addEstado(eFinal);
            //
            automataResultante.setEstadoInicial(eInicial);
            automataResultante.addTransicion(eInicial,eFinal,caracter);

        return automataResultante;
    }

    /**
     * Entrada:
     *  AutomataEntradaIzquierda: recibe una construccion de thompson que se concatenara con otra (parte Izquierda)
     *  AutomataEntradaDerecha: recibe una construccion de thompson que se concatenara con otra (parte derecha)
     *
     *  Salida: una contrucccion de thompson de la concatenacion de 2 automatas
     ***/
    public Automata aConcatenacion(Automata automataDeEntradaIzq,Automata automataDeEntradaDer){
            //aqui tendria que quitarle la calidad de estado inicial al estado inicial del automata Derecho
            // pero como esta definido en el automata y no en el  los estados no es necesario ya que se copian
            // los estados solamente
        getEstadoFinal(automataDeEntradaIzq).addTransicion(automataDeEntradaDer.getEstadoInicial(),"");
        getEstadoFinal(automataDeEntradaIzq).setEsFinal(false);
        copiarEstados(automataDeEntradaDer,automataDeEntradaIzq);
        return automataDeEntradaIzq;
    }


    /**
     * Entrada:
     *  AutomataEntradaIzquierda: recibe una construccion de thompson que se concatenara con otra (parte Izquierda)
     *  AutomataEntradaDerecha: recibe una construccion de thompson que se concatenara con otra (parte derecha)
     *
     *  Salida: una contrucccion de thompson de la concatenacion de 2 automatas
     ***/
    public Automata aConcatenacionFusionDeEstados(Automata automataDeEntradaIzq,Automata automataDeEntradaDer){
        copiarTuplasSinEliminar(automataDeEntradaDer.getEstadoInicial(),getEstadoFinal(automataDeEntradaIzq));
        automataDeEntradaDer.deleteEstado(automataDeEntradaDer.getEstadoInicial());
        getEstadoFinal(automataDeEntradaIzq).setEsFinal(false);
        copiarEstados(automataDeEntradaDer,automataDeEntradaIzq);
        return automataDeEntradaIzq;
    }
    /**
     * Entrada:
     *  AutomataEntradaIzquierda: recibe una construccion de thompson que se unira con otra (parte Izquierda)
     *  AutomataEntradaDerecha: recibe una construccion de thompson que se unira con otra (parte derecha)
     *
     *  Salida: una contrucccion de thompson de la union de 2 automatas
     ***/
    public Automata aUnion(Automata automataDeEntradaIzq,Automata automataDeEntradaDer){
        Automata automataResultante=new Automata();
       //incrementando el numero de estados para no repetir nombres
        Estado nuevoInicial=new Estado(Integer.toString(++contador));
        nuevoInicial.addTransicion(automataDeEntradaIzq.getEstadoInicial(),"");
        nuevoInicial.addTransicion(automataDeEntradaDer.getEstadoInicial(),"");
      //incrementando el numero de estados para no repetir nombres
        Estado nuevoFinal =new Estado(Integer.toString(++contador),true);
        this.getEstadoFinal(automataDeEntradaIzq).addTransicion(nuevoFinal,"");
        this.getEstadoFinal(automataDeEntradaDer).addTransicion(nuevoFinal,"");
        this.getEstadoFinal(automataDeEntradaIzq).setEsFinal(false);
        this.getEstadoFinal(automataDeEntradaDer).setEsFinal(false);
        automataResultante.addEstadoInicial(nuevoInicial);
        automataResultante.addEstado(nuevoFinal);
        copiarEstados(automataDeEntradaIzq,automataResultante);
        copiarEstados(automataDeEntradaDer,automataResultante);
        return automataResultante;
    }
    /**
     * Entrada:
     *  automataDeEntrada: recibe una construccion de thompson a la cual se le aplicara cerradura positiva
     *
     *  Salida: una contruccion de thompson de la cerradura positiva de un automata
     ***/
    public Automata aPlus(Automata automataDeEntrada){
        Automata automataResultante=new Automata();
        contador++;//incrementando el numero de estados para no repetir nombres
        Estado nuevoInicial=new Estado(Integer.toString(contador));
        nuevoInicial.addTransicion(automataDeEntrada.getEstadoInicial(),"");
        contador++;
        Estado nuevoFinal=new Estado(Integer.toString(contador),true);
        this.getEstadoFinal(automataDeEntrada).addTransicion(nuevoFinal,"");
        this.getEstadoFinal(automataDeEntrada).addTransicion(automataDeEntrada.getEstadoInicial(),"");
        this.getEstadoFinal(automataDeEntrada).setEsFinal(false);
        automataResultante.addEstadoInicial(nuevoInicial);
        automataResultante.addEstado(nuevoFinal);
        copiarEstados(automataDeEntrada,automataResultante);
        return automataResultante;
    }
    /**
     * Entrada:
     *  automataDeEntrada: recibe una construccion de thompson a la cual se le aplicara cerradura kleene
     *
     *  Salida: una contruccion de thompson de la cerradura de kleene de un automata
     ***/
    public Automata aCerraduraKleene(Automata automataDeEntrada){
        Automata automataResultante=new Automata();
        contador++;//incrementando el numero de estados para no repetir nombres
        Estado nuevoInicial=new Estado(Integer.toString(contador));
        nuevoInicial.addTransicion(automataDeEntrada.getEstadoInicial(),"");
        contador++;
        Estado nuevoFinal=new Estado(Integer.toString(contador),true);
        this.getEstadoFinal(automataDeEntrada).addTransicion(nuevoFinal,"");
        this.getEstadoFinal(automataDeEntrada).addTransicion(automataDeEntrada.getEstadoInicial(),"");
        this.getEstadoFinal(automataDeEntrada).setEsFinal(false);
        nuevoInicial.addTransicion(nuevoFinal,"");
        automataResultante.addEstadoInicial(nuevoInicial);
        automataResultante.addEstado(nuevoFinal);
        copiarEstados(automataDeEntrada,automataResultante);
        return automataResultante;
    }

    /**
     * Copia los estados de el automata de origen al automata de destino
     * **/
    private void copiarEstados(Automata origen,Automata destino){
        for (Estado estadoDeAutoDeOrigen:origen.getEstadosMaquina()) {
            //contador ++; en teoria ya no tendrian que renombrarse ya que tienen nombres dieferentes si falla  descomentar
            // y agregar parametro a la isntruccion de abajo
            destino.addEstado(estadoDeAutoDeOrigen);
        }
    }

    /**TODO
     * Copia las trancisiones de un estado estado "o" a un estado "d" , aplicando el origen de la tupla al estado "d" (conservando la direccion de destino de la tupla)
     * **/
    private void copiarTuplas(Estado o,Estado d){
        for (Transicion transicionDelEstadoO:o.getTransiciones()) {
            transicionDelEstadoO.setEstadoDeOrigen(d);
            d.addTransicion(transicionDelEstadoO);
        }
    }
    /**
     * Copia las trancisiones de un estado estado "o" a un estado "d" , aplicando el origen de la tupla al estado "d" (conservando la direccion de destino de la tupla)
     *  ### sin eliminar las trancisiones del estado "o" ###
     * **/
    private void copiarTuplasSinEliminar(Estado o,Estado d){
        Transicion tAuxiliar=null;

        for (Transicion transicionDelEstadoO:o.getTransiciones()) {
            tAuxiliar=transicionDelEstadoO;
            tAuxiliar.setEstadoDeOrigen(d);
            d.addTransicion(tAuxiliar);
        }
        System.gc();
    }


    /**
     * Obtiene el estado Final de un automata en la contruccion de thompson
     * (no valido para automatas con mas de 1 estado final )
     * **/
    private Estado getEstadoFinal(Automata automata){
        Estado resultado=null;
        for (Estado estado:automata.getEstadosMaquina()) {
            if(estado.isFinal()){
                resultado = estado;
            }
        }
        return resultado;
    }

}
