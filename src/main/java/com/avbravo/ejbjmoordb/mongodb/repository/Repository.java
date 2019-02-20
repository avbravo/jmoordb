/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.mongodb.repository;
// <editor-fold defaultstate="collapsed" desc="import">

import com.avbravo.ejbjmoordb.DatePatternBeans;
import com.avbravo.ejbjmoordb.EmbeddedBeans;
import com.avbravo.ejbjmoordb.FieldBeans;
import com.avbravo.ejbjmoordb.JmoordbException;
import com.avbravo.ejbjmoordb.PrimaryKey;
import com.avbravo.ejbjmoordb.ReferencedBeans;
import com.avbravo.ejbjmoordb.mongodb.internal.DocumentToJavaJmoordbResult;
import com.avbravo.ejbjmoordb.mongodb.internal.DocumentToJavaMongoDB;
import com.avbravo.ejbjmoordb.mongodb.internal.JavaToDocument;
import com.avbravo.ejbjmoordb.pojos.JmoordbResult;
import com.avbravo.ejbjmoordb.util.Analizador;
import com.avbravo.ejbjmoordb.util.Util;
import com.github.vincentrussell.query.mongodb.sql.converter.MongoDBQueryHolder;
import com.github.vincentrussell.query.mongodb.sql.converter.QueryConverter;
import com.mongodb.Block;
import com.mongodb.CursorType;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.out;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.Spliterators.iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.bson.Document;
import org.bson.conversions.Bson;
// </editor-fold>

/**
 *
 * @author avbravo
 * @param <T>
 */
public abstract class Repository<T> implements InterfaceRepository {
// <editor-fold defaultstate="collapsed" desc="field">

    protected abstract MongoClient getMongoClient();

    Integer contador = 0;
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
    List<EmbeddedBeans> embeddedBeansList = new ArrayList<>();
    List<ReferencedBeans> referencedBeansList = new ArrayList<>();
    List<DatePatternBeans> datePatternBeansList = new ArrayList<>();
    List<FieldBeans> fieldBeansList = new ArrayList<>();
    Exception exception;
    Util util = new Util();
    // </editor-fold>
//MongoDatabase db_;
// <editor-fold defaultstate="collapsed" desc="get/set">

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getMongoDatabase()">
    @Override
    public MongoDatabase getMongoDatabase() {
        try {

            MongoDatabase db = getMongoClient().getDatabase(database);
            if (db == null) {
                //Test.msg("+++AbstractFacade.getMonogDatabase() == null");
            } else {
                //Test.msg("+++AbstractFacade.getMonogDatabase() != null");
            }
            return db;
        } catch (Exception ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            new JmoordbException("getMongoDatabase() " + ex.getLocalizedMessage());
            exception = new Exception("getMongoDatabase() " + ex.getLocalizedMessage());
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
        embeddedBeansList = new ArrayList<>();
        referencedBeansList = new ArrayList<>();
        datePatternBeansList = new ArrayList<>();
        fieldBeansList = new ArrayList<>();

        /**
         * lee las anotaciones @Id para obtener los PrimaryKey del documento
         */
        final Field[] fields = entityClass.getDeclaredFields();
        Analizador analizador = new Analizador();
        analizador.analizar(fields);
        primaryKeyList = analizador.getPrimaryKeyList();
        embeddedBeansList = analizador.getEmbeddedBeansList();
        referencedBeansList = analizador.getReferencedBeansList();
        datePatternBeansList = analizador.getDatePatternBeansList();
        fieldBeansList = analizador.getFieldBeansList();

        //Llave primary
        if (primaryKeyList.isEmpty()) {
            exception = new Exception("No have primaryKey() ");

        }
        if (fieldBeansList.isEmpty()) {
            exception = new Exception("No have fields() ");
        }
//db_ = getMongoDatabase();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getMongoDatabase()">
    // </editor-fold>
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

        } catch (Exception ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            new JmoordbException("save() " + ex.getLocalizedMessage());
            exception = new Exception("save() " + ex.getLocalizedMessage());
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

                t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedBeansList, referencedBeansList);
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

        } catch (Exception ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
            new JmoordbException("save() " + ex.getLocalizedMessage());
            exception = new Exception("save() " + ex.getLocalizedMessage());
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
        return javaToDocument.toDocument(t, embeddedBeansList, referencedBeansList);
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
                    Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
                    exception = new Exception("getDocumentPrimaryKey() ", e);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "getDocumentPrimaryKey()").log(Level.SEVERE, null, e);
            exception = new Exception("getDocumentPrimaryKey() ", e);
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
            Logger.getLogger(Repository.class.getName() + "getIndexPrimaryKey()").log(Level.SEVERE, null, e);
            exception = new Exception("getIndexPrimaryKey() ", e);
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
            Logger.getLogger(Repository.class.getName() + "createIndex()").log(Level.SEVERE, null, e);
            exception = new Exception("createIndex() ", e);
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
                    Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
                    exception = new Exception("findById() ", e);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "findById()").log(Level.SEVERE, null, e);
            exception = new Exception("findById() ", e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findById(Document doc)">
    public Optional<T> findById(Document doc) {

        try {
            //  t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedBeansList, referencedBeansList);
            T t_ = (T) find(doc);

            if (t_ == null) {
                // no lo encontro
            } else {
                return Optional.of(t_);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "findById()").log(Level.SEVERE, null, e);
            exception = new Exception("findById() ", e);
        }
        return Optional.empty();
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

            //  t1 = (T) documentToJava.fromDocument(entityClass, doc, embeddedBeansList, referencedBeansList);
            T t_ = (T) find(doc);

            if (t_ == null) {
                // no lo encontro
            } else {
                return Optional.of(t_);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "findById()").log(Level.SEVERE, null, e);
            exception = new Exception("findById() ", e);
        }
        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="find(String key, Object value">
    @Override
    public Optional<T> find(String key, Object value) {
        try {

            //   Object t = entityClass.newInstance();
            MongoDatabase db = getMongoClient().getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(new Document(key, value));

            haveElements = false;
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        haveElements = true;
                        tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "find()").log(Level.SEVERE, null, e);
                        exception = new Exception("find() ", e);
                    }

                }
            });
            if (haveElements) {

                return Optional.of(tlocal);
            }
            return Optional.empty();

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("find() ", e);

        }

        return Optional.empty();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="T search(String key, Object value)">
    public T search(String key, Object value) {
        try {

            MongoDatabase db = getMongoClient().getDatabase(database);

            if (db == null) {
                return null;
            } else {
            }
            FindIterable<Document> iterable = db.getCollection(collection).find(new Document(key, value));
            //Test.msg("+++ paso iterable");
            haveElements = false;
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        haveElements = true;
                        tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "search()").log(Level.SEVERE, null, e);
                        exception = new Exception("search() ", e);
                    }

                }
            });
            if (haveElements) {
                return tlocal;
            }
            return null;

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("search() ", e);

        }

        return null;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T search(String key, Integer value)">

    public T search(String key, Integer value) {
        try {

            MongoDatabase db = getMongoClient().getDatabase(database);

            if (db == null) {
                return null;
            } else {
            }
            FindIterable<Document> iterable = db.getCollection(collection).find(new Document(key, value));
            //Test.msg("+++ paso iterable");
            haveElements = false;
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        haveElements = true;
                        tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "search()").log(Level.SEVERE, null, e);
                        exception = new Exception("search() ", e);
                    }

                }
            });
            if (haveElements) {
                return tlocal;
            }
            return null;

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("search() ", e);

        }

        return null;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="T search(String key, String value)">

    public T search(String key, String value) {
        try {

            MongoDatabase db = getMongoClient().getDatabase(database);

            if (db == null) {
                return null;
            } else {
            }
            FindIterable<Document> iterable = db.getCollection(collection).find(new Document(key, value));
            //Test.msg("+++ paso iterable");
            haveElements = false;
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        haveElements = true;
                        tlocal = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "search()").log(Level.SEVERE, null, e);
                        exception = new Exception("search() ", e);
                    }

                }
            });
            if (haveElements) {
                return tlocal;
            }
            return null;

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("search() ", e);

        }

        return null;

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
            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(document);
            tlocal = (T) iterableSimple(iterable);
            return Optional.of(tlocal);
            //return (T) tlocal;
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("find() ", e);
            new JmoordbException("find()");
        }
        return Optional.empty();
//        return null;
    }
// </editor-fold>
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
            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("find() ", e);
            new JmoordbException("find()");
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
            MongoDatabase db = getMongoClient().getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(doc).limit(1);
            tlocal = (T) iterableSimple(iterable);
            return Optional.of(tlocal);

            //return (T) tlocal;
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("find() ", e);
            new JmoordbException("find()");
        }
        return Optional.empty();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findInternal(Document document)">
    private T findInternal(Document document) {
        try {
            //   Object t = entityClass.newInstance();
            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(document);
            tlocal = (T) iterableSimple(iterable);
            return tlocal;
            //return (T) tlocal;
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("find() ", e);
            new JmoordbException("find()");
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
            //      //Test.msg("$$$$$$$iterable simple");
            haveElements = false;

            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        haveElements = true;
                        t1 = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "find()").log(Level.SEVERE, null, e);
                        exception = new Exception("find() ", e);
                    }

                }
            });

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("iterableSimple() ", e);

        }
        if (haveElements) {
            return (T) t1;
        }
        return null;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="iterableList(FindIterable<Document> iterable)">
    private List< T> iterableList(FindIterable<Document> iterable) {
        List< T> l = new ArrayList<>();
        try {
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        t1 = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                        l.add(t1);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "find()").log(Level.SEVERE, null, e);
                        exception = new Exception("find() ", e);
                    }

                }
            });

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("iterableSimple() ", e);

        }

        return l;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="iterableList(FindIterable<Document> iterable)">

    private List< T> processAggregateIterable(AggregateIterable<Document> iterable) {
        List< T> l = new ArrayList<>();
        try {
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        t1 = (T) documentToJava.fromDocument(entityClass, document, embeddedBeansList, referencedBeansList);
                        l.add(t1);
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "find()").log(Level.SEVERE, null, e);
                        exception = new Exception("find() ", e);
                    }

                }
            });

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("iterableSimple() ", e);

        }

        return l;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="processAggregateIterableJmoordbResult(AggregateIterable<Document> iterable)">

    private List< JmoordbResult> processAggregateIterableJmoordbResult(AggregateIterable<Document> iterable) {
        List<JmoordbResult> l = new ArrayList<>();
        List<Map<String, Object>> lObject = new ArrayList<>();
        try {
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {

                        // document.remove("_id");
                        Map<String, Object> map = new HashMap<>(document);
                        lObject.add(map);

                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "find()").log(Level.SEVERE, null, e);
                        exception = new Exception("find() ", e);
                        System.out.println("apply error() " + e.getLocalizedMessage());
                    }

                }
            });
            for (Map m : lObject) {
                JmoordbResult jmoordbResult = new JmoordbResult();
                for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
//                    System.out.println("====>key "+entry.getKey() + " value "+entry.getValue().toString());
                    jmoordbResult.put(entry.getKey(), entry.getValue().toString());

                }
                l.add(jmoordbResult);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("iterableSimple() ", e);
            System.out.println("error() " + e.getLocalizedMessage());
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
            Logger.getLogger(Repository.class.getName() + "sizeOfPage()").log(Level.SEVERE, null, e);
            exception = new Exception("sizeOfPage()", e);
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
            Logger.getLogger(Repository.class.getName() + "listOfPage()").log(Level.SEVERE, null, e);
            exception = new Exception("listOfPage()", e);
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
                MongoDatabase db = getMongoClient().getDatabase(database);
                FindIterable<Document> iterable = db.getCollection(collection).find(documento);

                iterable.forEach(new Block<Document>() {
                    @Override
                    public void apply(final Document document) {
                        try {
                            contador++;
                        } catch (Exception e) {
                            Logger.getLogger(Repository.class.getName() + "count()").log(Level.SEVERE, null, e);
                            exception = new Exception("count()", e);
                        }
                    }
                });

            } else {
                // no tiene parametros
                contador = (int) getMongoClient().getDatabase(database).getCollection(collection).count();

            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "count()").log(Level.SEVERE, null, e);
            exception = new Exception("count()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(filter);

            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {
                        contador++;
                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "count()").log(Level.SEVERE, null, e);
                        exception = new Exception("count()", e);
                    }
                }
            });

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "count()").log(Level.SEVERE, null, e);
            exception = new Exception("count()", e);
        }
        return contador;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="findAll(Document... docSort) ">
    /**
     *
     * @param document
     * @return
     */
    public List< T> findAll(Document... docSort) {
        list = new ArrayList<>();
        Document sortQuery = new Document();
        try {
            if (docSort.length != 0) {
                sortQuery = docSort[0];

            }

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find().sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findAll() ", e);
            new JmoordbException("findAll()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find().skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findPagination() ", e);
            new JmoordbException("findPagination()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(filter).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findPagination() ", e);
            new JmoordbException("findPagination()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(doc).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findPagination() ", e);
            new JmoordbException("findPagination()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            Document iterable = db.getCollection(collection).findOneAndUpdate(doc, inc, findOneAndUpdateOptions);

            try {

                t1 = (T) documentToJava.fromDocument(entityClass, iterable, embeddedBeansList, referencedBeansList);

            } catch (Exception e) {
                Logger.getLogger(Repository.class.getName() + "findOneAndUpdate()").log(Level.SEVERE, null, e);
                exception = new Exception("findOneAndUpdate()", e);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findOneAndUpdate()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            Document iterable = db.getCollection(collection).findOneAndUpdate(doc, inc, findOneAndUpdateOptions);

            try {
                t1 = (T) documentToJava.fromDocument(entityClass, iterable, embeddedBeansList, referencedBeansList);
//                Method method = entityClass.getDeclaredMethod("toPojo", Document.class);
//                list.add((T) method.invoke(t, iterable));
            } catch (Exception e) {
                Logger.getLogger(Repository.class.getName() + "findOneAndUpdate()").log(Level.SEVERE, null, e);
                exception = new Exception("findOneAndUpdate()", e);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findOneAndUpdate()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            Document iterable = db.getCollection(collection).findOneAndUpdate(doc, inc, findOneAndUpdateOptions);

            try {
                t1 = (T) documentToJava.fromDocument(entityClass, iterable, embeddedBeansList, referencedBeansList);

            } catch (Exception e) {
                Logger.getLogger(Repository.class.getName() + "findOneAndUpdate()").log(Level.SEVERE, null, e);
                exception = new Exception("findOneAndUpdate()", e);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findOneAndUpdate()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findBy() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(builder).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findBy() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("sql() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(documentList);

            list = processAggregateIterable(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("aggregate() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(documentList);
            list = processAggregateIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("aggregate() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            AggregateIterable<Document> iterable = db.getCollection(collection).aggregate(builder);
            list = processAggregateIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("aggregateFromBuilder ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);
            list = iterableList(iterable);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findBy() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(filter).sort(sortQuery);
            list = iterableList(iterable);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findBy() ", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);

            FindIterable<Document> iterable = db.getCollection(collection).find(filter).sort(sortQuery);
            list = iterableList(iterable);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findBy() ", e);
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
            Logger.getLogger(Repository.class.getName() + "drop()").log(Level.SEVERE, null, e);
            exception = new Exception("listCollecctions() ", e);
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
            Logger.getLogger(Repository.class.getName() + "existsCollection()").log(Level.SEVERE, null, e);
            exception = new Exception("existsCollection() ", e);
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
            Logger.getLogger(Repository.class.getName() + "existsCollection()").log(Level.SEVERE, null, e);
            exception = new Exception("existsCollection() ", e);
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
            Logger.getLogger(Repository.class.getName() + "drop()").log(Level.SEVERE, null, e);
            exception = new Exception("drop() ", e);
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
            Logger.getLogger(Repository.class.getName() + "drop()").log(Level.SEVERE, null, e);
            exception = new Exception("drop() ", e);
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
            Logger.getLogger(Repository.class.getName() + "drop()").log(Level.SEVERE, null, e);
            exception = new Exception("drop() ", e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<T> findHelperSort(String predicate, Document doc, String key, String value)">
    public List<T> findHelperSort(String predicate, Document doc, String key, String value) {
        try {

            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findHelperSort()", e);
        }
        return list;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> findHelperSortPagination(String predicate, Document doc, String key, String value, Integer pageNumber, Integer rowsForPage)">

    public List<T> findHelperSortPagination(String predicate, Document doc, String key, String value, Integer pageNumber, Integer rowsForPage) {
        try {

            Object t = entityClass.newInstance();
            list = new ArrayList<>();

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findHelperSort()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("helpers()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("helpers()", e);
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
            public FindIterable<Document> modifiers(Bson bson) {
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
            public void forEach(Block<? super Document> block) {
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
            public FindIterable<Document> maxScan(long l) {
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
            public FindIterable<Document> snapshot(boolean bln) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            Logger.getLogger(Repository.class.getName() + "delete()").log(Level.SEVERE, null, e);
            exception = new Exception("delete() ", e);
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
            Logger.getLogger(Repository.class.getName() + "remove()").log(Level.SEVERE, null, e);
            exception = new Exception("remove() ", e);
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
            Logger.getLogger(Repository.class.getName() + "remove()").log(Level.SEVERE, null, e);
            exception = new Exception("delete() ", e);
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
            Logger.getLogger(Repository.class.getName() + "deleteManye()").log(Level.SEVERE, null, e);
            exception = new Exception("deleteMany() ", e);
        }
        return cont;
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Integer deleteAll()">

    /**
     * Remove all documment of a collection
     *
     * @return count of document delete
     */
    public Integer deleteAll() {
        Integer cont = 0;
        try {
            DeleteResult dr = getMongoDatabase().getCollection(collection).deleteMany(new Document());

            cont = (int) dr.getDeletedCount();
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "removeDocument()").log(Level.SEVERE, null, e);
            exception = new Exception("removeAll() ", e);
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
            Logger.getLogger(Repository.class.getName() + "updateOne()").log(Level.SEVERE, null, e);
            exception = new Exception("updateOne() ", e);
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
            Logger.getLogger(Repository.class.getName() + "updateOne()").log(Level.SEVERE, null, e);
            exception = new Exception("updateOne() ", e);
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
            Logger.getLogger(Repository.class.getName() + "updateMany()").log(Level.SEVERE, null, e);
            exception = new Exception("updateMany() ", e);
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
            Logger.getLogger(Repository.class.getName() + "replaceOne()").log(Level.SEVERE, null, e);
            exception = new Exception("replaceOne() ", e);
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
            Logger.getLogger(Repository.class.getName() + "replaceOne()").log(Level.SEVERE, null, e);
            exception = new Exception("replaceOne() ", e);
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
            Logger.getLogger(Repository.class.getName() + "updateOne()").log(Level.SEVERE, null, e);
            exception = new Exception("updateOne() ", e);
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
                    Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
                    exception = new Exception("getDocumentPrimaryKey() ", e);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "getDocumentPrimaryKey()").log(Level.SEVERE, null, e);
            exception = new Exception("getDocumentPrimaryKey() ", e);
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
            Logger.getLogger(Repository.class.getName() + "getDocumentPrimaryKey()").log(Level.SEVERE, null, e);
            exception = new Exception("getDocumentPrimaryKey() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDate() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDate() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDate() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDate() ", e);
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
            Date dateStart = setHourToDate(datestartvalue, 0,0);
            Date dateEnd = setHourToDate(datelimitvalue, 23,59);
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd)
            );

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDateWithoutHours()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDateWithoutHours() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDate() ", e);
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
            Date dateStart = setHourToDate(datestartvalue, 0,0);
            Date dateEnd = setHourToDate(datelimitvalue, 23,59);
            Bson dates = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));
            Bson filter = Filters.or(
                    myfilter, dates
            );

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDate() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
             Date dateStart = setHourToDate(datestartvalue, 0,0);
            Date dateEnd = setHourToDate(datelimitvalue, 23,59);
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
 Date dateStart = setHourToDate(datestartvalue, 0,0);
            Date dateEnd = setHourToDate(datelimitvalue, 23,59);
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
             Date dateStart = setHourToDate(datestartvalue, 0,0);
            Date dateEnd = setHourToDate(datelimitvalue, 23,59);
            Bson dates = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));
            Bson filter = Filters.or(
                    myfilter, dates
            );

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenInteger()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenInteger() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenIntegerPagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenIntegerPagination() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenIntege()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenInteger() ", e);
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
            Logger.getLogger(Repository.class.getName() + "filterBetweenIntege()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenInteger() ", e);
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
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterDayWithoutHourPagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterDayWithoutHourPagination ", e);
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
            Bson filter = Filters.and(Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterDayWithoutHour()").log(Level.SEVERE, null, e);
            exception = new Exception("filterDayWithoutHour() ", e);
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
            Bson filter = Filters.and(myfilter, Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterDayWithoutHour()").log(Level.SEVERE, null, e);
            exception = new Exception("filterDayWithoutHour() ", e);
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
            Bson date = Filters.and(Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));
            Bson filter = Filters.and(myfilter, date);

            list = filters(filter, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterDayWithoutHour()").log(Level.SEVERE, null, e);
            exception = new Exception("filterDayWithoutHour() ", e);
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
            Bson filter = Filters.and(Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterDayWithoutHourPagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterDayWithoutHourPagination() ", e);
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
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Bson filter = Filters.and(
                    myfilter,
                    Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterBetweenDatePagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterBetweenDatePagination ", e);
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
            Bson filter = Filters.and(
                    Filters.eq(secondaryfield, secondaryfieldvalue),
                    Filters.gte(fielddate, datevalue),
                    Filters.lte(fielddate, datevalue));

            list = filtersPagination(filter, pageNumber, rowsForPage, sortQuery);
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "filterDayWithoutHourPagination()").log(Level.SEVERE, null, e);
            exception = new Exception("filterDayWithoutHourPagination ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection)
                    .find(new Document("$text", new Document("$search", value)
                            .append("$caseSensitive", caseSensitive)
                            .append("$diacriticSensitive", diacriticSensitive)));

            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findText()", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection)
                    .find(new Document("$text", new Document("$search", value)
                            .append("$caseSensitive", caseSensitive)
                            .append("$diacriticSensitive", diacriticSensitive))).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage);

            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findText()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegex()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegex()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegex()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegex()", e);
        }
        return list;
    }// </editor-fold>

//    // <editor-fold defaultstate="collapsed" desc="findRegex(String key, String value, Boolean caseSensitive,Bson filter, Document... docSort) ">
//
//    /**
//     *
//     * @param key
//     * @param value
//     * @param docSort
//     * @return
//     */
//    public List<T> findRegex(String key, String value, Boolean caseSensitive, Bson filter, Document... docSort) {
//        Document sortQuery = new Document();
//        list = new ArrayList<>();
//
//        try {
//
//            if (docSort.length != 0) {
//                sortQuery = docSort[0];
//
//            }
//            Object t = entityClass.newInstance();
//            Pattern regex = Pattern.compile(value);
//
//            MongoDatabase db = getMongoClient().getDatabase(database);
//            FindIterable<Document> iterable;
//            if (!caseSensitive) {
//                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value))).filter(filter).sort(sortQuery);
////iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
//            } else {
//                iterable = db.getCollection(collection)
//                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i"))).filter(filter).sort(sortQuery);
////               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));
//
//            }
//
//            list = iterableList(iterable);
//
//        } catch (Exception e) {
//            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
//            exception = new Exception("findRegex()", e);
//        }
//        return list;
//    }// </editor-fold>    
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value))).sort(sortQuery);
            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i"))).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexInText()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value)).append(keySecond, valueSecond)).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i")).append(keySecond, valueSecond)).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegex()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable;
            if (!caseSensitive) {
                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", value)).append(keySecond, valueSecond).append(keyThree, valueThree)).sort(sortQuery);

            } else {
                iterable = db.getCollection(collection)
                        .find(new Document(key, new Document("$regex", value).append("$options", "i")).append(keySecond, valueSecond).append(keyThree, valueThree)).sort(sortQuery);

            }

            list = iterableList(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegex()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexPagination()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexPagination()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexPagination()", e);
        }
        return list;
    }// </editor-fold>
//    // <editor-fold defaultstate="collapsed" desc="findRegexPagination(String key, String value, Boolean caseSensitive, Bson filter, Integer pageNumber, Integer rowsForPage, Document... docSort)">
//
//    /**
//     *
//     * @param key
//     * @param value
//     * @param docSort
//     * @return
//     */
//    public List<T> findRegexPagination(String key, String value, Boolean caseSensitive, Bson filter, Integer pageNumber, Integer rowsForPage, Document... docSort) {
//        Document sortQuery = new Document();
//        list = new ArrayList<>();
//
//        try {
//
//            if (docSort.length != 0) {
//                sortQuery = docSort[0];
//
//            }
//            Object t = entityClass.newInstance();
//            Pattern regex = Pattern.compile(value);
//
//            MongoDatabase db = getMongoClient().getDatabase(database);
//            FindIterable<Document> iterable;
//            if (!caseSensitive) {
//                iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", "^" + value))).filter(filter).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
//                        limit(rowsForPage).sort(sortQuery);
////iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex)));
//            } else {
//                iterable = db.getCollection(collection)
//                        .find(new Document(key, new Document("$regex", "^" + value).append("$options", "i"))).filter(filter).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
//                        limit(rowsForPage).sort(sortQuery);
////               iterable = db.getCollection(collection).find(new Document(key, new Document("$regex", regex).append("$options", "si")));
//
//            }
//
//            list = iterableList(iterable);
//
//        } catch (Exception e) {
//            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
//            exception = new Exception("findRegexPagination()", e);
//        }
//        return list;
//    }// </editor-fold>

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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexInTextPagination()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexInTextPagination()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findRegexInTextPagination()", e);
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
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("complete()", e);
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(docQuery);

            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findAll() ", e);
            new JmoordbException("findAll()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(doc).sort(sortQuery);

            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findAll() ", e);
            new JmoordbException("findAll()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).find(filter).sort(sortQuery);

            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findAll() ", e);
            new JmoordbException("findAll()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(sortQuery).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage);
            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findPagination() ", e);
            new JmoordbException("findPagination()");
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

            MongoDatabase db = getMongoClient().getDatabase(database);
            FindIterable<Document> iterable = db.getCollection(collection).
                    find(doc).skip(pageNumber > 0 ? ((pageNumber - 1) * rowsForPage) : 0).
                    limit(rowsForPage).sort(sortQuery);
            list = processUnknownIterableJmoordbResult(iterable);

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("findPagination() ", e);
            new JmoordbException("findPagination()");
        }

        return list;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="processUnknownIterableJmoordbResult(AggregateIterable<Document> iterable)">
    private List< JmoordbResult> processUnknownIterableJmoordbResult(FindIterable<Document> iterable) {
        List<JmoordbResult> l = new ArrayList<>();
        List<Map<String, Object>> lObject = new ArrayList<>();
        try {
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    try {

                        Map<String, Object> map = new HashMap<>(document);
                        lObject.add(map);

                    } catch (Exception e) {
                        Logger.getLogger(Repository.class.getName() + "find()").log(Level.SEVERE, null, e);
                        exception = new Exception("find() ", e);
                        System.out.println("apply error() " + e.getLocalizedMessage());
                    }

                }
            });
            for (Map m : lObject) {
                JmoordbResult jmoordbResult = new JmoordbResult();
                for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                    jmoordbResult.put(entry.getKey(), entry.getValue().toString());

                }
                l.add(jmoordbResult);
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, e);
            exception = new Exception("processUnkownIterableJmoordbResult() ", e);
            System.out.println("error() " + e.getLocalizedMessage());
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

            MongoDatabase db = getMongoClient().getDatabase(database);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            UpdateResult updateResult = db.getCollection(collection).replaceOne(builder, docUpdate);
            return (int) updateResult.getModifiedCount();

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "unknownUpdate()").log(Level.SEVERE, null, e);
            exception = new Exception("unknownUpdate() ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteOne(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "unknownDelete()").log(Level.SEVERE, null, e);
            exception = new Exception("unknownDelete() ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteOne(builder);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "unknownDelete()").log(Level.SEVERE, null, e);
            exception = new Exception("unknownDelete() ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteMany(new Document());
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "unknownDeleteAll()").log(Level.SEVERE, null, e);
            exception = new Exception("unknownDeleteAll() ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteMany(doc);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "unknownDeleteMany()").log(Level.SEVERE, null, e);
            exception = new Exception("unknownDeleteMany() ", e);
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
            MongoDatabase db = getMongoClient().getDatabase(database);
            DeleteResult dr = db.getCollection(collection).deleteMany(builder);
            if (dr.getDeletedCount() >= 0) {
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "unknownDeleteAll()").log(Level.SEVERE, null, e);
            exception = new Exception("unknownDeleteAll() ", e);
        }
        return false;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isAvailableBetweenDateHour(Bson filter, String namefieldOfStart,Date valueStart,namefieldOfEnd,Date valueEnd )">
    /**
     * Devuelve true si no hay registros con la condicion fechay hora de inicio y fin  y el filtro que se pasa como parametro
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

            Bson _filter = Filters.and(filter, or(b, c_e_f_g_h_l, d, i, j, k));

            List<T> list = findBy(_filter);

            if (list.isEmpty()) {
                return true;
            }

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "isAvailableBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("isAvailableBetweenDate() ", e);

        }
        return false;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<T> notAvailableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) ">
    /**
     * Devuelve una lista de los elementos que estan en ese rango de fechas y que cumplan la condicion del filtro que se
     * pasa como parametro
     *
     * @param filter
     * @param namefieldOfStart
     * @param valueStart
     * @param namefieldOfEnd
     * @param valueEnd
     * @return Devuelve una lista de los elementos que estan en ese rango de fechas y que cumplan la condicion del filtro que se
     * pasa como parametro
     */
    public List<T> notAvailableBetweenDateHour(Bson filter, String namefieldOfStart, Date valueStart, String namefieldOfEnd, Date valueEnd) {
        try {
            //Vehiculos en viajes
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
            Logger.getLogger(Repository.class.getName() + "isAvailableBetweenDate()").log(Level.SEVERE, null, e);
            exception = new Exception("isAvailableBetweenDate() ", e);

        }
        return list;
    }
    // </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="setHourToDate()">
    /**
     * Asigna horas a la fecha util para cuando se desee buscar entre fechas que tengas horas excluyendolas
     * @param date
     * @param hour
     * @return 
     */
     private Date setHourToDate(Date date,Integer hour,Integer  minute) {
        
         Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, hour);
    calendar.add(Calendar.MINUTE, minute);
    return calendar.getTime();
     }
     
     // </editor-fold>
}
