/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.producer;

//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
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
public class JmoordbProducer {
@Inject
    private Config config;
    @Inject
    @ConfigProperty(name = "mongodb.uri")
    private String mongodburi;
    @Produces
    @ApplicationScoped
    public MongoClient mongoClient() {
          
            MongoClient mongoClient = MongoClients.create(mongodburi);
            System.out.println("@Produces :{Connected successfully to server.}");
        return mongoClient;
     
    }
}
