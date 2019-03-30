/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history;


import com.avbravo.jmoordb.pojos.ErrorInfo;
import com.avbravo.jmoordb.util.JmoordbUtil;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ErrorInfoServices { 

  
    @Inject
    ErrorInfoRepository errorInfoStoreejbRepository;

   
    
     public void  errorMessage(String clase, String metodo, String message){
    
        try {

             ErrorInfo errorInfo = new ErrorInfo();
             errorInfo.setIderror(JmoordbUtil.getUUID());
             errorInfo.setClase(clase);
             errorInfo.setMensaje(clase);
             errorInfo.setMetodo(metodo);
             errorInfoStoreejbRepository.save(errorInfo);
            
        } catch (Exception e) {
            JmoordbUtil.errorMessage("errorMessage() " + e.getLocalizedMessage());
        }
  JmoordbUtil.errorMessage(metodo +" " + message);
    }
     public void  errorDialog(String clase, String metodo, String titulo,String message){
    
        try {

             ErrorInfo errorInfo = new ErrorInfo();
             errorInfo.setIderror(JmoordbUtil.getUUID());
             errorInfo.setClase(clase);
             errorInfo.setMensaje(titulo + " "+message);
             errorInfo.setMetodo(metodo);
             errorInfoStoreejbRepository.save(errorInfo);
            
        } catch (Exception e) {
            JmoordbUtil.errorMessage("errorMessage() " + e.getLocalizedMessage());
        }
  JmoordbUtil.errorMessage( message);
    }
}
