/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb;

 
 

/**
 *
 * @author avbravo
 */

public class MicroservicesModel {
    private String name;
    private String type;
    private String document;
    private String field;
    private String javatype;
    private String repository;
    private Boolean lazy;
    private String url;
    private String user;
    private String password;

    public MicroservicesModel() {
    }

    @Override
    public String toString() {
        return "ReferencedBeans{" + "name=" + name + ", type=" + type + ", document=" + document + ", field=" + field + ", javatype=" + javatype + ", repository=" + repository + ", lazy=" + lazy + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getJavatype() {
        return javatype;
    }

    public void setJavatype(String javatype) {
        this.javatype = javatype;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   

  
    
    
}
