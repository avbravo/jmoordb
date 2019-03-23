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
 *
 * @author avbravo
 */
public class JmoordbConfiguration {

    RevisionHistoryServices revisionHistoryServices;
    Repository repositoryRevisionHistory;
    Boolean revisionSave;
    String username;
    Boolean spanish;
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = externalContext.getSessionMap();

    public JmoordbConfiguration() {
    }

    
    
    public JmoordbConfiguration(RevisionHistoryServices revisionHistoryServices, Repository repositoryRevisionHistory, Boolean revisionSave, String username, Boolean spanish) {
        this.revisionHistoryServices = revisionHistoryServices;
        this.repositoryRevisionHistory = repositoryRevisionHistory;
        this.revisionSave = revisionSave;
        this.username = username;
        this.spanish = spanish;
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("repositoryRevisionHistory", repositoryRevisionHistory);
        sessionMap.put("revisionHistoryServices", revisionHistoryServices);
        sessionMap.put("username", username);
        sessionMap.put("revisionSave", revisionSave);
        sessionMap.put("spanish", spanish);
    }

    public RevisionHistoryServices getRevisionHistoryServices() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
         RevisionHistoryServices revisionHistoryServices = (RevisionHistoryServices) sessionMap.get("revisionHistoryServices");
        return revisionHistoryServices;
    }

    public Repository getRepositoryRevisionHistory() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Repository repositoryRevisionHistory = (Repository) sessionMap.get("repositoryRevisionHistory");
        return repositoryRevisionHistory;
    }

  
    public Boolean getRevisionSave() {
         ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
           Boolean revisionSave = (Boolean) sessionMap.get("revisionSave");
        return revisionSave;
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

    public Boolean getSpanish() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Boolean spanish = (Boolean) sessionMap.get("spanish");
        return spanish;
    }
    
    
      public void setRepositoryRevisionHistory(Repository repositoryRevisionHistory) {
        this.repositoryRevisionHistory = repositoryRevisionHistory;
    }

    public void setRevisionSave(Boolean revisionSave) {
        this.revisionSave = revisionSave;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSpanish(Boolean spanish) {
        this.spanish = spanish;
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

    public void setRevisionHistoryServices(RevisionHistoryServices revisionHistoryServices) {
        this.revisionHistoryServices = revisionHistoryServices;
    }

    public static class Builder {

        RevisionHistoryServices revisionHistoryServices;
        Repository repositoryRevisionHistory;
        Boolean revisionSave;
        String username;
        Boolean spanish;

        public Builder withRevisionHistoryServices(RevisionHistoryServices revisionHistoryServices) {
            this.revisionHistoryServices = revisionHistoryServices;
            return this;
        }

        public Builder withRepositoryRevisionHistory(Repository repositoryRevisionHistory) {
            this.repositoryRevisionHistory = repositoryRevisionHistory;
            return this;
        }

        public Builder withRevisionSave(Boolean revisionSave) {
            this.revisionSave = revisionSave;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withSpanish(Boolean spanish) {
            this.spanish = spanish;
            return this;
        }

        public JmoordbConfiguration build() {
            return new JmoordbConfiguration(revisionHistoryServices, repositoryRevisionHistory, revisionSave, username, spanish);
        }

    }

}
