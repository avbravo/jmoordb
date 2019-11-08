/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history.services;

import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.mongodb.history.entity.ErrorEmbedded;
import com.avbravo.jmoordb.mongodb.history.repository.ErrorInfoRepository;
import com.avbravo.jmoordb.mongodb.history.entity.ErrorInfo;
import com.avbravo.jmoordb.util.JmoordbUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ErrorInfoServices {

    @Inject
    ErrorInfoRepository errorInfoRepository;

    public void errorMessage(String clase, String metodo, String message, Exception e) {

        try {

            JmoordbConfiguration jmc = new JmoordbConfiguration();
            String username = jmc.getUsername();
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setIderror(JmoordbUtil.getUUID());
            errorInfo.setClase(clase);
            errorInfo.setMensaje(message);
            errorInfo.setMetodo(metodo);
            errorInfo.setDate(JmoordbUtil.getFechaHoraActual());
            errorInfo.setUsername(username);
            List<ErrorEmbedded> errorEmbeddedList = new ArrayList<>();
            Integer c = 0;
            if(e !=null){
               for (StackTraceElement s : e.getStackTrace()) {
                if (s.getFileName().indexOf(clase) != -1) {
                    ErrorEmbedded errorEmbedded = new ErrorEmbedded();
                    errorEmbedded.setIderrorembedded(c++);
                    errorEmbedded.setMethod(s.getMethodName());
                    errorEmbedded.setLine(s.getLineNumber());

                    //    System.out.println("---> className"+s.getClassName() + " filename: "+s.getFileName());
                    //   System.out.println("---> getMethodName: "+s.getMethodName() + " LineNumber: "+s.getLineNumber());
                    errorEmbeddedList.add(errorEmbedded);
                }

            } 
            }
            
            errorInfo.setErrorEmbedded(errorEmbeddedList);
            errorInfoRepository.save(errorInfo);

        } catch (Exception ex) {
            JmoordbUtil.errorMessage("errorMessage() " + ex.getLocalizedMessage());
        }
        //  JmoordbUtil.errorMessage(message);
         JmoordbUtil.errorMessage(metodo + " " + message);
    //    errorDialog(clase, metodo, "error", message, e);
    }

    public void errorDialog(String clase, String metodo, String titulo, String message, Exception e) {

        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            String username = jmc.getUsername();
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setIderror(JmoordbUtil.getUUID());
            errorInfo.setClase(clase);
            errorInfo.setMensaje(titulo + " " + message);
            errorInfo.setMetodo(metodo);
            errorInfo.setDate(JmoordbUtil.getFechaHoraActual());
            errorInfo.setUsername(username);
            List<ErrorEmbedded> errorEmbeddedList = new ArrayList<>();
            Integer c = 0;
            for (StackTraceElement s : e.getStackTrace()) {
                if (s.getFileName().indexOf(clase) != -1) {
                    ErrorEmbedded errorEmbedded = new ErrorEmbedded();
                    errorEmbedded.setIderrorembedded(c++);
                    errorEmbedded.setMethod(s.getMethodName());
                    errorEmbedded.setLine(s.getLineNumber());

                    //    System.out.println("---> className"+s.getClassName() + " filename: "+s.getFileName());
                    //   System.out.println("---> getMethodName: "+s.getMethodName() + " LineNumber: "+s.getLineNumber());
                    errorEmbeddedList.add(errorEmbedded);
                }

            }
            errorInfo.setErrorEmbedded(errorEmbeddedList);
            errorInfoRepository.save(errorInfo);

        } catch (Exception ex) {
            JmoordbUtil.errorMessage("errorMessage() " + ex.getLocalizedMessage());
        }
        JmoordbUtil.errorDialog(titulo,message);
    }
}
