/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.pojos.RevisionHistory;
 
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class RevisionHistoryRepository extends Repository<RevisionHistory> {

    public RevisionHistoryRepository() {
        super(RevisionHistory.class, "_history");
    }

   
}
