package Model.AutomataStructure;

/**
 * Created by criscastro on 22/08/17.
 */
public class Transicion {
    private Estado estadoDeDestino;
    private Estado estadoDeOrigen;
    private String valorAceptado;
    private boolean epsilon;

    public Transicion(Estado origen, Estado destino, String acepta) {
        if (acepta.equals("")) {//check == or equals
            this.estadoDeOrigen = origen;
            this.estadoDeDestino = destino;
            this.valorAceptado = acepta;
            this.epsilon = true;
        } else {
            this.estadoDeOrigen = origen;
            this.estadoDeDestino = destino;
            this.valorAceptado = acepta;
            this.epsilon = false;
        }
    }

    public void enviarCadenaAEvaluar(String cadena){
        if(isEpsilon()){

            this.estadoDeDestino.procesar(cadena);
           // System.out.println("Cadena que esta enviando epsilon "+cadena+" origen: "+estadoDeOrigen.getClave()+"|| destino: "+estadoDeDestino.getClave());
        }else{
            this.estadoDeDestino.procesar(cadena.substring(1));
          //  System.out.println("Cadena que esta enviando  "+cadena+" origen: "+estadoDeOrigen.getClave()+"|| destino: "+estadoDeDestino.getClave());

        }
    }

    public Estado getEstadoDeDestino() {
        return estadoDeDestino;
    }

    public void setEstadoDeDestino(Estado estadoDeDestino) {
        this.estadoDeDestino = estadoDeDestino;
    }

    public Estado getEstadoDeOrigen() {
        return estadoDeOrigen;
    }

    public void setEstadoDeOrigen(Estado estadoDeOrigen) {
        this.estadoDeOrigen = estadoDeOrigen;
    }

    public String getValorAceptado() {
        return valorAceptado;
    }

    public void setValorAceptado(String valorAceptado) {
        if (valorAceptado.equals("")) {
            this.valorAceptado = valorAceptado;
            this.epsilon = true;
        } else {
            this.valorAceptado = valorAceptado;
            this.epsilon = false;
        }
    }

    public boolean isEpsilon() {
        return epsilon;
    }
    public String toString(){
        StringBuilder builder =new StringBuilder();
        builder.append(estadoDeOrigen.getClave()+" -"+this.valorAceptado+"-> "+estadoDeDestino.getClave());
        return builder.toString();
    }


}
