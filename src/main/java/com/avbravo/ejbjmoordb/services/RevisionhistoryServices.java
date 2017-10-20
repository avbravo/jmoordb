/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.services;

import com.avbravo.ejbjmoordb.pojos.Revisionhistory;
import com.avbravo.ejbjmoordb.pojos.Userinfo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author avbravo
 */
@Stateless
public class RevisionhistoryServices {

    public Revisionhistory getRevisionhistory(String id, String username, String operation, String document, String content) {
        Revisionhistory revisionhistory = new Revisionhistory();
        try {
            UUID uuid = UUID.randomUUID();

            revisionhistory.setIdrevisionhistory(uuid.toString().toLowerCase());
            revisionhistory.setId(id);
            revisionhistory.setDocument(document);

            revisionhistory.setDescription(operation);
            revisionhistory.setContent(content);
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            Userinfo userinfo = new Userinfo(username, date2, operation);
            revisionhistory.setUserinfo(userinfo);

        } catch (Exception e) {
            System.out.println("getRevisionhistory() " + e.getLocalizedMessage());
        }
        return revisionhistory;
    }

}
