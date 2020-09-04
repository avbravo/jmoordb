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
public enum Comparison {
   EQ("EQ"),
   NE("NE"),
   BETWEEN("BETWEEN"),
   GT("GT"),
   GTE("GTE"),
   LT("LT"),
   IN("IN"),
   NIN("NIN"),
   LTE("LTE")
   
   ;
   
   private String value;

    private Comparison(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
