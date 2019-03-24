/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.metafactory;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.util.JmoordbUtil;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandles;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author avbravo
 */
public class JmoordbIntrospection {
    
     // <editor-fold defaultstate="collapsed" desc="Integer  getPageInController(Object controller, Object entity)">
   public static Integer  getPageInController(Object controller, Object entity) {
        Integer page =0;
        try {

            Object entitynew = entity.getClass().newInstance();
            String nameOfEntity = JmoordbUtil.nombreEntity(entity.getClass().getName());
            nameOfEntity = JmoordbUtil.letterToLower(nameOfEntity);
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
//------------------------------SET------------------------


            //Guardar en el contexto el numero de pagina
            JmoordbContext.put("page" + nameOfEntity, page.toString());
           // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("page" + nameOfEntity, page.toString());

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "__invokeForDeleteInController").log(Level.SEVERE, null, e);
            System.out.println("__invokeForDeleteInController " + e.getLocalizedMessage());
        }
        return page;
    }// </editor-fold>
   
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
}
