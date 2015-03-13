package com.idi.marc.vocabulari;

import java.util.HashMap;
/**
 * Created by Marc on 12/03/2015.
 */
public class Diccionari {

    private HashMap<Paraula,Paraula> diccionari1;
    private HashMap<Paraula,Paraula> diccionari2;
    private Idioma idiomaFrom, idiomaTo;

    public Diccionari(Idioma idiomaFrom, Idioma idiomaTo){
        this.idiomaTo=idiomaTo;
        this.idiomaFrom=idiomaFrom;
    }

}
