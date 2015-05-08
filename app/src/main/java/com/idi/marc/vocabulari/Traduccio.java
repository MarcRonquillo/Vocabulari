package com.idi.marc.vocabulari;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

public class Traduccio implements Serializable {

    private static final long serialVersionUID = 1L;
    private static HashMap<Idioma[],Diccionari> diccionaris;
    private static HashMap<String,Idioma> idiomes;
    private ArrayList<Integer> puntuacionsMode;

    private ArrayList<Integer> erradesMode;

    public static  ArrayList<String> modesStatic;
    public static ArrayList<Integer> puntuacionsModeStatic;
    public static ArrayList<Integer> erradesModeStatic;

    private ArrayList<String> modes;

    public static String idioma1Joc;
    public static String idioma2Joc;
    public static String mode;
    Joc jocActual;

    Integer idIdiomes=0;

    public Traduccio(){
        idiomes=new HashMap<String,Idioma>();
        diccionaris= new HashMap<Idioma[],Diccionari>();
        puntuacionsMode=new ArrayList<Integer>();
        erradesMode=new ArrayList<Integer>();
        modes= new ArrayList<String>();
    }

    public void nouIdioma(String nomIdioma)throws Exception{

        if(!idiomes.containsKey(nomIdioma)){
            Idioma nou=new Idioma(nomIdioma);
            idiomes.put(nomIdioma,nou);
            idIdiomes++;


            Iterator<Entry<String,Idioma>> it = idiomes.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String,Idioma> pair = (Entry<String,Idioma>)it.next();
                if(!pair.getValue().equals(nou)){
                    crearDiccionari(nou,(Idioma)pair.getValue());
                    afegirParaulesExistentsAlDiccionari(getDiccionari(nou.getId(),pair.getValue().getId()));
                }
            }
        }
        else{
            throw new Exception("L'idioma ja existeix");
        }

    }

    public void afegirParaulesExistentsAlDiccionari(Diccionari dicc){
        Idioma idi1=dicc.getIdiomes()[0];
        Idioma idi2=dicc.getIdiomes()[1];

        Iterator<String> iterator = idi1.getLlistaParaules().iterator();
        while (iterator.hasNext()) {
            getDiccionari(idi1.getId(),idi2.getId()).afegirParaula(getParaula(iterator.next(),idi1.getId()),idi1);
        }

        Iterator<String> iterator2 = idi2.getLlistaParaules().iterator();
        while (iterator2.hasNext()) {
            getDiccionari(idi1.getId(),idi2.getId()).afegirParaula(getParaula(iterator2.next(),idi2.getId()),idi2);
        }


    }

    public void eliminarIdioma(String idioma) throws Exception{

        if(idiomes.containsKey(idioma)){
            idiomes.remove(idioma);

            Iterator<Entry<Idioma[],Diccionari>> it = diccionaris.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Idioma[],Diccionari> pair = (Entry<Idioma[],Diccionari>)it.next();
                if((pair.getKey()[0].getId().equals(idioma))||(pair.getKey()[1].getId().equals(idioma))){
                    it.remove();
                }
            }
        }
        else{
            throw new Exception("L'idioma no existeix");
        }

    }

    public void crearDiccionari(Idioma idioma1, Idioma idioma2){

        Diccionari dic= new Diccionari(idioma1,idioma2);
        Idioma[] idArray=new Idioma[2];
        idArray[0]=idioma1;
        idArray[1]=idioma2;

        diccionaris.put(idArray,dic);
    }

    //idiomes

    public void afegirParaula(String paraula, String idioma) throws Exception{
        idiomes.get(idioma).afegirParaula(paraula);

        Paraula par=getParaula(paraula,idioma);
        Idioma idi=getIdioma(idioma);

        Iterator<Entry<Idioma[],Diccionari>> it = diccionaris.entrySet().iterator();

        while (it.hasNext()) {

            Entry<Idioma[],Diccionari> pair = (Entry<Idioma[],Diccionari>)it.next();
            if( (pair.getKey()[0].getId().equals(idioma)) || (pair.getKey()[1].getId().equals(idioma)) ){
                pair.getValue().afegirParaula(par, idi);
            }
        }

    }

    public void eliminarParaula(String paraula, String idioma){

        Paraula par=getParaula(paraula,idioma);
        Idioma idi=getIdioma(idioma);

        Iterator<Entry<Idioma[],Diccionari>> it = diccionaris.entrySet().iterator();

        while (it.hasNext()) {

            Entry<Idioma[],Diccionari> pair = (Entry<Idioma[],Diccionari>)it.next();

            if( (pair.getKey()[0].getId().equals(idioma)) || (pair.getKey()[1].getId().equals(idioma)) ){
                pair.getValue().eliminarParaula(par, idi);
            }
        }

        idiomes.get(idioma).eliminarParaula(paraula);
    }

    public Paraula getParaula(String paraula, String idioma){
        return idiomes.get(idioma).getParaula(paraula);
    }

    public Idioma getIdioma(String idioma){
        return idiomes.get(idioma);
    }


    //diccionari
    public void connectarParaules(String paraula1,String idioma1, String paraula2, String idioma2){

        Paraula par1=getParaula(paraula1,idioma1);
        Paraula par2=getParaula(paraula2,idioma2);

        getDiccionari(idioma1, idioma2).connectarParaules(par1, par2);

    }


    public static Diccionari getDiccionari(String idioma1, String idioma2){

        Iterator<Entry<Idioma[],Diccionari>> it = diccionaris.entrySet().iterator();

        while (it.hasNext()) {

            Entry<Idioma[],Diccionari> pair = (Entry<Idioma[],Diccionari>)it.next();

            if(((pair.getKey()[0].getId().equals(idioma1))&&(pair.getKey()[1].getId().equals(idioma2)))
                    ||((pair.getKey()[0].getId().equals(idioma2))&&(pair.getKey()[1].getId().equals(idioma1)))){

                return diccionaris.get(pair.getKey());

            }
        }
        return null;
    }


    public boolean checkConnexio(String paraula1,String idioma1, String paraula2, String idioma2){


        Paraula sjds=getParaula(paraula1,idioma1);
        Paraula sjsds=getParaula(paraula2,idioma2);

        if((getParaula(paraula1,idioma1).equals(null)) || (getParaula(paraula2,idioma2).equals(null)) ){
            return false;
        }
        else{
            Paraula par1=getParaula(paraula1,idioma1);
            Paraula par2=getParaula(paraula2,idioma2);

            return getDiccionari(idioma1,idioma2).checkConnexio(par1, par2);
        }


    }

    public ArrayList<String> getTraduccio(String par, String idiomaIn, String idiomaOut){

        ArrayList<String> tornada=new ArrayList<String>();
        int i=0;
        ArrayList<Paraula> trans=getDiccionari(idiomaIn,idiomaOut).getTraduccio(getParaula(par,idiomaIn));

        while (trans.size()>i){
            tornada.add(trans.get(i).getId());
            i++;
        }

        return tornada;

    }

    public void removeTraduccio(String paraula1,String idioma1, String paraula2, String idioma2){

        Diccionari a=getDiccionari(idioma1,idioma2);
        Paraula par1=getParaula(paraula1,idioma1);
        Paraula par2=getParaula(paraula2,idioma2);

        a.removeTraduccio(par1,par2);
    }

    public ArrayList<String> getLlistaDiccionaris(){

        ArrayList<String> dicc=new ArrayList<String>();

        Iterator<Entry<Idioma[],Diccionari>> it = diccionaris.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Idioma[],Diccionari> pair = (Entry<Idioma[],Diccionari>)it.next();
            dicc.add(pair.getValue().getIdiomesString());
        }
        return dicc;
    }

    public ArrayList<String> getLlistaIdiomes(){

        ArrayList<String> dicc=new ArrayList<String>();

        Iterator<Entry<String, Idioma>> it = idiomes.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String,Idioma> pair = (Entry<String,Idioma>)it.next();
            dicc.add(pair.getValue().getId());
        }
        return dicc;
    }

    public ArrayList<String> getLlistaIdiomesMenysPropi(String idioma){
        ArrayList<String> dicc=new ArrayList<String>();

        Iterator<Entry<String, Idioma>> it = idiomes.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String,Idioma> pair = (Entry<String,Idioma>)it.next();
            if(!pair.getValue().getId().equals(idioma))
            dicc.add(pair.getValue().getId());
        }
        return dicc;
    }

    public ArrayList<String> getLlistaParaules(String idi){

        return idiomes.get(idi).getLlistaParaules();
    }

    public static HashMap<String,Idioma> getHashIdiomes(){
        return idiomes;
    }

    public static HashMap<Idioma[],Diccionari> getHashDiccionaris(){
        return diccionaris;
    }

    public void crearJoc(String idioma1, String idioma2){
        jocActual=new Joc(getIdioma(idioma1),getIdioma(idioma2),getDiccionari(idioma1,idioma2));
    }

    public String enviarParaula(){
        return jocActual.enviarParaula();
    }

    public boolean rebreParaula(String gottenWord){
        return jocActual.rebreParaula(gottenWord);
    }

    public Integer getPuntuacio(){
        return jocActual.getPuntuacio();
    }

    public Integer getErrades(){
        return jocActual.getErrades();
    }

    public void acabarJoc(Integer punts, Integer errors){

        puntuacionsMode.add(punts);
        erradesMode.add(errors);
        modes.add(mode);

        Log.i("MyActivity", "Mode  " + puntuacionsMode.get(0).toString());
        Log.i("MyActivity", "punts  " + erradesMode.get(0).toString());
        Log.i("MyActivity", "errors  " + modes.get(0).toString());

    }

    public ArrayList<String> getModes(){
        return modes;
    }


    public ArrayList<Integer> getPunts(){
        return puntuacionsMode;
    }


    public ArrayList<Integer> getErrors(){
        return erradesMode;
    }


    public ArrayList<String> getIdiomesAmbParaules(){

        ArrayList<String> arr=new ArrayList<String>();

        Iterator<Entry<String, Idioma>> it = idiomes.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String,Idioma> pair = (Entry<String,Idioma>)it.next();
            if(pair.getValue().getLlistaParaules().isEmpty()){}
            else
                arr.add(pair.getKey());
        }
        return arr;

    }

    public void inicialitzar() throws Exception{
        String casa="casa";
        String cotxe="cotxe";
        String forquilla="forquilla";
        String cadira="cadira";
        String verd="verd";
        String house="house";
        String car="car";
        String fork="fork";
        String chair="chair";
        String green="green";
        String catala="català";
        String angles="anglès";



       // try{
            nouIdioma(catala);
            afegirParaula(casa,catala);
            afegirParaula(cotxe,catala);
            afegirParaula(forquilla,catala);
            afegirParaula(cadira,catala);
            afegirParaula(verd,catala);
            nouIdioma(angles);
            afegirParaula(house,angles);
            afegirParaula(car,angles);
            afegirParaula(fork,angles);
            afegirParaula(chair,angles);
            afegirParaula(green,angles);

        connectarParaules(casa,catala,house,angles);
        connectarParaules(cotxe,catala,car,angles);
        connectarParaules(forquilla,catala,fork,angles);
        connectarParaules(cadira,catala,chair,angles);
        connectarParaules(verd,catala,green,angles);
    }
}
