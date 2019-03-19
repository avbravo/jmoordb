/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

/**
 *
 * @author avbravo
 */
public class JmoordbUtils {
     // <editor-fold defaultstate="collapsed" desc=" String textoDespuesUltimoPunto(String texto)">
   
   /**
    * obtiene el texto despues del ultimo puento
    * @param texto (com.avbravo.entity.Rol)
    * @return Rol
    */
   public static String nombreEntity(String texto){
       String result="";
          // TODO code application logic here
          try {
       
        Integer pos =texto.lastIndexOf(".");

       result = texto.substring(pos+1, texto.length());

       } catch (Exception e) {
       }
        return result;
        
   }
    // </editor-fold>
}
