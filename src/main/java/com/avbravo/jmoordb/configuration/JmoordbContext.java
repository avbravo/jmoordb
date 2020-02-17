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

    // <editor-fold defaultstate="collapsed" desc="void put(String key, Object value)">
    /**
     * guarda en el session
     * @param key
     * @param value 
     */
    public static void put(String key, Object value) {
        try{
             FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
        }catch(Exception e){
              JmoordbUtil.errorMessage("put()" + " " + e.getLocalizedMessage());
        }  
      
       
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="putApplication()">
    public static void putApplication(String key, Object value) {
        try{
             FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
        }catch(Exception e){
              JmoordbUtil.errorMessage("put()" + " " + e.getLocalizedMessage());
        }  
      
       
    }
    
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object get(String key)">
    public static Object get(String key) {
        try{
             return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
        }catch(Exception e){
              JmoordbUtil.errorMessage("get()" + " " + e.getLocalizedMessage());
        }  
      
       return "";
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object getApplication(String key)">
    /**
     * GetApplication obtiene el valor del application
     * @param key
     * @return 
     */
    public static Object getApplication(String key) {
        try{
             return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
        }catch(Exception e){
              JmoordbUtil.errorMessage("getApplication()" + " " + e.getLocalizedMessage());
        }  
      
       return "";
    }
    // </editor-fold>
}
