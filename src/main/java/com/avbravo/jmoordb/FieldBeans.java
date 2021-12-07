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
 
 
public class FieldBeans {
    private String name;
    private String type;
    private Boolean isKey;
    private Boolean isEmbedded;
    private Boolean isReferenced;
    private Boolean isMicroservices;
    private Boolean isSecondary;
    private Boolean isTertiary;
    private Boolean isComposite;

    public FieldBeans() {
    }

   

    public FieldBeans(String name, String type, Boolean isKey, Boolean isEmbedded, Boolean isReferenced,Boolean isMicroservices, Boolean isSecondary, Boolean isTertiary, Boolean isComposite) {
        this.name = name;
        this.type = type;
        this.isKey = isKey;
        this.isEmbedded = isEmbedded;
        this.isReferenced = isReferenced;
        this.isMicroservices = isMicroservices;
        this.isSecondary = isSecondary;
        this.isTertiary = isTertiary;
        this.isComposite = isComposite;
    }

    public Boolean getIsMicroservices() {
        return isMicroservices;
    }

    public void setIsMicroservices(Boolean isMicroservices) {
        this.isMicroservices = isMicroservices;
    }

    
    
    
    
    public Boolean getIsTertiary() {
        return isTertiary;
    }

    public void setIsTertiary(Boolean isTertiary) {
        this.isTertiary = isTertiary;
    }

    public Boolean getIsComposite() {
        return isComposite;
    }

    public void setIsComposite(Boolean isComposite) {
        this.isComposite = isComposite;
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

    public Boolean getIsKey() {
        return isKey;
    }

    public void setIsKey(Boolean isKey) {
        this.isKey = isKey;
    }

    public Boolean getIsEmbedded() {
        return isEmbedded;
    }

    public void setIsEmbedded(Boolean isEmbedded) {
        this.isEmbedded = isEmbedded;
    }

    public Boolean getIsReferenced() {
        return isReferenced;
    }

    public void setIsReferenced(Boolean isReferenced) {
        this.isReferenced = isReferenced;
    }

    public Boolean getIsSecondary() {
        return isSecondary;
    }

    public void setIsSecondary(Boolean isSecondary) {
        this.isSecondary = isSecondary;
    }
    
    
    
    
}
