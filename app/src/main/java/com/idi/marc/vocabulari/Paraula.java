package com.idi.marc.vocabulari;

/**
 * Created by Marc on 12/03/2015.
 */
public class Paraula {

    private String id;
    private Idioma idioma;

    public Paraula(String valor,Idioma idioma){

        setId(valor);
        setIdioma(idioma);
    }

    /**
     * Getters i setters
     * @return
     */

    public String getId(){
        return this.id;
    }

    public void setId(String valor){
        this.id=valor;
    }

    public void setIdioma(Idioma idioma){
        this.idioma=idioma;
    }

    public Idioma getIdioma(){
        return idioma;
    }


}
