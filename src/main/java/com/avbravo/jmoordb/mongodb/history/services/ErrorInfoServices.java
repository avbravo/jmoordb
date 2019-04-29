/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history.services;

import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.mongodb.history.repository.ErrorInfoRepository;
import com.avbravo.jmoordb.mongodb.history.entity.ErrorInfo;
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
    ErrorInfoRepository errorInfoRepository;

    public void errorMessage(String clase, String metodo, String message) {

        try {
    
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            String username = jmc.getUsername();
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setIderror(JmoordbUtil.getUUID());
            errorInfo.setClase(clase);
            errorInfo.setMensaje(clase);
            errorInfo.setMetodo(metodo);
            errorInfo.setDate(JmoordbUtil.getFechaHoraActual());
            errorInfo.setUsername(username);

            errorInfoRepository.save(errorInfo);

        } catch (Exception e) {
            JmoordbUtil.errorMessage("errorMessage() " + e.getLocalizedMessage());
        }
       // JmoordbUtil.errorMessage(metodo + " " + message);
        errorDialog(clase, metodo,"error", message);
    }

    public void errorDialog(String clase, String metodo, String titulo, String message) {

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
            errorInfoRepository.save(errorInfo);

        } catch (Exception e) {
            JmoordbUtil.errorMessage("errorMessage() " + e.getLocalizedMessage());
        }
        JmoordbUtil.errorMessage(message);
    }
}
