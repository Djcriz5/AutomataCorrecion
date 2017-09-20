package AFDtoAFDbySubsets;


import Model.AutomataStructure.Estado;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by criscastro on 12/09/17.
 */
public class Subconjunto {
    private Set<String> kernel;
    private Set<String> cerradura;
    private  Estado estado;
    public Subconjunto(String claveEstado){
        this.setKernel(new HashSet<>());
        this.setCerradura(new HashSet<>());
        this.estado=new Estado(claveEstado);
    }
    public Subconjunto(String claveEstado,boolean esFinal){
        this.setKernel(new HashSet<>());
        this.setCerradura(new HashSet<>());
        this.estado=new Estado(claveEstado,esFinal);
    }

    public Subconjunto(String claveEstado,Set<Estado> kernel,Set<Estado>cerradura){
        this.setKernel(new HashSet<>());
        this.setCerradura(new HashSet<>());
        this.estado=new Estado(claveEstado);
    }


    public Set<String> getKernel() {
        return kernel;
    }

    public void setKernel(Set<String> kernel) {
        this.kernel = kernel;
    }

    public Set<String> getCerradura() {
        return cerradura;
    }

    public void setCerradura(Set<String> cerradura) {
        this.cerradura = cerradura;
    }

    public Estado getEstado(){
        return this.estado;
    }
/*
    @Override
    public boolean equals(Object obj) {
        boolean response=false;
        for (String e1:this.kernel) {
            for (String e2:((Subconjunto)obj).getKernel()) {
              response=(e1.equals(e2));
            }
        }
        return response;
    }*/
@Override
public boolean equals(Object obj) {
    return this.kernel.equals(((Subconjunto)obj).getKernel());
    }
}
