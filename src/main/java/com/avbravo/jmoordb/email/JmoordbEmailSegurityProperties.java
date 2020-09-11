/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.email;

/**
 *
 * @author avbravo
 */
public class JmoordbEmailSegurityProperties {
   Boolean mailSmtpAuth;
   Boolean mailSmtpStarttlsEnable;
   String  mailSmtpHost;
   Integer  mailSmtpPort;

    public JmoordbEmailSegurityProperties() {
    }

    public JmoordbEmailSegurityProperties(Boolean mailSmtpAuth, Boolean mailSmtpStarttlsEnable, String mailSmtpHost, Integer mailSmtpPort) {
        this.mailSmtpAuth = mailSmtpAuth;
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
        this.mailSmtpHost = mailSmtpHost;
        this.mailSmtpPort = mailSmtpPort;
    }

    public Boolean getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(Boolean mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public Boolean getMailSmtpStarttlsEnable() {
        return mailSmtpStarttlsEnable;
    }

    public void setMailSmtpStarttlsEnable(Boolean mailSmtpStarttlsEnable) {
        this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public Integer getMailSmtpPort() {
        return mailSmtpPort;
    }

    public void setMailSmtpPort(Integer mailSmtpPort) {
        this.mailSmtpPort = mailSmtpPort;
    }
   
   
}
