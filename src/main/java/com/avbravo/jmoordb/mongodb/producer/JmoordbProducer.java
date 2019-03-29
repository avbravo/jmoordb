/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.producer;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

/**
 *
 * @author avbravo
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class JmoordbProducer {

    @Produces
    @ApplicationScoped
    public MongoClient mongoClient() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        try {
            String username = (String) JmoordbContext.get("username");
            String password = (String) JmoordbContext.get("password");
            String database = (String) JmoordbContext.get("database");
            String host = (String) JmoordbContext.get("host");
            String port = (String) JmoordbContext.get("port");
            Boolean security = (Boolean) JmoordbContext.get("security");
            if (security == null && username == null) {
                security = false;
            }
            if (security) {
 
            char[] charArray = password.toCharArray(); 
            MongoCredential credential = MongoCredential.createCredential(username, database, charArray);
            ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
//             mongo = new MongoClient(serverAddress, new ArrayList<>());
            
             mongo = new MongoClient(serverAddress, new ArrayList<MongoCredential>() {{ add(credential); }});
//           
            }else{
                mongo = new MongoClient();
            }

        } catch (Exception e) {
            JmoordbUtil.errorMessage("coneecction() "+e.getLocalizedMessage());
        }

        return mongo;
    }
}
