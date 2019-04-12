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
import java.util.List;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author avbravo
 */
public class JmoordbUtil {
      public static  String letterToUpper(String texto) {
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
    public static    String letterToLower(String texto) {

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
    
    public static  String traductor(String texto, String idioma){
        String traduccion = "";
        try {
            
        } catch (Exception e) {
            //Test.msg("traductor() "+e.getLocalizedMessage());
            new JmoordbException("traductor() "+e.getLocalizedMessage());
        }
 return traduccion;       
    }
    
    
     public static  Date getFechaHoraActual() {
        LocalDateTime ahora = LocalDateTime.now();
        Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
        return date2;
    }
     
     // <editor-fold defaultstate="collapsed" desc="nombreEntity(String texto)">
   
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
   
   
   
       // <editor-fold defaultstate="collapsed" desc="errorMessage"> 

    public static void errorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            errorMessage(msg);
        } else {
            errorMessage(defaultMsg);
        }
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorMessages"> 

    public static void errorMessages(List<String> messages) {
        for (String message : messages) {
            errorMessage(message);

        }
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorMessage(String msg)"> 

    public static void errorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
     
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="testMessage"> 
    public static void testMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
   
        
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="successMessage"> 
    public static void successMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
                msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="warningMessage"> 

    public static void warningMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
       
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="fatalMessage"> 

    public static void fatalMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, ""));
    
    }    // </editor-fold>
    
    
//     // <editor-fold defaultstate="collapsed" desc="infoDialog"> 
//    public static void infoDialog(String titulo, String texto) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo,
//                texto);
//        RequestContext.getCurrentInstance().showMessageInDialog(message);
//    }
//    // </editor-fold>
//
//    // <editor-fold defaultstate="collapsed" desc="warningDialog"> 
//    public static void warningDialog(String titulo, String texto) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo,
//                texto);
//        RequestContext.getCurrentInstance().showMessageInDialog(message);
//        LOG.warning(titulo + " " + texto);
//    }    // </editor-fold>
//// <editor-fold defaultstate="collapsed" desc="fatalDialog"> 
//
//    public static void fatalDialog(String titulo, String texto) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo,
//                texto);
//        RequestContext.getCurrentInstance().showMessageInDialog(message);
//        LOG.warning(titulo + " " + texto);
//    }    // </editor-fold>
//// <editor-fold defaultstate="collapsed" desc="errorDialog"> 
//
//    public static void errorDialog(String titulo, String texto) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                titulo, texto);
//        RequestContext.getCurrentInstance().showMessageInDialog(message);
//        LOG.warning(titulo + " " + texto);
//    }    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="isVacio(String texto)()"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isVacio(String texto) {
        texto = texto.trim();
        return texto == null || texto.equals("") || texto.isEmpty();
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isVacio(Integer texto)"> 

    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isVacio(Integer texto) {
        return texto == null || texto.equals("");
    }// </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="isVacio(Integer texto)"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isVacio(Double texto) {
        return texto == null || texto.equals("");
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean isNegativo(Double texto)"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isNegativo(Double numero) {
        return numero == null || numero.equals("") || numero <0;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean isNegativo(Double texto)"> 
    /**
     * return true si es null empty equals("")
     *
     * @param texto
     * @return
     */
    public static Boolean isNegativo(Integer numero) {
        return numero == null || numero.equals("") || numero <0;
    }// </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="getUUID"> 
    /**
     * genera id
     *
     * @return returna un randomUUID automatico
     */
    public static String getUUID() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }// </editor-fold>
    
    
     // <editor-fold defaultstate="collapsed" desc="infoDialog"> 
    public static void infoDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo,
                texto);
   PrimeFaces.current().dialog().showMessageDynamic(message);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="warningDialog"> 
    public static void warningDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo,
                texto);
        
        
          PrimeFaces.current().dialog().showMessageDynamic(message);
    
       
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="fatalDialog"> 

    public static void fatalDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo,
                texto);
          PrimeFaces.current().dialog().showMessageDynamic(message);
    }    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="errorDialog"> 

    public static void errorDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                titulo, texto);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }    // </editor-fold>
}
