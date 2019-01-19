/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.mongodb.repository;

import com.mongodb.client.MongoDatabase;
import java.lang.reflect.Field;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public interface InterfaceRepository<T> {

    public MongoDatabase getMongoDatabase();

//    public T findById(String key, String value);
//
//    public T findById(String key, Integer value);

    public T find(String key, Object value);

    public T find(Document document);

    public T findFirst(Document... document);

}
