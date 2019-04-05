/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class JmoordbForm {
    @Id
    private String idform;
    private String description;
    private String active;  
    @Embedded
    private List<JmoordbProfile> access;
    @Embedded
    private List<JmoordbProfile> list;
    @Embedded
    private List<JmoordbProfile> create;
    @Embedded
    private List<JmoordbProfile> edit;
    @Embedded
    private List<JmoordbProfile> delete;

    public JmoordbForm() {
    }

    public JmoordbForm(String idform, String description, String active, List<JmoordbProfile> access, List<JmoordbProfile> list, List<JmoordbProfile> create, List<JmoordbProfile> edit, List<JmoordbProfile> delete) {
        this.idform = idform;
        this.description = description;
        this.active = active;
        this.access = access;
        this.list = list;
        this.create = create;
        this.edit = edit;
        this.delete = delete;
    }

    public String getIdform() {
        return idform;
    }

    public void setIdform(String idform) {
        this.idform = idform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<JmoordbProfile> getAccess() {
        return access;
    }

    public void setAccess(List<JmoordbProfile> access) {
        this.access = access;
    }

    public List<JmoordbProfile> getList() {
        return list;
    }

    public void setList(List<JmoordbProfile> list) {
        this.list = list;
    }

    public List<JmoordbProfile> getCreate() {
        return create;
    }

    public void setCreate(List<JmoordbProfile> create) {
        this.create = create;
    }

    public List<JmoordbProfile> getEdit() {
        return edit;
    }

    public void setEdit(List<JmoordbProfile> edit) {
        this.edit = edit;
    }

    public List<JmoordbProfile> getDelete() {
        return delete;
    }

    public void setDelete(List<JmoordbProfile> delete) {
        this.delete = delete;
    }
    
    
    
}
