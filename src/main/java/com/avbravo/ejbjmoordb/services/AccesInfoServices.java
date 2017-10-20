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
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class AccesInfoServices {
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
    public AccessInfo generateAccessInfo(String username,String form,String ip, String description) {
        AccessInfo accessInfo = new AccessInfo();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            accessInfo.setUsername(username);
            accessInfo.setDatetime(date2);
            accessInfo.setForm(form);
            accessInfo.setIp(ip);
            accessInfo.setDescription(description);

        } catch (Exception e) {
            System.out.println("generateAccessInfo() " + e.getLocalizedMessage());
        }
        return accessInfo;
    }

}
