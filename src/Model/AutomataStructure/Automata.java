package Model.AutomataStructure;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by criscastro on 22/08/17.
 */
public class Automata {
    private LinkedList<Estado> estadosMaquina;
    private Estado estadoInicial;
    private int numeroEstados;
    private Set<String> alfabeto;
    public Automata(){
       this.estadosMaquina=new LinkedList<>();
       this.numeroEstados=0;
       this.alfabeto= new HashSet<>();
    }
    public void addEstado(Estado estado){
        this.estadosMaquina.add(estado);
    }

    public void setEstadoInicial(Estado estadoInicial){
        this.estadoInicial=estadoInicial;
    }

    public void  evaluar(String cadenaAEvaluar,String nombreEjecucion){
        System.out.println(">>> nueva operacion    ******* >>>>>>>>" +nombreEjecucion);
       this.estadoInicial.procesar(cadenaAEvaluar);
       //PROBLEMA DE HILOS DEBE ESPERAR A QUE LOS DEMAS TERMINEN
       if(checarEstadosFinales(nombreEjecucion)){
           System.out.println("**********la cadena "+cadenaAEvaluar+"pertenece al lenguaje ********* "+nombreEjecucion);
       }else{
           System.out.println("---------la cadena "+cadenaAEvaluar+" no pertenece al lenguaje -----------"+ nombreEjecucion);

       }
    }

    public boolean checarEstadosFinales(String nombreEjecucion){
        boolean resultado=false;
        for (Estado estado:estadosMaquina) {
           System.out.println("EL estado es "+estado.getClave()+" acepto alguna cadena: "+estado.aceptoLaCadena()+" "+nombreEjecucion);
            if(estado.aceptoLaCadena()){
                resultado=true;
            }
            estado.setAceptoLaCadena(false);
            estado.setVisitado(false);

        }
        return  resultado;
    }
    public void addEstadoInicial(Estado estado){
       this.addEstado(estado);
       this.setEstadoInicial(estado);
    }

    public void addEstadoInicial(){
            Estado estado =new Estado(String.valueOf(estadosMaquina.size()+1));
            this.addEstado(estado);
            this.setEstadoInicial(estado);
            estado=null;
            System.gc();
    }

    public void addEstado(boolean esFinal){
        if(this.estadosMaquina!=null) {
            this.addEstado(new Estado(String.valueOf(estadosMaquina.size()+1),esFinal));
        }
    }


    public void addEstado(String name,Boolean esFinal){
        if(this.estadosMaquina!=null) {
            this.addEstado(new Estado(name,esFinal));
        }
    }
    public void addEstado(String name){
        if(this.estadosMaquina!=null) {
            this.addEstado(new Estado(name));
        }
    }

    public void addEstado(){
        if(this.estadosMaquina!=null) {
            this.addEstado(new Estado(String.valueOf(estadosMaquina.size()+1)));
        }
    }

    public void addTransicion(Estado estadoOrigen,Estado estadoDestino,String valorQueAcepta){
            estadoOrigen.addTransicion(estadoDestino,valorQueAcepta);
    }

    public LinkedList<Estado> getEstadosMaquina() {
        return estadosMaquina;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public int getNumeroEstados() {
        return estadosMaquina.size();
    }

    public void deleteEstado(Estado estado){
        this.estadosMaquina.remove(estado);
    }

    public String toString(){
        StringBuilder builder =new StringBuilder();
        //para fines educativos eliminar estas 2 lineas
        builder.append("\nEstado Inicial: "+this.getEstadoInicial().getClave());
      //  builder.append("\nEstado Final: "+this.getEstadoFinal(this).getClave());
        builder.append("\nNumero de estados: "+getNumeroEstados());
        for (Estado e:this.estadosMaquina) {
            builder.append(e.toString());
        }
        return builder.toString();
    }
/// solo aplica par automatas de thompson
    public Estado getEstadoFinal(Automata automata){
        Estado resultado=null;
        for (Estado estado:automata.getEstadosMaquina()) {
            if(estado.isFinal()){
                resultado = estado;
            }
        }
        return resultado;
    }

    private void calcularAlfabeto(){
        for (Estado estado:estadosMaquina) {
            for (Transicion transicion:estado.getTransiciones()) {
                    this.alfabeto.add(transicion.getValorAceptado());
            }
        }
    }
    private void calcularAlfabetoSinEpsilon(){
        for (Estado estado:estadosMaquina) {
            for (Transicion transicion:estado.getTransiciones()) {
                if(!transicion.getValorAceptado().equals(""))
                this.alfabeto.add(transicion.getValorAceptado());
            }
        }
    }

    public Set<String> getAlfabeto(){
        if(this.alfabeto.isEmpty()){
            calcularAlfabeto();
        }
        return this.alfabeto;
    }
    public Set<String> getAlfabetoSinEpsilon(){
        if(this.alfabeto.isEmpty()){
            calcularAlfabetoSinEpsilon();
        }
        return this.alfabeto;
    }

    public  Estado getEstado(String clave){
            return this.estadosMaquina.get(estadosMaquina.indexOf(new Estado(clave)));
    }
}
