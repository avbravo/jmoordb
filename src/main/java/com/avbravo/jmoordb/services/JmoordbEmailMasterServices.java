/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.services;


import com.avbravo.jmoordb.pojos.JmoordbEmailMaster;
import com.avbravo.jmoordb.profiles.repository.JmoordbEmailMasterRepository;
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
public class JmoordbEmailMasterServices {

    @Inject
    JmoordbEmailMasterRepository repository;

    List<JmoordbEmailMaster> emailMasterList = new ArrayList<>();

    public List<JmoordbEmailMaster> complete(String query) {
        List<JmoordbEmailMaster> suggestions = new ArrayList<>();
           try {
          suggestions=repository.complete(query);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<JmoordbEmailMaster> getCorreoMasterList() {
        try {
            emailMasterList = repository.findAll(new Document("emailMaster", 1));
        } catch (Exception e) {
              JmoordbUtil.errorMessage("getCorreoMasterList() " + e.getLocalizedMessage());
        }

        return emailMasterList;
    }

    public void setCorreoMasterList(List<JmoordbEmailMaster> emailMasterList) {
        this.emailMasterList = emailMasterList;
    }
    
        // <editor-fold defaultstate="collapsed" desc="isDeleted(CorreoMaster emailMaster)">
  
    public Boolean isDeleted(JmoordbEmailMaster emailMaster){
        Boolean found=false;
        try {
         
            
        } catch (Exception e) {
        JmoordbUtil.errorMessage("isDeleted() " + e.getLocalizedMessage());
        }
        return true;
    }  // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="findById(Integer id)">

    public JmoordbEmailMaster findById(String id){
           JmoordbEmailMaster emailMaster = new JmoordbEmailMaster();
        try {
         
            emailMaster.setEmail(id);
            Optional<JmoordbEmailMaster> optional = repository.findById(emailMaster);
            if (optional.isPresent()) {
               return optional.get();
            } 
        } catch (Exception e) {
          JmoordbUtil.errorMessage("findById() " + e.getLocalizedMessage());
        }
      
      return emailMaster;
    }
    // </editor-fold>

}
