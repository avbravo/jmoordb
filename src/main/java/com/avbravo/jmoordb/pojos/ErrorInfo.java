/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.pojos;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
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
    
    public static class Builder {

          private String iderror;
    private String username;
    private Date date;
    private String clase;
    private String metodo;
    private String mensaje;

       
        public Builder withIderror(String iderror) {
            this.iderror = iderror;
            return this;
        }
        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }
       
        public Builder withClase(String clase) {
            this.clase = clase;
            return this;
        }
        public Builder withMetodo(String metodo) {
            this.metodo = metodo;
            return this;
        }
        public Builder withMensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public ErrorInfo build() {
            return new ErrorInfo(iderror, username, date, clase, metodo, mensaje);
        }

    }

    
    
}
