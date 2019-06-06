/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.pojos;

import com.avbravo.jmoordb.anotations.Id;
import java.util.Date;

/**
 *Se usa con los websocket para notificaciones con los usuarios
 * @author avbravo
 */
public class JmoordbNotifications {
 @Id   
 private Integer idjoordbnotifications;
 private String username;
 private String message;
 private String type;
 private Date date;
 private String viewed;

    public JmoordbNotifications() {
    }

    public JmoordbNotifications(Integer idjoordbnotifications, String username, String message, String type, Date date, String viewed) {
        this.idjoordbnotifications = idjoordbnotifications;
        this.username = username;
        this.message = message;
        this.type = type;
        this.date = date;
        this.viewed = viewed;
    }

   
 
 
 
    public Integer getIdjoordbnotifications() {
        return idjoordbnotifications;
    }

    public void setIdjoordbnotifications(Integer idjoordbnotifications) {
        this.idjoordbnotifications = idjoordbnotifications;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }

   
}
