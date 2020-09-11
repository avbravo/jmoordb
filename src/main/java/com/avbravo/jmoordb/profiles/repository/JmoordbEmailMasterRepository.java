/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.profiles.repository;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.email.JmoordbEmailMaster;

import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class JmoordbEmailMasterRepository extends Repository<JmoordbEmailMaster> {

    public JmoordbEmailMasterRepository(){
        super(JmoordbEmailMaster.class);
    }
   

}
