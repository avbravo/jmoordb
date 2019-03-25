/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.metafactory;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.util.JmoordbUtil;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author avbravo
 */
public class JmoordbIntrospection {
        // <editor-fold defaultstate="collapsed" desc="nameOfEntity( Object entity)">
   /**
    * devuelve en nombre del entity
    * @param entity
    * @return 
    */

   public static String nameOfEntity( Object entity) {
       String nameOfEntity ="";
        try {

            Object entitynew = entity.getClass().newInstance();
          nameOfEntity = JmoordbUtil.nombreEntity(entity.getClass().getName());
            nameOfEntity = JmoordbUtil.letterToLower(nameOfEntity);
          
           // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("page" + nameOfEntity, page.toString());

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "__invokeForDeleteInController").log(Level.SEVERE, null, e);
            System.out.println("__invokeForDeleteInController " + e.getLocalizedMessage());
        }
        return nameOfEntity;
    }// </editor-fold>
   
     // <editor-fold defaultstate="collapsed" desc="Integer  getPageInController(Object controller, Object entity)">
   public static Integer  getPageInController(Object controller) {
        Integer page =0;
        try {
            //Propiedades
           
            PropertyDescriptor pageProperty;

            final BeanInfo beanInfo = Introspector.getBeanInfo(controller.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            /**
             * Definimos los atributos
             */
          
            pageProperty = property.apply("page");

            final MethodHandles.Lookup lookup = MethodHandles.lookup();
        
            final Function pageGetter = JmoordbLambdaMetaFactory.createGetter(lookup,
                    lookup.unreflect(pageProperty.getReadMethod()));

            page = (Integer) pageGetter.apply(controller);

         
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "getPageInController()").log(Level.SEVERE, null, e);
            System.out.println("getPageInController() " + e.getLocalizedMessage());
        }
        return page;
    }// </editor-fold>
   
   
     // <editor-fold defaultstate="collapsed" desc="Boolean  getWritableInController(Object controller)">
   public static Boolean  getWritableInController(Object controller) {
        Boolean writable =false;
        try {

            PropertyDescriptor writableProperty;

            final BeanInfo beanInfo = Introspector.getBeanInfo(controller.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            /**
             * Definimos los atributos
             */
          
            writableProperty = property.apply("writable");

            final MethodHandles.Lookup lookup = MethodHandles.lookup();
        
            final Function writableGetter = JmoordbLambdaMetaFactory.createGetter(lookup,
                    lookup.unreflect(writableProperty.getReadMethod()));

            writable = (Boolean) writableGetter.apply(controller);



        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "getWritableInController").log(Level.SEVERE, null, e);
            System.out.println("getWritableInController " + e.getLocalizedMessage());
        }
        return writable;
    }// </editor-fold>
     // <editor-fold defaultstate="collapsed" desc="callGet(Object controller, String method) ">
   /**
    * invoca el metodo del controller y devuelve un object con el valor
    * @param controller
    * @param method
    * @return 
    */
   public static Object  callGet(Object controller, String method) {
     Object value = null;
        try {

            PropertyDescriptor valueProperty;

            final BeanInfo beanInfo = Introspector.getBeanInfo(controller.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            /**
             * Definimos los atributos
             */
          
            valueProperty = property.apply(method);

            final MethodHandles.Lookup lookup = MethodHandles.lookup();
        
            final Function valueGetter = JmoordbLambdaMetaFactory.createGetter(lookup,
                    lookup.unreflect(valueProperty.getReadMethod()));

         return valueGetter.apply(controller);



        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "callGet").log(Level.SEVERE, null, e);
            System.out.println("callGet " + e.getLocalizedMessage());
        }
        return value;
    }// </editor-fold>
   
   
    // <editor-fold defaultstate="collapsed" desc="callSet(Object controller, String method)">
   /**
    * invoca al metodo set del metodo pasado como atributo en el controller con el valor especificado en value
    * @param controller
    * @param method
    * @param value
    * @return 
    */
  public static Boolean callSet(Object controller, String method, Object value) {
        try {

           
            //Propiedades
            PropertyDescriptor variableProperty;
          

            final BeanInfo beanInfo = Introspector.getBeanInfo(controller.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
            /**
             * Definimos los atributos
             */
            variableProperty = property.apply(method);
          

            final MethodHandles.Lookup lookup = MethodHandles.lookup();
         

            final BiConsumer variableSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                    lookup.unreflect(variableProperty.getWriteMethod()));
            

            //Actualizamos las propiedades en el controller
            // y asignamos al atributo writable del controller false
           variableSetter.accept(controller, value);
           return true;
        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "callSet() ").log(Level.SEVERE, null, e);
            System.out.println("callSet() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>
   
  
  
   // <editor-fold defaultstate="collapsed" desc="List<String> nameOfAllMethod(Object lcontroller">
  /**
   * devuelve una lista de todos los metodos
   * @param lcontroller
   * @return 
   */
    public static List<String> nameOfAllMethod(Object controller) {
        List<String> list = new ArrayList<>();
        try {
            PropertyDescriptor userInfoProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(controller.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
       
            for (MethodDescriptor m : beanInfo.getMethodDescriptors()) {
                String name = new String(m.getName());
                list.add(name);
             
            }


        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "nameOfAllMethod()").log(Level.SEVERE, null, e);
            System.out.println("error " + e.getLocalizedMessage());
        }
        return list;
    }
// </editor-fold>
   // <editor-fold defaultstate="collapsed" desc="List<String> nameOfAllMethod(Object lcontroller()">
  /**
   * Devuelve true si encuentra el metodo
   * devuelve una lista de todos los metodos
   * @param controller
   * @return 
   */
    public static Boolean isFound(Object controller, String method) {
        Boolean found =false;
        try {
            PropertyDescriptor userInfoProperty;
            final BeanInfo beanInfo = Introspector.getBeanInfo(controller.getClass());
            final java.util.function.Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                    .filter(p -> name.equals(p.getName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Not found: " + name));
       
            for (MethodDescriptor m : beanInfo.getMethodDescriptors()) {
                
                String name = new String(m.getName());
                if(name.equals(method)){
                    found = true;
                }
             
            }


        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "isFound()").log(Level.SEVERE, null, e);
            System.out.println("isFound " + e.getLocalizedMessage());
        }
        return found;
    }
// </editor-fold>

   

}
