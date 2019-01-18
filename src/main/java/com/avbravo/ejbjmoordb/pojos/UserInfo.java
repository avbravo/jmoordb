/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Id;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class UserInfo {

    @Id
    private String iduserinfo;
    private String username;
    private Date datetime;
    private String description;

    public UserInfo() {
    }

    public UserInfo(String iduserinfo, String username, Date datetime, String description) {
        this.iduserinfo = iduserinfo;
        this.username = username;
        this.datetime = datetime;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Userinfo{" + "username=" + username + ", datetime=" + datetime + ", description=" + description + '}';
    }

    public String getIduserinfo() {
        return iduserinfo;
    }

    public void setIduserinfo(String iduserinfo) {
        this.iduserinfo = iduserinfo;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Builder {

        private String iduserinfo;
        private String username;
        private Date datetime;
        private String description;

        public Builder withIduserinfo(String iduserinfo) {
            this.iduserinfo = iduserinfo;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder datetime(Date datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public UserInfo build() {
            return new UserInfo(iduserinfo, username, datetime, description);
        }

    }

}
