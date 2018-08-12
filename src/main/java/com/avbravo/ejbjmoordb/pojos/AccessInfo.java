/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Id;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author avbravo
 */

public class AccessInfo {
    @Id
    private String idaccessinfo;
    private String username;
    private Date datetime;
    private String form;
    private String ip;
    private String description;

    public AccessInfo() {
    }

    public AccessInfo(String idaccessinfo, String username, Date datetime, String form, String ip, String description) {
        this.idaccessinfo = idaccessinfo;
        this.username = username;
        this.datetime = datetime;
        this.form = form;
        this.ip = ip;
        this.description = description;
    }

    @Override
    public String toString() {
        return "AcessInfo{" + "idaccessinfo=" + idaccessinfo + ", username=" + username + ", datetime=" + datetime + ", form=" + form + ", ip=" + ip + ", description=" + description + '}';
    }

    public String getIdaccessinfo() {
        return idaccessinfo;
    }

    public void setIdaccessinfo(String idaccessinfo) {
        this.idaccessinfo = idaccessinfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
    
    
}
