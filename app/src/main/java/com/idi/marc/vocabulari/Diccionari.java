package com.idi.marc.vocabulari;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Created by Marc on 12/03/2015.
 */
public class Diccionari /*implements Parcelable*/{

    private HashMap<Paraula,ArrayList<Paraula>> diccionari1;
    private HashMap<Paraula,ArrayList<Paraula>> diccionari2;
    private Idioma idiomaFrom, idiomaTo;
    private String idiomes;

    public Diccionari(Idioma idiomaFrom, Idioma idiomaTo){
        this.idiomaTo=idiomaTo;
        this.idiomaFrom=idiomaFrom;
        diccionari1=new HashMap<Paraula,ArrayList<Paraula>>();
        diccionari2=new HashMap<Paraula,ArrayList<Paraula>>();
    }

    public void connectarParaules(Paraula par1, Paraula par2){

        if(par1.getIdioma().equals(idiomaFrom)){
            ArrayList<Paraula> testPar=diccionari1.get(par1);
            testPar.add(par2);

            ArrayList<Paraula> testPar2=diccionari2.get(par2);
            testPar2.add(par1);

    		/*diccionari1.get(par1).add(par2);
    		diccionari2.get(par2).add(par1);*/
        }

        else if(par2.getIdioma().equals(idiomaFrom)){

            ArrayList<Paraula> testPar=diccionari1.get(par2);
            testPar.add(par1);

            ArrayList<Paraula> testPar2=diccionari2.get(par1);
            testPar2.add(par2);
    		/*
    		diccionari1.get(par2).add(par1);
    		diccionari2.get(par1).add(par2);*/
        }
    }

    public boolean checkConnexio(Paraula par1, Paraula par2){

        ArrayList<Paraula> aux=new ArrayList<Paraula>();

        if(par1.getIdioma()==idiomaFrom){
            aux=diccionari1.get(par1);
            if(aux.contains(par2))
                return true;

            else
                return false;
        }

        if(par2.getIdioma()==idiomaFrom){
            aux=diccionari1.get(par2);
            if(aux.contains(par1))
                return true;

            else
                return false;
        }
        return false;
    }

    public Idioma[] getIdiomes(){

        Idioma vecIdiomes[]=new Idioma[2];
        vecIdiomes[0]=idiomaFrom;
        vecIdiomes[1]=idiomaTo;
        return vecIdiomes;

    }

    public ArrayList<Paraula> getTraduccio(Paraula paraulaIn){

        if(paraulaIn.getIdioma()==idiomaFrom){
            return diccionari1.get(paraulaIn);
        }

        else{
            return diccionari2.get(paraulaIn);
        }
    }

    public void removeTraduccio(Paraula par1, Paraula par2){

        if(par1.getIdioma()==idiomaFrom){
            if(diccionari1.get(par1).contains(par2))
                diccionari1.get(par1).remove(par2);
            diccionari2.get(par2).remove(par1);
        }

        if(par2.getIdioma()==idiomaFrom){
            if(diccionari1.get(par2).contains(par1))
                diccionari1.get(par2).remove(par1);
            diccionari2.get(par1).remove(par2);
        }
    }

    public String getIdiomesString(){
        idiomes=idiomaFrom.getId()+idiomaTo.getId();
        return idiomes;
    }

    public void afegirParaula(Paraula par, Idioma idi){

        ArrayList<Paraula> llistaTraduccions=new ArrayList<Paraula>();

        if(idi.equals(idiomaFrom)){
            diccionari1.put(par,llistaTraduccions);
        }

        else if(idi.equals(idiomaTo)){
            diccionari2.put(par,llistaTraduccions);
        }
    }

    public void eliminarParaula(Paraula par, Idioma idi){

        ArrayList<Paraula> llistaTraduccions;
        Paraula aux;

        if(idi.equals(idiomaFrom)){

            llistaTraduccions=diccionari1.get(par);

            Iterator<Paraula> it = llistaTraduccions.iterator();
            while (it.hasNext()) {
                aux=it.next();
                diccionari2.get(aux).remove(par);
            }

            diccionari1.remove(par);

        }

        else if(idi.equals(idiomaTo)){

            llistaTraduccions=diccionari2.get(par);

            Iterator<Paraula> it = llistaTraduccions.iterator();
            while (it.hasNext()) {
                aux=it.next();
                diccionari1.get(aux).remove(par);
            }

            diccionari2.remove(par);
        }



    }

    public boolean teTraduccio(Paraula par){

        if(par.getIdioma().equals(idiomaFrom)){
            if(diccionari1.get(par).isEmpty())
                return false;
            else
                return true;
        }
        else if(par.getIdioma().equals(idiomaTo)){
            if(diccionari2.get(par).isEmpty())
                return false;
            else
                return true;
        }

        return false;

    }

}
