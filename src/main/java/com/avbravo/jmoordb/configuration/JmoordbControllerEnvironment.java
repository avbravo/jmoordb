/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.configuration;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author avbravo
 */
public class JmoordbControllerEnvironment {

    Repository repository;
    String nameFieldOfPage;
    String nameFieldOfRowPage;
    Object entity;
    Object service;
    Object controller;
    Boolean searchbysecondarykey;
    String pathReportDetail;
    String pathReportAll;
    HashMap parameters = new HashMap();
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = externalContext.getSessionMap();

    public JmoordbControllerEnvironment() {
    }

    public JmoordbControllerEnvironment(Object controller, Repository repository, Object entity, Object service, String nameFieldOfPage, String nameFieldOfRowPage, Boolean searchbysecondarykey,
            String pathReportDetail, String pathReportAll, HashMap parameters) {
        this.repository = repository;
        this.nameFieldOfPage = nameFieldOfPage;
        this.nameFieldOfRowPage = nameFieldOfRowPage;
        this.entity = entity;
        this.service = service;
        this.controller = controller;
        this.searchbysecondarykey = searchbysecondarykey;
        this.pathReportDetail = pathReportDetail;
        this.pathReportAll = pathReportAll;
        this.parameters = parameters;

        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("repository", repository);
        sessionMap.put("service", service);
        sessionMap.put("entity", entity);
        sessionMap.put("controller", controller);
        sessionMap.put("nameFieldOfPage", nameFieldOfPage);
        sessionMap.put("nameFieldOfRowPage", nameFieldOfRowPage);
        sessionMap.put("searchbysecondarykey", searchbysecondarykey);
        sessionMap.put("pathReportDetail", pathReportDetail);
        sessionMap.put("pathReportAll", pathReportAll);
        sessionMap.put("parameters", parameters);
    }

    public HashMap getParameters() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        HashMap parameters = (HashMap) sessionMap.get("parameters");
        return parameters;
    }

    public String getPathReportAll() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String pathReportAll = (String) sessionMap.get("pathReportAll");
        return pathReportAll;
    }

    public String getPathReportDetail() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String pathReportDetail = (String) sessionMap.get("pathReportDetail");
        return pathReportDetail;
    }

    public Repository getRepository() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Repository repository = (Repository) sessionMap.get("repository");

        return repository;
    }

    public Integer getPage() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Integer nameFieldOfPage = (Integer) sessionMap.get("nameFieldOfPage");

        return nameFieldOfPage;
    }

    public Integer getRowPage() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Integer nameFieldOfRowPage = (Integer) sessionMap.get("nameFieldOfRowPage");
        return nameFieldOfRowPage;
    }

    public Object getEntity() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Object entity = (Object) sessionMap.get("entity");
        return entity;
    }

    public Object getService() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Object service = (Object) sessionMap.get("service");
        return service;
    }

    public Object getController() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Object controller = (Object) sessionMap.get("controller");
        return controller;
    }

    public String getNameFieldOfPage() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String nameFieldOfPage = (String) sessionMap.get("nameFieldOfPage");
        return nameFieldOfPage;
    }

    public String getNameFieldOfRowPage() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        String nameFieldOfRowPage = (String) sessionMap.get("nameFieldOfRowPage");
        return nameFieldOfRowPage;
    }

    public Boolean getSearchbysecondarykey() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Boolean searchbysecondarykey = (Boolean) sessionMap.get("searchbysecondarykey");
        return searchbysecondarykey;
    }

    public void setPathReportDetail(String pathReportDetail) {
        this.pathReportDetail = pathReportDetail;
    }

    public void setPathReportAll(String pathReportAll) {
        this.pathReportAll = pathReportAll;
    }

    public void setParameters(HashMap parameters) {
        this.parameters = parameters;
    }

    public void setSearchbysecondarykey(Boolean searchbysecondarykey) {
        this.searchbysecondarykey = searchbysecondarykey;
    }

    public void setNameFieldOfRowPage(String nameFieldOfRowPage) {
        this.nameFieldOfRowPage = nameFieldOfRowPage;
    }

    public void setNameFieldOfPage(String nameFieldOfPage) {
        this.nameFieldOfPage = nameFieldOfPage;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public void setRowPage(String nameFieldOfRowPage) {
        this.nameFieldOfRowPage = nameFieldOfRowPage;
    }

    public void setPage(String nameFieldOfPage) {
        this.nameFieldOfPage = nameFieldOfPage;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
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

        Repository repository;
        String nameFieldOfPage;
        String nameFieldOfRowPage;
        Object entity;
        Object service;
        Object controller;
        Boolean searchbysecondarykey;
        String pathReportDetail;
        String pathReportAll;
        HashMap parameters = new HashMap();

        
          public Builder withPathReportDetail(String pathReportDetail) {
            this.pathReportDetail = pathReportDetail;
            return this;
        }
          public Builder withPathReportAll(String pathReportAll) {
            this.pathReportAll = pathReportAll;
            return this;
        }
          public Builder withparameters(HashMap parameters ) {
            this.parameters = parameters;
            return this;
        }
          
        public Builder withRepository(Repository repository) {
            this.repository = repository;
            return this;
        }

        public Builder withNameFieldOfPage(String nameFieldOfPage) {
            this.nameFieldOfPage = nameFieldOfPage;
            return this;
        }

        public Builder withNameFieldOfRowPage(String nameFieldOfRowPage) {
            this.nameFieldOfRowPage = nameFieldOfRowPage;
            return this;
        }

        public Builder withEntity(Object entity) {
            this.entity = entity;
            return this;
        }

        public Builder withService(Object service) {
            this.service = service;
            return this;
        }

        public Builder withController(Object controller) {
            this.controller = controller;
            return this;
        }

        public Builder withSearchbysecondarykey(Boolean searchbysecondarykey) {
            this.searchbysecondarykey = searchbysecondarykey;
            return this;
        }

        public JmoordbControllerEnvironment build() {
            return new JmoordbControllerEnvironment(controller, repository, entity, service, nameFieldOfPage, nameFieldOfRowPage, searchbysecondarykey, pathReportDetail,  pathReportAll,  parameters);
        }

    }

}
