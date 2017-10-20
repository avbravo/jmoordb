/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author avbravo
 */
@Getter
@Setter

public class Revisionhistory {

    @Id
    private String idrevisionhistory;
    private String document;
    private String id;
    private String description;
    private String content;
    @Embedded
    private Userinfo userinfo;

    public Revisionhistory() {
    }

    public Revisionhistory(String idrevisionhistory, String document, String id, String description, String content) {
        this.idrevisionhistory = idrevisionhistory;
        this.document = document;
        this.id = id;
        this.description = description;
        this.content = content;
    }

    @Override
    public String toString() {
        return "RevisionHistory{" + "idrevisionhistory=" + idrevisionhistory + ", document=" + document + ", id=" + id + ", description=" + description + ", content=" + content + '}';
    }

}
