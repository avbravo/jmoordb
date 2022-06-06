/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.interfaces;

import com.avbravo.jmoordb.SecondaryKey;
import com.avbravo.jmoordb.TertiaryKey;
import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.anotations.Aspect;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.configuration.JmoordbControllerEnvironment;
import com.avbravo.jmoordb.metafactory.JmoordbIntrospection;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.services.RevisionHistoryServices;

import com.avbravo.jmoordb.util.JmoordbUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Optional;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.bson.Document;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @authoravbravo
 */
public interface IController<T> {

    default public String refresh() {
        return "";
    }

    // <editor-fold defaultstate="collapsed" desc="Integer sizeOfPage()">
    default public Integer sizeOfPage() {
        Integer size = 0;
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();

            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfController = controller.getClass().getSimpleName();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);
            Integer rowPage = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfRowPage);
            size = repository.sizeOfPage(rowPage);

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return size;

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="last">

    default public String last() {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            page = sizeOfPage();
            move(page);
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="first">
    default public String first() {
        try {
            Integer page = 1;
            move(page);
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="next">
    default public String next() {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);
            // Integer page = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");
            if (page < sizeOfPage()) {
                page++;
            }

            move(page);
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="back">
    default public String back() {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);
//            Integer page = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");

            if (page > 1) {
                page--;
            }
            move(page);
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    
        // <editor-fold defaultstate="collapsed" desc="move(Integer page)">
    
  default  public void move(Integer page){
      
  }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="nameOfClassAndMethod())">
    public default String nameOfClassAndMethod() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
        final String s = e.getClassName();
        return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="nameOfClass()">
    public default String nameOfClass() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
        final String s = e.getClassName();
        return s.substring(s.lastIndexOf('.') + 1, s.length());
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="nameOfMethod())">

    public default String nameOfMethod() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
        final String s = e.getClassName();
        return e.getMethodName();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="save()">
    default public String save() {
        Boolean saved = false;
        try {
            
            System.out.println("****llego al save");
            
            
            
            //Obtener la configuracion de Jmoordb
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            typeKey = typeKey.toLowerCase().trim();

            if (!beforeSave()) {
                return "";
            }
            switch (typeKey) {
                case "primary":
                    //convierte la llave primaria a minuscula
                    String primaryValue = repository.primaryKeyValue(entity);
                    if (primaryValue == null || primaryValue.isEmpty() || primaryValue.equals("null")) {
                        JmoordbUtil.warningMessage(spanish ? "La llave primaria esta vacia" : "the primary key is empty");
                        return "";
                    }
                    if (!searchLowerCase) {
                        entity = repository.primaryKeyValueToUpper(entity);
                    } else {
                        entity = repository.primaryKeyValueToLower(entity);
                    }
                    //Busca por llave primaria
                    Optional<Object> optional = repository.findById(entity);
                    if (optional.isPresent()) {
                        JmoordbUtil.warningMessage(spanish ? "Existe un documento con esos datos" : "There is a document with this data");
                        return "";
                    }
                    break;
                case "secondary":
                    String secondaryValue = repository.secondaryKeyValue(entity);
                    if (secondaryValue == null || secondaryValue.isEmpty() || secondaryValue.equals("null")) {
                        JmoordbUtil.warningMessage(spanish ? "La llave secundaria esta vacia" : "the secondary key is empty");
                        return "";
                    }
                    if (!searchLowerCase) {
                        entity = repository.secondaryKeyValueToUpper(entity);
                    } else {
                        entity = repository.secondaryKeyValueToLower(entity);
                    }
                    //Busca por llave secundaria
                    Optional<Object> optionalSecondary = repository.findBySecondaryKey(entity);
                    if (optionalSecondary.isPresent()) {
                        JmoordbUtil.warningMessage(spanish ? "Existe un documento con esos datos" : "There is a document with this data");
                        return "";
                    }
                    break;
                case "tertiary":
                    String tertiaryValue = repository.tertiaryKeyValue(entity);
                    if (tertiaryValue == null || tertiaryValue.isEmpty() || tertiaryValue.equals("null")) {
                        JmoordbUtil.warningMessage(spanish ? "La llave @Tertiary esta vacia" : "the @Tertiary key is empty");
                        return "";
                    }
                    if (!searchLowerCase) {
                        entity = repository.tertiaryKeyValueToUpper(entity);
                    } else {
                        entity = repository.tertiaryKeyValueToLower(entity);
                    }
                    //Busca por llave secundaria
                    Optional<Object> optionalTertiary = repository.findByTertiaryKey(entity);
                    if (optionalTertiary.isPresent()) {
                        JmoordbUtil.warningMessage(spanish ? "Existe un documento con esos datos" : "There is a document with this data");
                        return "";
                    }
                    break;
                case "composite":
                    break;

                default:
                    JmoordbUtil.warningMessage(spanish ? "typeKey debe ser(primary,secondary,tertiary,composite) " : "typeKey could by(primary,secondary,tertiary,composite)");

            }

            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForSaveMethod(entity, username, "create");

            String primaryValue = repository.primaryKeyValue(entity);
            if (primaryValue == null || primaryValue.isEmpty() || primaryValue.equals("null")) {
                JmoordbUtil.warningMessage(spanish ? "La llave primaria esta vacia" : "the primary key is empty");
                return "";
            }
            if (repository.save(entity)) {
                //Devuelve el valor de la llave primaria
                String primarykeyvalue = repository.primaryKeyValue(entity);

                if (saverevision) {

                    if (_validRevisionHistory(repositoryRevisionHistory, revisionHistoryServices, spanish)) {
                        repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                                "create", nameOfEntity, repository.toDocument(entity).toString()));
                    }

                }

                JmoordbUtil.successMessage(spanish ? "Guardado" : "Saved");
                if (resetInSave) {
                    reset();
                }

            } else {
                JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
            }
            saved = true;

        } catch (Exception ex) {
System.out.println("****>> error "+ex.getLocalizedMessage());
            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        afterSave(saved);

        return "";

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="edit()">

    default public String edit() {
        Boolean edited = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Los pasa el usuaario
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            typeKey = typeKey.toLowerCase().trim();
            switch (typeKey) {
                case "primary":
                    //Busca por llave primaria
                    String primaryValue = repository.primaryKeyValue(entity);
                    if (primaryValue == null || primaryValue.isEmpty() || primaryValue.equals("null")) {
                        JmoordbUtil.warningMessage(spanish ? "La llave primaria esta vacia" : "the primary key is empty");
                        return "";
                    }
                    Optional<Object> optional = repository.findById(entity);
                    if (!optional.isPresent()) {
                        JmoordbUtil.warningMessage(spanish ? "No existe un documento con esos datos" : "There is nodocument with this data");
                        return "";
                    }
                    break;
                case "secondary":
                    //Busca por llave secundaria
                    String secondaryValue = repository.secondaryKeyValue(entity);
                    if (secondaryValue == null || secondaryValue.isEmpty() || secondaryValue.equals("null")) {
                        JmoordbUtil.warningMessage(spanish ? "La llave Secundaria esta vacia" : "The Secondary key is empty");
                        return "";
                    }
                    Optional<Object> optionalSecondary = repository.findBySecondaryKey(entity);
                    if (!optionalSecondary.isPresent()) {
                        JmoordbUtil.warningMessage(spanish ? "No existe un documento con esos datos" : "there is no document with this data");
                        return "";
                    }
                    break;
                case "tertiary":
                    //Busca por llave secundaria
                    String tertiaryValue = repository.tertiaryKeyValue(entity);
                    if (tertiaryValue == null || tertiaryValue.isEmpty() || tertiaryValue.equals("null")) {
                        JmoordbUtil.warningMessage(spanish ? "La llave @Tertiary esta vacia" : "The @Tertiary key is empty");
                        return "";
                    }
                    Optional<Object> optionalTertiary = repository.findBySecondaryKey(entity);
                    if (!optionalTertiary.isPresent()) {
                        JmoordbUtil.warningMessage(spanish ? "No existe un documento con esos datos" : "there is no document with this data");
                        return "";
                    }
                    break;
                case "composite":
                    //Busca por llave secundaria

                    break;

                default:
                    JmoordbUtil.warningMessage(spanish ? "typeKey debe ser(primary,secondary,tertiary,composite) " : "typeKey could by(primary,secondary,tertiary,composite)");

            }

            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForEditMethod(entity, username, "update");

            if (!beforeEdit()) {
                return "";
            }
            String primaryValue = repository.primaryKeyValue(entity);
            if (primaryValue == null || primaryValue.isEmpty() || primaryValue.equals("null")) {
                JmoordbUtil.warningMessage(spanish ? "La llave primaria esta vacia" : "the primary key is empty");
                return "";
            }
            if (repository.update(entity)) {
                //Devuelve el valor de la llave primaria
                String primarykeyvalue = repository.primaryKeyValue(entity);

                if (saverevision) {

                    if (_validRevisionHistory(repositoryRevisionHistory, revisionHistoryServices, spanish)) {
                        repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                                "update", nameOfEntity, repository.toDocument(entity).toString()));
                    }

                }
                JmoordbUtil.successMessage(spanish ? "Editado" : "Edited");

            } else {
                JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
            }
            edited = true;

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        afterEdit(edited);

        return "";

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean _validRevisionHistory(Repository repositoryRevisionHistory, RevisionHistoryServices revisionHistoryServices, ResourcesFiles rf)">
    default public Boolean
            _validRevisionHistory(Repository repositoryRevisionHistory,
                    RevisionHistoryServices revisionHistoryServices,
                    Boolean spanish) {
        Boolean isvalid
                = false;

        try {

            if (repositoryRevisionHistory == null) {

                JmoordbUtil.warningMessage(spanish ? "Configure el parametro [repositoryRevisionHistory} en el ExternalContext de la clase principal" : "Configure the repositoryRevisionHistory parameter in the ExternalContext of the main class");

                return false;

            }
            if (revisionHistoryServices == null) {

                JmoordbUtil.warningMessage(spanish ? "Configure el parametro (revisionHistoryServices) en el ExternalContext de la clase principal" : "Configure the revisionHistoryServices parameter in the ExternalContext of the main class");

                return false;

            }
            isvalid = true;

        } catch (Exception ex) {
            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }
        return isvalid;

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="beforeSave()">
    /**
     * se inyecta en el metodo save() antes de ejecutar el repository.save();
     *
     * @return
     */
    default Boolean beforeSave() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="afterSave(Boolean saved)">
    /**
     * se invoca despues del metodo save()
     *
     * @param saved
     * @return
     */
    @Aspect
    default Boolean afterSave(Boolean saved) {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="beforeEdit()">
    /**
     * se inyecta en el metodo save() antes de ejecutar el repository.edit();
     *
     * @return
     */
    default Boolean beforeEdit() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="afterEdit(Boolean edit)">
    /**
     * se invoca despues del metodo edit()
     *
     * @param edit
     * @return
     */
    default Boolean afterEdit(Boolean edited) {
        return true;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" beforeDelete()">

    /**
     * se inyecta en el metodo antes de ejecutar el repository.edit();
     *
     * @return
     */
    default Boolean beforeDelete() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean afterDelete(Boolean deleted) ">
    /**
     * se invoca despues del metodo deleted()
     *
     * @param edit
     * @return
     */
    default Boolean afterDelete(Boolean deleted) {
        return true;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String delete()">
    default public String delete() {
        Boolean deleted = false;
        String url = "";
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            //Los pasa el usuaario
            url = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("url");

//Agregar el UserInfo al entity
            entity = repository.addUserInfoForEditMethod(entity, username, "delete");

            if (!beforeDelete()) {
                return "";
            }

            String nameOfPrimaryKey = repository.primaryKeyName(entity);

            if (repository.primaryKeyIsInteger(entity)) {
                //si es llave primaria  de tipo Integer
                Integer value = repository.primaryKeyValueInteger(entity);
                if (repository.delete(nameOfPrimaryKey, value)) {
                    deleted = true;
                } else {
                    JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
                }
            } else {
                //si es llave primaria de tipo String
                String value = repository.primaryKeyValue(entity);
                if (repository.delete(nameOfPrimaryKey, value)) {
                    deleted = true;
                } else {
                    JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
                }

            }
            if (deleted) {
                //Devuelve el valor de la llave primaria
                String primarykeyvalue = repository.primaryKeyValue(entity);

                if (saverevision) {

                    if (_validRevisionHistory(repositoryRevisionHistory, revisionHistoryServices, spanish)) {
                        repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                                "delete", nameOfEntity, repository.toDocument(entity).toString()));
                    }

                }

                /**
                 * 1. Limpiar el entity con un new 2. invocar el metodo
                 * setEntity en el controller 3. Asignar writable a false
                 * invocando el metodo setWritable del controller
                 */
                Object entitynew = JmoordbIntrospection.newEntity(entity);
                JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                JmoordbIntrospection.callSet(controller, "writable", false);

                JmoordbUtil.successMessage(spanish ? "Eliminado" : "Deleted");
                reset();

            }

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        afterDelete(deleted);
        //move(Integer.SIZE);

//        return "";
        return prepareGoList();

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String deleteFromListxhtml()">
    /**
     * elimina un registro de una lista invocado desde un formulario list.xhtml
     * <jmoordbjsf:column>
     * Obtiene la lista del controller elimina el entity de esa lista guarda en
     * el FaceContext la pagina actual
     *
     * @return
     */
    default public String deleteFromListxhtml() {
        Boolean deleted = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Object item = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("item");
            entity = item;
            JmoordbIntrospection.callSet(controller, nameOfEntity, item);
            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForEditMethod(entity, username, "delete");

            if (!beforeDeleteFromListXhtml()) {
                return "";

            }

            //Obetener la lista seleccionada para eliminar
            List<Object> listSelected = (List<Object>) JmoordbIntrospection.callGet(controller, nameOfEntity.trim() + "ListSelected");
            for (Object entityDelete : listSelected) {

                String nameOfPrimaryKey = repository.primaryKeyName(entityDelete);

                if (repository.primaryKeyIsInteger(entityDelete)) {
                    //si es llave primaria  de tipo Integer
                    Integer value = repository.primaryKeyValueInteger(entityDelete);
                    if (repository.delete(nameOfPrimaryKey, value)) {
                        deleted = true;
                    } else {
                        JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
                    }
                } else {
                    //si es llave primaria de tipo String
                    String value = repository.primaryKeyValue(entityDelete);
                    if (repository.delete(nameOfPrimaryKey, value)) {
                        deleted = true;
                    } else {
                        JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
                    }

                }

                if (deleted) {
                    //Devuelve el valor de la llave primaria
                    String primarykeyvalue = repository.primaryKeyValue(entityDelete);

                    if (saverevision) {

                        if (_validRevisionHistory(repositoryRevisionHistory, revisionHistoryServices, spanish)) {
                            repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                                    "delete", nameOfEntity, repository.toDocument(entityDelete).toString()));
                        }

                    }

                }

                /**
                 * 1. Obtener la lista de Entity 2. Remover el elemento
                 * seleccionado 3. Invocar el setEntityList con la nueva lista
                 * 4. Invocar el setWritable con false 5- Obtener el numero de
                 * pagina actual 6- Guardar en el Context el numero de pagina
                 * actual
                 */
                List<Object> list = (List<Object>) JmoordbIntrospection.callGet(controller, nameOfEntity.trim() + "List");
                for (Object entityDelete2 : listSelected) {
                    list.remove(entityDelete2);
                }

                Integer page = JmoordbIntrospection.getPageInController(controller);

                JmoordbIntrospection.callSet(controller, nameOfEntity.trim() + "List", list);
                JmoordbIntrospection.callSet(controller, "writable", false);
                JmoordbContext.put("page" + nameOfController, page.toString());

                JmoordbUtil.successMessage(spanish ? "Eliminado" : "Deleted");

            }

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        afterDeleteFromListXhtml(deleted);

        return "";

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" beforeDeleteFromListXhtml()">
    /**
     * se inyecta en el metodo antes de ejecutar el repository. ();
     *
     * @return
     */
    default Boolean beforeDeleteFromListXhtml() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean afterDeleteFromListXhtml(Boolean delete)">
    /**
     * se invoca despues del metodo deleted()
     *
     * @param edit
     * @return
     */
    default Boolean afterDeleteFromListXhtml(Boolean delete) {
        return true;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareView()">
    /**
     * usa el componente <jmoordbjsf:column>
     * lo invoca el componente
     *
     * @return
     */
    default public String prepareView() {
        String url = "";
        Boolean prepare = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Object item = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("item");
            entity = item;
            JmoordbIntrospection.callSet(controller, nameOfEntity, item);

            url = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("url");

            /*
            Necesitamos:
            1.Obtener el nombre del entity
            2.Invocar al metodo getPage() del Controller
            3.Guardar en el FaceContext page{nombre_entity} con page.toString()
            4.Guardar en el FaceContext {nombre_entity} con "view"
            5.Invocar a setEntity con {entity} ya que viene de un item de un list.xtml
            6.Invocar a setEntitySelected con {entity} ya que viene de un item de un list.xtml
            7.Obtener el nombre de la llave primaria
            8.Obtener el valor de la llave primaria
            9.Colocar en el FacesContext {nombre_atributo_llaveprimaria} con el valor de la llave primaria
            10. invocar el setWritable con true
            11. Guarda el entity en el Context (entity+"_value")
            
            crear en el FacesContext pagerol
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfController, page.toString());
            JmoordbContext.put("action" + nameOfController, "view");
            JmoordbContext.put("entityValue" + nameOfController, entity);
//           
            JmoordbIntrospection.callSet(controller, nameOfEntity, entity);
            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entity);
            if (repository.primaryKeyIsInteger(entity)) {
                JmoordbContext.put(repository.primaryKeyName(entity), repository.primaryKeyValueInteger(entity));
            } else {
                JmoordbContext.put(repository.primaryKeyName(entity), repository.primaryKeyValue(entity));
            }

            if (!beforePrepareView()) {
                return "";
            }
            prepare = true;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        afterPrepareView(prepare);
        return url;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String prepareViewDialog()">
    /**
     * usa el componente <jmoordbjsf:column>
     * lo invoca el componente
     *
     * @return
     */
    default public String prepareViewDialog() {
        String url = "";
        Boolean prepare = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Object item = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("item");
            entity = item;
            JmoordbIntrospection.callSet(controller, nameOfEntity, item);

            url = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("url");

            /*
            Necesitamos:
            1.Obtener el nombre del entity
            2.Invocar al metodo getPage() del Controller
            3.Guardar en el FaceContext page{nombre_entity} con page.toString()
            4.Guardar en el FaceContext {nombre_entity} con "view"
            5.Invocar a setEntity con {entity} ya que viene de un item de un list.xtml
            6.Invocar a setEntitySelected con {entity} ya que viene de un item de un list.xtml
            7.Obtener el nombre de la llave primaria
            8.Obtener el valor de la llave primaria
            9.Colocar en el FacesContext {nombre_atributo_llaveprimaria} con el valor de la llave primaria
            10. invocar el setWritable con true
            11. Guarda el entity en el Context (entity+"_value")
            
            crear en el FacesContext pagerol
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfController, page.toString());
            JmoordbContext.put("action" + nameOfController, "view");
            JmoordbContext.put("entityValue" + nameOfController, entity);
//           
            JmoordbIntrospection.callSet(controller, nameOfEntity, entity);
            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entity);
            if (repository.primaryKeyIsInteger(entity)) {
                JmoordbContext.put(repository.primaryKeyName(entity), repository.primaryKeyValueInteger(entity));
            } else {
                JmoordbContext.put(repository.primaryKeyName(entity), repository.primaryKeyValue(entity));
            }

            if (!beforePrepareView()) {
                return "";
            }
            prepare = true;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        afterPrepareView(prepare);
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" beforePrepareView(Boolean prepare)()">

    /**
     * se inyecta en el metodo antes de ejecutar el repository. ();
     *
     * @return
     */
    default Boolean beforePrepareView() {
        return true;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" afterPrepareView(Boolean prepare)()">

    /**
     * se inyecta en el metodo antes de ejecutar el repository. ();
     *
     * @return
     */
    default Boolean afterPrepareView(Boolean prepare) {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="start()">
    /**
     * 1. Obtener el nameOfController mediante JmoordbContext.get(nombreentity)
     *
     * 2. Obtener eñ pageSession mediante
     * JmoordbContext.get("page"+nombreentity")
     *
     * 3. Verificar si existe el search+nombreentity
     *
     * 4. si no existe asignarle init
     *
     * 5. asignar false a writable
     *
     * 6. Inicializar el entityList
     *
     * 7- Invocar el set del entityList
     *
     * 8. Crear un nuevo objeto del tipo entity
     *
     * 9. Invocar el set del Entity
     *
     * 10. verificar si pageSession == null
     *
     * 11. invocar al sizeOfPage del Controller para obtener el tamano de
     * documentos
     *
     * 12. invocar el setPage para asignar el numero de pagina
     *
     * 13. Validar el nameOfController
     */
    default void start() {
        Boolean started = true;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForEditMethod(entity, username, "delete");

            if (!beforeStart()) {
                return;

            }

            //  String nameOfController = (String) JmoordbContext.get(nameOfEntity);
            String pageSession = (String) JmoordbContext.get("page" + nameOfController);
            //Search

            if (JmoordbContext.get("search" + nameOfController) == null || (JmoordbContext.get("search" + nameOfController).toString().equals(""))) {
                JmoordbContext.put("search" + nameOfController, "_init");
            }
            /**
             * Action
             */
            if (JmoordbContext.get("action" + nameOfController) == null || (JmoordbContext.get("action" + nameOfController).toString().equals(""))) {
                //inicializa generalmente con el gotlist
                if (action == null || action.equals("")) {
                    action = "golist";
                }
                JmoordbContext.put("action" + nameOfController, action);
            } else {
                //Obtiene el action del contexto
                action = (String) JmoordbContext.get("action" + nameOfController);
            }

            JmoordbIntrospection.callSet(controller, "writable", false);

            List<Object> list = (List<Object>) JmoordbIntrospection.callGet(controller, nameOfEntity + "List");
            list = new ArrayList<>();
            JmoordbIntrospection.callSet(controller, nameOfEntity + "List", list);

            Object entitynew = JmoordbIntrospection.newEntity(entity);
            JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);

            Integer page = 0;

            if (pageSession != null) {
                page = Integer.parseInt(pageSession);
            }

            Integer c = sizeOfPage();
            page = page > c ? c : page;

            JmoordbIntrospection.callSet(controller, "page", page);

            if (action != null) {
                switch (action) {
                    case "gonew":
                        //selected
                        JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                        break;
                    case "view":
                        //Recupera el entiry desdel el Context
                        //invoca elsetEntity pasandole el entity recuperado
                        //invoca el setEntitySelected pasandole el entity seleccionado
                        entity = (Object) JmoordbContext.get("entityValue" + nameOfController);

                        JmoordbIntrospection.callSet(controller, nameOfEntity, entity);
                        JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entity);
                        JmoordbIntrospection.callSet(controller, "writable", true);
                        break;

                    case "golist":
                        move(page);
                        break;
                }
            } else {
                move(page);
            }
            //  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ---String nameOfPrimaryKey = repository.primaryKeyName(entity);
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
            started = false;
        }

        afterStart(started);

    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="beforeStart()">
    default Boolean beforeStart() {
        return true;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="afterStart(Boolean started)">
    default Boolean afterStart(Boolean started) {
        return started;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareGoList()">
    /**
     * usa el componente <jmoordbjsf:column>
     * lo invoca el componente
     *
     * @return
     */
    default public String prepareGoList() {
        String url = "";
        Boolean golist = true;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();

            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            //Los pasa el usuaario
            url = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("url");

            /*
            Necesitamos:
            1.Obtener el nombre del entity
            2.Invocar al metodo getPage() del Controller
            3.Guardar en el FaceContext page{nombre_entity} con page.toString()
            4.Guardar en el FaceContext {nombre_entity} con "view"
            5.Invocar a setEntity con {entity} ya que viene de un item de un list.xtml
            6.Invocar a setEntitySelected con {entity} ya que viene de un item de un list.xtml
            7.Obtener el nombre de la llave primaria
            8.Obtener el valor de la llave primaria
            9.Colocar en el FacesContext {nombre_atributo_llaveprimaria} con el valor de la llave primaria
            10. invocar el setWritable con true
            11. Guarda el entity en el Context (entity+"_value")
            
            crear en el FacesContext pagerol
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfController, page.toString());
            JmoordbContext.put("action" + nameOfController, "golist");

            if (beforePrepareGoList()) {

            }

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
            golist = false;
        }
        afterPrepareGoList(golist);
        return url;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean beforePrepareGoList()">
    default Boolean beforePrepareGoList() {
        return true;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean afterPrepareGoList(Boolean golist)">
    default Boolean afterPrepareGoList(Boolean golist) {
        return golist;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareGoNew()">
    /**
     *
     * lo invoca el componente
     *
     * @return
     */
    default public String prepareGoNew() {
        String url = "";
        Boolean gonew = true;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            url = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("url");

            /*
            Necesitamos:
            1.Obtener el nombre del entity
            2.Invocar al metodo getPage() del Controller
            3.Guardar en el FaceContext page{nombre_entity} con page.toString()
            4.Guardar en el FaceContext {nombre_entity} con "view"
            5.Invocar a setEntity con {entity} ya que viene de un item de un list.xtml
            6.Invocar a setEntitySelected con {entity} ya que viene de un item de un list.xtml
            7.Obtener el nombre de la llave primaria
            8.Obtener el valor de la llave primaria
            9.Colocar en el FacesContext {nombre_atributo_llaveprimaria} con el valor de la llave primaria
            10. invocar el setWritable con true
            11. Guarda el entity en el Context (entity+"_value")
            
            crear en el FacesContext pagerol
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfController, page.toString());
            JmoordbContext.put("action" + nameOfController, "gonew");

            if (beforePrepareGoNew()) {

            }

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
            gonew = false;
        }
        afterPrepareGoNew(gonew);
        return url;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String prepareGoNew()">
    /**
     *
     * lo invoca el componente
     *
     * @return
     */
    default public String prepareGoNewDialog() {
        String url = "";
        Boolean gonew = true;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            url = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("url");

            /*
            Necesitamos:
            1.Obtener el nombre del entity
            2.Invocar al metodo getPage() del Controller
            3.Guardar en el FaceContext page{nombre_entity} con page.toString()
            4.Guardar en el FaceContext {nombre_entity} con "view"
            5.Invocar a setEntity con {entity} ya que viene de un item de un list.xtml
            6.Invocar a setEntitySelected con {entity} ya que viene de un item de un list.xtml
            7.Obtener el nombre de la llave primaria
            8.Obtener el valor de la llave primaria
            9.Colocar en el FacesContext {nombre_atributo_llaveprimaria} con el valor de la llave primaria
            10. invocar el setWritable con true
            11. Guarda el entity en el Context (entity+"_value")
            
            crear en el FacesContext pagerol
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfController, page.toString());
            JmoordbContext.put("action" + nameOfController, "gonew");

            if (beforePrepareGoNew()) {

            }

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
            gonew = false;
        }
        afterPrepareGoNew(gonew);
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean beforePrepareGoNew()">
    default Boolean beforePrepareGoNew() {
        return true;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean afterPrepareGoNew(Boolean gonew)">
    default Boolean afterPrepareGoNew(Boolean gonew) {
        return gonew;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareView()">
    /**
     * usa el componente <jmoordbjsf:column>
     * lo invoca el componente
     *
     * @return
     */
    default public String prepareNew() {
        String url = "";
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            /*
            Necesitamos:
            Crea un nuevo entity vacio
            lo pasa el entity
            lo asigna al entitySelected
            writable lo coloca a false
            crear en el FacesContext pageentity
            crear en el FacesContext el nameOfController
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfController, page.toString());
            JmoordbContext.put("action" + nameOfController, "new");
            Object entitynew = JmoordbIntrospection.newEntity(entity);

//           
            JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);
            JmoordbIntrospection.callSet(controller, "writable", false);

            if (beforePrepareNew()) {

            }

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }

        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean beforePrepareGoNew()">
    default Boolean beforePrepareNew() {
        return true;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="reset">
    default public void reset() {
        PrimeFaces.current().resetInputs(":form:content");
        prepareNew();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String clear() ">
    /**
     * usa el componente <jmoordbjsf:column>
     * lo invoca el componente
     *
     * @return
     */
    default public String clear() {
        String url = "";
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            Integer page = 1;

            JmoordbContext.put("search" + nameOfController, "_init");
//           
            JmoordbIntrospection.callSet(controller, "page", 1);
            move(page);

            if (beforeClear()) {

            }

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }

        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean beforeClear()">
    default Boolean beforeClear() {
        return true;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String validateNew()">
    /**
     * se ejecuta cuando el usuario da enter desde el componente
     * <jmoordbjsg:toolbarnew>
     * Verifica si existe o no el entity
     *
     * @return
     */
    default public String validateNew() {
        Boolean validate = false;
        try {
            //Obtener la configuracion de Jmoordb
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();

            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbIntrospection.callSet(controller, "writable", true);
            typeKey = typeKey.toLowerCase().trim();
            switch (typeKey) {
                case "primary":
                    //----------------------------------
                    //Busca por llave primaria
                    //----------------------------------
                    if (JmoordbUtil.isVacio(repository.primaryKeyValue(entity))) {
                        JmoordbIntrospection.callSet(controller, "writable", false);
                        afterValidateNew(false);
                        return "";
                    }

                    //Convierte a mayusculas la llave primaria y la devuelve en el entity
                    if (!searchLowerCase) {
                        entity = repository.primaryKeyValueToUpper(entity);
                    } else {
                        entity = repository.primaryKeyValueToLower(entity);
                    }

                    Optional<Object> optional = repository.findById(entity);

                    if (optional.isPresent()) {
                        JmoordbIntrospection.callSet(controller, "writable", false);
                        JmoordbUtil.warningMessage(spanish ? "Ya existe un documento con esos datos" : "A document with this data already exists");
                        afterValidateNew(false);
                        return "";
                    } else {
                        String id = repository.primaryKeyValue(entity);

                        Object entitynew = JmoordbIntrospection.newEntity(entity);
                        entitynew = repository.primaryKeySetValue(entity, id);

                        /**
                         * crea un entity nuevo le asigna la llave primaria
                         * asigna al selected el entity
                         */
                        JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                        JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                    }
                    break;
                case "secondary":
                    //----------------------------------
                    //Busca por llave secundaria
                    //----------------------------------
                    List<SecondaryKey> ls = repository.getSecondaryKeyList();

                    if (ls == null || ls.isEmpty()) {
                        JmoordbUtil.warningMessage(spanish ? "El entity no contiene llave @Secondary" : "The entity have not @Secondary");

                    } else {

                        if (JmoordbUtil.isVacio(repository.secondaryKeyValue(entity))) {
                            JmoordbIntrospection.callSet(controller, "writable", false);
                            afterValidateNew(false);
                            return "";
                        }

                        //Convierte a mayusculas la llave primaria y la devuelve en el entity
                        if (!searchLowerCase) {
                            entity = repository.secondaryKeyValueToUpper(entity);
                        } else {
                            entity = repository.secondaryKeyValueToLower(entity);
                        }

                        Optional<Object> optionalSecondary = repository.findBySecondaryKey(entity);

                        if (optionalSecondary.isPresent()) {
                            JmoordbIntrospection.callSet(controller, "writable", false);
                            JmoordbUtil.warningMessage(spanish ? "Ya existe un documento con esos datos" : "A document with this data already exists");
                            afterValidateNew(false);
                            return "";
                        } else {

                            String id = repository.secondaryKeyValue(entity);

                            Object entitynew = JmoordbIntrospection.newEntity(entity);
                            entitynew = repository.secondaryKeySetValue(entity, id);

                            /**
                             * crea un entity nuevo le asigna la llave primaria
                             * asigna al selected el entity
                             */
                            JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                            /**
                             * crea un entity nuevo le asigna la llave primaria
                             * asigna al selected el entity
                             */
                            JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                        }
                    }
                    break;
                case "tertiary":
                    //----------------------------------
                    //Busca por llave secundaria
                    //----------------------------------
                    List<TertiaryKey> lsTertiary = repository.getTertiaryKeyList();

                    if (lsTertiary == null || lsTertiary.isEmpty()) {
                        JmoordbUtil.warningMessage(spanish ? "El entity no contiene llave @Secondary" : "The entity have not @Secondary");

                    } else {

                        if (JmoordbUtil.isVacio(repository.tertiaryKeyValue(entity))) {
                            JmoordbIntrospection.callSet(controller, "writable", false);
                            afterValidateNew(false);
                            return "";
                        }

                        //Convierte a mayusculas la llave primaria y la devuelve en el entity
                        if (!searchLowerCase) {
                            entity = repository.tertiaryKeyValueToUpper(entity);
                        } else {
                            entity = repository.tertiaryKeyValueToLower(entity);
                        }

                        Optional<Object> optionalTertiary = repository.findByTertiaryKey(entity);

                        if (optionalTertiary.isPresent()) {
                            JmoordbIntrospection.callSet(controller, "writable", false);
                            JmoordbUtil.warningMessage(spanish ? "Ya existe un documento con esos datos" : "A document with this data already exists");
                            afterValidateNew(false);
                            return "";
                        } else {

                            String id = repository.tertiaryKeyValue(entity);

                            Object entitynew = JmoordbIntrospection.newEntity(entity);
                            entitynew = repository.tertiaryKeySetValue(entity, id);

                            /**
                             * crea un entity nuevo le asigna la llave primaria
                             * asigna al selected el entity
                             */
                            JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                            /**
                             * crea un entity nuevo le asigna la llave primaria
                             * asigna al selected el entity
                             */
                            JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                        }
                    }
                    break;
                case "composite":
                    break;
                default:
                    JmoordbUtil.warningMessage(spanish ? "typeKey debe ser(primary,secondary,tertiary,composite) " : "typeKey could by(primary,secondary,tertiary,composite)");

            }

            //Agregar el UserInfo al entity
            if (!beforeValidateNew()) {
                afterValidateNew(false);
                return "";
            }

            validate = true;

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
            validate = false;
        }

        afterValidateNew(validate);

        return "";

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean beforeValidateNew()">
    default Boolean
            beforeValidateNew() {
        return true;

    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean afterValidateNew(Boolean validate)">
    default Boolean
            afterValidateNew(Boolean validate
            ) {
        return validate;

    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String satar(String field)">
    /**
     * componentes <jmoordbjsf:search>
     * Lo invocan los componentes de busqueda
     *
     * @param field
     * @param value
     * @return
     */
    default public String searchBy(String field, Object value) {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbContext.put("search" + nameOfController, field);
            JmoordbIntrospection.callSet(controller, "writable", true);
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            JmoordbContext.put("valuesearch" + nameOfController, value);

            move(page);

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String searchBy(String nameOfSearch, String field, Object value) ">
    /**
     * Se usa para los casos que las busquedas no son por el entity y
     * construimos otros atributos componentes <jmoordbjsf:search>
     * Lo invocan los componentes de busqueda
     *
     * @param field
     * @param value
     * @return
     */
    default public String searchBy(String nameOfSearch, String field, Object value) {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbContext.put("search" + nameOfSearch, field);
            JmoordbIntrospection.callSet(controller, "writable", true);
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            JmoordbContext.put("valuesearch" + nameOfSearch, value);

            move(page);

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="searchBetweenDate(String field, Date start, Date end)">
    /**
     * se usa con componente
     * <jmoordbjsf:searchBetweenDate/>
     * asigna: _searchfield con la fecha de inicio _searchfield2 con la fecha de
     * fin
     *
     * @param field
     * @param start
     * @param end
     * @return
     */
    default public String searchBetweenDate(String field, Date start, Date end) {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbContext.put("search" + nameOfController, field);
            JmoordbIntrospection.callSet(controller, "writable", true);
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            JmoordbContext.put("valuesearch" + nameOfController, start);
            JmoordbContext.put("valuesearch" + nameOfController + "2", end);
            move(page);

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String skip()">
    /**
     * componentes <jmoordbjsf:paginator>
     * toma el numero de pagina y lo mueve
     *
     * @param field
     * @param value
     * @return
     */
    default public String skip() {
        try {
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            move(page);

        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="print()">
    /**
     * <jmoodbjsf:toolbarview>
     * lo invoca para imprimir
     *
     * @return
     */
    default public String print() {
        Boolean edited = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Los pasa el usuaario
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            List<Object> list = new ArrayList<Object>();
            list.add(entity);

//            JmoordbPrinter.imprimir(list, pathReportDetail, parameters);
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return "";

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="printAll()">

    /**
     * <jmoodbjsf:toolbarview>
     * lo invoca para imprimir
     *
     * @return
     */
    default public String printAll() {
        Boolean edited = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Los pasa el usuaario
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
//Crea una ordenacion en base al nombre de la llave primaria
            Document doc = new Document(repository.primaryKeyName(entity), 1);
            List<Object> list = repository.findAll(doc);

//            JmoordbPrinter.imprimir(list, pathReportDetail, parameters);
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return "";

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String aspectHandleAutocompleteOfListXhtml()">

    /**
     * <jmoodbjsf:toolbarview>
     * lo invoca para imprimir
     *
     * @return
     */
    default public String handleAutocompleteOfListXhtml(SelectEvent event) {
//    default public String aspectHandleAutocompleteOfListXhtml() {
        Boolean edited = false;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Los pasa el usuaario
            //------------------------------------
            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            /**
             * invoca el valor del entitySearch Crea una lista del entity vacia
             * asigna el entitySearch invoca el metodo setEntityList para
             * asignarlo invoca al ser pasandole la llave primaria como
             * parametro y el valor de la misma
             */
            Object entitySearch = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity + "Search");
            List<Object> list = new ArrayList<>();
            list.add(entitySearch);
            JmoordbIntrospection.callSet(controller, nameOfEntity + "List", list);
            //
            searchBy(repository.primaryKeyName(entitySearch), repository.primaryKeyValue(entitySearch));

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return "";

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" String getSearch()">
    /**
     * Devuelve el valor de la condicion que se usara para el search en el move
     *
     * @return
     */
    default String getSearch() {
        String condition = "_init";
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            if (JmoordbContext.get("search" + nameOfController) == null) {
                JmoordbContext.put("search" + nameOfController, "_init");
            }
            condition = (String) JmoordbContext.get("search" + nameOfController);

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return condition;

    } // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Package entityPackage()">

    /**
     * Devuelve el paquete del entity
     *
     * @return
     */
    default Package entityPackage() {
        String condition = "_init";
        Package p = null;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();

            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            p = entity.getClass().getPackage();
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return p;

    } // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Package repositoryPackage()">

    /**
     * Devuelve el paquete del repositorio
     *
     * @return
     */
    default Package repositoryPackage() {

        Package p = null;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();

            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            p = repository.getClass().getPackage();
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return p;

    } // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Package repositoryPackage()">

    /**
     * Devuelve el paquete del sevice
     *
     * @return
     */
    default Package servicesPackage() {

        Package p = null;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();

            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            p = service.getClass().getPackage();
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return p;

    } // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object getValueSearch() )">

    /**
     * Devuelve el valor de la condicion que se usara para el search en el move
     *
     * @return
     */
    default Object getValueSearch() {
        Object value = null;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            if (JmoordbContext.get("valuesearch" + nameOfController) == null) {
                JmoordbContext.put("valuesearch" + nameOfController, "_init");
            }
            value = JmoordbContext.get("valuesearch" + nameOfController);

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return value;

    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String setSearchAndValue(String search, Object valuesearch)">
    /**
     * Asigna al search y searchvalue los valores para ser usados en el move()
     * Se invocan desde los metodos handleSelected generalmente.
     *
     * @return
     */
    default String setSearchAndValue(String search, Object valuesearch) {
        Object value = null;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            JmoordbContext.put("search" + nameOfController, search);
            JmoordbContext.put("valuesearch" + nameOfController, valuesearch);

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return "";

    } // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Object getAction() )">
    /**
     * Obtiene el valor del action
     *
     * @return
     */
    default String getAction() {
        String actionLocal = "golist";
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            if (JmoordbContext.get("action" + nameOfController) == null) {
                JmoordbContext.put("action" + nameOfController, "golist");
            }
            actionLocal = (String) JmoordbContext.get("action" + nameOfController);

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return actionLocal;

    } // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="void setAction(String actionValue) ">

    /**
     * Asigna valor al action
     *
     * @return
     */
    default void setAction(String actionValue) {

        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            JmoordbContext.put("action" + nameOfController, actionValue);
            jme.setAction(actionValue);

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Object getEntityValue()">
    /**
     * Devuelve el valor del entity del controller actual en el contexto
     *
     * @return
     */
    default Object getEntityValue() {
        Object value = null;
        try {
            JmoordbConfiguration jmc = new JmoordbConfiguration();
            JmoordbControllerEnvironment jme = new JmoordbControllerEnvironment();
            String username = jmc.getUsername();
            Repository repositoryRevisionHistory = jmc.getRepositoryRevisionHistory();
            RevisionHistoryServices revisionHistoryServices = jmc.getRevisionHistoryServices();
            Boolean saverevision = jmc.getRevisionSave();
            Boolean languaguespanish = jmc.getSpanish();

            Boolean spanish = true;
            if (languaguespanish == null) {
                JmoordbUtil.warningMessage("Configure el parametro {languaguespanish} en el ExternalContext de la clase principal");
            } else {
                spanish = languaguespanish;
            }

            if (saverevision == null) {
                JmoordbUtil.warningMessage(spanish ? "Configure el parametro {saverevision} en el ExternalContext de la clase principal" : "Configure the {saverevision} parameter in the ExternalContext of the main class");
                saverevision = false;
            }

            //Obtenerlos desde el JmoordbControllerEnvironment
            Repository repository = jme.getRepository();
            Object controller = jme.getController();
            Object entity = jme.getEntity();
            Object service = jme.getService();
            String nameFieldOfPage = jme.getNameFieldOfPage();
            String nameFieldOfRowPage = jme.getNameFieldOfRowPage();
            String typeKey = jme.getTypeKey();
            Boolean searchLowerCase = jme.getSearchLowerCase();
            Boolean resetInSave = jme.getResetInSave();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            String action = jme.getAction();
            String nameOfController = controller.getClass().getSimpleName();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            if (JmoordbContext.get("entityValue" + nameOfController) == null) {
                value = null;
            }
            value = (Object) JmoordbContext.get("entityValue" + nameOfController);

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());

        }

        return value;

    } // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="handleSelect(SelectEvent event) ">
    default public void handleSelect(SelectEvent event) {
        try {
        } catch (Exception e) {
              JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
    }// </editor-fold>

}
