/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.services;

import com.avbravo.ejbjmoordb.pojos.Userinfo;
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
public class UserinfoServices {
/**
 * 
 * @param username
 * @param description
 * @return 
 */
    public List<Userinfo> generateListUserinfo(String username, String description) {
        List<Userinfo> listUserinfo = new ArrayList<>();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());

            listUserinfo.add(new Userinfo(username, date2, description));
        } catch (Exception e) {
            System.out.println("generateListUserinfo() " + e.getLocalizedMessage());
        }
        return listUserinfo;
    }
/**
 * 
 * @param username
 * @param description
 * @return 
 */
    public Userinfo generateUserinfo(String username, String description) {
        Userinfo userinfo = new Userinfo();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            userinfo.setUsername(username);
            userinfo.setDatetime(date2);
            userinfo.setDescription(description);

        } catch (Exception e) {
            System.out.println("generateUserinfo() " + e.getLocalizedMessage());
        }
        return userinfo;
    }

}
