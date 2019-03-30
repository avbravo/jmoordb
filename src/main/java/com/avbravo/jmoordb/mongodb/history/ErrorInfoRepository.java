/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.mongodb.history;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import javax.ejb.Stateless;
import com.avbravo.jmoordb.pojos.ErrorInfo;
 

/**
 *
 * @author avbravo
 */
@Stateless
public class ErrorInfoRepository extends Repository<ErrorInfo> {
    
    public ErrorInfoRepository(){
        super(ErrorInfo.class,"_history");
    }

    public ErrorInfoRepository(Class<ErrorInfo> entityClass, String database, String collection, Boolean... lazy) {
        super(entityClass, database, collection, lazy);
    }

    


}
