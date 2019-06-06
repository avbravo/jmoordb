/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.repository;

import javax.ejb.Stateless;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.pojos.JmoordbNotifications;
 


/**
 *
 * @author avbravo
 */
@Stateless
public class JmoordbNotificationsRepository extends Repository<JmoordbNotifications> {
  
    public JmoordbNotificationsRepository() {
        super(JmoordbNotifications.class);
    }

}
