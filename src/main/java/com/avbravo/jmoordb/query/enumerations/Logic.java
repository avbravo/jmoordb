/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.query.enumerations;

/**
 *
 * @author avbravo
 */
public enum Logic {
  
   AND("AND"),
   OR("OR"),
   NOT("NOT"),  
   NOR("NOR")  
   ;
   
   private String value;

    private Logic(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
