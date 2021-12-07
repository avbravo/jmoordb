/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.internal;

import com.avbravo.jmoordb.EmbeddedModel;
import com.avbravo.jmoordb.JmoordbException;
import com.avbravo.jmoordb.MicroservicesModel;
import com.avbravo.jmoordb.ReferencedModel;
import com.avbravo.jmoordb.anotations.Microservices;
import com.avbravo.jmoordb.util.Analizador;
import com.avbravo.jmoordb.util.ClassDescriptor;
import com.avbravo.jmoordb.util.ClassDescriptorsCache;
import com.avbravo.jmoordb.util.FieldDescriptor;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.jmoordb.util.ReflectionUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class DocumentToJavaMongoDB<T> {

    private static final Logger LOG = Logger.getLogger(DocumentToJavaMongoDB.class.getName());
    Document documentMaster = new Document();

    private ClassDescriptorsCache cache = new ClassDescriptorsCache();
    List<EmbeddedModel> embeddedModelList = new ArrayList<>();
    List<ReferencedModel> referencedModelList = new ArrayList<>();
    List<MicroservicesModel> microservicesModelList = new ArrayList<>();
    ReferencedModel referencedModel = new ReferencedModel();
    MicroservicesModel microservicesModel = new MicroservicesModel();
    T t1;
// <editor-fold defaultstate="collapsed" desc="<T> T fromDocument(Class<T> clazz, Document document, List<EmbeddedModel> embeddedModelList, List<ReferencedModel> referencedModelList, List<MicroservicesModel> microservicesModelList)">    

    @SuppressWarnings("unchecked")
    public <T> T fromDocument(Class<T> clazz, Document document, List<EmbeddedModel> embeddedModelList, List<ReferencedModel> referencedModelList, List<MicroservicesModel> microservicesModelList) {

        if (document == null) {
            return null;
        }
        //Guarda el documento pasado como argumento
        documentMaster = document;

        this.embeddedModelList = embeddedModelList;
        this.referencedModelList = referencedModelList;
        this.microservicesModelList = microservicesModelList;
        ClassDescriptor classDescriptor = cache.get(clazz);
        Object object = classDescriptor.newInstance();
        for (FieldDescriptor fieldDescriptor : classDescriptor.getFields()) {
            try {
                //  //Test.msg("--------->Analizando: " + fieldDescriptor.getName());
                fieldDescriptor.getField().set(object,
                        fromDocumentRecursive(document.get(fieldDescriptor.getName()), fieldDescriptor));
            } catch (Exception e) {
                System.out.println("------------------------------------------------------------------------------------------------");
                System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
                System.out.println("Error " + e.getLocalizedMessage());
                System.out.println("------------------------------------------------------------------------------------------------");
                throw new JmoordbException("Failed to set field value " + fieldDescriptor.getName(), e);
            }
        }
        return (T) object;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Object fromDocumentRecursive(Object dbObject, FieldDescriptor fieldDescriptor)">    
    /**
     *
     * @param <T>
     * @param clazz
     * @param dbObject
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object fromDocumentRecursive(Object dbObject, FieldDescriptor fieldDescriptor) {
        try {
            //Test.msg("            fromDocumentRecursive: " + fieldDescriptor.getName());

            if (dbObject == null) {

                return fieldDescriptor.getDefaultValue();
            }

            Class<?> fieldType = fieldDescriptor.getField().getType();
            if (fieldDescriptor.isSimple()) {
                //Test.msg("   [isSimple] " + fieldDescriptor.getSimpleValue(dbObject));
                return fieldDescriptor.getSimpleValue(dbObject);
            } else if (fieldDescriptor.isArray()) {
                //Test.msg("   [ isArray]");
                BasicDBList dbList = (BasicDBList) dbObject;
                if (fieldType.getComponentType().isPrimitive()) {

                    return ReflectionUtils.dbListToArrayOfPrimitives(dbList, fieldType);
                }
                List list = new ArrayList();
                for (Object listEl : dbList) {

                    if (listEl == null || ReflectionUtils.isSimpleClass(listEl.getClass())) {

                        list.add(listEl);
                    } else {

                        list.add(fromDocument((Class<Object>) fieldType.getComponentType(), (Document) listEl, embeddedModelList, referencedModelList, microservicesModelList));
                    }
                }

                Object[] arrayPrototype = (Object[]) Array.newInstance(fieldType.getComponentType(), 0);
                return list.toArray(arrayPrototype);
            } else if (fieldDescriptor.isList()) {
                //Test.msg(" [isList()  ]" + fieldDescriptor.getName());
                if (isEmbedded(fieldDescriptor.getName())) {
                    //Test.msg("***************************************************************");
                    //Test.msg("Es  -->>isList()  AND  [isEmbedded]");

                    List<BasicDBObject> dbList = (ArrayList<BasicDBObject>) dbObject;

                    List list = (List) fieldDescriptor.newInstance();

                    for (Object listEl : dbList) {

                        if (ReflectionUtils.isSimpleClass(listEl.getClass())) {

                            list.add(listEl);
                        } else {
                            final Field[] fields = fieldDescriptor.getClass().getDeclaredFields();

                            //Test.msg("      Obtuve los campos..invocare al analizdor");
                            Analizador analizador = new Analizador();
                            analizador.analizar(fields);
                            //Test.msg("------Ahora usare la lista del analizador...");
                            list.add(fromDocument(ReflectionUtils.genericType(fieldDescriptor.getField()), (Document) listEl, analizador.getEmbeddedBeansList(), analizador.getReferencedBeansList(), analizador.getMicroservicesModelList()));
//                            list.add(fromDocument(ReflectionUtils.genericType(fieldDescriptor.getField()), (Document) listEl, embeddedModelList, referencedModelList));
                        }
                    }

                    return list;
                } else {
                    if (isReferenced(fieldDescriptor.getName())) {
                        //Referenciado
                        //Test.msg("     [es Referenciado]");
                        if (referencedModel.getLazy()) {
                            //Test.msg("[    Lazy == true no carga los relacionados ]");

                            List<BasicDBObject> dbList = (ArrayList<BasicDBObject>) dbObject;
                            List list = (List) fieldDescriptor.newInstance();
                            for (Object listEl : dbList) {
                                if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
                                    list.add(listEl);
                                } else {
                                    list.add(fromDocument(ReflectionUtils.genericType(fieldDescriptor.getField()), (Document) listEl, embeddedModelList, referencedModelList, microservicesModelList));
                                }
                            }

                            return list;
                        } else {
                            //Test.msg("[    Lazy == false carga los relacionados ]");
//
                            List<BasicDBObject> dbList = (ArrayList<BasicDBObject>) dbObject;
                            List list = (List) fieldDescriptor.newInstance();

                            for (Object listEl : dbList) {

                                if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
                                    list.add(listEl);
                                } else {
                                    Document doc = (Document) listEl;
                                    Class[] paramString = new Class[2];
                                    paramString[0] = String.class;
                                    Class cls = Class.forName(referencedModel.getRepository());
//                                    Object obj = cls.newInstance();
                                    Object obj = lookUpClassInBeanManager(cls);
                                    Method method;
                                    String value = "";
                                    if (referencedModel.getJavatype().toLowerCase().equals("integer")) {
                                        //@Id de tipo Integer
                                        Integer n = (Integer) doc.get(referencedModel.getField());
                                        //  method = cls.getDeclaredMethod("findById", String.class, Integer.class);
                                        //Invocar el metodod de la superclase
                                        Class parent = cls.getSuperclass();
                                        method = parent.getDeclaredMethod("search", String.class, Integer.class);

//////Test.msg(" voy a optional Integer");
                                        t1 = (T) method.invoke(obj, referencedModel.getField(), n);

                                    } else {
                                        //Test.msg(" voy a optional String");
                                        value = (String) doc.get(referencedModel.getField());
                                        paramString[1] = String.class;
                                        Class parent = cls.getSuperclass();
                                        method = parent.getDeclaredMethod("search", paramString);
//                                        method = cls.getMethod("search", paramString);
                                        //method = cls.getMethod("search", paramString);

                                        String[] param = {referencedModel.getField(), value};

                                        t1 = (T) method.invoke(obj, param);
                                    }

                                    list.add(t1);

                                }
                            }

                            return list;
                        }

                    } else {
                        //Test.msg("    No es[Embebido] ni  [Referenciado]");

                        // aqui validar que sea @Microservices
                        if (isReferenced(fieldDescriptor.getName())) {
                            //Referenciado
                            //Test.msg("     [es Referenciado]");
                            if (referencedModel.getLazy()) {
                                //Test.msg("[    Lazy == true no carga los relacionados ]");

                                List<BasicDBObject> dbList = (ArrayList<BasicDBObject>) dbObject;
                                List list = (List) fieldDescriptor.newInstance();
                                for (Object listEl : dbList) {
                                    if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
                                        list.add(listEl);
                                    } else {
                                        list.add(fromDocument(ReflectionUtils.genericType(fieldDescriptor.getField()), (Document) listEl, embeddedModelList, referencedModelList, microservicesModelList));
                                    }
                                }

                                return list;
                            } else {
                                //Test.msg("[    Lazy == false carga los relacionados ]");
//
                                List<BasicDBObject> dbList = (ArrayList<BasicDBObject>) dbObject;
                                List list = (List) fieldDescriptor.newInstance();

                                for (Object listEl : dbList) {

                                    if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
                                        list.add(listEl);
                                    } else {
                                        Document doc = (Document) listEl;
                                        Class[] paramString = new Class[2];
                                        paramString[0] = String.class;
                                        Class cls = Class.forName(referencedModel.getRepository());
//                                    Object obj = cls.newInstance();
                                        Object obj = lookUpClassInBeanManager(cls);
                                        Method method;
                                        String value = "";
                                        if (referencedModel.getJavatype().toLowerCase().equals("integer")) {
                                            //@Id de tipo Integer
                                            Integer n = (Integer) doc.get(referencedModel.getField());
                                            //  method = cls.getDeclaredMethod("findById", String.class, Integer.class);
                                            //Invocar el metodod de la superclase
                                            Class parent = cls.getSuperclass();
                                            method = parent.getDeclaredMethod("search", String.class, Integer.class);

//////Test.msg(" voy a optional Integer");
                                            t1 = (T) method.invoke(obj, referencedModel.getField(), n);

                                        } else {
                                            //Test.msg(" voy a optional String");
                                            value = (String) doc.get(referencedModel.getField());
                                            paramString[1] = String.class;
                                            Class parent = cls.getSuperclass();
                                            method = parent.getDeclaredMethod("search", paramString);
//                                        method = cls.getMethod("search", paramString);
                                            //method = cls.getMethod("search", paramString);

                                            String[] param = {referencedModel.getField(), value};

                                            t1 = (T) method.invoke(obj, param);
                                        }

                                        list.add(t1);

                                    }
                                }

                                return list;
                            }

                        } else {
                            List<BasicDBObject> foundDocument = (ArrayList<BasicDBObject>) dbObject;
                            List list = (List) fieldDescriptor.newInstance();

                            for (Object listEl : foundDocument) {
                                if (ReflectionUtils.isSimpleClass(listEl.getClass())) {
                                    list.add(listEl);
                                } else {

                                    list.add(fromDocument(ReflectionUtils.genericType(fieldDescriptor.getField()), (Document) listEl, embeddedModelList, referencedModelList, microservicesModelList));
                                }
                            }

                            return list;
                        }

                    }
                }

            } else if (fieldDescriptor.isSet()) {
                //Test.msg(" [isSet()  ]");
                BasicDBList dbList = (BasicDBList) dbObject;
                Set set = (Set) fieldDescriptor.newInstance();
                for (Object listEl : dbList) {

                    if (ReflectionUtils.isSimpleClass(listEl.getClass())) {

                        set.add(listEl);
                    } else {

                        set.add(fromDocument(ReflectionUtils.genericType(fieldDescriptor.getField()), (Document) listEl, embeddedModelList, referencedModelList, microservicesModelList));
                    }
                }
                return set;
            } else if (fieldDescriptor.isMap()) {
                //Test.msg(" isMap()  ]");
                DBObject dbMap = (DBObject) dbObject;
                Map map = (Map) fieldDescriptor.newInstance();
                for (Object key : dbMap.keySet()) {

                    Object mapEl = dbMap.get(key.toString());
                    if (mapEl == null || ReflectionUtils.isSimpleClass(mapEl.getClass())) {

                        map.put(key, mapEl);
                    } else {

                        map.put(key,
                                fromDocument(ReflectionUtils.genericTypeOfMapValue(fieldDescriptor.getField()),
                                        (Document) mapEl, embeddedModelList, referencedModelList, microservicesModelList));
                    }
                }
                return map;
            } else if (fieldDescriptor.isObject()) {
                //Test.msg("   [isObject] " + fieldDescriptor.getName() + " ]");
                if (isEmbedded(fieldDescriptor.getName())) {
                    //Test.msg("  [es Embebido]");
                    Object object = fieldDescriptor.newInstance();
                    for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                        try {
                            childDescriptor.getField()
                                    .set(object,
                                            fromDocumentRecursive(((Document) dbObject).get(childDescriptor.getName()),
                                                    childDescriptor));
                        } catch (Exception e) {
                            System.out.println("------------------------------------------------------------------------------------------------");
                            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
                            System.out.println("Error " + e.getLocalizedMessage());
                            System.out.println("------------------------------------------------------------------------------------------------");
                            throw new JmoordbException("[isObject]Failed to set field value " + childDescriptor.getName(), e);
                        }
                    }
                    return object;
                } else {
                    if (isReferenced(fieldDescriptor.getName())) {
                        //Referenciado
                        //Test.msg("         [es Referenciado] ");
                        if (referencedModel.getLazy()) {
                            //Test.msg("[    {Lazy == true} No carga los relacionados ]");
                            Object object = fieldDescriptor.newInstance();
                            for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                                try {
                                    if (childDescriptor.getField().getName().equals(referencedModel.getField())) {

                                        childDescriptor.getField()
                                                .set(object,
                                                        fromDocumentRecursive(((Document) dbObject).get(childDescriptor.getName()),
                                                                childDescriptor));
                                    }
                                } catch (Exception e) {
                                    System.out.println("------------------------------------------------------------------------------------------------");
                                    System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
                                    System.out.println("Error " + e.getLocalizedMessage());
                                    System.out.println("------------------------------------------------------------------------------------------------");
                                    throw new JmoordbException("Failed to set field value " + childDescriptor.getName(), e);
                                }
                            }
                            return object;
//                       
                        } else {
                            //Test.msg("                 [   Lazy == false carga los relacionados ]");
                            Class cls = Class.forName(referencedModel.getRepository());

                            Object obj = lookUpClassInBeanManager(cls);
                            Method method;

                            //             
                            if (referencedModel.getJavatype().toLowerCase().equals("integer")) {
                                //@Id de tipo Integer
                                Class[] paramString = new Class[2];
                                Class parent = cls.getSuperclass();
                                //  method = cls.getDeclaredMethod("findById", String.class, Integer.class);
                                method = parent.getDeclaredMethod("search", String.class, Integer.class);

                                Integer value = 0;
                                for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                                    if (childDescriptor.getField().getName().equals(referencedModel.getField())) {
                                        Object x = ((Document) dbObject).get(childDescriptor.getName());
                                        value = (Integer) childDescriptor.getSimpleValue(x);
                                    }
                                }

                                t1 = (T) method.invoke(obj, referencedModel.getField(), value);

                            } else {
                                Class[] paramString = new Class[2];
                                paramString[0] = String.class;
                                paramString[1] = String.class;
                                Class parent = cls.getSuperclass();
                                //method = cls.getDeclaredMethod("findById", paramString);
                                method = parent.getDeclaredMethod("search", paramString);

                                String value = "";
                                for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                                    if (childDescriptor.getField().getName().equals(referencedModel.getField())) {
                                        Object x = ((Document) dbObject).get(childDescriptor.getName());
                                        value = (String) childDescriptor.getSimpleValue(x);
                                    }
                                }
                                String[] param = {referencedModel.getField(), value};
                                t1 = (T) method.invoke(obj, param);
                            }

                            return t1;

                        }
                    } else {
                        /**
                         * Verifico si es microservicio
                         */
                        if (isMicroservices(fieldDescriptor.getName())) {

                            //Microservices
                            //Test.msg("         [es Microservices] ");
                            if (microservicesModel.getLazy()) {
                                //Test.msg("[    {Lazy == true} No carga los relacionados ]");
                                Object object = fieldDescriptor.newInstance();
                                for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                                    try {
                                        if (childDescriptor.getField().getName().equals(microservicesModel.getField())) {

                                            childDescriptor.getField()
                                                    .set(object,
                                                            fromDocumentRecursive(((Document) dbObject).get(childDescriptor.getName()),
                                                                    childDescriptor));
                                        }
                                    } catch (Exception e) {
                                        System.out.println("------------------------------------------------------------------------------------------------");
                                        System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
                                        System.out.println("Error " + e.getLocalizedMessage());
                                        System.out.println("------------------------------------------------------------------------------------------------");
                                        throw new JmoordbException("Failed to set field value " + childDescriptor.getName(), e);
                                    }
                                }
                                return object;
//                       
                            } else {
                                //Test.msg("                 [   Lazy == false carga los relacionados ]");
                                Class cls = Class.forName(microservicesModel.getRepository());

                                Object obj = lookUpClassInBeanManager(cls);
                                Method method;

                                //             
                                if (microservicesModel.getJavatype().toLowerCase().equals("integer")) {
                                    //@Id de tipo Integer
                                    Class[] paramString = new Class[2];
                                    Class parent = cls.getSuperclass();
                                    //  method = cls.getDeclaredMethod("findById", String.class, Integer.class);
                                    method = parent.getDeclaredMethod("clientEndPoint", MicroservicesModel.class, String.class, Integer.class);

                                    Integer value = 0;
                                    for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                                        if (childDescriptor.getField().getName().equals(microservicesModel.getField())) {
                                            Object x = ((Document) dbObject).get(childDescriptor.getName());
                                            value = (Integer) childDescriptor.getSimpleValue(x);
                                        }
                                    }

                                    t1 = (T) method.invoke(obj, microservicesModel,microservicesModel.getField(), value);

                                } else {
                                    Class[] paramString = new Class[3];
                                    paramString[0] = MicroservicesModel.class;
                                    paramString[1] = String.class;
                                    paramString[2] = String.class;
                                    Class parent = cls.getSuperclass();
                                    //method = cls.getDeclaredMethod("findById", paramString);
                                    method = parent.getDeclaredMethod("clientEndPoint", paramString);

                                    String value = "";
                                    for (FieldDescriptor childDescriptor : fieldDescriptor.getChildren()) {
                                        if (childDescriptor.getField().getName().equals(microservicesModel.getField())) {
                                            Object x = ((Document) dbObject).get(childDescriptor.getName());
                                            value = (String) childDescriptor.getSimpleValue(x);
                                        }
                                    }
                                    Object[] param = {microservicesModel,microservicesModel.getField(), value};
                                    t1 = (T) method.invoke(obj, param);
                                }

                                return t1;

                            }
                        } else {

                            //Test.msg("                   [No es @Referenced, Ni @Embedded Ni @Microservices]");
                            //Test.msg("      Intentare...Reflexionar..............................");
                            //Test.msg("Convertir object a Class: " +fieldDescriptor.getClass().getName());
                            /*
                        Trato de hacer reflexion
                             */
                            final Field[] fields = fieldDescriptor.getClass().getDeclaredFields();

                            //Test.msg("      Obtuve los campos..invocare al analizdor");
                            Analizador analizador = new Analizador();
                            analizador.analizar(fields);
                            //Test.msg("Regreso del analizador... y hare la invovacion");
                            fromDocument(fieldDescriptor.getClass(), documentMaster, analizador.getEmbeddedBeansList(), analizador.getReferencedBeansList(), analizador.getMicroservicesModelList());
                            //Test.msg("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx");
                            //Test.msg("    NO se si debe devolver esto....");
                            new JmoordbException("@Embedded or @Reference or @Microservices is required for this field " + fieldDescriptor.getName());
                            return new Document();
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            throw new JmoordbException("fromDocumentRecursive() " + fieldDescriptor.getName(), e);
        }
        return null;
    }

    // </editor-fold>
//    public Object getBeanByName(String name) // eg. name=availableCountryDao
//    {
//        Object o = null;
//        try {
//             BeanManager bm = getBeanManager();
//        Bean bean = bm.getBeans(name).iterator().next();
//        CreationalContext ctx = bm.createCreationalContext(bean); // could be inlined below
//     o = bm.getReference(bean, bean.getClass(), ctx); // could be inlined with
//        } catch (Exception e) {            System.out.println("------------------------------------------------------------------------------------------------");            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());            System.out.println("Error " + e.getLocalizedMessage());            System.out.println("------------------------------------------------------------------------------------------------");
//            LOG.warning("getBeanByName() "+e.getLocalizedMessage());
//            ////Test.msg("getBeanByName() "+e.getLocalizedMessage());
//        }
//  
//        return o;
//    }
//     public static <T> T getBean(Class<T> clazz) {
//    BeanManager bm = getBeanManager();
//    Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
//    CreationalContext<T> ctx = bm.createCreationalContext(bean);
//    return (T) bm.getReference(bean, clazz, ctx);
//  }
//
//    private static BeanManager getBeanManager() {
//        ServletContext servletContext = (ServletContext) FacesContext
//                .getCurrentInstance().getExternalContext().getContext();
//        return (BeanManager) servletContext
//                .getAttribute("javax.enterprise.inject.spi.BeanManager");
//    }
    // <editor-fold defaultstate="collapsed" desc="Boolean isEmbedded(String name)">    
    private Boolean isEmbedded(String name) {
        try {
            if (embeddedModelList.stream().anyMatch((eb) -> (eb.getName().equals(name)))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            new JmoordbException("isEmbedded() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean isReferenced(String name)">    
    /**
     *
     * @param name
     * @return
     */
    private Boolean isReferenced(String name) {
        try {

            for (ReferencedModel eb : referencedModelList) {
                if (eb.getName().equals(name)) {
                    referencedModel = eb;
                    ////Test.msg("Referenced() " + eb.toString());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            new JmoordbException("isReferenced() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean isMicroservices(String name) ">    

    /**
     *
     * @param name
     * @return
     */
    private Boolean isMicroservices(String name) {
        try {

            for (MicroservicesModel eb : microservicesModelList) {
                if (eb.getName().equals(name)) {
                    microservicesModel = eb;
                    ////Test.msg("Microservices() " + eb.toString());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            new JmoordbException("isMicroservices()) " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static <T> T lookUpClassInBeanManager(Class<T> clazz)">    
    private static <T> T lookUpClassInBeanManager(Class<T> clazz) {
        BeanManager bm = CDI.current().getBeanManager();
        Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next();
        CreationalContext<T> ctx = bm.createCreationalContext(bean);
        return (T) bm.getReference(bean, clazz, ctx);
    }
    // </editor-fold>
}
