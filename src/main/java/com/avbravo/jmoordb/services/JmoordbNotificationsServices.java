/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.services;


import com.avbravo.jmoordb.pojos.JmoordbNotifications;
import com.avbravo.jmoordb.profiles.repository.JmoordbNotificationsRepository;
import com.avbravo.jmoordb.util.JmoordbUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class JmoordbNotificationsServices {

    @Inject
    JmoordbNotificationsRepository repository;

    List<JmoordbNotifications> jmoordbnotificationsList = new ArrayList<>();

    public List<JmoordbNotifications> complete(String query) {
        List<JmoordbNotifications> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<JmoordbNotifications> getJmoordbnotificationsList() {
        try {
            jmoordbnotificationsList = repository.findAll(new Document("username", 1));
        } catch (Exception e) {
              JmoordbUtil.errorMessage("getJmoordbnotificationsList() " + e.getLocalizedMessage());
        }

        return jmoordbnotificationsList;
    }

    public void setCorreoMasterList(List<JmoordbNotifications> jmoordbnotificationsList) {
        this.jmoordbnotificationsList = jmoordbnotificationsList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(CorreoMaster jmoordbnotifications)">
  
    public Boolean isDeleted(JmoordbNotifications jmoordbnotifications){
        Boolean found=false;
        try {
         
            
        } catch (Exception e) {
        JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public JmoordbNotifications findById(Integer id){
           JmoordbNotifications jmoordbNotifications = new JmoordbNotifications();
        try {
         
            jmoordbNotifications.setIdjoordbnotifications(id);
            Optional<JmoordbNotifications> optional = repository.findById(jmoordbNotifications);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
          JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return jmoordbNotifications;
    }
    // </editor-fold>

}
