/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.converter;

import com.avbravo.jmoordb.profiles.entity.JmoordbForm;
import com.avbravo.jmoordb.profiles.repository.JmoordbFormRepository;
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
public class JmoordbFormConverter implements Converter {

    @Inject
    JmoordbFormRepository jmoordbFormRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        JmoordbForm jmoordbForm = new JmoordbForm();
        try {
            if (!s.equals("null")) {
                JmoordbForm b = new JmoordbForm();
                b.setIdform(s);
                Optional<JmoordbForm> optional = jmoordbFormRepository.findById(b);
                if (optional.isPresent()) {
                    jmoordbForm = optional.get();
                }
            }
        } catch (Exception e) {
           JmoordbUtil.errorMessage("getAsObject() " + e.getLocalizedMessage());
        }
        return jmoordbForm;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof JmoordbForm) {
                JmoordbForm jmoordbForm = (JmoordbForm) o;
                r = String.valueOf(jmoordbForm.getIdform());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getAsString() " + e.getLocalizedMessage());
        }
        return r;
    }

}
