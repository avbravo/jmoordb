/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.interfaces;



/**
 *
 * @authoravbravo
 */
public interface IController<T> {

     public String preRenderView(String action);
     

    public String prepare(String action, Object... item) ;
    

    default public String refresh(){
        return "";
    }

    public String isNew();

    public void reset();

    public String showAll();

    public String save();

    public String edit();


    public String delete(Object item, Boolean deleteonviewpage );

    public String deleteAll();

    public String print();

    public String printAll();


    public String clear();
    public String last();

    public String first();

    public String next();

    public String back();

    public String skip(Integer page);

    public void move();
    
    public String searchBy(String field);

}
