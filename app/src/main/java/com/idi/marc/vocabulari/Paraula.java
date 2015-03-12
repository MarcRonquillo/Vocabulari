package com.idi.marc.vocabulari;

/**
 * Created by Marc on 12/03/2015.
 */
public class Paraula {

    private static Integer id;
    private String valor;

    public Paraula(String valor){
        setValor(valor);
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

}
