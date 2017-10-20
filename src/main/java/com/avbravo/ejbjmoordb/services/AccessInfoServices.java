/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.services;

import com.avbravo.ejbjmoordb.pojos.AccessInfo;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author avbravo
 */
@Stateless
public class AccessInfoServices {
    
/**
 * 
 * @param username
 * @param description
 * @return 
 */
   
/**
 * 
 * @param username
 * @param description
 * @return 
 */
    public AccessInfo generateAccessInfo(String username,String form, String description) {
        AccessInfo accessInfo = new AccessInfo();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            UUID uuid = UUID.randomUUID();
            accessInfo.setIdaccessinfo(uuid.toString());
            accessInfo.setUsername(username);
            accessInfo.setDatetime(date2);
            accessInfo.setForm(form);
            accessInfo.setIp(getIp());
            accessInfo.setDescription(description);

        } catch (Exception e) {
            System.out.println("generateAccessInfo() " + e.getLocalizedMessage());
        }
        return accessInfo;
    }
    
     private String getIp() {
        String myip = "";
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
                
            }
            myip = ipAddress;
        } catch (Exception e) {
            System.out.println("getIp() "+e.getLocalizedMessage());
        }
        return myip;
    }// </editor-fold>

}
