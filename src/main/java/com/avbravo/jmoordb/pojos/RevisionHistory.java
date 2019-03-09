/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.pojos;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;

/**
 *
 * @author avbravo
 */
public class RevisionHistory {

    @Id
    private String idrevisionhistory;
    private String document;
    private String id;
    private String description;
    private String content;
    @Embedded
    private UserInfo userInfo;

    public RevisionHistory() {
    }

    public RevisionHistory(String idrevisionhistory, String document, String id, String description, String content) {
        this.idrevisionhistory = idrevisionhistory;
        this.document = document;
        this.id = id;
        this.description = description;
        this.content = content;
    }

    public RevisionHistory(String idrevisionhistory, String document, String id, String description, String content, UserInfo userInfo) {
        this.idrevisionhistory = idrevisionhistory;
        this.document = document;
        this.id = id;
        this.description = description;
        this.content = content;
        this.userInfo = userInfo;
    }
    
    
    

    @Override
    public String toString() {
        return "RevisionHistory{" + "idrevisionhistory=" + idrevisionhistory + ", document=" + document + ", id=" + id + ", description=" + description + ", content=" + content + '}';
    }

    public String getIdrevisionhistory() {
        return idrevisionhistory;
    }

    public void setIdrevisionhistory(String idrevisionhistory) {
        this.idrevisionhistory = idrevisionhistory;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static class Builder {

        private String idrevisionhistory;
        private String document;
        private String id;
        private String description;
        private String content;
        @Embedded
        private UserInfo userInfo;

        public Builder withUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Builder withIdrevisionhistory(String idrevisionhistory) {
            this.idrevisionhistory = idrevisionhistory;
            return this;
        }
        public Builder withDocument(String document) {
            this.document = document;
            return this;
        }
        public Builder withId(String id) {
            this.id =  id;
            return this;
        }
        public Builder withDescription(String description) {
            this.description=  description;
            return this;
        }
        public Builder withContent(String content) {
            this.content=  content;
            return this;
        }

       

        public RevisionHistory build() {
            return new RevisionHistory(idrevisionhistory, document, id, description, content, userInfo);
        }

    }

}
