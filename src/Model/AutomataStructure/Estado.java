package Model.AutomataStructure;

import AFDtoAFDbySubsets.Subconjunto;

import java.util.LinkedList;

/**
 * Created by criscastro on 22/08/17.
 */
public class Estado {
    private String clave;
    private boolean esFinal;
    private LinkedList<Transicion> transiciones;
    private boolean visitado;
    private boolean aceptaCadena;

    public Estado(String clave,boolean esFinal){
        this.clave=clave;
        this.visitado=false;
        this.esFinal=esFinal;
        this.aceptaCadena=true;
        transiciones = new LinkedList<>();
    }
    public Estado(String clave){
        this.clave=clave;
        this.visitado=false;
        this.esFinal=false;
        this.aceptaCadena=false;
        transiciones = new LinkedList<>();
    }

    public void procesar(String cadena){
        this.aceptaCadena=false;
        System.out.println("Estamos en : "+clave+" la cadena es "+cadena);
        if(this.esFinal && cadena.length()==0){
            this.aceptaCadena=true;
            System.out.println("\n******cadena aceptada Estamos en : "+clave+"*************\n");


            //    System.out.println("Hilos activos: "+Thread.activeCount());

             /* qif(this.tieneTransicionesEpsilon()){
                //TODO  do something (bucle infinito)
            }else{

            }*/
            // hilosDeBifurcacion.list();
            // hilosDeBifurcacion.stop();
            // System.exit(0);

        }else{
            if(cadena.length()>0){
                Transicion buscada=buscarTransicion(cadena.substring(0,1));
                if(buscada!=null){

                    buscada.enviarCadenaAEvaluar(cadena);
                }else{
                    if(!visitado) {
                        LinkedList<Transicion> transicionesEpsilon;
                        if ((transicionesEpsilon = getTransicionesEpsilon()) != null) {

                            for (Transicion transicion : transicionesEpsilon) {
                                new Thread( () -> {
                                    System.out.println("////////realizando bifurcacion");
                                    transicion.enviarCadenaAEvaluar(cadena);

                                }).start();
                            }
                        } else {
                            System.out.println("error 2  no tiene transiciones epsilon y no se encontro una transicion que tenga al caracter deseado estamos en"+clave);
                            buscada = null;
                            transicionesEpsilon = null;
                            System.gc();
                        }
                    }
                }
            }else{

                System.out.println("error 1 la cadena no tiene caracteres estamos en"+clave);
            }

        }
        this.visitado=true;
    }

    public void addTransicion(Transicion t){
        this.transiciones.add(t);
    }

    public void addTransicion(Estado estadoDestino,String valorQueAcepta){
        this.transiciones.add(new Transicion(this,estadoDestino,valorQueAcepta));
    }

    private boolean tieneTransicionesEpsilon() {
        boolean resultado=false;
        for (Transicion transicion:this.transiciones) {
            if(transicion.isEpsilon()){
                resultado =true;
            }
        }
        return  resultado;
    }

    public Transicion buscarTransicion(String acepta){
        Transicion buscada=null;
        for (Transicion transicion:this.transiciones) {
           // System.out.println("valor trancision:"+transicion.getValorAceptado()+" valor que se busca :"+(acepta));
            if(transicion.getValorAceptado().equals(acepta)){
                buscada=transicion;
            }
        }


        return buscada;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }

    public LinkedList<Transicion> getTransiciones() {
        return transiciones;
    }

    public LinkedList<Transicion> getTransicionesEpsilon(){
        LinkedList<Transicion> transicionesEpsilon=new LinkedList<>();
        for ( Transicion transicion: this.transiciones) {
            if(transicion.isEpsilon())
                transicionesEpsilon.add(transicion);
        }
        if(transicionesEpsilon.isEmpty()){
            transicionesEpsilon=null;
            System.gc();
        }
        return  transicionesEpsilon;
    }

    public void setTransiciones(LinkedList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    public boolean aceptoLaCadena() {
        return aceptaCadena;
    }
    public void setAceptoLaCadena(boolean valor) {
        this.aceptaCadena=valor;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    public String toString(){
        StringBuilder builder =new StringBuilder();
        builder.append("\nEstado "+this.getClave()+" es final: "+this.isFinal()+"\ntransiciones\n");
        for (Transicion t:this.transiciones) {
            builder.append("\n"+t.toString()+"\n");
        }
        return builder.toString();
    }
        //solucion temporal solo checa si  el nombre es el mismo pero relamente debria ver si las transiciones son las mismas tambien
    @Override
    public boolean equals(Object obj) {
        if (this.clave.equals(((Estado) obj).getClave()))
            return true;
        else
            return false;
    }
}
