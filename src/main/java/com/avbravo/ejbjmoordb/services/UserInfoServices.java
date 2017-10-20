/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.services;

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
public class UserInfoServices {
/**
 * 
 * @param username
 * @param description
 * @return 
 */
    public List<UserInfo> generateListUserinfo(String username, String description) {
        List<UserInfo> listUserinfo = new ArrayList<>();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());

            listUserinfo.add(new UserInfo(username, date2, description));
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
    public UserInfo generateUserinfo(String username, String description) {
        UserInfo userinfo = new UserInfo();
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
