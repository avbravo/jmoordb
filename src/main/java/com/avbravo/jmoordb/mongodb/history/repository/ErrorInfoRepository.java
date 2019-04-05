/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.mongodb.history.repository;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import javax.ejb.Stateless;
import com.avbravo.jmoordb.mongodb.history.entity.ErrorInfo;
 

/**
 *
 * @author avbravo
 */
@Stateless
public class ErrorInfoRepository extends Repository<ErrorInfo> {
    
    public ErrorInfoRepository(){
        super(ErrorInfo.class,"_history");
    }



    


}
