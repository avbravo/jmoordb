/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

import com.avbravo.jmoordb.CompositeKey;
import com.avbravo.jmoordb.DatePatternBeans;
import com.avbravo.jmoordb.EmbeddedModel;
import com.avbravo.jmoordb.FieldBeans;
import com.avbravo.jmoordb.MicroservicesModel;
import com.avbravo.jmoordb.PrimaryKey;
import com.avbravo.jmoordb.ReferencedModel;
import com.avbravo.jmoordb.SecondaryKey;
import com.avbravo.jmoordb.TertiaryKey;
import com.avbravo.jmoordb.anotations.Composite;
import com.avbravo.jmoordb.anotations.DatePattern;
import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.anotations.Microservices;
import com.avbravo.jmoordb.anotations.Referenced;
import com.avbravo.jmoordb.anotations.Secondary;
import com.avbravo.jmoordb.anotations.Tertiary;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class Analizador {
// <editor-fold defaultstate="collapsed" desc="fields ">    

    List<PrimaryKey> primaryKeyList = new ArrayList<>();
    List<SecondaryKey> secondaryKeyList = new ArrayList<>();
    List<TertiaryKey> tertiaryKeyList = new ArrayList<>();
    List<CompositeKey> compositeKeyList = new ArrayList<>();
    
    List<EmbeddedModel> embeddedModelList = new ArrayList<>();
    List<DatePatternBeans> datePatternBeansList = new ArrayList<>();
    List<FieldBeans> fieldBeansList = new ArrayList<>();
    List<ReferencedModel> referencedModelList = new ArrayList<>();
     List<MicroservicesModel> microservicesModelList = new ArrayList<>();

    Exception exception;
// </editor-fold>
   

    // <editor-fold defaultstate="collapsed" desc="set/get ">
     public List<MicroservicesModel> getMicroservicesModelList() {
        return microservicesModelList;
    }
    public void setMicroservicesModelList(List<MicroservicesModel> microservicesModelList) {
        this.microservicesModelList = microservicesModelList;
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

    
    
    
    public List<PrimaryKey> getPrimaryKeyList() {
        return primaryKeyList;
    }

    public void setPrimaryKeyList(List<PrimaryKey> primaryKeyList) {
        this.primaryKeyList = primaryKeyList;
    }

    public List<SecondaryKey> getSecondaryKeyList() {
        return secondaryKeyList;
    }

    public void setSecondaryKeyList(List<SecondaryKey> secondaryKeyList) {
        this.secondaryKeyList = secondaryKeyList;
    }

    public List<EmbeddedModel> getEmbeddedBeansList() {
        return embeddedModelList;
    }

    public void setEmbeddedBeansList(List<EmbeddedModel> embeddedModelList) {
        this.embeddedModelList = embeddedModelList;
    }

    public List<DatePatternBeans> getDatePatternBeansList() {
        return datePatternBeansList;
    }

    public void setDatePatternBeansList(List<DatePatternBeans> datePatternBeansList) {
        this.datePatternBeansList = datePatternBeansList;
    }

    public List<FieldBeans> getFieldBeansList() {
        return fieldBeansList;
    }

    public void setFieldBeansList(List<FieldBeans> fieldBeansList) {
        this.fieldBeansList = fieldBeansList;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public List<ReferencedModel> getReferencedBeansList() {
        return referencedModelList;
    }

    public void setReferencedBeansList(List<ReferencedModel> referencedModelList) {
        this.referencedModelList = referencedModelList;
    }
// </editor-fold>
    
    
    // <editor-fold defaultstate="collapsed" desc="Analizador() ">    

    public Analizador() {
        primaryKeyList = new ArrayList<>();
        secondaryKeyList = new ArrayList<>();
        tertiaryKeyList = new ArrayList<>();
        compositeKeyList = new ArrayList<>();
        embeddedModelList = new ArrayList<>();
        referencedModelList = new ArrayList<>();
        microservicesModelList = new ArrayList<>();
        datePatternBeansList = new ArrayList<>();
        fieldBeansList = new ArrayList<>();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="analizar(Field[] fields)">
/**
 * Analiza el entity pasado
 * @param fields
 * @return 
 */
    public Boolean analizar(Field[] fields) {
        try {

            for (final Field field : fields) {
                Annotation anotacion = field.getAnnotation(Id.class);
                Annotation anotacionSecondary = field.getAnnotation(Secondary.class);
                Annotation anotacionTertiary = field.getAnnotation(Tertiary.class);
                Annotation anotacionComposite = field.getAnnotation(Composite.class);
                Annotation anotacionEmbedded = field.getAnnotation(Embedded.class);
                Annotation anotacionReferenced = field.getAnnotation(Referenced.class);
                Annotation anotacionMicroservices = field.getAnnotation(Microservices.class);
                Annotation anotacionDateFormat = field.getAnnotation(DatePattern.class);

                Embedded embedded = field.getAnnotation(Embedded.class);
                Referenced referenced = field.getAnnotation(Referenced.class);
                Microservices microservices = field.getAnnotation(Microservices.class);
                DatePattern datePattern = field.getAnnotation(DatePattern.class);

                field.setAccessible(true);
                FieldBeans fieldBeans = new FieldBeans();
                fieldBeans.setIsKey(false);
                fieldBeans.setIsEmbedded(false);
                fieldBeans.setIsReferenced(false);
                fieldBeans.setIsSecondary(false);
                fieldBeans.setIsTertiary(false);
                fieldBeans.setIsComposite(false);
                fieldBeans.setName(field.getName());
                fieldBeans.setType(field.getType().getName());

                //PrimaryKey
                if (anotacion != null) {
                    verifyPrimaryKey(field, anotacion);
                    fieldBeans.setIsKey(true);

                }
//SecondaryKey
                if (anotacionSecondary != null) {
                    verifySecondaryKey(field, anotacionSecondary);
                    fieldBeans.setIsSecondary(true);

                }
//TertiaryKey
                if (anotacionTertiary != null) {
                    verifyTertiaryKey(field, anotacionTertiary);
                    fieldBeans.setIsTertiary(true);

                }
//CombinedKey
                if (anotacionComposite  != null) {
                    verifyCompositeKey(field, anotacionComposite);
                    fieldBeans.setIsComposite(true);

                }
                
                

                if (anotacionEmbedded != null) {

                    verifyEmbedded(field, anotacionEmbedded);
                    fieldBeans.setIsEmbedded(true);

                }

                if (anotacionReferenced != null) {

                    verifyReferenced(field, anotacionReferenced, referenced);
                    fieldBeans.setIsReferenced(true);

                }
                if (anotacionMicroservices != null) {

                    verifyMicroservices(field, anotacionMicroservices, microservices);
                    fieldBeans.setIsMicroservices(true);

                }
                if (anotacionDateFormat != null) {

                    verifyDatePattern(field, anotacionReferenced, datePattern);
                    //fieldBeans.setIsReferenced(true);

                }

                fieldBeansList.add(fieldBeans);

            }
            //Llave primary
            if (primaryKeyList.isEmpty()) {
                exception = new Exception("No have primaryKey() ");

            }
            if (fieldBeansList.isEmpty()) {
                exception = new Exception("No have fields() ");
            }

        } catch (Exception e) {
            exception = new Exception("analizar() " + e.getLocalizedMessage());
        }
        return false;
    }    // </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="Boolean verifyPrimaryKey(Field variable, Annotation anotacion)">
 
    /**
     *
     * @param variable
     * @param anotacion
     * @return
     */
    private Boolean verifyPrimaryKey(Field variable, Annotation anotacion) {
        try {
            final Id anotacionPK = (Id) anotacion;
            PrimaryKey primaryKey = new PrimaryKey();

            Boolean found = false;
            for (PrimaryKey pk : primaryKeyList) {
                if (pk.getName().equals(primaryKey.getName())) {
                    found = true;
                }
            }

            primaryKey.setName(variable.getName());
            primaryKey.setType(variable.getType().getName());

            // obtengo el valor del atributo
            if (!found) {
                primaryKeyList.add(primaryKey);
            }
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyPrimaryKey() " + e.getLocalizedMessage());
        }
        return false;
    }
       // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean verifyTertiaryKey(Field variable, Annotation anotacion)">
 
    /**
     *
     * @param variable
     * @param anotacion
     * @return
     */
    private Boolean verifyTertiaryKey(Field variable, Annotation anotacion) {
        try {
            final Tertiary anotacionTertiary = (Tertiary) anotacion;
            TertiaryKey tertiaryKey = new TertiaryKey();

            Boolean found = false;
            for (TertiaryKey tk : tertiaryKeyList) {
                if (tk.getName().equals(tertiaryKey.getName())) {
                    found = true;
                }
            }

          tertiaryKey.setName(variable.getName());
           tertiaryKey.setType(variable.getType().getName());

            // obtengo el valor del atributo
            if (!found) {
                tertiaryKeyList.add(tertiaryKey);
            }
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyPrimaryKey() " + e.getLocalizedMessage());
        }
        return false;
    }
       // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Boolean verifyCompositeKey(Field variable, Annotation anotacion)">
 
    /**
     *
     * @param variable
     * @param anotacion
     * @return
     */
    private Boolean verifyCompositeKey(Field variable, Annotation anotacion) {
        try {
            final Composite anotacionComposite= (Composite) anotacion;
            CompositeKey compositeKey = new CompositeKey();

            Boolean found = false;
            for (CompositeKey ck : compositeKeyList) {
                if (ck.getName().equals(compositeKey.getName())) {
                    found = true;
                }
            }

        compositeKey.setName(variable.getName());
        compositeKey.setType(variable.getType().getName());

            // obtengo el valor del atributo
            if (!found) {
                compositeKeyList.add(compositeKey);
            }
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyPrimaryKey() " + e.getLocalizedMessage());
        }
        return false;
    }
       // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="verifySecondaryKey(Field variable, Annotation anotacion)">
  
    /**
     *
     * @param variable
     * @param anotacion
     * @return
     */
    private Boolean verifySecondaryKey(Field variable, Annotation anotacion) {
        try {
            final Secondary anotacionSecondary = (Secondary) anotacion;
            SecondaryKey secondaryKey = new SecondaryKey();

            Boolean found = false;
            for (SecondaryKey sk : secondaryKeyList) {
                if (sk.getName().equals(secondaryKey.getName())) {
                    found = true;
                }
            }

            secondaryKey.setName(variable.getName());
            secondaryKey.setType(variable.getType().getName());

            // obtengo el valor del atributo
            if (!found) {
                secondaryKeyList.add(secondaryKey);
            }
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyPrimaryKey() " + e.getLocalizedMessage());
        }
        return false;
    }  // </editor-fold>
    
    
    

    private Boolean verifyEmbedded(Field variable, Annotation anotacion) {
        try {
            // final Embedded anotacionPK = (Embedded) anotacionEmbedded;
            EmbeddedModel embeddedModel = new EmbeddedModel();
            embeddedModel.setName(variable.getName());
            embeddedModel.setType(variable.getType().getName());
            embeddedModelList.add(embeddedModel);
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyEmbedded() " + e.getLocalizedMessage());
        }
        return false;
    }

    
    // <editor-fold defaultstate="collapsed" desc="Boolean verifyReferenced(Field variable, Annotation anotacion, Referenced referenced) ">    

    /**
     * guarda la informacion de la anotacion
     *
     * @param variable
     * @param anotacion
     * @param referenced
     * @return
     */
    private Boolean verifyReferenced(Field variable, Annotation anotacion, Referenced referenced) {
        try {

            ReferencedModel referencedModel = new ReferencedModel();
            referencedModel.setName(variable.getName());
            referencedModel.setType(variable.getType().getName());
            referencedModel.setDocument(referenced.collection());
            referencedModel.setField(referenced.field());
            referencedModel.setJavatype(referenced.javatype());
            referencedModel.setRepository(referenced.repository());
            referencedModel.setLazy(referenced.lazy());

            referencedModelList.add(referencedModel);
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyReferenced() " + e.getLocalizedMessage());
        }
        return false;
    }
    
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean verifyMicroservices(Field variable, Annotation anotacion, Microservices microservices) ">    

    /**
     * guarda la informacion de la anotacion
     *
     * @param variable
     * @param anotacion
     * @param referenced
     * @return
     */
    private Boolean verifyMicroservices(Field variable, Annotation anotacion, Microservices microservices) {
        try {

            MicroservicesModel microservicesModel = new MicroservicesModel();
            microservicesModel.setName(variable.getName());
            microservicesModel.setType(variable.getType().getName());
            microservicesModel.setDocument(microservices.collection());
            microservicesModel.setField(microservices.field());
            microservicesModel.setJavatype(microservices.javatype());
            microservicesModel.setRepository(microservices.repository());
            microservicesModel.setUrl(microservices.url());
            microservicesModel.setUser(microservices.user());
            microservicesModel.setPassword(microservices.password());
            microservicesModel.setLazy(microservices.lazy());

            microservicesModelList.add(microservicesModel);
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyReferenced() " + e.getLocalizedMessage());
            System.out.println("Analizador.verifyMicroservices() "+e.getLocalizedMessage());
        }
        return false;
    }
    
    // </editor-fold>
    
    

    
    // <editor-fold defaultstate="collapsed" desc="Boolean verifyDatePattern(Field variable, Annotation anotacion, DatePattern datePattern)">    

    /**
     *
     *
     * @param variable
     * @param anotacion
     * @param referenced
     * @return
     */
    private Boolean verifyDatePattern(Field variable, Annotation anotacion, DatePattern datePattern) {
        try {

            DatePatternBeans datePatternBeans = new DatePatternBeans();
            datePatternBeans.setName(variable.getName());
            datePatternBeans.setType(variable.getType().getName());
            datePatternBeans.setDateformat(datePattern.dateformat());

            datePatternBeansList.add(datePatternBeans);
            return true;
        } catch (Exception e) {
            ////Test.msg("verifyReferenced() " + e.getLocalizedMessage());
        }
        return false;
    }
// </editor-fold>
}
