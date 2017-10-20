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
@Getter
@Setter
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
    
    
}
