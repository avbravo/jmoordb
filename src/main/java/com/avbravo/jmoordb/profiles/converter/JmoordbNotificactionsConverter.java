/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.profiles.converter;

import com.avbravo.jmoordb.pojos.JmoordbNotifications;
import com.avbravo.jmoordb.profiles.repository.JmoordbNotificationsRepository;
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
public class JmoordbNotificactionsConverter implements Converter {

    @Inject
    JmoordbNotificationsRepository jmoordbNotificationsRepository;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        JmoordbNotifications jmoordbNotifications = new JmoordbNotifications();
        try {
            if (!s.equals("null")) {
                JmoordbNotifications b = new JmoordbNotifications();
                b.setIdjoordbnotifications(Integer.parseInt(s));
                Optional<JmoordbNotifications> optional = jmoordbNotificationsRepository.findById(b);
                if (optional.isPresent()) {
                    jmoordbNotifications = optional.get();
                }
            }
        } catch (Exception e) {
           JmoordbUtil.errorMessage("getAsObject() " + e.getLocalizedMessage());
        }
        return jmoordbNotifications;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof JmoordbNotifications) {
                JmoordbNotifications jmoordbNotifications = (JmoordbNotifications) o;
                r = String.valueOf(jmoordbNotifications.getIdjoordbnotifications());
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getAsString() " + e.getLocalizedMessage());
        }
        return r;
    }

}
