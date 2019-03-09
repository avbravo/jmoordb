/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

import com.avbravo.jmoordb.JmoordbException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class Util {
      public  String letterToUpper(String texto) {
        try {

            texto = texto.trim();
            int largo = texto.length();
            if (largo <= 0) {
                return texto;
            }
            String letra = texto.substring(0, 1);

            texto = letra.toUpperCase() + texto.substring(1);
        } catch (Exception ex) {
          //Test.msg("letterToUpper() " + ex.getLocalizedMessage());
        }
        return texto;
    }

    /**
     * ConvertirLetraMinuscula
     *
     * @param s_cadena
     * @param caracter
     * @return
     */
    public  String letterToLower(String texto) {

        try {

            texto = texto.trim();
            int largo = texto.length();
            if (largo <= 0) {
                return texto;
            }
            String letra = texto.substring(0, 1);

            texto = letra.toLowerCase() + texto.substring(1);
        } catch (Exception ex) {
          //Test.msg("letterToLower() " + ex.getLocalizedMessage());
        }
        return texto;
    }
    
    public String traductor(String texto, String idioma){
        String traduccion = "";
        try {
            
        } catch (Exception e) {
            //Test.msg("traductor() "+e.getLocalizedMessage());
            new JmoordbException("traductor() "+e.getLocalizedMessage());
        }
 return traduccion;       
    }
    
    
     public  Date getFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
        return date2;
    }
}
