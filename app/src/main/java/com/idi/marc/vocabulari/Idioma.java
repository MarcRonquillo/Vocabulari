package com.idi.marc.vocabulari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
/**
 * Created by Marc on 12/03/2015.
 */
public class Idioma implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    // private Integer idParaules=0;
    private HashMap<String,Paraula> llistaParaules;
    // private ArrayList<String> arrayParaules;

    public Idioma(String id){
        setId(id);
        llistaParaules=new HashMap<String,Paraula>();
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id=id;
    }

    public void afegirParaula(String paraula) throws Exception{

        Iterator<Entry<String,Paraula>> it = llistaParaules.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String,Paraula> pair = (Entry<String,Paraula>)it.next();
            if(pair.getKey().equals(paraula)){
                throw new Exception("La paraula ja existeix");
            }
        }

        Paraula paraulaAAfegir=new Paraula(paraula,this);
        llistaParaules.put(paraulaAAfegir.getId(),paraulaAAfegir);
        //idParaules++;
    }

    public void eliminarParaula(String id){
        llistaParaules.remove(id);
    }

    public Paraula getParaula(String id){
        return llistaParaules.get(id);
    }

    public ArrayList<String> getLlistaParaules(){

        ArrayList<String> dicc=new ArrayList<String>();

        Iterator<Entry<String,Paraula>> it = llistaParaules.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String,Paraula> pair = (Entry<String,Paraula>)it.next();
            dicc.add(pair.getKey());
        }
        return dicc;
    }

    public ArrayList<Paraula> getLlistaParaulesObj(){

        ArrayList<Paraula> dicc=new ArrayList<Paraula>();

        Iterator<Entry<String,Paraula>> it = llistaParaules.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String,Paraula> pair = (Entry<String,Paraula>)it.next();
            dicc.add(pair.getValue());
        }
        return dicc;
    }




    @Override
    public String toString() {
        return "Idioma [id=" + id + ", llistaParaules=" + llistaParaules + "]";
    }
}
