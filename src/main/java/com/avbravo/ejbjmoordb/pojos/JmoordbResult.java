/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.mongodb.repository.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author avbravo
 */
public class JmoordbResult {

    private HashMap<String, Object> field = new HashMap<>();
        Exception exception = new Exception();

    // <editor-fold defaultstate="collapsed" desc="put(String key, String value)">
    public void put(String key, Object value) {
        try {
            field.put(key, value);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("put() ", e);
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="put(String key, Integer value)">
    public void put(String key, Integer value) {
        try {
            field.put(key, value);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("put() ", e);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="get(String key)">
    public Object get(String key) {
       Object value = "";
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
