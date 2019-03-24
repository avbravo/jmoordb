/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.configuration;

import com.avbravo.jmoordb.util.JmoordbUtil;
import javax.faces.context.FacesContext;

/**
 *
 * @author avbravo
 */
public class JmoordbContext {

    public static void put(String key, Object value) {
        try{
             FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
        }catch(Exception e){
              JmoordbUtil.errorMessage("put()" + " " + e.getLocalizedMessage());
        }  
      
       
    }
    public static Object get(String key) {
        try{
             return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
        }catch(Exception e){
              JmoordbUtil.errorMessage("put()" + " " + e.getLocalizedMessage());
        }  
      
       return "";
    }
}
