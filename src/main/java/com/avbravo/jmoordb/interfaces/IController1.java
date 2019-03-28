/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.interfaces;

import com.avbravo.jmoordb.SecondaryKey;
import com.avbravo.jmoordb.configuration.JmoordbConfiguration;
import com.avbravo.jmoordb.anotations.Aspect;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.configuration.JmoordbControllerEnvironment;
import com.avbravo.jmoordb.metafactory.JmoordbIntrospection;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordb.util.JmoordbPrinter;
import com.avbravo.jmoordb.util.JmoordbUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.Set;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
public interface IController1<T> {

    public String preRenderView(String action);

    default public String refresh() {
        return "";
    }



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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);
            Integer rowPage = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfRowPage);
            size = repository.sizeOfPage(rowPage);

        } catch (Exception e) {
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return size;

    }

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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            page = sizeOfPage();
            move(page);
        } catch (Exception e) {
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    public void move(Integer page);

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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            //Los pasa el usuaario
//            String primarykeyvalue = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("primarykeyvalue");
            if (searchbysecondary) {
                //Busca por llave secundaria
                Optional<Object> optional = repository.findBySecondaryKey(entity);
                if (optional.isPresent()) {
                    JmoordbUtil.warningMessage(spanish ? "Existe un documento con esos datos" : "There is a document with this data");
                    return "";
                }

            } else {
                //Busca por llave primaria
                Optional<Object> optional = repository.findById(entity);
                if (optional.isPresent()) {
                    JmoordbUtil.warningMessage(spanish ? "Existe un documento con esos datos" : "There is a document with this data");
                    return "";
                }
            }

            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForSaveMethod(entity, username, "create");

            if (!beforeSave()) {
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
                reset();
            } else {
                JmoordbUtil.errorMessage(nameOfMethod() + " " + repository.getException().toString());
            }
            saved = true;

        } catch (Exception ex) {

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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            if (searchbysecondary) {
                //Busca por llave secundaria
                Optional<Object> optional = repository.findBySecondaryKey(entity);
                if (!optional.isPresent()) {
                    JmoordbUtil.warningMessage(spanish ? "No existe un documento con esos datos" : "there is no document with this data");
                    return "";
                }

            } else {
                //Busca por llave primaria
                Optional<Object> optional = repository.findById(entity);
                if (!optional.isPresent()) {
                    JmoordbUtil.warningMessage(spanish ? "No existe un documento con esos datos" : "There is nodocument with this data");
                    return "";
                }
            }
            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForEditMethod(entity, username, "update");

            if (!beforeEdit()) {
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
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

        return "";

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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
                 * 1. Obtener la lista de Entity 2. Remover el elemento
                 * seleccionado 3. Invocar el setEntityList con la nueva lista
                 * 4. Invocar el setWritable con false 5- Obtener el numero de
                 * pagina actual 6- Guardar en el Context el numero de pagina
                 * actual
                 */

                List<Object> list = (List<Object>) JmoordbIntrospection.callGet(controller, nameOfEntity.trim() + "List");
                list.remove(entity);
                Integer page = JmoordbIntrospection.getPageInController(controller);

                JmoordbIntrospection.callSet(controller, nameOfEntity.trim() + "List", list);
                JmoordbIntrospection.callSet(controller, "writable", false);
                JmoordbContext.put("page" + nameOfEntity, page.toString());

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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
            JmoordbContext.put("page" + nameOfEntity, page.toString());
            JmoordbContext.put(nameOfEntity, "view");
            JmoordbContext.put(nameOfEntity + "_value", entity);
//           
            JmoordbIntrospection.callSet(controller, nameOfEntity, entity);
            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entity);
            if (repository.primaryKeyIsInteger(entity)) {
                JmoordbContext.put(repository.primaryKeyName(entity), repository.primaryKeyValueInteger(entity));
            } else {
                JmoordbContext.put(repository.primaryKeyName(entity), repository.primaryKeyValue(entity));
            }

            if (beforePrepareView()) {

            }

        } catch (Exception e) {
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }

        return url;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" beforePrepareView()">

    /**
     * se inyecta en el metodo antes de ejecutar el repository. ();
     *
     * @return
     */
    default Boolean beforePrepareView() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="start()">
    /**
     * 1. Obtener el action mediante JmoordbContext.get(nombreentity)
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
     * 13. Validar el action
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);

            //Agregar el UserInfo al entity
            entity = repository.addUserInfoForEditMethod(entity, username, "delete");

            if (!beforeStart()) {
                return;

            }

            String action = (String) JmoordbContext.get(nameOfEntity);

            String pageSession = (String) JmoordbContext.get("page" + nameOfEntity);
            //Search

            if (JmoordbContext.get("search" + nameOfEntity) == null || (JmoordbContext.get("search" + nameOfEntity).toString().equals(""))) {
                JmoordbContext.put("search" + nameOfEntity, "_init");
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
                        entity = (Object) JmoordbContext.get(nameOfEntity + "_value");

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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
            JmoordbContext.put("page" + nameOfEntity, page.toString());
            JmoordbContext.put(nameOfEntity, "golist");

            if (beforePrepareGoList()) {

            }

        } catch (Exception e) {
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
            JmoordbContext.put("page" + nameOfEntity, page.toString());
            JmoordbContext.put(nameOfEntity, "gonew");

            if (beforePrepareGoNew()) {

            }

        } catch (Exception e) {
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
            gonew = false;
        }
        afterPrepareGoNew(gonew);
        return url;
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
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
            crear en el FacesContext el action
             */
            //1.Obtener el nombre del entity
            Integer page = JmoordbIntrospection.getPageInController(controller);

            // 2.Invocar al metodo getPage() del Controller
            JmoordbContext.put("page" + nameOfEntity, page.toString());
            JmoordbContext.put(nameOfEntity, "new");

//           
            JmoordbIntrospection.callSet(controller, nameOfEntity, entity);
            JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entity);
            JmoordbIntrospection.callSet(controller, "writable", false);

            if (beforePrepareNew()) {

            }

        } catch (Exception e) {
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
     public void reset() ;
       
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            Integer page = 1;

            JmoordbContext.put("search" + nameOfEntity, "_init");
//           
            JmoordbIntrospection.callSet(controller, "page", 1);
            move(page);

            if (beforeClear()) {

            }

        } catch (Exception e) {
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }

        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean beforePrepareGoNew()">
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbIntrospection.callSet(controller, "writable", true);

            if (searchbysecondary) {
                //Busca por llave secundaria
                List<SecondaryKey> ls = repository.getSecondaryKeyList();

                HashMap<String, Object> map = repository.secondaryKeyValueObject(entity);
                Set set = map.entrySet();
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry) iterator.next();
                    for (SecondaryKey s : ls) {
                        if (s.getName().equals(mentry.getKey())) {
                            switch (s.getType()) {
                                case "java.lang.String":
                                    if (JmoordbUtil.isVacio(mentry.getValue().toString())) {
                                        JmoordbIntrospection.callSet(controller, "writable", false);
                                        afterValidateNew(false);
                                        return "";
                                    }
                                    break;
                                case "java.lang.Integer":
                                    if (JmoordbUtil.isVacio((Integer) mentry.getValue())) {
                                        JmoordbIntrospection.callSet(controller, "writable", false);
                                        afterValidateNew(false);
                                        return "";
                                    }
                                    break;

                            }
                        }

                    }
                }

                //Convierte a mayusculas la llave primaria y la devuelve en el entity
                entity = repository.secondaryKeyValueToUpper(entity);

                Optional<Object> optional = repository.findBySecondaryKey(entity);

                if (optional.isPresent()) {
                    JmoordbIntrospection.callSet(controller, "writable", false);
                    JmoordbUtil.warningMessage(spanish ? "Ya existe un documento con esos datos" : "A document with this data already exists");
                    afterValidateNew(false);
                    return "";
                } else {
                    /*
                    Obtienes las llaves secundarias
                    creas un entity nuevo con new
                    asignas las llaves secundarias.
                     */

                    HashMap<String, Object> secondary = repository.secondaryKeyValueObject(entity);

                    Object entitynew = JmoordbIntrospection.newEntity(entity);

                    Set set1 = map.entrySet();
                    Iterator iterator1 = set1.iterator();
                    while (iterator.hasNext()) {
                        Map.Entry mentry = (Map.Entry) iterator.next();
                        entitynew = repository.secondaryKeySetValue(entity, mentry.getKey().toString(), mentry.getValue());
                    }

                    /**
                     * crea un entity nuevo le asigna la llave primaria asigna
                     * al selected el entity
                     */
                    JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                    JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                }

            } else {
                //----------------------------------
                //Busca por llave primaria
                //----------------------------------
                if (JmoordbUtil.isVacio(repository.primaryKeyValue(entity))) {
                    JmoordbIntrospection.callSet(controller, "writable", false);
                    afterValidateNew(false);
                    return "";
                }

                //Convierte a mayusculas la llave primaria y la devuelve en el entity
                entity = repository.primaryKeyValueToUpper(entity);

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
                     * crea un entity nuevo le asigna la llave primaria asigna
                     * al selected el entity
                     */
                    JmoordbIntrospection.callSet(controller, nameOfEntity, entitynew);
                    JmoordbIntrospection.callSet(controller, nameOfEntity + "Selected", entitynew);

                }
//            

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

    // <editor-fold defaultstate="collapsed" desc="String searchBy(String field)">
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbContext.put("search" + nameOfEntity, field);
            JmoordbIntrospection.callSet(controller, "writable", true);
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            JmoordbContext.put("_fieldsearch" + nameOfEntity, value);
            move(page);

        } catch (Exception e) {
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
            JmoordbContext.put("search" + nameOfEntity, field);
            JmoordbIntrospection.callSet(controller, "writable", true);
            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            JmoordbContext.put("_fieldsearch" + nameOfEntity, start);
            JmoordbContext.put("_fieldsearch" + nameOfEntity + "2", end);
            move(page);

        } catch (Exception e) {
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
              String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            Integer page = (Integer) JmoordbIntrospection.callGet(controller, nameFieldOfPage);

            move(page);

        } catch (Exception e) {
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="print()">
    /**
     * <jmoodbjsf:toolbarview>
     * lo invoca para imprimir
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------

            List<Object> list = new ArrayList<Object>();
            list.add(entity);
          
             JmoordbPrinter.imprimir(list, pathReportDetail, parameters);
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return "";

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="printAll()">
    /**
     * <jmoodbjsf:toolbarview>
     * lo invoca para imprimir
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);
            //------------------------------------
//Crea una ordenacion en base al nombre de la llave primaria
            Document doc = new Document(repository.primaryKeyName(entity),1);
            List<Object> list = repository.findAll(doc);
                     
             JmoordbPrinter.imprimir(list, pathReportDetail, parameters);
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return "";

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String aspectHandleAutocompleteOfListXhtml()">
    /**
     * <jmoodbjsf:toolbarview>
     * lo invoca para imprimir
     * @return 
     */
    default public String aspectHandleAutocompleteOfListXhtml() {
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
            Boolean searchbysecondary = jme.getSearchbysecondarykey();
            String pathReportDetail = jme.getPathReportDetail();
            String pathReportAll = jme.getPathReportAll();
            HashMap parameters = jme.getParameters();
            String nameOfEntity = JmoordbIntrospection.nameOfEntity(entity);
            entity = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity);            
            //------------------------------------
/**
 * invoca el valor del entitySearch
 * Crea una lista del entity vacia
 * asigna el entitySearch
 * invoca el metodo setEntityList para asignarlo
 * invoca al ser pasandole la llave primaria como parametro y el valor de la misma
 */
            Object entitySearch = (Object) JmoordbIntrospection.callGet(controller, nameOfEntity+"Search");            
            List<Object> list = new ArrayList<>();
            list.add(entitySearch);
            JmoordbIntrospection.callSet(controller, nameOfEntity+"List", list);
            //
             searchBy(repository.primaryKeyName(entitySearch), repository.primaryKeyValue(entitySearch));
                     
            
        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return "";

    }// </editor-fold>

}
