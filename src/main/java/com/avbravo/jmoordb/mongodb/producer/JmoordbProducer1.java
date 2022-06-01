/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.producer;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterSettings;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.singletonList;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author avbravo
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class JmoordbProducer1 {
//@Inject
//    private Config config;
//    @Inject
//    @ConfigProperty(name = "mongodb.uri")
//    private String mongodburi;
//    @Produces
//    @ApplicationScoped
//    public MongoClient mongoClient() {
////        String baseconection = "mongodb://localhost:27017";
////        MongoClient mongo  = MongoClients.create(baseconection);
//System.out.println("*************************************");
//        System.out.println("*");
//        System.out.println("*");
//        System.out.println("*           PRODUCES");
//        System.out.println("*");
//System.out.println("*************************************");
//
//        System.out.println("@Nicroprofile Config ");
//        System.out.println(">>>>mongodb.uri "+mongodburi);
//        MongoClient mongo ; 
//        System.out.println("----- paso 1");
//        try {
//            String uri = JmoordbContext.get("uri") == null ? "" : (String) JmoordbContext.get("uri");
//            System.out.println("----- paso 2");
//            if (uri == null || uri.isEmpty()) {
//                System.out.println("----- paso 3");
//                String username = JmoordbContext.get("username") == null ? "" : (String) JmoordbContext.get("username");
//                String password = JmoordbContext.get("password") == null ? "" : (String) JmoordbContext.get("password");
//                String database = JmoordbContext.get("database") == null ? "" : (String) JmoordbContext.get("database");
//                String host = JmoordbContext.get("host") == null ? "" : (String) JmoordbContext.get("host");
//                String port = JmoordbContext.get("port") == null ? "" : (String) JmoordbContext.get("port");
//                Boolean security = JmoordbContext.get("security") == null ? false : (Boolean) JmoordbContext.get("security");
//                if (security == null && username == null) {
//                    security = false;
//                    System.out.println("----- paso 4");
//                }
//                if (security) {
//                    System.out.println("----- paso 5");
//                     System.out.println("***************************************");
//                      System.out.println("--->conectacndose con Security.....");
//                    MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
//                    Block<ClusterSettings.Builder> localhost = builder -> builder.hosts(singletonList(new ServerAddress(host, Integer.parseInt(port))));
//                    MongoClientSettings settings = MongoClientSettings.builder()
//                            .applyToClusterSettings(localhost)
//                            .credential(credential)
//                            .build();
//                    mongo = MongoClients.create(settings);
//                    return mongo;
//     
//                } else {
//                    System.out.println("----- paso 6");
//                    System.out.println("***************************************");
//                      System.out.println("--->conectacndose a Uri Local");
//                    String mongodburi = "mongodb://" + host + ":" + port;
//                    mongo = MongoClients.create(mongodburi);
//                    return mongo;
//                }
//            } else {
//                System.out.println("----- paso 7");
//                System.out.println("***************************************");
//                System.out.println("--->conectacndose a MongoDB Atlas");
//                 mongo = MongoClients.create(uri);
//                 return mongo;
//            }
//
//        } catch (Exception e) {
//            System.out.println("error() " + e.getLocalizedMessage());
//        }
//
////        return mongo;
//System.out.println("----- paso 8");
//  String mongodburi = "mongodb://localhost:27017";
//                    mongo = MongoClients.create(mongodburi);
//        return null;
//    }
}
