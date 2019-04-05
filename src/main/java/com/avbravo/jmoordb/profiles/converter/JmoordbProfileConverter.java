/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.converter;

import com.avbravo.jmoordb.profiles.entity.JmoordbProfile;
import com.avbravo.jmoordb.profiles.repository.JmoordbProfileRepository;
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
public class JmoordbProfileConverter implements Converter {

    @Inject
    JmoordbProfileRepository jmoordbProfileRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        JmoordbProfile jmoordbProfile = new JmoordbProfile();
        try {
            if (!s.equals("null")) {
                JmoordbProfile b = new JmoordbProfile();
                b.setIdprofile(s);
                Optional<JmoordbProfile> optional = jmoordbProfileRepository.findById(b);
                if (optional.isPresent()) {
                    jmoordbProfile = optional.get();
                }
            }
        } catch (Exception e) {
           JmoordbUtil.errorMessage("getAsObject() " + e.getLocalizedMessage());
        }
        return jmoordbProfile;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof JmoordbProfile) {
                JmoordbProfile jmoordbProfile = (JmoordbProfile) o;
                r = String.valueOf(jmoordbProfile.getIdprofile());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getAsString() " + e.getLocalizedMessage());
        }
        return r;
    }

}
