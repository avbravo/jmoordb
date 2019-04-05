/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.repository;

import com.avbravo.jmoordb.profiles.entity.JmoordbForm;
import javax.ejb.Stateless;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.pojos.Autoincrementable;
 


/**
 *
 * @author avbravo
 */
@Stateless
public class JmoordbFormRepository extends Repository<JmoordbForm> {
  
    public JmoordbFormRepository() {
        super(JmoordbForm.class);
    }

}
