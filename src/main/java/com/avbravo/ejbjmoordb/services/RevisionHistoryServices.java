/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.services;

import com.avbravo.ejbjmoordb.pojos.RevisionHistory;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class RevisionHistoryServices {

    public RevisionHistory getRevisionhistory(String id, String username, String operation, String document, String content) {
        RevisionHistory revisionhistory = new RevisionHistory();
        try {
            UUID uuid = UUID.randomUUID();

            revisionhistory.setIdrevisionhistory(uuid.toString());
            revisionhistory.setId(id);
            revisionhistory.setDocument(document);

            revisionhistory.setDescription(operation);
            revisionhistory.setContent(content);
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
        UUID uuiduser = UUID.randomUUID();
            UserInfo userinfo = new UserInfo(uuiduser.toString(),username, date2, operation);
            revisionhistory.setUserInfo(userinfo);

        } catch (Exception e) {
            System.out.println("getRevisionhistory() " + e.getLocalizedMessage());
        }
        return revisionhistory;
    }

}
