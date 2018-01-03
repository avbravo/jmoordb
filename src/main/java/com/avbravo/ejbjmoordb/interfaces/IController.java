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
     
//action= new, edit, ("edit", entity)
    public String prepare(String action, Object... item) ;
    
//public String prepareNew();
//    //public String open(); se reemplazo por prepareSearch
//    public String prepareSearch();
//    //public String query();
//
//    public String prepareWritable();

    
    

    public String isNew();

    public void reset();

    public String showAll();

    public String save();

    public String edit();

  //  public String prepareEdit(Object item);

    /*
      donotleave= true se queda en el form false = salta al formulario list
      reset = true resetea el formulario (view), list reset = false;
      removeFromList= true remueve de la lista se llama desde el list.
     */
    public String delete(Object item, Boolean deleteonviewpage );

    public String deleteAll();

    public String print();

    public String printAll();

 //public void handleSelect(SelectEvent event);

    public String last();

    public String first();

    public String next();

    public String back();

    public String skip(Integer page);

    public void move();

}
