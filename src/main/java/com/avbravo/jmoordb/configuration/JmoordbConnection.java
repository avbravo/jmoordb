/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.configuration;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *Guarda la informacion de la conexion de mongodb
 * que sera indicada desde la aplicacion
 * @author avbravo
 */
public class JmoordbConnection {

    String database;
    String username;
    String password;
    String host;
    String port;
    Boolean security;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = externalContext.getSessionMap();

    public JmoordbConnection() {
    }

    public JmoordbConnection(
            String database,
            String username,
            String password,
            String host,
            String port,
            Boolean security) {

        this.database = database;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.security = security;

        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("database", database);
        sessionMap.put("username", username);
        sessionMap.put("password", password);
        sessionMap.put("host", host);
        sessionMap.put("security", security);

    }

    /*
    Para que devuelva el del contexto
     */
    public String getUsername() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String username = (String) sessionMap.get("username");
        return username;
    }

    public String getPassword() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String password = (String) sessionMap.get("password");
        return password;
    }

    public String getDatabase() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String database = (String) sessionMap.get("database");
        return database;
    }

    public String getHost() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String host = (String) sessionMap.get("host");
        return host;
    }

    public String getPort() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String port = (String) sessionMap.get("port");
        return port;
    }

    public Boolean getSecurity() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Boolean security = (Boolean) sessionMap.get("security");
        return security;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setSecurity(Boolean security) {
        this.security = security;
    }

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public static class Builder {

        String database;
        String username;
        String password;
        String host;
        String port;
        Boolean security;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withPort(String port) {
            this.port = port;
            return this;
        }

        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        public Builder withDatabase(String database) {
            this.port = database;
            return this;
        }

        public Builder withSecurity(Boolean security) {
            this.security = security;
            return this;
        }

        public JmoordbConnection build() {
            return new JmoordbConnection(
                    database,
                    username,
                    password,
                    host,
                    port,
                    security
            );
        }

    }

}
