/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Id;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class ErrorInfo {
    @Id
    private String iderror;
    private String username;
    private Date date;
    private String clase;
    private String metodo;
    private String mensaje;

    public ErrorInfo() {
    }

    public ErrorInfo(String iderror, String username, Date date, String clase, String metodo, String mensaje) {
        this.iderror = iderror;
        this.username = username;
        this.date = date;
        this.clase = clase;
        this.metodo = metodo;
        this.mensaje = mensaje;
    }

    
    
    
    public String getIderror() {
        return iderror;
    }

    public void setIderror(String iderror) {
        this.iderror = iderror;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

  

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    
}
