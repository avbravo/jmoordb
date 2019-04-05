/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.mongodb.history.repository;
import javax.ejb.Stateless;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.mongodb.history.entity.AccessInfo;
 


/**
 *
 * @author avbravo
 */
@Stateless
public class AccessInfoRepository extends Repository<AccessInfo> {

    public AccessInfoRepository(){
        super(AccessInfo.class,"_history");
    }


}
