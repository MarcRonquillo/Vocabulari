package com.idi.marc.vocabulari;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Joc implements Serializable {

    private static final long serialVersionUID = 1L;
    private Idioma idiomaFrom;
    private Idioma idiomaTo;
    Diccionari diccionari;
    Integer errades, puntuacio;
    private ArrayList<String> llistaParaules;
    private ArrayList<Paraula> llistaParaulesObj;
    private Paraula paraula1;

    //passar nomes diccionari?
    public Joc(Idioma idioma1, Idioma idioma2, Diccionari diccionari){
        idiomaFrom=idioma1;
        idiomaTo=idioma2;
        this.diccionari=diccionari;
        llistaParaules=idioma1.getLlistaParaules();
        llistaParaulesObj=idioma1.getLlistaParaulesObj();
        errades=0;
        puntuacio=0;
    }

    public String enviarParaula(){

        //ArrayList<String> aux=getParaulesAmbTraduccio();
        //llistaParaules=getParaulesAmbTraduccio();
        Random r = new Random();
        String paraulaRandom= getParaulesAmbTraduccio().get(r.nextInt(getParaulesAmbTraduccio().size()));
        paraula1=idiomaFrom.getParaula(paraulaRandom);
        return paraulaRandom;
    }

    public boolean rebreParaula(String gottenWord){

        if(!existeixParaula(gottenWord,idiomaTo.getId())){
            errades++;
            return false;
        }
        else{
            Paraula paraula2=idiomaTo.getParaula(gottenWord);
            boolean resultat=diccionari.checkConnexio(paraula1, paraula2);
            if(resultat)
                puntuacio++;
            else
                errades++;
            return resultat;
        }
    }

    public Integer getPuntuacio(){
        return puntuacio;
    }

    public Integer getErrades(){
        return errades;
    }

    public Paraula getParaula(String paraula, String idioma){
        return Traduccio.getHashIdiomes().get(idioma).getParaula(paraula);
    }

    public boolean existeixParaula(String par, String idioma){

        if((getParaula(par,idioma)==null) || (getParaula(par,idioma)==null) ){
            return false;
        }
        else{
            return true;
        }
    }

    public ArrayList<String> getParaulesAmbTraduccio(){

        ArrayList<String> paraules=new ArrayList<String>();

        Iterator<Paraula> iterator = llistaParaulesObj.iterator();
        while (iterator.hasNext()) {
            Paraula aux=iterator.next();
            if(diccionari.teTraduccio(aux)){
                paraules.add(aux.getId());
            }
        }

        return paraules;
    }



}
