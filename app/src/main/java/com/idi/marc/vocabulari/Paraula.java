package com.idi.marc.vocabulari;

/**
 * Created by Marc on 12/03/2015.
 */
public class Paraula {

    private Integer id;
    private String valor;
    private Idioma idioma;

    public Paraula(Integer id, String valor,Idioma idioma){
        setId(id);
        setValor(valor);
        setIdioma(idioma);
    }

    /**
     * Getters i setters
     * @return
     */
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

    public void setIdioma(Idioma idioma){
        this.idioma=idioma;
    }

}
