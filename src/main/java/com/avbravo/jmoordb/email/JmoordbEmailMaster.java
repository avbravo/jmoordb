/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.email;

import com.avbravo.jmoordb.anotations.Id;

/**
 *
 * @author avbravo
 */
public class JmoordbEmailMaster {

    @Id
    private String email;
    private String password;
    private String mail_smtp_host;
    private String mail_smtp_auth;
    private String mail_smtp_port;
    private String mail_smtp_starttls_enable;
    private Boolean active;

    public JmoordbEmailMaster() {
    }

    public JmoordbEmailMaster(String email, String password, String mail_smtp_host, String mail_smtp_auth, String mail_smtp_port, String mail_smtp_starttls_enable, Boolean active) {
        this.email = email;
        this.password = password;
        this.mail_smtp_host = mail_smtp_host;
        this.mail_smtp_auth = mail_smtp_auth;
        this.mail_smtp_port = mail_smtp_port;
        this.mail_smtp_starttls_enable = mail_smtp_starttls_enable;
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    


    
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail_smtp_host() {
        return mail_smtp_host;
    }

    public void setMail_smtp_host(String mail_smtp_host) {
        this.mail_smtp_host = mail_smtp_host;
    }

    public String getMail_smtp_auth() {
        return mail_smtp_auth;
    }

    public void setMail_smtp_auth(String mail_smtp_auth) {
        this.mail_smtp_auth = mail_smtp_auth;
    }

    public String getMail_smtp_port() {
        return mail_smtp_port;
    }

    public void setMail_smtp_port(String mail_smtp_port) {
        this.mail_smtp_port = mail_smtp_port;
    }

    public String getMail_smtp_starttls_enable() {
        return mail_smtp_starttls_enable;
    }

    public void setMail_smtp_starttls_enable(String mail_smtp_starttls_enable) {
        this.mail_smtp_starttls_enable = mail_smtp_starttls_enable;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JmoordbEmailMaster)) {
            return false;
        }
      JmoordbEmailMaster other = (JmoordbEmailMaster) object;
        if ((this.email== null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }
    
}
