/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.converter;



import com.avbravo.jmoordb.pojos.JmoordbEmailMaster;
import com.avbravo.jmoordb.profiles.repository.JmoordbEmailMasterRepository;

import com.avbravo.jmoordb.util.JmoordbUtil;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped
public class JmoordbEmailMasterConverter implements Converter {

    @Inject
JmoordbEmailMasterRepository jmoordbEmailMasterRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        JmoordbEmailMaster emailMaster = new JmoordbEmailMaster();
        try {
            if (!s.equals("null")) {
                JmoordbEmailMaster b = new JmoordbEmailMaster();
                b.setEmail(s);
                Optional<JmoordbEmailMaster> optional = jmoordbEmailMasterRepository.findById(b);
                if (optional.isPresent()) {
                    emailMaster = optional.get();
                }
            }
        } catch (Exception e) {
             JmoordbUtil.errorMessage("getAsObject() " + e.getLocalizedMessage());
        }
        return emailMaster;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof JmoordbEmailMaster) {
                JmoordbEmailMaster emailMaster = (JmoordbEmailMaster) o;
                r = String.valueOf(emailMaster.getEmail());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
   JmoordbUtil.errorMessage("getAsString() " + e.getLocalizedMessage());
        }
        return r;
    }

}
