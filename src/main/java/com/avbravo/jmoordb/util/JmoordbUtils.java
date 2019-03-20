/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

import java.util.List;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *
 * @author avbravo
 */
public class JmoordbUtils {
         private static final Logger LOG = Logger.getLogger(JmoordbUtils.class.getName());
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
        LOG.warning(msg);
    }    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="testMessage"> 
    public static void testMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        LOG.warning(msg);
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
        LOG.warning(msg);
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
}
