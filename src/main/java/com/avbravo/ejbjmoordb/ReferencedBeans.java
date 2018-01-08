/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author avbravo
 */
@Getter
@Setter
public class ReferencedBeans {
    private String name;
    private String type;
    private String document;
    private String field;
    private String javatype;
    private String repository;
    private Boolean lazy;

    public ReferencedBeans() {
    }

    @Override
    public String toString() {
        return "ReferencedBeans{" + "name=" + name + ", type=" + type + ", document=" + document + ", field=" + field + ", javatype=" + javatype + ", repository=" + repository + ", lazy=" + lazy + '}';
    }

   

  
    
    
}
