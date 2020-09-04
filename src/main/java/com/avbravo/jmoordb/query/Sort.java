/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.query;

/**
 *
 * @author avbravo
 */
public enum Sort {
   ASC("ASC"),
   DESC("DESC")  
   ;
   
   private String value;

    private Sort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
