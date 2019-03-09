/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.pojos;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author avbravo
 */
public class JmoordbResult {

    private HashMap<String, String> field = new HashMap<>();
        Exception exception = new Exception();

        
    public HashMap<String, String> getField() {
        return field;
    }

    public void setField(HashMap<String, String> field) {
        this.field = field;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public JmoordbResult() {
    }
    public JmoordbResult(String key, String value) {
        put(key, value);
    }

        
        
    // <editor-fold defaultstate="collapsed" desc="put(String key, String value)">
    public void put(String key, String value) {
        try {
            field.put(key, value);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("put() ", e);
        }
    }
    // </editor-fold>
  
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="get(String key)">
    public String get(String key) {
       String value = "";
        try {
            value = field.get(key);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("get() ", e);
        }
        return value;
    }   // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getInteger(String key)">
    public Object getInteger(String key) {
       Object value = "";
        try {
            value = field.get(key);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("get() ", e);
        }
        return Integer.parseInt(value.toString());
    }   // </editor-fold>
    
    
}
