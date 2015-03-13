package com.idi.marc.vocabulari;

import java.util.HashMap;
/**
 * Created by Marc on 12/03/2015.
 */
public class Idioma {

    private Integer id;
    private String valor;
    private Integer idParaules=0;
    private HashMap<Integer,Paraula> llistaParaules=new HashMap<Integer,Paraula>();

    public Idioma(Integer id, String valor){
        setValor(valor);
        setId(id);
    }

    public Integer getId(){
        return this.id;
    }

    public String getValor(){
        return this.valor;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public void setValor(String valor){
        this.valor=valor;
    }

    public void afegirParaula(String paraula){

       Paraula paraulaAAfegir=new Paraula(idParaules,paraula,this);
       llistaParaules.put(paraulaAAfegir.getId(),paraulaAAfegir);
       idParaules++;
    }

    public void eliminarParaula(Integer id){
        llistaParaules.remove(id);
    }
}
