/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.repository;
// <editor-fold defaultstate="collapsed" desc="import">

import com.avbravo.jmoordb.CompositeKey;
import com.avbravo.jmoordb.DatePatternBeans;
import com.avbravo.jmoordb.EmbeddedModel;
import com.avbravo.jmoordb.FieldBeans;
import com.avbravo.jmoordb.JmoordbException;
import com.avbravo.jmoordb.JmoordbStatistics;
import com.avbravo.jmoordb.MicroservicesModel;
import com.avbravo.jmoordb.PrimaryKey;
import com.avbravo.jmoordb.ReferencedModel;
import com.avbravo.jmoordb.SecondaryKey;
import com.avbravo.jmoordb.TertiaryKey;
import com.avbravo.jmoordb.metafactory.JmoordbLambdaMetaFactory;
import com.avbravo.jmoordb.mongodb.internal.DocumentToJavaJmoordbResult;
import com.avbravo.jmoordb.mongodb.internal.DocumentToJavaMongoDB;
import com.avbravo.jmoordb.mongodb.internal.JavaToDocument;
import com.avbravo.jmoordb.pojos.JmoordbResult;
import com.avbravo.jmoordb.pojos.UserInfo;
import com.avbravo.jmoordb.query.Query;
import com.avbravo.jmoordb.query.Sort;
import com.avbravo.jmoordb.query.Sorter;
import com.avbravo.jmoordb.util.Analizador;
import com.avbravo.jmoordb.util.JmoordbDateUtil;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.github.vincentrussell.query.mongodb.sql.converter.MongoDBQueryHolder;
import com.github.vincentrussell.query.mongodb.sql.converter.QueryConverter;
import com.mongodb.CursorType;
import com.mongodb.Function;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoCredential;

//import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Indexes.descending;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
// </editor-fold>

/**
 *
 * @author avbravo
 * @param <T>
 */
public abstract class Repository<T> implements InterfaceRepository {
// <editor-fold defaultstate="collapsed" desc="field">
//invoca el @JmoordbProducer que tiene un metodo MongoClient mongoClient

    @Inject
    MongoClient mongoClient;

    Integer contador = 0;
    Document querySorter = new Document();
    Document querySearch = new Document();
    /*
    limite de documentos que puede traer un findAll()
    para evitar la sobrecarga
     */
    Integer limitOfDocumentInFindAllMethod = 4000;

    private JavaToDocument javaToDocument = new JavaToDocument();
    private DocumentToJavaMongoDB documentToJava = new DocumentToJavaMongoDB();
    private DocumentToJavaJmoordbResult documentToJavaJmoordbResult = new DocumentToJavaJmoordbResult();
    T t1, tlocal;
    private Class<T> entityClass;
    private String database;
    private String collection;
    private Boolean haveElements = false;
    //lazy load
    private Boolean lazy;
    List<T> list = new ArrayList<>();
    List<PrimaryKey> primaryKeyList = new ArrayList<>();
    List<SecondaryKey> secondaryKeyList = new ArrayList<>();
    List<TertiaryKey> tertiaryKeyList = new ArrayList<>();
    List<CompositeKey> compositeKeyList = new ArrayList<>();

    List<EmbeddedModel> embeddedModelList = new ArrayList<>();
    List<ReferencedModel> referencedModelList = new ArrayList<>();
    List<MicroservicesModel> microservicesModelList = new ArrayList<>();
    List<DatePatternBeans> datePatternBeansList = new ArrayList<>();
    List<FieldBeans> fieldBeansList = new ArrayList<>();
    Exception exception;
    JmoordbUtil util = new JmoordbUtil();
    // </editor-fold>
//MongoDatabase db_;
// <editor-fold defaultstate="collapsed" desc="get/set">

    public List<MicroservicesModel> getMicroservicesModelList() {
        return microservicesModelList;
    }

    public void setMicroservicesModelList(List<MicroservicesModel> microservicesModelList) {
        this.microservicesModelList = microservicesModelList;
    }

   
    
    
    public Document getQuerySorter() {
        return querySorter;
    }

    public void setQuerySorter(Document querySorter) {
        this.querySorter = querySorter;
    }

    public Document getQuerySearch() {
        return querySearch;
    }

    public void setQuerySearch(Document querySearch) {
        this.querySearch = querySearch;
    }

    
    
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Integer getLimitOfDocumentInFindAllMethod() {
        return limitOfDocumentInFindAllMethod;
    }

    public void setLimitOfDocumentInFindAllMethod(Integer limitOfDocumentInFindAllMethod) {
        this.limitOfDocumentInFindAllMethod = limitOfDocumentInFindAllMethod;
    }

    public List<TertiaryKey> getTertiaryKeyList() {
        return tertiaryKeyList;
    }

    public void setTertiaryKeyList(List<TertiaryKey> tertiaryKeyList) {
        this.tertiaryKeyList = tertiaryKeyList;
    }

    public List<CompositeKey> getCompositeKeyList() {
        return compositeKeyList;
    }

    public void setCompositeKeyList(List<CompositeKey> compositeKeyList) {
        this.compositeKeyList = compositeKeyList;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<SecondaryKey> getSecondaryKeyList() {
        return secondaryKeyList;
    }

    public void setSecondaryKeyList(List<SecondaryKey> secondaryKeyList) {
        this.secondaryKeyList = secondaryKeyList;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getMongoDatabase()">
    @Override
    public MongoDatabase getMongoDatabase() {
        try {

            MongoDatabase db = mongoClient.getDatabase(database);
            if (db == null) {
                ////Test.msg("+++AbstractFacade.getMonogDatabase() == null");
            } else {
                ////Test.msg("+++AbstractFacade.getMonogDatabase() != null");
            }
            return db;
        } catch (Exception ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            new JmoordbException("getMongoDatabase() " + ex.getLocalizedMessage());
            exception = new Exception("getMongoDatabase() " + ex.getLocalizedMessage());
            // JmoordbUtil.errorDialog("getMongoDatabase() ", ex.getLocalizedMessage());

        }
        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Repository(Class<T> entityClass, String database, String collection, Boolean... lazy)">
    public Repository(Class<T> entityClass, String database, String collection, Boolean... lazy) {

        this.entityClass = entityClass;

        this.database = database;
        this.collection = collection;
        Boolean l = false;
        if (lazy.length != 0) {
            l = lazy[0];

        }
        this.lazy = l;

        primaryKeyList = new ArrayList<>();
        secondaryKeyList = new ArrayList<>();
        tertiaryKeyList = new ArrayList<>();
        compositeKeyList = new ArrayList<>();
        embeddedModelList = new ArrayList<>();
        referencedModelList = new ArrayList<>();
       microservicesModelList= new ArrayList<>(); 
        datePatternBeansList = new ArrayList<>();
        fieldBeansList = new ArrayList<>();

        /**
         * lee las anotaciones @Id para obtener los PrimaryKey del documento
         */
        final Field[] fields = entityClass.getDeclaredFields();
        Analizador analizador = new Analizador();
        analizador.analizar(fields);
        primaryKeyList = analizador.getPrimaryKeyList();
        secondaryKeyList = analizador.getSecondaryKeyList();
        tertiaryKeyList = analizador.getTertiaryKeyList();
        compositeKeyList = analizador.getCompositeKeyList();
        embeddedModelList = analizador.getEmbeddedBeansList();
        referencedModelList = analizador.getReferencedBeansList();
        microservicesModelList = analizador.getMicroservicesModelList();
        datePatternBeansList = analizador.getDatePatternBeansList();
        fieldBeansList = analizador.getFieldBeansList();

        //Llave primary
        if (primaryKeyList.isEmpty()) {
            exception = new Exception("No have primaryKey ");
            // JmoordbUtil.errorDialog("Warning", "No have primaryKey in " + entityClass.getName());

        } else {

            if (primaryKeyList.size() > 1) {
                exception = new Exception("the entity has more than one primary key @ID ");
                // JmoordbUtil.errorDialog("Warning", "the entity has more than one primary key @ID ");

            }
        }
        if (fieldBeansList.isEmpty()) {
            exception = new Exception("No have fields ");
            // JmoordbUtil.errorDialog("Warning", "No have fields ");
        }

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Repository(Class<T> entityClass, Boolean.. lazy)">

    /**
     * toma el nombre de la base de datos de la configuracion inicial toma el
     * nombre de la coleccion en base al nombre del entity
     *
     * @param entityClass
     * @param lazy
     */
    public Repository(Class<T> entityClass, Boolean... lazy) {
        String database = "";
        if (FacesContext.getCurrentInstance() != null) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            database = (String) sessionMap.get("database");
            if (database == null) {
                database = "";
            }
        }

        this.entityClass = entityClass;

        String collection = this.entityClass.getName().toLowerCase().trim();
        collection = JmoordbUtil.nombreEntity(collection);
        this.database = database;
        this.collection = collection;
        Boolean l = false;
        if (lazy.length != 0) {
            l = lazy[0];

        }
        this.lazy = l;

        primaryKeyList = new ArrayList<>();
        secondaryKeyList = new ArrayList<>();
        tertiaryKeyList = new ArrayList<>();
        compositeKeyList = new ArrayList<>();
        embeddedModelList = new ArrayList<>();
        referencedModelList = new ArrayList<>();
        microservicesModelList = new ArrayList<>();
        datePatternBeansList = new ArrayList<>();
        fieldBeansList = new ArrayList<>();

        /**
         * lee las anotaciones @Id para obtener los PrimaryKey del documento
         */
        final Field[] fields = entityClass.getDeclaredFields();
        Analizador analizador = new Analizador();
        analizador.analizar(fields);
        primaryKeyList = analizador.getPrimaryKeyList();
        secondaryKeyList = analizador.getSecondaryKeyList();
        tertiaryKeyList = analizador.getTertiaryKeyList();
        compositeKeyList = analizador.getCompositeKeyList();
        embeddedModelList = analizador.getEmbeddedBeansList();
        referencedModelList = analizador.getReferencedBeansList();
        microservicesModelList = analizador.getMicroservicesModelList();
        datePatternBeansList = analizador.getDatePatternBeansList();
        fieldBeansList = analizador.getFieldBeansList();

        //Llave primary
        if (primaryKeyList.isEmpty()) {
            exception = new Exception("No have primaryKey ");

        } else {

            if (primaryKeyList.size() > 1) {
                exception = new Exception("the entity has more than one primary key @ID ");

            }
        }
        if (fieldBeansList.isEmpty()) {
            exception = new Exception("No have fields ");
        }

    }// </editor-fold>

    /**
     * .
     * @param entityClass
     * @param internalDatabaseHistoryAcronimo : se invocainternamente
     * generalmente se le pasa el valor de _history
     */
    public Repository(Class<T> entityClass, String internalDatabaseHistoryAcronimo, Boolean... lazy) {
        String database = "";
        if (internalDatabaseHistoryAcronimo == null || internalDatabaseHistoryAcronimo.equals("")) {
            internalDatabaseHistoryAcronimo = "_history";
        }
        if (FacesContext.getCurrentInstance() != null) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            database = (String) sessionMap.get("database");
            if (database == null) {
                database = "";
            } else {
                database = database + internalDatabaseHistoryAcronimo;
            }
        }

        this.entityClass = entityClass;

        String collection = this.entityClass.getName().toLowerCase().trim();
        collection = JmoordbUtil.nombreEntity(collection);
        this.database = database;
        this.collection = collection;
        Boolean l = false;
        if (lazy.length != 0) {
            l = lazy[0];

        }
        this.lazy = l;

        primaryKeyList = new ArrayList<>();
        secondaryKeyList = new ArrayList<>();
        tertiaryKeyList = new ArrayList<>();
        compositeKeyList = new ArrayList<>();
        embeddedModelList = new ArrayList<>();
        referencedModelList = new ArrayList<>();
        microservicesModelList = new ArrayList<>();
        datePatternBeansList = new ArrayList<>();
        fieldBeansList = new ArrayList<>();

        /**
         * lee las anotaciones @Id para obtener los PrimaryKey del documento
         */
        final Field[] fields = entityClass.getDeclaredFields();
        Analizador analizador = new Analizador();
        analizador.analizar(fields);
        primaryKeyList = analizador.getPrimaryKeyList();
        secondaryKeyList = analizador.getSecondaryKeyList();
        tertiaryKeyList = analizador.getTertiaryKeyList();
        compositeKeyList = analizador.getCompositeKeyList();
        embeddedModelList = analizador.getEmbeddedBeansList();
        referencedModelList = analizador.getReferencedBeansList();
       microservicesModelList = analizador.getMicroservicesModelList();
        datePatternBeansList = analizador.getDatePatternBeansList();
        fieldBeansList = analizador.getFieldBeansList();

        //Llave primary
        if (primaryKeyList.isEmpty()) {
            exception = new Exception("No have primaryKey ");

        } else {

            if (primaryKeyList.size() > 1) {
                exception = new Exception("the entity has more than one primary key @ID ");

            }
        }
        if (fieldBeansList.isEmpty()) {
            exception = new Exception("No have fields ");
        }

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="save(T t, Boolean... verifyID)">
    /**
     *
     * @param t
     * @param verifyID
     * @return
     */
    public Boolean save(T t, Boolean... verifyID) {
        try {
            Boolean verificate = true;
            if (verifyID.length != 0) {
                verificate = verifyID[0];

            }
            if (verificate) {
                //Buscar llave primaria

                T t_ = (T) findInternal(findDocPrimaryKey(t));

                if (t_ == null) {
                    // no lo encontro
                } else {
                    exception = new Exception("A document with the primary key already exists.");
                    return false;
                }
            }

            getMongoDatabase().getCollection(collection).insertOne(toDocument(t));

            return true;

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="save(Document doc, Boolean... verifyID) ">
    /**
     *
     * @param doc
     * @param verifyID
     * @return
     */
    public Boolean save(Document doc, Boolean... verifyID) {
        try {
            Boolean verificate = true;
            if (verifyID.length != 0) {
                verificate = verifyID[0];

            }
            if (verificate) {
                //Buscar llave primaria

                t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedModelList, referencedModelList, microservicesModelList);
                T t_ = (T) findInternal(findDocPrimaryKey(t1));

                if (t_ == null) {
                    // no lo encontro
                } else {
                    exception = new Exception("A document with the primary key already exists.");
                    return false;
                }
            }

            getMongoDatabase().getCollection(collection).insertOne(doc);

            return true;

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Document toDocument(Object t)">
    /**
     *
     * @param t2
     * @return devuelve el Pojo convertido a documento.
     */
    public Document toDocument(Object t) {
        return javaToDocument.toDocument(t, embeddedModelList, referencedModelList);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Document findDocPrimaryKey(T t2)">
    /**
     *
     * @return Document() correspondiente a la llave primaria
     */
    private Document findDocPrimaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Document getIndexPrimaryKey()">
    /**
     * Crea un Index en base a la llave primaria
     *
     * @return
     */
    private Document getIndexPrimaryKey() {
        Document doc = new Document();
        try {
            primaryKeyList.forEach((p) -> {
                doc.put(p.getName(), 1);
            });
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Document getIndexSecondaryKey()">

    /**
     * Crea un Index en base a las llaves secundarias
     *
     * @return
     */
    private Document getIndexSecondaryKey() {
        Document doc = new Document();
        try {
            secondaryKeyList.forEach((p) -> {
                doc.put(p.getName(), 1);
            });
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Document getIndexTertiaryKey()">
    /**
     * Crea un Index en base a las llaves tertiary
     *
     * @return
     */
    private Document getIndexTertiaryKey() {
        Document doc = new Document();
        try {
            tertiaryKeyList.forEach((p) -> {
                doc.put(p.getName(), 1);
            });
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Document getIndexCompositeKey()">
    /**
     * Crea un Index en base a las llaves tertiary
     *
     * @return
     */
    private Document getIndexCompositeKey() {
        Document doc = new Document();
        try {
            compositeKeyList.forEach((p) -> {
                doc.put(p.getName(), 1);
            });
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createIndex(Document... doc)">
    /**
     *
     * @param doc
     * @return
     */
    public Boolean createIndex(Document... doc) {
        Document docIndex = new Document();
        try {
            if (doc.length != 0) {
                docIndex = doc[0];

            } else {
                docIndex = getIndexPrimaryKey();
            }
            getMongoDatabase().getCollection(collection).createIndex(docIndex);
            return true;
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(T t2)">
    /**
     * Busca por la llave primaria del documento
     *
     * @param t2
     * @return
     */
    public Optional<T> findById(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {
                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                    return find(doc);
                 } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String primaryKeyValue(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public String primaryKeyValue(T t2) {

        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }

                    //    doc.put(p.getName(), method.invoke(t2));
                } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean primaryKeyIsInteger(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Boolean primaryKeyIsInteger(T t2) {

        Boolean value = false;
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {

                if (p.getType().equals("java.lang.String")) {
                    value = false;
                } else {
                    value = true;
                }

                //    doc.put(p.getName(), method.invoke(t2));
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer primaryKeyValueInteger(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Integer primaryKeyValueInteger(T t2) {

        Integer value = 0;
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        exception = new Exception("La llave primaria no es de tipo Integer ");
                    } else {
                        value = Integer.parseInt(String.valueOf(v));
                    }

                    //    doc.put(p.getName(), method.invoke(t2));
                } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fieldsOfBean()">
    /**
     *
     * @return
     */
    public List<FieldBeans> fieldsOfBean() {
        return fieldBeansList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Map<String,String> primaryKey(T t2) ">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Map<String, String> primaryKey(T t2) {
        HashMap<String, String> map = new HashMap<String, String>();
        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }
                    map.put(p.getName(), value);

                } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return map;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String primaryKeyName(T t2)">

    /**
     * Devuelve el nombre del campo @ID llave primaria
     *
     * @param t2
     * @return
     */
    public String primaryKeyName(T t2) {

        String name = "";
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                name = util.letterToLower(p.getName());

            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return name;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Map<String, String> compositeKey(T t2) ">
    /**
     * devuelve la lista de compositeKey con las llaves compuestas,name, value
     *
     * @return
     */
    public Map<String, String> compositeKey(T t2) {
        HashMap<String, String> map = new HashMap<String, String>();
        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (CompositeKey p : compositeKeyList) {
                String name = util.letterToLower(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }
                    map.put(name, value);

                 } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return map;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findById(Document doc)">
    public Optional<T> findById(Document doc) {

        try {
            //  t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedModelList, referencedModelList);
            T t_ = (T) find(doc);

            if (t_ == null) {
                // no lo encontro
            } else {
                return Optional.of(t_);
            }

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isFound(Document doc)">
    /**
     * busca si existe o no por la
     *
     * @param doc
     * @return
     */
    public Boolean isFound(Document doc) {

        try {
            //  t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedModelList, referencedModelList);
            T t_ = (T) find(doc);

            if (t_ == null) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isFoundByPrimaryKey(T t2)">
    /**
     * Busca por la llave secundaria del documento
     *
     * @param t2
     * @return
     */
    public Boolean isFoundByPrimaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                    T t_ = (T) find(doc);
                    if (t_ == null) {
                        return false;
                    } else {
                        return true;
                    }
               } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(String sql)">
    public Optional<T> findById(String sql) {
        Document sortQuery = new Document();

        Document doc = new Document();
        try {
            QueryConverter queryConverter = new QueryConverter(sql);
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            String collection = mongoDBQueryHolder.getCollection();
            doc = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            if (sql.toLowerCase().indexOf("order by") != -1) {
                sortQuery = mongoDBQueryHolder.getSort();
            }

            //  t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedModelList, referencedModelList);
            T t_ = (T) find(doc);

            if (t_ == null) {
                // no lo encontro
            } else {
                return Optional.of(t_);
            }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="find(String key, Object value">
    @Override
    public Optional<T> find(String key, Object value) {
        try {

            //   Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(new Document(key, value));

           

            Consumer<Document> printConsumer = document
                    -> tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList);

            db.getCollection(collection).find(new Document(key, value)).forEach(printConsumer);

 
            if (tlocal != null) {

                return Optional.of(tlocal);
            }
            return Optional.empty();
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return Optional.empty();
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="find(Document document) ">

    /**
     *
     * @param document
     * @return
     */
    @Override
    public Optional<T> find(Document document) {
        try {
            //   Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(document);
            tlocal = (T) iterableSimple(iterable);
            if (tlocal == null) {
                return Optional.empty();
            }
            return Optional.of(tlocal);
            //return (T) tlocal;
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
//        return null;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="find(Bson filter) ">

    /**
     * Filtra en base a un Filter eq("activo","si")
     *
     * @param filter
     * @return
     */
    public Optional<T> find(Bson filter) {
        try {
            //   Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(filter);
            tlocal = (T) iterableSimple(iterable);
            return Optional.of(tlocal);
            //return (T) tlocal;
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
//        return null;
    }
// </editor-fold>
    
    
    
    

    // <editor-fold defaultstate="collapsed" desc="T search(String key, Object value)">
    public T search(String key, Object value) {
        try {

            MongoDatabase db = mongoClient.getDatabase(database);

            if (db == null) {
                return null;
            } else {
            }
            Consumer<Document> printConsumer = document
                    -> tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList);

            db.getCollection(collection).find(new Document(key, value)).forEach(printConsumer);

            if (tlocal != null) {
                return tlocal;
            }
            return null;

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return null;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T search(String key, Integer value)">

    public T search(String key, Integer value) {
        try {

            MongoDatabase db = mongoClient.getDatabase(database);

            if (db == null) {
                return null;
            } else {
            }
             Consumer<Document> printConsumer = document
                    -> tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList);

            db.getCollection(collection).find(new Document(key, value)).forEach(printConsumer);
          
            if (tlocal != null) {
                return tlocal;
            }
            return null;

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return null;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T search(String key, String value)">

    public T search(String key, String value) {
        try {

            MongoDatabase db = mongoClient.getDatabase(database);

            if (db == null) {
                return null;
            } else {
            }

            Consumer<Document> printConsumer = document
                    -> tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList);

            db.getCollection(collection).find(new Document(key, value)).forEach(printConsumer);
            if (tlocal != null) {
                return tlocal;
            }
            return null;

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return null;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findFirst(Document... doc) ">
    /**
     * Devuelve el primer documento de la coleccion
     *
     * @param document
     * @return
     */
    @Override
    public Optional<T> findFirst(Document... doc) {
        try {
            Document document = new Document();
            MongoDatabase db = mongoClient.getDatabase(database);
            if (doc.length != 0) {
                document = doc[0];

                FindIterable<Document> iterable = db.getCollection(collection).find(document).limit(1);
                tlocal = (T) iterableSimple(iterable);
                return Optional.of(tlocal);
            } else {

                FindIterable<Document> iterable = db.getCollection(collection).find().limit(1);
                tlocal = (T) iterableSimple(iterable);
                return Optional.of(tlocal);
            }

            //return (T) tlocal;
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findFirst(Document... doc) ">

    /**
     * Devuelve el primer documento de la coleccion
     *
     * @param document
     * @return
     */
    public Optional<T> findFirst(String sql) {
        try {
            Document doc = new Document();
            Document sortQuery = new Document();
            QueryConverter queryConverter = new QueryConverter(sql);
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            String collection = mongoDBQueryHolder.getCollection();
            doc = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            if (sql.toLowerCase().indexOf("order by") != -1) {
                sortQuery = mongoDBQueryHolder.getSort();
            }
            MongoDatabase db = mongoClient.getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(doc).limit(1);
            tlocal = (T) iterableSimple(iterable);
            return Optional.of(tlocal);

            //return (T) tlocal;
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findInternal(Document document)">
    private T findInternal(Document document) {
        try {
            //   Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(document);
            tlocal = (T) iterableSimple(iterable);
            return tlocal;
            //return (T) tlocal;
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T iterableSimple(FindIterable<Document> iterable)">
    /**
     * Internamente recorre el iterable
     *
     * @param iterable
     * @return
     */
    private T iterableSimple(FindIterable<Document> iterable) {
         try {
          
            haveElements = false;

            Consumer<Document> printConsumer = document
                    -> tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList);

            iterable.forEach(printConsumer);

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        if (haveElements) {
            return (T) t1;
        }
        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="iterableList(FindIterable<Document> iterable)">
    /**
     * 
     * @param iterable
     * @return  List<T>
     */
    private List< T> iterableList(FindIterable<Document> iterable) {
        List< T> l = new ArrayList<>();
        try {
            //Test.msg("-->IterableList::");

            Consumer<Document> printConsumer = document
                    -> l.add((T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList));

            iterable.forEach(printConsumer);


        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return l;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="iterableList(FindIterable<Document> iterable)">

    private List< T> processAggregateIterable(AggregateIterable<Document> iterable) {
       List< T> l = new ArrayList<>();
        try {
              Consumer<Document> printConsumer = document
                    -> l.add((T) documentToJava.fromDocument(entityClass, document, embeddedModelList, referencedModelList, microservicesModelList));

            iterable.forEach(printConsumer);
        

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return l;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="processAggregateIterableJmoordbResult(AggregateIterable<Document> iterable)">

    private List< JmoordbResult> processAggregateIterableJmoordbResult(AggregateIterable<Document> iterable) {
         List<JmoordbResult> l = new ArrayList<>();
        List<Map<String, Object>> lObject = new ArrayList<>();
        try {
              Consumer<Document> printConsumer = document -> 
                               lObject.add(new HashMap<>(document));
                   
                    
            
iterable.forEach(printConsumer);

           
            for (Map m : lObject) {
                JmoordbResult jmoordbResult = new JmoordbResult();
                for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();

                    jmoordbResult.put(entry.getKey(), entry.getValue().toString());

                }
                l.add(jmoordbResult);
            }

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return l;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sizeOfPage(Integer rowsForPage, Document... doc)">
    /**
     * Devuele el numero de paginas en una coleccion
     *
     * @param rowsForPage
     * @param doc
     * @return
     */
    public Integer sizeOfPage(Integer rowsForPage, Document... doc) {
        Integer size = 0;
        Integer mod = 0;
        try {
            size = count(doc) / rowsForPage;
            mod = count(doc) % rowsForPage;
            if (mod > 0) {
                size++;
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return size;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public List<Integer> listOfPage(Integer rowsForPage)">
    /**
     * Devuele una lista de numeros correspondientes a las paginas
     *
     * @param rowsForPage
     * @param doc
     * @return
     */
    public List<Integer> listOfPage(Integer rowsForPage) {
        List<Integer> pages = new ArrayList<>();
        try {

            Integer size = sizeOfPage(rowsForPage);
            for (int i = 1; i <= size; i++) {
                pages.add(new Integer(i));

            }
            return pages;

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return pages;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="count(Document... doc)">
    /**
     *
     * @param doc
     * @return el numero de documentos en la coleccion
     */
    public Integer count(Document... doc) {
         try {
            contador = 0;
            Document documento = new Document();
            if (doc.length != 0) {
                documento = doc[0];
                //nuev a version ejecuta el count con documento como filtro
                contador = (int) mongoClient.getDatabase(database).getCollection(collection).countDocuments(documento);

            } else {

                contador = (int) mongoClient.getDatabase(database).getCollection(collection).countDocuments();

            }

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return contador;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="count(Document... doc)">

    /**
     *
     * @param doc
     * @return el numero de documentos en la coleccion
     */
     public Integer count(Bson filter) {
        try {
            contador = 0;
            //Nueva version cuenta directamente con el filtro
            contador = (int) mongoClient.getDatabase(database).getCollection(collection).countDocuments(filter);

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return contador;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findAll(Document... docSort) ">
    /**
     *
     * @param document
     * @return
     */
    public List< T> findAll(Document... docSort) {
       //Test.msg("---->findAll()::");
        list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
   
            Integer size = count();
            if (size > limitOfDocumentInFindAllMethod) {
                // JmoordbUtil.warningDialog("findAll()", "Existen " + size + " documentos mejor use findPagination() en lugar de findAll(). Se devolveran los primeros " + limitOfDocumentInFindAllMethod);
                exception = new Exception("findAll() " + "Existen " + size + " documentos mejor use findPagination() en lugar de findAll(). Se devolveran los primeros " + limitOfDocumentInFindAllMethod);
                
                list = findPagination(1, limitOfDocumentInFindAllMethod, sortQuery);
             
                return list;
            }

            MongoDatabase db = mongoClient.getDatabase(database);
         
            FindIterable<Document> iterable = db.getCollection(collection).find().sort(sortQuery);
         
            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findPagination(Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * Busca con paginacion en una coleccion
     *
     * @param document
     * @return
     */
    public List< T> findPagination(Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find().skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            
      
            list = iterableList(iterable);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findPagination(Document filter, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * Busca con paginacion en una coleccion con filtro
     *
     * @param document
     * @return
     */
    public List< T> findPagination(Document filter, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(filter).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findPagination(String sql, Integer pageNumber, Integer rowsForPage)">
    /**
     * Busca con paginacion en una coleccion con filtro
     *
     * @param document
     * @return
     */
    public List< T> findPagination(String sql, Integer pageNumber, Integer rowsForPage) {
        list = new ArrayList<>();
        Document sortQuery = new Document();

        Document doc = new Document();
        try {

            QueryConverter queryConverter = new QueryConverter(sql);
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            String collection = mongoDBQueryHolder.getCollection();
            doc = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            if (sql.toLowerCase().indexOf("order by") != -1) {
                sortQuery = mongoDBQueryHolder.getSort();
            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(doc).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findOneAndUpdate(String key, String value, String field, Integer... incremento) ">
    /**
     *
     * @param key
     * @param value
     * @param field
     * @return
     */
    public T findOneAndUpdate(String key, String value, String field, Integer... incremento) {

        try {
            Integer increment = 1;
            if (incremento.length != 0) {
                increment = incremento[0];

            }
            Document doc = new Document(key, value);
            Document inc = new Document("$inc", new Document(field, increment));

            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
            findOneAndUpdateOptions.upsert(true);

            findOneAndUpdateOptions.returnDocument(ReturnDocument.AFTER);

            Object t = entityClass.newInstance();

            MongoDatabase db = mongoClient.getDatabase(database);
            Document iterable = db.getCollection(collection).findOneAndUpdate(doc, inc, findOneAndUpdateOptions);

            try {

                t1 = (T) documentToJava.fromDocument(entityClass, iterable, embeddedModelList, referencedModelList, microservicesModelList);

           } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return t1;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findOneAndUpdate(Document doc, String field, Integer... incremento)">
    /**
     * findOneAndUpdate
     *
     * @param doc
     * @param field
     * @param incremento
     * @return
     */
    public T findOneAndUpdate(Document doc, String field, Integer... incremento) {
        try {
            Integer increment = 1;
            if (incremento.length != 0) {
                increment = incremento[0];

            }

            Document inc = new Document("$inc", new Document(field, increment));

            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
            findOneAndUpdateOptions.upsert(true);

            findOneAndUpdateOptions.returnDocument(ReturnDocument.AFTER);

            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            Document iterable = db.getCollection(collection).findOneAndUpdate(doc, inc, findOneAndUpdateOptions);

            try {
                t1 = (T) documentToJava.fromDocument(entityClass, iterable, embeddedModelList, referencedModelList, microservicesModelList);
//                Method method = entityClass.getDeclaredMethod("toPojo", Document.class);
//                list.add((T) method.invoke(t, iterable));
             } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findOneAndUpdate(Document doc, Document inc, Integer... incremento)">
    /**
     *
     * @param doc
     * @param inc
     * @param incremento
     * @return
     */
    public T findOneAndUpdate(Document doc, Document inc, Integer... incremento) {
        try {

            FindOneAndUpdateOptions findOneAndUpdateOptions = new FindOneAndUpdateOptions();
            findOneAndUpdateOptions.upsert(true);

            findOneAndUpdateOptions.returnDocument(ReturnDocument.AFTER);

            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            Document iterable = db.getCollection(collection).findOneAndUpdate(doc, inc, findOneAndUpdateOptions);

            try {
                t1 = (T) documentToJava.fromDocument(entityClass, iterable, embeddedModelList, referencedModelList, microservicesModelList);

           } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return t1;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" findBy(Document doc, Document... docSort)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<T> findBy(Document doc, Document... docSort) {
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" List<T> shell(String shell, Document... docSort)">
   /**
    * 
    * @param shell
    * @param docSort
    * @return 
    */
    public List<T> shell(String shell, Document... docSort) {
        Document sortQuery = new Document();
        try {
            Document doc= jsonToDocument(shell);
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" findBy(Bson doc, Document... docSort)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<T> findBy(Bson builder, Document... docSort) {
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(builder).sort(sortQuery);
            list = iterableList(iterable);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" findBy(String sql)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<T> findBy(String sql) {
        Document sortQuery = new Document();
        Document doc = new Document();
        try {

            list = new ArrayList<>();
            QueryConverter queryConverter = new QueryConverter(sql);
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            String collection = mongoDBQueryHolder.getCollection();
            doc = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            if (sql.toLowerCase().indexOf("order by") != -1) {
                sortQuery = mongoDBQueryHolder.getSort();
            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="aggregate(List<Document> documentList)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<T> aggregateToEntity(List<Document> documentList) {
//    public List<U extends Number> aggregate(List<Document> documentList) {      
        try {
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(documentList);

            list = processAggregateIterable(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="aggregate(List<Document> documentList)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<T> aggregateToEntityBson(List<Bson> documentList) {
//    public List<U extends Number> aggregate(List<Document> documentList) {      
        try {
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(documentList);

            list = processAggregateIterable(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="aggregateJmoordbResult(List<Document> documentList)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<JmoordbResult> aggregateFromDocument(List<Document> documentList) {
//    public List<U extends Number> aggregate(List<Document> documentList) {      
        List<JmoordbResult> list = new ArrayList<>();
        try {
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(documentList);
            list = processAggregateIterableJmoordbResult(iterable);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="aggregateJmoordbResult(List<Document> documentList)">
    /**
     *
     * @param doc
     * @param docSort
     * @return
     */
    public List<JmoordbResult> aggregateFromBuilder(List<Bson> builder) {
//    public List<U extends Number> aggregate(List<Document> documentList) {      
        List<JmoordbResult> list = new ArrayList<>();
        try {
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(builder);
            list = processAggregateIterableJmoordbResult(iterable);

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="findBy(String key, Object value, Document... docSort)">
    public List<T> findBy(String key, Object value, Document... docSort) {
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            list = new ArrayList<>();
            Document doc = new Document(key, value);
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filters(Bson filter, Document... docSort)">
    /**
     *
     * @param filter
     * @param docSort
     * @return
     */
    public List<T> filters(Bson filter, Document... docSort) {
        Document sortQuery = new Document();
        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(filter).sort(sortQuery);
            list = iterableList(iterable);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filtersPagination(Bson filter, Integer pageNumber, Integer rowsForPage, Document... docSort">

    /**
     *
     * @param filter
     * @param docSort
     * @return
     */
    public List<T> filtersPagination(Bson filter, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(filter).sort(sortQuery);
            list = iterableList(iterable);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="listCollecctions()">
    /**
     * devuelva la lista de colecciones
     *
     * @return
     */
    public List<String> listCollecctions() {
        List<String> list = new ArrayList<>();
        try {
            for (Document name : getMongoDatabase().listCollections()) {
                list.add(name.get("name").toString());
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="existsCollection(String nameCollection)">
    /**
     * verifica si existe una coleccion
     *
     * @param collection
     * @return
     */
    public Boolean existsCollection(String nameCollection) {
        try {
            Boolean found = false;
            for (String s : listCollecctions()) {
                if (s.equals(nameCollection)) {
                    return true;
                }
            }

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="createCollection(String nameCollection)">
    /**
     * createCollection
     *
     * @param nameCollection
     * @return
     */
    public Boolean createCollection(String nameCollection) {
        try {
            getMongoDatabase().createCollection(nameCollection);
            return true;
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="drop()">
    /**
     * elimina la coleccion actual
     *
     * @return
     */
    public Boolean drop() {

        try {
            if (existsCollection(collection)) {
                getMongoDatabase().getCollection(collection).drop();
                return true;
            }

            return false;

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="drop(String collection)">

    /**
     * elimina la coleccion que se indiquem como parametro
     *
     * @param collection
     * @return
     */
    public Boolean drop(String collection) {

        try {
            getMongoDatabase().getCollection(collection).drop();

            return true;

      } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean dropDatabase()o">
    /**
     *
     */
    public Boolean dropDatabase() {

        try {
            getMongoDatabase().drop();

            return true;

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<T> findHelperSort(String predicate, Document doc, String key, String value)">
    public List<T> findHelperSort(String predicate, Document doc, String key, String value) {
        try {

            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = getIterable();
            switch (predicate) {
                case "ascending":
                    iterable = db.getCollection(collection).find(doc).sort(ascending(key, value));
                    break;
                case "descending":
                    iterable = db.getCollection(collection).find(doc).sort(descending(key, value));
                    break;

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> findHelperSortPagination(String predicate, Document doc, String key, String value, Integer pageNumber, Integer rowsForPage)">

    public List<T> findHelperSortPagination(String predicate, Document doc, String key, String value, Integer pageNumber, Integer rowsForPage) {
        try {

            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = getIterable();
            switch (predicate) {
                case "ascending":
                    iterable = db.getCollection(collection).find(doc).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                            limit(rowsForPage).sort(ascending(key, value));
                    break;
                case "descending":
                    iterable = db.getCollection(collection).find(doc).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                            limit(rowsForPage).sort(descending(key, value));
                    break;

            }

            list = iterableList(iterable);

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<T> helpers(String predicate, String key, Object value, Document... docSort)">
    /**
     *
     * @param predicate eq,gt.lt
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> helpers(String predicate, String key, Object value, Document... docSort) {
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = getIterable();
            switch (predicate) {
                case "eq":
                    iterable = db.getCollection(collection).find(eq(key, value)).sort(sortQuery);
                    break;
                case "lt":
                    iterable = db.getCollection(collection).find(lt(key, value)).sort(sortQuery);
                    break;
                case "gt":
                    iterable = db.getCollection(collection).find(gt(key, value)).sort(sortQuery);
                    break;
            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> helpersPagination(String predicate, String key, Object value, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     *
     * @param predicate eq,gt.lt
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> helpersPagination(String predicate, String key, Object value, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = getIterable();
            switch (predicate) {
                case "eq":
                    iterable = db.getCollection(collection).find(eq(key, value)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                            limit(rowsForPage).sort(sortQuery);
                    break;
                case "lt":
                    iterable = db.getCollection(collection).find(lt(key, value)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                            limit(rowsForPage).sort(sortQuery);
                    break;
                case "gt":
                    iterable = db.getCollection(collection).find(gt(key, value)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                            limit(rowsForPage).sort(sortQuery);
                    break;
            }

            list = iterableList(iterable);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FindIterable<Document> getIterable()">
    private FindIterable<Document> getIterable() {
       FindIterable<Document> iterable = new FindIterable<Document>() {
            @Override
            public FindIterable<Document> filter(Bson bson) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> limit(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> skip(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> maxTime(long l, TimeUnit tu) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> maxAwaitTime(long l, TimeUnit tu) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

           
            @Override
            public FindIterable<Document> projection(Bson bson) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> sort(Bson bson) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> noCursorTimeout(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> oplogReplay(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> partial(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> cursorType(CursorType ct) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> batchSize(int i) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public MongoCursor<Document> iterator() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Document first() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public <U> MongoIterable<U> map(Function<Document, U> fnctn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

         

            @Override
            public <A extends Collection<? super Document>> A into(A a) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> collation(Collation cltn) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> comment(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> hint(Bson bson) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> max(Bson bson) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> min(Bson bson) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

          
          

            @Override
            public FindIterable<Document> returnKey(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public FindIterable<Document> showRecordId(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

          
            @Override
            public FindIterable<Document> hintString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public MongoCursor<Document> cursor() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        return iterable;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="delete(String key, Object value)">
    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean delete(String key, Object value) {
        try {
            Document doc = new Document(key, value);
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteOne(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean delete(T t) ">

    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean delete(T t) {
        try {
            T t_ = (T) findInternal(findDocPrimaryKey(t));

            if (t_ == null) {
                // no lo encontro
            } else {
                exception = new Exception("A document with the primary key already exists.");
                return false;
            }

            Document doc = new Document();

            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteOne(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean delete(Document doc)">
    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean delete(Document doc) {
        try {
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteOne(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean delete(String sql)">

    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean delete(String sql) {
        try {
            Document doc = new Document();
            Document sortQuery = new Document();
            QueryConverter queryConverter = new QueryConverter(sql);
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            String collection = mongoDBQueryHolder.getCollection();
            doc = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            if (sql.toLowerCase().indexOf("order by") != -1) {
                sortQuery = mongoDBQueryHolder.getSort();
            }

            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteOne(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="deleteMany(String key, Object value)">
    /**
     *
     * @param doc
     * @return
     */
    public Integer deleteMany(String key, Object value) {
        Integer cont = 0;
        try {
            Document doc = new Document(key, value);
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteMany(doc);
            cont = (int) dr.getDeletedCount();
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return cont;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer deleteMany(Document doc)">
    /**
     *
     * @param doc
     * @return
     */
    public Integer deleteMany(Document doc) {
        Integer cont = 0;
        try {
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteMany(doc);
            cont = (int) dr.getDeletedCount();
        } catch (Exception e) {
           
            
            
           
            Logger.getLogger(Repository.class.getName() + "deleteManye()").log(Level.SEVERE, null, e);
            exception = new Exception("deleteMany() ", e);
        }
        return cont;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Integer deleteMany(Document doc)">

    /**
     *
     * @param doc
     * @return
     */
    public Integer deleteMany(String sql) {
        Integer cont = 0;
        Document sortQuery = new Document();
        Document doc = new Document();
        try {
            QueryConverter queryConverter = new QueryConverter(sql);
            MongoDBQueryHolder mongoDBQueryHolder = queryConverter.getMongoQuery();
            String collection = mongoDBQueryHolder.getCollection();
            doc = mongoDBQueryHolder.getQuery();
            Document projection = mongoDBQueryHolder.getProjection();
            if (sql.toLowerCase().indexOf("order by") != -1) {
                sortQuery = mongoDBQueryHolder.getSort();
            }
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteMany(doc);
            cont = (int) dr.getDeletedCount();
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return cont;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Integer deleteAll()">

    /**
     * Remove all document of a collection
     *
     * @return count of document delete
     */
    public Integer deleteAll() {
        Integer cont = 0;
        try {
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteMany(new Document());

            cont = (int) dr.getDeletedCount();
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return cont;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="update(T t)">
    public Boolean update(T t) {

        Integer n = update(t, new Document("$set", toDocument(t)));
        if (n >= 1) {
            return true;
        } else {
            return false;
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer update(T t2, Document doc)">
    private Integer update(T t2, Document doc) {
        Integer documentosModificados = 0;
        Document search = new Document();

        try {
            search = findDocPrimaryKey(t2);

            UpdateResult updateResult = getMongoDatabase().getCollection(collection).updateOne(search, doc);
            return (int) updateResult.getModifiedCount();

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer update(Document docSearch, Document docUpdate)">
    public Integer update(Document docSearch, Document docUpdate) {
        Integer documentosModificados = 0;

        try {

            UpdateResult updateResult = getMongoDatabase().getCollection(collection).updateOne(docSearch, docUpdate);
            return (int) updateResult.getModifiedCount();

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="updateMany(Document docSearch, Document docUpdate)  "> 
    /**
     * Actualiza multiples documentos
     *
     * @param docSearch
     * @param docUpdate
     * @return
     */
    public Integer updateMany(Document docSearch, Document docUpdate) {
        Integer documentosModificados = 0;

        try {

            UpdateResult updateResult = getMongoDatabase().getCollection(collection).updateMany(docSearch, docUpdate);
            return (int) updateResult.getModifiedCount();

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="replaceOne(String key, String value, Document docUpdate) ">
    /**
     * implementa replaceOne
     *
     * @param key
     * @param value
     * @param docUpdate
     * @return
     */
    public Integer replaceOne(String key, String value, Document docUpdate) {
        Integer documentosModificados = 0;

        try {
            UpdateResult updateResult = getMongoDatabase().getCollection(collection).replaceOne(Filters.eq(key, value), docUpdate);

            return (int) updateResult.getModifiedCount();

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="replaceOne(Bson search, Document docUpdate) ">
    /**
     *
     * @param key
     * @param value
     * @param docUpdate
     * @return
     */
    public Integer replaceOne(Bson search, Document docUpdate) {
        Integer documentosModificados = 0;

        try {
            UpdateResult updateResult = getMongoDatabase().getCollection(collection).replaceOne(search, docUpdate);

            return (int) updateResult.getModifiedCount();

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="replaceOne(Document docSearch, Document docUpdate, String... options)">
    /**
     *
     * @param docSearch
     * @param docUpdate
     * @param options
     * @return
     */
    public Integer replaceOne(Document docSearch, Document docUpdate, String... options) {
        Integer documentosModificados = 0;

        try {

            UpdateResult updateResult = getMongoDatabase().getCollection(collection).replaceOne(docSearch, docUpdate);
            return (int) updateResult.getModifiedCount();

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getPrimaryKeyValue(T t2)">
    private Object getPrimaryKeyValue(T t2) {
        Object o = new Object();
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {

                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    o = method.invoke(t2);

               } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return o;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getPrimaryKeyType(T t2)">

    private String getPrimaryKeyType(T t2) {
        String type = "String";
        try {
            Object t = entityClass.newInstance();
            for (PrimaryKey p : primaryKeyList) {
                type = p.getType();

            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return type;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="getBean()">
    public static <T> T getBean(Class<T> clazz) {
        BeanManager bm = getBeanManager();
        Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
        CreationalContext<T> ctx = bm.createCreationalContext(bean);
        return (T) bm.getReference(bean, clazz, ctx);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="getBeanManager()">
    private static BeanManager getBeanManager() {
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        return (BeanManager) servletContext
                .getAttribute("javax.enterprise.inject.spi.BeanManager");
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filterBetweenDate(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDate(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterBetweenDate(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas excluyendo las horas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
              Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson filter = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filterBetweenDate(String secondaryfield,String secondaryfieldvalue,String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDate(String secondaryfield, String secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue)
            );

            list = filters(filter, sortQuery);
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterBetweenDate(String secondaryfield, Integer secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDate(String secondaryfield, Integer secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue)
            );

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDate(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDate(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue)
            );

            list = filters(filter, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDateWithoutHours(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Filtra entre fechas omitiendo las horas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDateWithoutHours(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd)
            );

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDateOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDateOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson dates = Filters.and(Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));
            Bson filter = Filters.or(
                    myfilter, dates
            );

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDateWithoutHoursOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDateWithoutHoursOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson dates = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));
            Bson filter = Filters.or(
                    myfilter, dates
            );

            list = filters(filter, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     * repository.filterBetweenDate("fechainicio",permiso.getFechainicio(),"fechafin",permiso.getFechafin());
     * @return
     */
    public List<T> filterBetweenDatePagination(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }

// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String secondaryfield, String secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePagination(String secondaryfield, String secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String secondaryfield, String secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(String secondaryfield, String secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String secondaryfield, Integer secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(String secondaryfield, Integer secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
     } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String secondaryfield,String secondaryfieldvalue,String thirdfield, String thirdvalue,, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(String secondaryfield, String secondaryfieldvalue, String thirdfield, String thirdvalue,
            String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.eq(thirdfield, thirdvalue),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String secondaryfield,Integer secondaryfieldvalue,String thirdfield, String thirdvalue,, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(String secondaryfield, Integer secondaryfieldvalue, String thirdfield, String thirdvalue,
            String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.eq(thirdfield, thirdvalue),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String secondaryfield,String secondaryfieldvalue,String thirdfield, Integer thirdvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(String secondaryfield, String secondaryfieldvalue, String thirdfield, Integer thirdvalue,
            String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.eq(thirdfield, thirdvalue),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="filterBetweenDatePagination(String integerFieldName,Integer integerFieldValue,String integerFieldName2, Integer integerFieldValue2, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(String integerFieldName, Integer integerFieldValue, String integerFieldName2, Integer integerFieldValue2,
            String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(integerFieldName, integerFieldValue),
                    Filters.eq(integerFieldName2, integerFieldValue2),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDatePagination(String secondaryfield, Integer secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePagination(String secondaryfield, Integer secondaryfieldvalue, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDatePagination(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePagination(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDatePaginationWithoutHours(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHours(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDatePaginationOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) ">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson dates = Filters.and(Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname, datelimitvalue));
            Bson filter = Filters.or(
                    myfilter, dates
            );

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="List<T> filterBetweenDatePaginationWithoutHoursOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) ">

    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenDatePaginationWithoutHoursOR(Bson myfilter, String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            Bson dates = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));
            Bson filter = Filters.or(
                    myfilter, dates
            );

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
// </editor-fold>

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterBetweenInteger(String fieldnamestart, Integer startvalue, String fieldlimitname, String limitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenInteger(String fieldnamestart, Integer startvalue, String fieldlimitname, Integer limitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(Filters.gte(fieldnamestart, startvalue), Filters.lte(fieldlimitname, limitvalue));

            list = filters(filter, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterBetweenInteger(String fieldnamestart, Integer startvalue, String fieldlimitname, String limitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     *
     * @return
     */
    public List<T> filterBetweenIntegerPagination(String fieldnamestart, Integer startvalue, String fieldlimitname, Integer limitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(Filters.gte(fieldnamestart, startvalue), Filters.lte(fieldlimitname, limitvalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filterBetweenDouble(String fieldnamestart, Integer startvalue, String fieldlimitname, String limitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     * @return
     */
    public List<T> filterBetweenDouble(String fieldnamestart, Integer startvalue, String fieldlimitname, String limitvalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(Filters.gte(fieldnamestart, startvalue), Filters.lte(fieldlimitname, limitvalue));

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterBetweenDoublePagination(String fieldnamestart, Integer startvalue, String fieldlimitname, String limitvalue, Document... docSort)">
    /**
     * Crea un filtro para filtrar entre fechas
     *
     * @param startname
     * @param datestart
     * @param endname
     * @param datelimit
     * @return
     */
    public List<T> filterBetweenDoublePagination(String fieldnamestart, Integer startvalue, String fieldlimitname, String limitvalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Bson filter = Filters.and(Filters.gte(fieldnamestart, startvalue), Filters.lte(fieldlimitname, limitvalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filterDayWithoutHour(String secondaryfield,String secondaryfieldvalue, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHour(String secondaryfield, String secondaryfieldvalue, String fielddate, Date datevalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filters(filter, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterDayWithoutHour(String secondaryfield,Integer secondaryfieldvalue, String fielddate, Date datevalue,  Document... docSort)">
    /**
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHour(String secondaryfield, Integer secondaryfieldvalue, String fielddate, Date datevalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterDayWithoutHour(String fielddate, Date datevalue,  Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHour(String fielddate, Date datevalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filters(filter, sortQuery);
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterDayWithoutHour(Bson myfilter,String fielddate, Date datevalue, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHour(Bson myfilter, String fielddate, Date datevalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(myfilter, Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filters(filter, sortQuery);
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterDayWithoutHourOR(Bson myfilter,String fielddate, Date datevalue, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHourOR(Bson myfilter, String fielddate, Date datevalue, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson date = Filters.and(Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));
            Bson filter = Filters.and(myfilter, date);

            list = filters(filter, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="filterDayWithoutHourPagination(String fielddate, Date datevalue,  Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHourPagination(String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));
            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterDayWithoutHourPagination(String secondaryfield,String secondaryfieldvalue, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHourPagination(String secondaryfield, String secondaryfieldvalue, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterDayWithoutHourPagination(Bson myfilter, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHourPagination(Bson myfilter, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> filterDayWithoutHourPaginationOR(Bson myfilter, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     * crea un filtro con paginacion de fechas and otro atributo
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHourPaginationOR(Bson myfilter, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="filterDayWithoutHourPagination(String secondaryfield,Integer secondaryfieldvalue, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort)">
    /**
     *
     * @param primarykeyfield
     * @param primarykeyvalue
     * @param fieldnamestart
     * @param datestartvalue
     * @param pageNumber
     * @param rowsForPage
     * @param docSort
     * @return
     */
    public List<T> filterDayWithoutHourPagination(String secondaryfield, Integer secondaryfieldvalue, String fielddate, Date datevalue, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        list = new ArrayList<>();
        try {
            Document sortQuery = new Document();

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            Date dateStart = setHourToDate(datevalue, 0, 0);
            Date dateEnd = setHourToDate(datevalue, 23, 59);

            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, dateStart),
                    Filters.lte(fielddate, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findText(String key, String value, Boolean caseSensitive, Boolean diacriticSensitive, Document... docSort)">
    /**
     * Requiere que se cree un indice primero
     * URL:https://docs.mongodb.com/manual/reference/operator/query/text/
     * Indice: db.planetas.createIndex( { idplaneta: "text" } )
     *
     * @param key
     * @param value
     * @param caseSensitive = true
     * @param diacriticSensitive = true
     * @param docSort
     * @return
     */
    public List<T> findText(String key, String value, Boolean caseSensitive, Boolean diacriticSensitive, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection)
                    .find(new Document("$text", new Document("$search", value)
                            .append("$caseSensitive", caseSensitive)
                            .append("$diacriticSensitive", diacriticSensitive)));

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="findText(String key, String value, Boolean caseSensitive, Boolean diacriticSensitive, Document... docSort)">

    /**
     * Requiere que se cree un indice primero
     * URL:https://docs.mongodb.com/manual/reference/operator/query/text/
     * Indice: db.planetas.createIndex( { idplaneta: "text" } )
     *
     * @param key
     * @param value
     * @param caseSensitive = true
     * @param diacriticSensitive = true
     * @param docSort
     * @return
     */
    public List<T> findTextPagination(String key, String value, Boolean caseSensitive, Boolean diacriticSensitive, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection)
                    .find(new Document("$text", new Document("$search", value)
                            .append("$caseSensitive", caseSensitive)
                            .append("$diacriticSensitive", diacriticSensitive))).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage);

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findRegex(String key, String value, Boolean caseSensitive, Document... docSort)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegex(String key, String value, Boolean caseSensitive, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value))).sort(sortQuery);
//iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i"))).sort(sortQuery);
//               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));

            }

            list = iterableList(iterable);

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findRegex(String key, String value, Boolean caseSensitive,  String keyandsearch,String valueandsearch,Document... docSort)">

    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegex(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, valueSecond)).sort(sortQuery);
//iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond)).sort(sortQuery);
//               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));

            }

            list = iterableList(iterable);

     } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findRegex(String key, String value, Boolean caseSensitive, String keySecond, Object valueSecond, Document... docSort)">

    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegex(String key, String value, Boolean caseSensitive, String keySecond, Object valueSecond, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                switch (typeOfObject(valueSecond)) {
                    case "String":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, valueSecond.toString())).sort(sortQuery);
                        break;
                    case "Integer":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, Integer.parseInt(valueSecond.toString()))).sort(sortQuery);
                        break;

                    case "Double":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, Double.parseDouble(valueSecond.toString()))).sort(sortQuery);
                        break;
                    case "Date":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, new Date(valueSecond.toString()))).sort(sortQuery);
                        break;
                    default:
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, valueSecond.toString())).sort(sortQuery);
                        break;

                }

            } else {
//                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond)).sort(sortQuery);
                switch (typeOfObject(valueSecond)) {
                    case "String":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond.toString())).sort(sortQuery);
                        break;
                    case "Integer":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, Integer.parseInt(valueSecond.toString()))).sort(sortQuery);
                        break;

                    case "Double":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, Double.parseDouble(valueSecond.toString()))).sort(sortQuery);
                        break;
                    case "Date":
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, new Date(valueSecond.toString()))).sort(sortQuery);
                        break;
                    default:
                        iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond.toString())).sort(sortQuery);

                        break;

                }

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="findRegex(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond,  String keyThree, String valueTree, Document... docSort)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegex(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, String keyThree, String valueTree, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, valueSecond).append(keyThree, valueTree)).sort(sortQuery);
//iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond).append(keyThree, valueTree)).sort(sortQuery);
//               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

 
    // <editor-fold defaultstate="collapsed" desc="findRegexInText(String key, String value, Boolean caseSensitive, Document... docSort)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexInText(String key, String value, Boolean caseSensitive, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value))).sort(sortQuery);
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i"))).sort(sortQuery);

            }

            list = iterableList(iterable);

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findRegexInText(String key, String value, Boolean caseSensitive,  String keySecond,String valueSecond,Document... docSort)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexInText(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value)).append(keySecond, valueSecond)).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i")).append(keySecond, valueSecond)).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findRegexInText(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond,String keyThree, String valueTree, Document... docSort)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexInText(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, String keyThree, String valueThree, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value)).append(keySecond, valueSecond).append(keyThree, valueThree)).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i")).append(keySecond, valueSecond).append(keyThree, valueThree)).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="findRegexPagination(String key, String value, Boolean caseSensitive, Integer pageNumber, Integer rowsForPage,Document... docSort)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexPagination(String key, String value, Boolean caseSensitive, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value))).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);
//iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i"))).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);
//               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));

            }

            list = iterableList(iterable);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findRegexPagination(String key, String value, Boolean caseSensitive, String keySecond,String valueSecond,Integer pageNumber, Integer rowsForPage,Document... docSort)">

    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexPagination(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, valueSecond)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);
//iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);
//               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));

            }

            list = iterableList(iterable);

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findRegexPagination(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, String keyThree, String valueTree,Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexPagination(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, String keyThree, String valueThree, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value)).append(keySecond, valueSecond).append(keyThree, valueThree)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);
//iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i")).append(keySecond, valueSecond).append(keyThree, valueThree)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);
//               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
//   

    // <editor-fold defaultstate="collapsed" desc="findRegexInTextPagination(String key, String value, Boolean caseSensitive, Integer pageNumber, Integer rowsForPage, Document... docSort))">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexInTextPagination(String key, String value, Boolean caseSensitive, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value))).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i"))).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findRegexInTextPagination(String key, String value, Boolean caseSensitive, String keySecond,String valueSecond,Integer pageNumber, Integer rowsForPage, Document... docSort))">

    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexInTextPagination(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value)).append(keySecond, valueSecond)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i")).append(keySecond, valueSecond)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);

            }

            list = iterableList(iterable);

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="findRegexInTextPagination(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond,String keyThree, String valueThree, Integer pageNumber, Integer rowsForPage, Document... docSort)">

    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> findRegexInTextPagination(String key, String value, Boolean caseSensitive, String keySecond, String valueSecond, String keyThree, String valueThree, Integer pageNumber, Integer rowsForPage, Document... docSort) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }
            Object t = entityClass.newInstance();
            Pattern regex = Pattern.compile(value);

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value)).append(keySecond, valueSecond).append(keyThree, valueThree)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i")).append(keySecond, valueSecond).append(keyThree, valueThree)).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                        limit(rowsForPage).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="typeOfObject(Object o)">
    /**
     * se usa para comparar el tipo que es el objeto
     *
     * @param o
     * @return
     */
    public String typeOfObject(Object o) {
        String type = "String";
        try {
            if (o instanceof String) {
                type = "String";
            } else {
                if (o instanceof Integer) {
                    type = "Integer";

                } else {
                    if (o instanceof Date) {
                        type = "Date";

                    } else {
                        if (o instanceof Double) {
                            type = "Double";

                        }
                    }

                }
            }
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return type;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="complete(String query)">
    /**
     *
     * @param key
     * @param value
     * @param docSort
     * @return
     */
    public List<T> complete(String query) {
        Document sortQuery = new Document();
        list = new ArrayList<>();

        try {

            query = query.trim();
            String field = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
            String fromstart = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fromstart");
            String fielddropdown = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fielddropdown");
            String fieldminquerylength = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fieldminquerylength");
            if (query.length() < Integer.parseInt(fieldminquerylength)) {
                return list;
            }
            if (fielddropdown.equals("false")) {

                if (fromstart.equals("true")) {
                    list = findRegex(field, query, true, new Document(field, 1));
                } else {
                    list = findRegexInText(field, query, true, new Document(field, 1));
                }
            } else {
                list = findRegexInText(field, query, true, new Document(field, 1));

            }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="unknown(Document... docSort) ">
    /**
     *
     * @param document
     * @return
     */
    public List<JmoordbResult> unknown(String database, String collection, Document... doc) {
        List<JmoordbResult> list = new ArrayList<>();
        Document docQuery = new Document();
        try {
            if (doc.length != 0) {
                docQuery = doc[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(docQuery);

            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="unknownSort(String database,String collection, Document doc,Document... docSort) ">
    /**
     *
     * @param document
     * @return
     */
    public List<JmoordbResult> unknownSort(String database, String collection, Document doc, Document... docSort) {
        List<JmoordbResult> list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);

            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="unknown(String database,String collection, Bson filter,Document... docSort)">

    /**
     *
     * @param document
     * @return
     */
    public List<JmoordbResult> unknown(String database, String collection, Bson filter, Document... docSort) {
        List<JmoordbResult> list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(filter).sort(sortQuery);

            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="public List<JmoordbResult> unknownPagination(String database,String collection,Integer pageNumber, Integer rowsForPage, Document... doc)">
    /**
     * Busca con paginacion en una coleccion
     *
     * @param document
     * @return
     */
    public List<JmoordbResult> unknownPagination(String database, String collection, Integer pageNumber, Integer rowsForPage, Document... doc) {
        List<JmoordbResult> list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (doc.length != 0) {
                sortQuery = doc[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(sortQuery).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage);
            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="public List<JmoordbResult> unknownPaginationSort(String database,String collection,Integer pageNumber, Integer rowsForPage, Document doc,Document... docSort)">

    /**
     * Busca con paginacion en una coleccion
     *
     * @param document
     * @return
     */
    public List<JmoordbResult> unknownPaginationSort(String database, String collection, Integer pageNumber, Integer rowsForPage, Document doc, Document... docSort) {
        List<JmoordbResult> list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(doc).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="processUnknownIterableJmoordbResult(AggregateIterable<Document> iterable)">
    private List< JmoordbResult> processUnknownIterableJmoordbResult(FindIterable<Document> iterable) {
          List<JmoordbResult> l = new ArrayList<>();
        List<Map<String, Object>> lObject = new ArrayList<>();
        try {
            
                      Consumer<Document> printConsumer = document -> 
                               lObject.add(new HashMap<>(document));
                   
                    
            
iterable.forEach(printConsumer);

           
            for (Map m : lObject) {
                JmoordbResult jmoordbResult = new JmoordbResult();
                for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                    jmoordbResult.put(entry.getKey(), entry.getValue().toString());

                }
                l.add(jmoordbResult);
            }

        } catch (Exception e) {
            excepcionManager(JmoordbUtil.nameOfClass(), JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return l;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean unknownSave(String database, String collection,Document doc)">
    /**
     *
     * @param doc
     * @param verifyID
     * @return
     */
    public Boolean unknownSave(String database, String collection, Document doc) {
        try {

            MongoDatabase db = mongoClient.getDatabase(database);
            db.getCollection(collection).insertOne(doc);
            return true;

        } catch (Exception ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            new JmoordbException("unknownSave() " + ex.getLocalizedMessage());
            exception = new Exception("unknownSave() " + ex.getLocalizedMessage());
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer unknownReplaceOne(String database, String collection, Bson builder, Document docUpdate) ">
    public Integer unknownReplaceOne(String database, String collection, Bson builder, Document docUpdate) {
        Integer documentosModificados = 0;

        try {
            MongoDatabase db = mongoClient.getDatabase(database);
            UpdateResult updateResult = db.getCollection(collection).replaceOne(builder, docUpdate);
            return (int) updateResult.getModifiedCount();

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return 0;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean unknownDelete(Document doc)">
    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean unknownDelete(String database, String collection, Document doc) {
        try {
            MongoDatabase db = mongoClient.getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteOne(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

      } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean unknownDelete(String database, String collection, Bson builder)">

    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean unknownDelete(String database, String collection, Bson builder) {
        try {
            MongoDatabase db = mongoClient.getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteOne(builder);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean unknownDeleteAll(String database, String collection)">

    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean unknownDeleteAll(String database, String collection) {
        Integer cont = 0;
        try {
            MongoDatabase db = mongoClient.getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteMany(new Document());
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean unknownDeleteMany(String database, String collection, Document doc)">
    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean unknownDeleteMany(String database, String collection, Document doc) {
        Integer cont = 0;
        try {
            MongoDatabase db = mongoClient.getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteMany(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean unknownDeleteMany(String database, String collection, Bson builder)">
    /**
     * elimina un documento
     *
     * @param doc
     * @return
     */
    public Boolean unknownDeleteMany(String database, String collection, Bson builder) {
        Integer cont = 0;
        try {
            MongoDatabase db = mongoClient.getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteMany(builder);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isAvailableBetweenDateHour(Bson filter, String namefieldOfStart,Date valueStart,namefieldOfEnd,Date valueEnd )">
    /**
     * Devuelve true si no hay registros con la condicion fechay hora de inicio
     * y fin y el filtro que se pasa como parametro
     *
     * @param filter
     * @param namefieldOfStart
     * @param valueStart
     * @param namefieldOfEnd
     * @param valueEnd
     * @return
     */
    public Boolean isAvailableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) {
        try {
            //Vehiculos en viajes

            Integer count = count();
            if (count.equals(0)) {
                return true;
            }
            //inicio

            Bson b = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );

            Bson c_e_f_g_h_l = Filters.or(
                    Filters.eq(namefieldOfStart, valueStart),
                    Filters.eq(namefieldOfStart, valueEnd),
                    Filters.eq(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson j = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson d = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );
            Bson i = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );
            Bson k = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );

            Bson filterAll = Filters.and(filter, or(b, c_e_f_g_h_l, d, i, j, k));

//            List<T> list = findBy(_filter);
//
//            if (list.isEmpty()) {
//                return true;
//            }
Integer contador = count(filterAll);

if(contador == 0){
    
    return true;
}
      } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="isAvailableBetweenDateHourOld(Bson filter, String namefieldOfStart,Date valueStart,namefieldOfEnd,Date valueEnd )">
    /**
     * Devuelve true si no hay registros con la condicion fechay hora de inicio
     * y fin y el filtro que se pasa como parametro
     *
     * @param filter
     * @param namefieldOfStart
     * @param valueStart
     * @param namefieldOfEnd
     * @param valueEnd
     * @return
     */
    public Boolean isAvailableBetweenDateHourOld(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) {
        try {
            //Vehiculos en viajes

            Integer count = count();
            if (count.equals(0)) {
                return true;
            }
            //inicio

            Bson b = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );

            Bson c_e_f_g_h_l = Filters.or(
                    Filters.eq(namefieldOfStart, valueStart),
                    Filters.eq(namefieldOfStart, valueEnd),
                    Filters.eq(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson j = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson d = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );
            Bson i = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );
            Bson k = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );

            Bson filterAll = Filters.and(filter, or(b, c_e_f_g_h_l, d, i, j, k));

          
            List<T> list = findBy(filterAll);

            if (list.isEmpty()) {
                return true;
            }
//Integer countador = count(filter);
//if(contador == 0){
//    return true;
//}
      } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }

    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="List<T> availableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) ">
    /**
     * Devuelve una lista de los elementos que estan en ese rango de fechas y
     * que cumplan la condicion del filtro que se pasa como parametro
     *
     * @param filter
     * @param namefieldOfStart
     * @param valueStart
     * @param namefieldOfEnd
     * @param valueEnd
     * @return Devuelve una lista de los elementos que estan en ese rango de
     * fechas y que cumplan la condicion del filtro que se pasa como parametro
     */
    public List<T> availableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) {
        try {
          
          
            list = new ArrayList<>();
            Integer count = count();
            if (count.equals(0)) {
                return list;
            }
            //inicio

           Bson b = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );

            Bson c_e_f_g_h_l = Filters.or(
                    Filters.eq(namefieldOfStart, valueStart),
                    Filters.eq(namefieldOfStart, valueEnd),
                    Filters.eq(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson j = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson d = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );
            Bson i = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );
            Bson k = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );

            Bson _filter = Filters.and(filter, or(b, c_e_f_g_h_l, d, i, j, k));


            list = findBy(_filter);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> notAvailableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) ">
    /**
     * Devuelve una lista de los elementos que estan en ese rango de fechas y
     * que cumplan la condicion del filtro que se pasa como parametro
     *
     * @param filter
     * @param namefieldOfStart
     * @param valueStart
     * @param namefieldOfEnd
     * @param valueEnd
     * @return Devuelve una lista de los elementos que estan en ese rango de
     * fechas y que cumplan la condicion del filtro que se pasa como parametro
     */
    public List<T> notAvailableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) {
        try {
          
            list = new ArrayList<>();
            Integer count = count();
            if (count.equals(0)) {
                return list;
            }
            //inicio

            Bson b = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );

            Bson c_e_f_g_h_l = Filters.or(
                    Filters.eq(namefieldOfStart, valueStart),
                    Filters.eq(namefieldOfStart, valueEnd),
                    Filters.eq(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson j = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.eq(namefieldOfEnd, valueEnd)
            );

            Bson d = Filters.and(
                    Filters.gt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );
            Bson i = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.gt(namefieldOfEnd, valueEnd)
            );
            Bson k = Filters.and(
                    Filters.lt(namefieldOfStart, valueStart),
                    Filters.lt(namefieldOfStart, valueEnd),
                    Filters.gt(namefieldOfEnd, valueStart),
                    Filters.lt(namefieldOfEnd, valueEnd)
            );

            Bson _filter = Filters.and(filter, or(b, c_e_f_g_h_l, d, i, j, k));

            list = findBy(_filter);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setHourToDate()">
    /**
     * Asigna horas a la fecha util para cuando se desee buscar entre fechas que
     * tengas horas excluyendolas
     *
     * @param date
     * @param hour
     * @return
     */
    private Date setHourToDate(Date date, Integer hour, Integer minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<UserInfo> generateListUserinfo(String username, String description)">
    public List<UserInfo> generateListUserinfo(String username, String description) {
        List<UserInfo> listUserinfo = new ArrayList<>();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            UUID uuid = UUID.randomUUID();

            listUserinfo.add(new UserInfo(uuid.toString(), username, date2, description));
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return listUserinfo;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserInfo generateUserinfo(String username, String description)">
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
            UUID uuid = UUID.randomUUID();
            userinfo.setIduserinfo(uuid.toString());
            userinfo.setUsername(username);
            userinfo.setDatetime(date2);
            userinfo.setDescription(description);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return userinfo;
    }  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="addUserInfoForSaveMethodo()">
    /**
     * Devuelve el entity con el UserInfo asigando la primera vez es un list
     *
     * @param t1
     * @return
     */
    public T addUserInfoForSaveMethod(T t1, String username, String descripcion) {
        try {
            PropertyDescriptor userInfoProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            userInfoProperty = property.apply("userInfo");
            Boolean found = false;
            for (MethodDescriptor m : beanInfo.getMethodDescriptors()) {
                if (m.getMethod().getName().contains("setUserInfo")) {
                    found = true;
                    break;
                }
            }
            if (found) {
                //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                final BiConsumer userInfoSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                        lookup.unreflect(userInfoProperty.getWriteMethod()));
                userInfoSetter.accept(t1, generateListUserinfo(username, descripcion));
            } else {
                //   JmoordbUtil.warningMessage("No contiene el metodo UserInfo");
                exception = new Exception("No contiene el metodo UserInfo");
            }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T addUserInfoForEditMethod(T t1, String username, String descripcion)">
    /**
     * Devuelve el entity con el UserInfo asigando a la lista del entity
     *
     * @param t1
     * @return
     */
    public T addUserInfoForEditMethod(T t1, String username, String descripcion) {
        try {
            PropertyDescriptor userInfoProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            userInfoProperty = property.apply("userInfo");
            Boolean found = false;
            for (MethodDescriptor m : beanInfo.getMethodDescriptors()) {
                if (m.getMethod().getName().contains("setUserInfo")) {
                    found = true;
                    break;
                }
            }
            if (found) {
                //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                final BiConsumer userInfoSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                        lookup.unreflect(userInfoProperty.getWriteMethod()));
                userInfoSetter.accept(t1, generateListUserinfo(username, descripcion));
                final java.util.function.Function userInfoGetter = JmoordbLambdaMetaFactory.createGetter(lookup,
                        lookup.unreflect(userInfoProperty.getReadMethod()));
                //Obtener la lista de UserInfo
                List<UserInfo> list = (List<UserInfo>) userInfoGetter.apply(t1);
                //Agregamos el nuevo a la lista
                list.add(generateUserinfo(username, descripcion));
                //Asigamos al setUserInfo
                userInfoSetter.accept(t1, list);

            } else {
                exception = new Exception("No contiene el metodo UserInfo");
                //JmoordbUtil.warningMessage("No contiene el metodo UserInfo");

            }

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T primaryKeyValueToUpper(T t1)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T primaryKeyValueToUpper(T t1) {
        try {

            String nameOfPrimaryKey = primaryKeyName(t1);
//            nameOfPrimaryKey = util.letterToUpper(nameOfPrimaryKey);
            String valueOfPrimaryKey = primaryKeyValue(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfPrimaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfPrimaryKey.toUpperCase());

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T primaryKeyValueToLower(T t1)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T primaryKeyValueToLower(T t1) {
        try {

            String nameOfPrimaryKey = primaryKeyName(t1);
//            nameOfPrimaryKey = util.letterToUpper(nameOfPrimaryKey);
            String valueOfPrimaryKey = primaryKeyValue(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfPrimaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfPrimaryKey.toLowerCase());

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T primaryKeySetValue(T t1, String valueOfPrimaryKey)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T primaryKeySetValue(T t1, String valueOfPrimaryKey) {
        try {

            String nameOfPrimaryKey = primaryKeyName(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfPrimaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfPrimaryKey);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T compositeKeyValueToLower(T t1)">
    /**
     * Devuelve el entity con las llaves secundarias en Mayuscula
     *
     * @param t1
     * @return
     */
    public T compositeKeyValueToLower(T t1) {
        try {
            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            for (CompositeKey s : compositeKeyList) {
                if (s.getType().equals("java.lang.String")) {

                    String nameOfCompositeKey = s.getName();

                    Set set = compositeKeyValueObject(t1).entrySet();
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry mentry = (Map.Entry) iterator.next();
                        for (CompositeKey s1 : compositeKeyList) {
                            if (mentry.getKey().equals(s.getName()) && s.getType().equals("java.lang.String")) {
                                String valueOfSecondKey = (String) mentry.getValue();
                                pkProperty = property.apply(nameOfCompositeKey);

                                //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
                                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                                final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                                        lookup.unreflect(pkProperty.getWriteMethod()));
                                pkSetter.accept(t1, valueOfSecondKey.toLowerCase());

                            }
                        }

                    }

                }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T compositeKeySetValue(T t1, String nameOfCompositeKey, String valueOfSeconddaryKey)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T compositeKeySetValue(T t1, String nameOfCompositeKey, Object valueOfSeconddaryKey) {
        try {

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfCompositeKey);

//           
            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, nameOfCompositeKey);

    } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T compositeKeyValueToUpper(T t1)">
    /**
     * Devuelve el entity con las llaves secundarias en Mayuscula
     *
     * @param t1
     * @return
     */
    public T compositeKeyValueToUpper(T t1) {
        try {
            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            for (CompositeKey s : compositeKeyList) {
                if (s.getType().equals("java.lang.String")) {

                    String nameOfCompositeKey = s.getName();

                    Set set = compositeKeyValueObject(t1).entrySet();
                    Iterator iterator = set.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry mentry = (Map.Entry) iterator.next();
                        for (CompositeKey s1 : compositeKeyList) {
                            if (mentry.getKey().equals(s.getName()) && s.getType().equals("java.lang.String")) {
                                String valueOfSecondKey = (String) mentry.getValue();
                                pkProperty = property.apply(nameOfCompositeKey);

                                //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
                                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                                final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                                        lookup.unreflect(pkProperty.getWriteMethod()));
                                pkSetter.accept(t1, valueOfSecondKey.toUpperCase());

                            }
                        }

                    }

                } else {

                    if (s.getType().equals("java.lang.Integer")) {

                        String nameOfCompositeKey = s.getName();

                        Set set = compositeKeyValueObject(t1).entrySet();
                        Iterator iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry mentry = (Map.Entry) iterator.next();
                            for (CompositeKey s1 : compositeKeyList) {
                                if (mentry.getKey().equals(s.getName()) && s.getType().equals("java.lang.Integer")) {
                                    String valueOfSecondKey = (String) mentry.getValue();
                                    pkProperty = property.apply(nameOfCompositeKey);

                                    //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
                                    final MethodHandles.Lookup lookup = MethodHandles.lookup();
                                    final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                                            lookup.unreflect(pkProperty.getWriteMethod()));
                                    pkSetter.accept(t1, valueOfSecondKey.toUpperCase());

                                }
                            }

                        }

                    }

                }
            }
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HashMap<String, Object> compositeValueObject(T t2)">
    /**
     * devuelve la lista de CompositeKey con las llaves secundarias,name, value
     *
     * @return
     */
    public HashMap<String, Object> compositeKeyValueObject(T t2) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (CompositeKey p : compositeKeyList) {
                String name = util.letterToLower(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }
                    map.put(name, value);

            } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return map;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isFoundByCompositeKey(T t2)">
    /**
     * Busca por la llave secundaria del documento
     *
     * @param t2
     * @return
     */
    public Boolean isFoundByCompositeKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            if (!compositeKeyList.isEmpty()) {

                for (CompositeKey p : compositeKeyList) {
                    String name = "get" + util.letterToUpper(p.getName());
                    Method method;
                    try {

                        method = entityClass.getDeclaredMethod(name);

                        doc.put(p.getName(), method.invoke(t2));

                    } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
                }
                T t_ = (T) find(doc);
                if (t_ == null) {
                    return false;
                } else {
                    return true;
                }
            } else {
                //     JmoordbUtil.warningMessage("No tiene llaves secundaria @Composite no se puede buscar");
                exception = new Exception("No tiene llaves secundaria @Composite no se puede buscar");
                return false;
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<T> findByCompositeKey(T t2)">
    /**
     * Busca por la llave secundaria del documento
     *
     * @param t2
     * @return
     */
    public Optional<T> findByCompositeKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            if (!compositeKeyList.isEmpty()) {

                for (CompositeKey p : compositeKeyList) {
                    String name = "get" + util.letterToUpper(p.getName());
                    Method method;
                    try {
                        method = entityClass.getDeclaredMethod(name);
                        doc.put(p.getName(), method.invoke(t2));
                  } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
                }
                return find(doc);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Optional<T> findByCompositeKey(Document doc)">
    public Optional<T> findByCompositeKey(Document doc) {

        try {
            //  t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedModelList, referencedModelList);
            T t_ = (T) find(doc);

            if (t_ == null) {
                // no lo encontro
            } else {
                return Optional.of(t_);
            }

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Document findDocSecondaryKey(T t2)">
    /**
     *
     * @return Document() correspondiente a la llave primaria
     */
    private Document findDocSecondaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

               } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
      } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(T t2)">
    /**
     * Busca por la llave primaria del documento
     *
     * @param t2
     * @return
     */
    public Optional<T> findBySecondaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {
                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                    return find(doc);
                } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="String secondaryKeyValue(T t2)">

    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public String secondaryKeyValue(T t2) {

        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }

                    //    doc.put(p.getName(), method.invoke(t2));
                 } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean secondaryKeyIsInteger(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Boolean secondaryKeyIsInteger(T t2) {

        Boolean value = false;
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {

                if (p.getType().equals("java.lang.String")) {
                    value = false;
                } else {
                    value = true;
                }

                //    doc.put(p.getName(), method.invoke(t2));
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer secondaryKeyValueInteger(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Integer secondaryKeyValueInteger(T t2) {

        Integer value = 0;
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        exception = new Exception("La llave primaria no es de tipo Integer ");
                    } else {
                        value = Integer.parseInt(String.valueOf(v));
                    }

                    //    doc.put(p.getName(), method.invoke(t2));
                 } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Map<String,String> secondaryKey(T t2) ">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Map<String, String> secondaryKey(T t2) {
        HashMap<String, String> map = new HashMap<String, String>();
        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }
                    map.put(p.getName(), value);

              } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return map;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String secondaryKeyName(T t2)">

    /**
     * Devuelve el nombre del campo @ID llave primaria
     *
     * @param t2
     * @return
     */
    public String secondaryKeyName(T t2) {

        String name = "";
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                name = util.letterToLower(p.getName());

            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return name;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isFoundBySecondaryKey(T t2)">
    /**
     * Busca por la llave secundaria del documento
     *
     * @param t2
     * @return
     */
    public Boolean isFoundBySecondaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                    T t_ = (T) find(doc);
                    if (t_ == null) {
                        return false;
                    } else {
                        return true;
                    }
                } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T secondaryKeyValueToUpper(T t1)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T secondaryKeyValueToUpper(T t1) {
        try {

            String nameOfSecondaryKey = secondaryKeyName(t1);
//            nameOfSecondaryKey = util.letterToUpper(nameOfSecondaryKey);
            String valueOfSecondaryKey = secondaryKeyValue(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfSecondaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfSecondaryKey.toUpperCase());

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="T secondaryKeyValueToLower(T t1)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T secondaryKeyValueToLower(T t1) {
        try {

            String nameOfSecondaryKey = secondaryKeyName(t1);
//            nameOfSecondaryKey = util.letterToUpper(nameOfSecondaryKey);
            String valueOfSecondaryKey = secondaryKeyValue(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfSecondaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfSecondaryKey.toLowerCase());

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Document findDocTertiaryKey(T t2)">
    /**
     *
     * @return Document() correspondiente a la llave primaria
     */
    private Document findDocTertiaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

             } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return doc;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(T t2)">
    /**
     * Busca por la llave primaria del documento
     *
     * @param t2
     * @return
     */
    public Optional<T> findByTertiaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {
                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                    return find(doc);
               } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return Optional.empty();
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="String tertiaryKeyValue(T t2)">

    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public String tertiaryKeyValue(T t2) {

        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }

                    //    doc.put(p.getName(), method.invoke(t2));
                 } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean tertiaryKeyIsInteger(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Boolean tertiaryKeyIsInteger(T t2) {

        Boolean value = false;
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {

                if (p.getType().equals("java.lang.String")) {
                    value = false;
                } else {
                    value = true;
                }

                //    doc.put(p.getName(), method.invoke(t2));
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer tertiaryKeyValueInteger(T t2)">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Integer tertiaryKeyValueInteger(T t2) {

        Integer value = 0;
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        exception = new Exception("La llave primaria no es de tipo Integer ");
                    } else {
                        value = Integer.parseInt(String.valueOf(v));
                    }

                    //    doc.put(p.getName(), method.invoke(t2));
              } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return value;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Map<String,String> tertiaryKey(T t2) ">
    /**
     * Devuelve el valor del campo primario
     *
     * @param t2
     * @return
     */
    public Map<String, String> tertiaryKey(T t2) {
        HashMap<String, String> map = new HashMap<String, String>();
        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);
                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }
                    map.put(p.getName(), value);

              } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return map;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String tertiaryKeyName(T t2)">

    /**
     * Devuelve el nombre del campo @ID llave primaria
     *
     * @param t2
     * @return
     */
    public String tertiaryKeyName(T t2) {

        String name = "";
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                name = util.letterToLower(p.getName());

            }
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return name;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isFoundByTertiaryKey(T t2)">
    /**
     * Busca por la llave secundaria del documento
     *
     * @param t2
     * @return
     */
    public Boolean isFoundByTertiaryKey(T t2) {
        Document doc = new Document();
        try {
            Object t = entityClass.newInstance();
            for (TertiaryKey p : tertiaryKeyList) {
                String name = "get" + util.letterToUpper(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    doc.put(p.getName(), method.invoke(t2));

                    T t_ = (T) find(doc);
                    if (t_ == null) {
                        return false;
                    } else {
                        return true;
                    }
              } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T tertiaryKeyValueToUpper(T t1)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T tertiaryKeyValueToUpper(T t1) {
        try {

            String nameOfTertiaryKey = tertiaryKeyName(t1);
//            nameOfTertiaryKey = util.letterToUpper(nameOfTertiaryKey);
            String valueOfTertiaryKey = tertiaryKeyValue(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfTertiaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfTertiaryKey.toUpperCase());

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="T tertiaryKeyValueToLower(T t1)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T tertiaryKeyValueToLower(T t1) {
        try {

            String nameOfTertiaryKey = tertiaryKeyName(t1);
//            nameOfTertiaryKey = util.letterToUpper(nameOfTertiaryKey);
            String valueOfTertiaryKey = tertiaryKeyValue(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfTertiaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfTertiaryKey.toLowerCase());

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T tertiaryKeySetValue(T t1, String valueOfTertiaryKey)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T tertiaryKeySetValue(T t1, String valueOfTertiaryKey) {
        try {

            String nameOfTertiaryKey = tertiaryKeyName(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfTertiaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfTertiaryKey);

       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="HashMap<String, Object> secondaryValueObject(T t2)">
    /**
     * devuelve la lista de SecondaryKey con las llaves secundarias,name, value
     *
     * @return
     */
    public HashMap<String, Object> secondaryKeyValueObject(T t2) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String value = "";
        try {
            Object t = entityClass.newInstance();
            for (SecondaryKey p : secondaryKeyList) {
                String name = util.letterToLower(p.getName());
                Method method;
                try {

                    method = entityClass.getDeclaredMethod(name);

                    Object v = method.invoke(t2);
                    if (p.getType().equals("java.lang.String")) {
                        value = v.toString();
                    } else {
                        value = String.valueOf(v);
                    }
                    map.put(name, value);

               } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return map;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T secondaryKeySetValue(T t1, String valueOfSecondaryKey)">
    /**
     * Devuelve el entity con la llave primaria en Mayuscula
     *
     * @param t1
     * @return
     */
    public T secondaryKeySetValue(T t1, String valueOfSecondaryKey) {
        try {

            String nameOfSecondaryKey = secondaryKeyName(t1);

            PropertyDescriptor pkProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(t1.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            //Si tiene el userInfo
            pkProperty = property.apply(nameOfSecondaryKey);

            //Definimos el metodo setUserInfo(List<UserInfo> userInfo)
            final MethodHandles.Lookup lookup = MethodHandles.lookup();
            final BiConsumer pkSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(pkProperty.getWriteMethod()));
            pkSetter.accept(t1, valueOfSecondaryKey);

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return t1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="JmoordbStatistics statistics() ">
    /**
     * devuelve las estadisticas de la base de datos
     *
     * @param document
     * @return
     */
    public JmoordbStatistics statistics() {
        JmoordbStatistics jmoordbStatistics = new JmoordbStatistics();
        Document sortQuery = new Document();
        try {

            MongoDatabase db = mongoClient.getDatabase(database);
            Document stats = db.runCommand(new Document("dbstats", 1));

            for (Map.Entry<String, Object> set : stats.entrySet()) {
                switch (set.getKey()) {
                    case "db":
                        jmoordbStatistics.setDb(set.getValue().toString());
                        break;

                    case "collections":
                        jmoordbStatistics.setCollections(Integer.parseInt(set.getValue().toString()));
                        break;
                    case "objects":
                        jmoordbStatistics.setObjects(Integer.parseInt(set.getValue().toString()));
                        break;
                    case "avgObjSize":
                        jmoordbStatistics.setAvgObjSize(Double.parseDouble(set.getValue().toString()));
                        break;
                    case "dataSize":
                        jmoordbStatistics.setDataSize(Double.parseDouble(set.getValue().toString()));
                        break;
                    case "storageSize":
                        jmoordbStatistics.setStorageSize(Double.parseDouble(set.getValue().toString()));
                        break;
                    case "numExtents":
                        jmoordbStatistics.setNumExtents(Integer.parseInt(set.getValue().toString()));
                        break;
                    case "indexes":
                        jmoordbStatistics.setIndexes(Integer.parseInt(set.getValue().toString()));
                        break;
                    case "indexSize":
                        jmoordbStatistics.setIndexSize(Double.parseDouble(set.getValue().toString()));
                        break;

                    case "fsTotalSize":
                        jmoordbStatistics.setFsTotalSize(Double.parseDouble(set.getValue().toString()));
                        break;
                    case "fsUsedSize":
                        jmoordbStatistics.setFsUsedSize(Double.parseDouble(set.getValue().toString()));
                        break;

                }

            }

         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return jmoordbStatistics;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean internalQueryExecMaxBlockingSortBytes(Integer bytes) ">

    /**
     * asigna la cantidad de memoria ram para colecciones de gran tamaño por
     * ejemplo: bytes=150151432 indica que usaremos 150MB para los documnentos
     *
     * @param document
     * @return
     */
    public Boolean internalQueryExecMaxBlockingSortBytes(Integer bytes) {
        Boolean success = true;
        Document sortQuery = new Document();
        try {

            MongoDatabase db = mongoClient.getDatabase(database);

            Document stats = db.runCommand(new Document("setParameter:", 1).append("internalQueryExecMaxBlockingSortBytes", bytes));

        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return success;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T createEntity() ">
    /**
     * Crea un entity vacio
     *
     * @return
     */
    public T createEntity() {
        try {
            return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return null;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" findBy(Document doc, Document... docSort)">
    /**
     *USA EL QUERY PARA EJECUTAR LAS OPERACIONES
     * @param doc
     * @param docSort
     * @return
     */
    public List<T> findBy(Query query, Sorter sorter) {
     querySearch = new Document();
        querySorter = new Document();
        try {          
           querySorter = queryCreateSort(query.getSorted());
           list = new ArrayList<>();
            MongoDatabase db = mongoClient.getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(querySearch).sort(querySorter);
            list = iterableList(iterable);
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return list;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Document queryCreateSort(Sorter sorter)">
    /**
     * para query devuelve un Document sort
     *
     * @return
     */
    public  Document queryCreateSort(Sorter sorter) {
         try{
             
             if(sorter.getSort().equals(Sort.ASC)){ 
                 querySorter.append(sorter.getField() ,1);
             }else{
                  if(sorter.getSort().equals(Sort.DESC)){
                  querySorter.append(sorter.getField() ,-1);
              }
             }
             
          if(sorter.getSorter() ==null){
          
                return querySorter;
            }else{
                return queryCreateSort(sorter.getSorter());
            }
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return querySorter;
    }// </editor-fold>

    
    
    // <editor-fold defaultstate="collapsed" desc="Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {">
    /**
   crea un filtro Bson entre fechas tomando en cuenta la hora
     * @param fieldnamestart
     * @param datestartvalue
     * @param fieldlimitname
     * @param datelimitvalue
     * @param docSort
     * @return 
     */
    public Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {
        Bson filter = new Document();
        try {
      
              Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
       filter = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));
return filter;
       } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return filter;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {">
    /**
     * crea un filtro Bson entre fechas sin tomar en cuenta la hora
     * @param fieldnamestart
     * @param datestartvalue
     * @param fieldlimitname
     * @param datelimitvalue
     * @return 
     */
    public Bson createBsonBetweenDateUsingHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {
   Bson filter = new Document();
        try {
      
              Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            filter = Filters.and(Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname,  datelimitvalue));
            

return filter;
         } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return filter;
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="public Document sortBuilder(String sortfield, String order  )">
    /**
     * crea un documento para ordenar
     * @param sortfield
     * @param order: asc/desc
     * @return 
     */
    public Document sortBuilder(String sortfield, String order  ){
            Document sort = new Document();
        try {
          
          if(sortfield == null || sortfield.equals("")){
            
          }else{
             return new Document(sortfield, createOrder(order));
          }
        } catch (Exception e) {           
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return sort;
    }
       // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Document sortBuilder(HashMap<String,String>hashmap )>
  /**
   * crea un document sort en base a un hashmap
   * @param hashmap
   * @return 
   */
    public Document sortBuilder(HashMap<String,String>map ){
            Document sort = new Document();
        try {
          
          sort.toJson();
          
            
          if(map == null || map.isEmpty()){
            
          }else{
              
              map.entrySet().forEach(m -> {
                  sort.append(m.getKey().toString(),createOrder(m.getValue().toString())) ;
              });  

          }
        } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
        return sort;
    }
       // </editor-fold>
    
    
    
    
      // <editor-fold defaultstate="collapsed" desc="Integer createOrder(String sorter)">
    /**
     * devuelve el indice de ordenacion
     * @param sorter
     * @return 
     */
   
 private Integer createOrder(String sorter){
     Integer ordernumber =1;
     try {
sorter = sorter.trim().toLowerCase();
switch(sorter){
    case "asc":
        ordernumber=1;
        break;
    case "desc":
        ordernumber=-1; 
        break;
        default:
           ordernumber=1;
}
     } catch (Exception e) {          
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
     return ordernumber;
 }
   // </editor-fold>
 
 
  // <editor-fold defaultstate="collapsed" desc="public Document sortBuilder(String sortfield, String order  )">
    /**
     * crea un documento para ordenar
     * @param sortfield
     * @param order: asc/desc
     * @return 
     */
    public Bson filterEQBuilder(String fieldname, String value,String fieldtype  ){
        
     
          Bson filter  ;
         try{
fieldtype = fieldtype.toLowerCase();
         switch(fieldtype){
             case "integer":
                 filter =Filters.eq(fieldname,Integer.parseInt(value));
                 break;
             case "double":
                 filter =Filters.eq(fieldname,Double.parseDouble(value));
                 break;
             case "string":
                  filter =Filters.eq(fieldname,value);
                  break;
             case "date":
                  filter =Filters.eq(fieldname,JmoordbDateUtil.stringToISODate(value));
             case "boolean":
                 Boolean valueBoolean =false;
                 if(value.equals("true")){
                     valueBoolean=true;
                 }
                  filter =Filters.eq(fieldname,valueBoolean);
                  break;
             default:
                 filter =Filters.eq(fieldname,value);
                 
         }
         
        } catch (Exception e) {    
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }
            return null;
        
    }
       // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Document jsonToDocument(String json)">
    /**
     * Convierte un Json a Document
     * @param json
     * @return 
     */
    public Document jsonToDocument(String json){
        return Document.parse(json.toString());
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String documentToJson(Document doc)">
    /**
     * convierre un Document a Json
     * @param doc
     * @return 
     */
    public String documentToJson(Document doc){
        return doc.toJson();
    }
    // </editor-fold>


  // <editor-fold defaultstate="collapsed" desc="String documentToJson(Document doc)">
        private void excepcionManager(String nameOfClass, String method, String error, Exception e){
            
            System.out.println("Class:" + nameOfClass+ " Metodo:" + method);
            System.out.println("Error " + e.getLocalizedMessage());
           
            Logger.getLogger(Repository.class.getName() + "sizeOfPage()").log(Level.SEVERE, null, e);
            exception = new Exception(method, e);
        }
        
        // </editor-fold>
        
        
  // <editor-fold defaultstate="collapsed" desc="T search(String key, Integer value)">

    public T clientEndPoint(MicroservicesModel microservicesModel, String key, Integer value) {
        try {
 HttpAuthenticationFeature httpAuthenticationFeature = null;
            System.out.println("Test --> microservicesModel.getUser() "+microservicesModel.getUser());
            System.out.println("Test --> microservicesModel.getPassword() "+microservicesModel.getPassword());
            System.out.println("Test -->microservicesModel.getUrl() "+microservicesModel.getUrl());
            System.out.println("Test -->microservicesModel.getField() "+microservicesModel.getField());
            System.out.println("Test -->value "+value);
            Client client = ClientBuilder.newClient();
            client.register(httpAuthenticationFeature(microservicesModel.getUser(),microservicesModel.getPassword()));
          tlocal = (T) client
                    .target(microservicesModel.getUrl())
                    .path("/{" +microservicesModel.getField() +"}")
                    .resolveTemplate(microservicesModel.getField(), value)
                    .request(MediaType.APPLICATION_JSON)
                    .get(tlocal.getClass());
          
          return tlocal;
           
         } catch (Exception e) {    
             System.out.println("clientEndPoint() "+e.getLocalizedMessage());
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return null;

    }// </editor-fold>
    
  // <editor-fold defaultstate="collapsed" desc="T search(String key, String value)">

    public T clientEndPoint(MicroservicesModel microservicesModel, String key, String value) {
        try {
            
             HttpAuthenticationFeature httpAuthenticationFeature = HttpAuthenticationFeature.basicBuilder()
          .credentials(JmoordbUtil.desencriptar(microservicesModel.getUser()), JmoordbUtil.desencriptar(microservicesModel.getPassword()))
          .build();
// HttpAuthenticationFeature httpAuthenticationFeature =  
//          HttpAuthenticationFeature.basic(
//                 JmoordbUtil.desencriptar(microservicesModel.getUser()),
//                 JmoordbUtil.desencriptar(microservicesModel.getPassword()));
            System.out.println("Test --> microservicesModel.getUser() "+JmoordbUtil.desencriptar(microservicesModel.getUser()));
            System.out.println("Test --> microservicesModel.getPassword() "+JmoordbUtil.desencriptar(microservicesModel.getPassword()));
            System.out.println("Test -->microservicesModel.getUrl() "+microservicesModel.getUrl());
            System.out.println("Test -->microservicesModel.getField() "+microservicesModel.getField());
            System.out.println("Test -->value "+value);
//            System.out.println("Text-->tlocal.getClass() "+tlocal.getClass());

            System.out.println("Test--> fijo");
 Client client = ClientBuilder.newClient();
 client.register(httpAuthenticationFeature);
//  WebTarget webTarget = client.target("http://localhost:9001/autentificacion/resources/user/search").path("username")
//    .path("/aristides.villarreal");
//  Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
//  Response response = invocationBuilder.put(Entity.entity(user, MediaType.APPLICATION_JSON));

  
    String entity = client.target("http://localhost:9001/autentificacion/resources/user").path("search").path("username/aristides.villarreal")
    .request(MediaType.APPLICATION_JSON).get(String.class);

  System.out.println("Test--> entity" +entity);
//  String userJson = response.readEntity(String.class);
//
//  System.out.println(response.getStatus());
//  System.out.println(userJson);

// JerseyClient jerseyClient = JerseyClientBuilder.createClient();
//  jerseyClient.register(httpAuthenticationFeature);
  
//  String entity = client.target("http://localhost:8080/jersey-crud-example/api").path("users").path("user/100")
//  WebTarget target = jerseyClient.target(microservicesModel.getUrl());
//    WebTarget target = jerseyClient.target(microservicesModel.getUrl()).path(microservicesModel.getField().trim()).path(value);
//            System.out.println("Test--> target "+target.getUri().getPath());
//    Response response = target.request(MediaType.APPLICATION_JSON).get();
//            System.out.println("Test-->response.toString() "+ response.toString());
//    // Status HTTP de retorno
//    int status = response.getStatus();
//            System.out.println("Test---> status"+status);
//    // Lï¿½ um Carro (converte diretamente da string do JSON)
//    T c = (T)response.readEntity(tlocal.getClass());
    
//  Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON_TYPE);
//  String response = invocationBuilder.put(Entity.entity(requestBody, MediaType.APPLICATION_JSON_TYPE)).readEntity(String.class);



//  return response;
  
// 
//            Client client = ClientBuilder.newClient();
//            client.register(httpAuthenticationFeature);
//            System.out.println("Test--> name ");
//            String path ="/{" +microservicesModel.getField().trim() +"}";
//            System.out.println("Test-->path "+path);
//           String field =microservicesModel.getField().trim();
//            System.out.println("Test-->field "+field);
//       String  json =client
//                    .target(microservicesModel.getUrl())
//                    .path(path)
//                    .resolveTemplate(microservicesModel.getField().trim(), value)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(String.class);
       
//       
//        tlocal = (T)client
//                    .target(microservicesModel.getUrl())
//                    .path(path)
//                    .resolveTemplate(field, value)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(tlocal.getClass());
//            System.out.println("==================================================");
////            System.out.println("Test--> json "+json);
//            System.out.println("==================================================");
//            Jsonb jsonb = JsonbBuilder.create();
//     tlocal  = jsonb.fromJson(jsonb,(T)tlocal);
//             tlocal = (T)client
//                    .target(microservicesModel.getUrl())
//                    .path("/{" +microservicesModel.getField().trim() +"}")
//                    .resolveTemplate(microservicesModel.getField().trim(), value)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(tlocal.getClass());
             

             
//          tlocal =(T)client
//                    .target(microservicesModel.getUrl())
//                    .path("/{" +microservicesModel.getField().trim() +"}")
//                    .resolveTemplate(microservicesModel.getField().trim(), value)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(new GenericEntity<tlocal>());
//          tlocal =client
//                    .target(microservicesModel.getUrl())
//                    .path("/{" +microservicesModel.getField().trim() +"}")
//                    .resolveTemplate(microservicesModel.getField().trim(), value)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get( new GenericType<T>() {});
          
//          (Class<T>) 
         ;
//          tlocal = (T) client
//                    .target(microservicesModel.getUrl())
//                    .path("/{" +microservicesModel.getField().trim() +"}")
//                    .resolveTemplate(microservicesModel.getField().trim(), value)
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(tlocal.getClass());
          
          
          
          
          return tlocal;
          
           
         } catch (Exception e) {    
             System.out.println("clientEndPoint() "+e.getLocalizedMessage());
          excepcionManager(JmoordbUtil.nameOfClass() , JmoordbUtil.nameOfMethod(), e.getLocalizedMessage(), e);
        }

        return null;

    }// </editor-fold>
    
  
    
 
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpAuthenticationFeature httpAuthenticationFeature()">
    /**
     * Devuelve la autentificacion para ser usada con el Client
     *
     * @return
     */
    public HttpAuthenticationFeature httpAuthenticationFeature(String user, String password) {
        HttpAuthenticationFeature httpAuthenticationFeature = null;
        try {
          String   userAutentification = JmoordbUtil.desencriptar(user);
            String passwordAutentification = JmoordbUtil.desencriptar(password);
            httpAuthenticationFeature = HttpAuthenticationFeature.basic(userAutentification,  passwordAutentification);

        } catch (Exception e) {
            
            System.out.println(" httpAuthenticationFeature() "+e.getLocalizedMessage());
 
        }

        return httpAuthenticationFeature;
    }

    // </editor-fold>
}
