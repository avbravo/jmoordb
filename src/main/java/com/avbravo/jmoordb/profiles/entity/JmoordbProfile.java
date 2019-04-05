/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.entity;

import com.avbravo.jmoordb.anotations.Id;

/**
 *
 * @author avbravo
 */
public class JmoordbProfile {
    @Id
    private String idprofile;
    private String profile;
    private String active;

    public JmoordbProfile() {
    }

    public JmoordbProfile(String idprofile, String profile, String active) {
        this.idprofile = idprofile;
        this.profile = profile;
        this.active = active;
    }

    
    
    
    public String getIdprofile() {
        return idprofile;
    }

    public void setIdprofile(String idprofile) {
        this.idprofile = idprofile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
    
    
}
