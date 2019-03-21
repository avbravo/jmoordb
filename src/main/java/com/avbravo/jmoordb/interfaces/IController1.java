/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.interfaces;

import com.avbravo.jmoordb.anotations.Aspect;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordb.util.JmoordbUtil;

import java.util.Map;
import java.util.Optional;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @authoravbravo
 */
public interface IController1<T> {

    public String preRenderView(String action);

    public String prepare(String action, Object item);
// <editor-fold defaultstate="collapsed" desc="internalPrepareGoNew()">

    default public String internalPrepareGoNew() {
        String url = "";
        try {
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
            url = prepare("gonew", entity);
        } catch (Exception e) {

        }

        return url;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String internalPrepareNew()">
    default public String internalPrepareNew() {
        String url = "";
        try {
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
            url = prepare("new", entity);
        } catch (Exception e) {

        }

        return url;
    }
    // <editor-fold defaultstate="collapsed" desc="String internalPrepareNew()">
    default public String internalCallDeleteOfView() {
        String url = "";
        try {
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
            url = delete( entity,true);
        } catch (Exception e) {

        }

        return url;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String internalPrepareGoList()">

    default public String internalPrepareGoList() {
        String url = "";
        try {
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
            url = prepare("golist", entity);
        } catch (Exception e) {

        }

        return url;
    }
    // </editor-fold>

    default public String refresh() {
        return "";
    }

    public String isNew();

    public void reset();

    public String showAll();


  

    public String delete(Object item, Boolean deleteonviewpage);

    public String deleteAll();

    public String print();

    public String printAll();

    public String clear();

    // <editor-fold defaultstate="collapsed" desc="last">
    default public String last() {
        try {
            Integer page = 0;
//            Integer sizeOfPage = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("sizeOfPage");

            page = sizeOfPage();
            move(page);
        } catch (Exception e) {
//           errorDialog(nameOfMethod(), e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="first">
    default public String first() {
        try {
            Integer page = 1;
            move(page);
        } catch (Exception e) {
//        errorDialog(nameOfMethod(), e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="next">
    default public String next() {
        try {
            Integer page = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");
            if (page < sizeOfPage()) {
                page++;
            }

            move(page);
        } catch (Exception e) {
//            JmoordbUtil.errorMessage(nameOfMethod(), e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="back">
    default public String back() {
        try {
            Integer page = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");

            if (page > 1) {
                page--;
            }
            move(page);
        } catch (Exception e) {
//             JmoordbUtil.errorMessage(nameOfMethod(), e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    public String skip(Integer page);


    public void move(Integer page);

    public String searchBy(String field);

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

    public Integer sizeOfPage() ;
   
    // <editor-fold defaultstate="collapsed" desc="save()">

    default public String save() {
        Boolean saved = false;
        try {
            //Se cargan en el LoginController
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            String username = (String) sessionMap.get("username");
            Repository repositoryRevisionHistory = (Repository) sessionMap.get("repositoryRevisionHistory");
            RevisionHistoryServices revisionHistoryServices = (RevisionHistoryServices) sessionMap.get("revisionHistoryServices");
            Boolean saverevision = (Boolean) sessionMap.get("saverevision");

            Boolean languaguespanish = (Boolean) sessionMap.get("languaguespanish");
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
            Repository repository = (Repository) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("repository");
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
//            String primarykeyvalue = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("primarykeyvalue");
            Boolean searchbyfieldsecond = (Boolean) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("searchbyfieldsecond");

            if (searchbyfieldsecond) {
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

            if (!beforeSave()) {
                return "";
            }
            if (repository.save(entity)) {
                //Devuelve el valor de la llave primaria
                String primarykeyvalue = repository.primaryKeyValue(entity);

                if (saverevision) {
                    String nameOfEntity = JmoordbUtil.nombreEntity(entity.getClass().getName());
                    if (_validRevisionHistory(repositoryRevisionHistory, revisionHistoryServices, spanish)) {
                        repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                                "create", nameOfEntity, repository.toDocument(entity).toString()));
                    }

                }
                   JmoordbUtil.successMessage(spanish ? "Guardado" : "Saved");
                reset();
            } else {
                JmoordbUtil.errorMessage(nameOfMethod()+ " "+   repository.getException().toString());
            }
            saved = true;

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod()+ " "+ ex.getLocalizedMessage());
        }

        afterSave(saved);

        return "";

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="edit()">

    default public String edit() {
        Boolean edited = false;
        try {
            //Se cargan en el LoginController
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            String username = (String) sessionMap.get("username");
            Repository repositoryRevisionHistory = (Repository) sessionMap.get("repositoryRevisionHistory");
            RevisionHistoryServices revisionHistoryServices = (RevisionHistoryServices) sessionMap.get("revisionHistoryServices");
            Boolean saverevision = (Boolean) sessionMap.get("saverevision");

            Boolean languaguespanish = (Boolean) sessionMap.get("languaguespanish");
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
            Repository repository = (Repository) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("repository");
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
//            String primarykeyvalue = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("primarykeyvalue");
            Boolean searchbyfieldsecond = (Boolean) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("searchbyfieldsecond");

            if (searchbyfieldsecond) {
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

            if (!beforeEdit()) {
                return "";
            }
            if (repository.update(entity)) {
                //Devuelve el valor de la llave primaria
                String primarykeyvalue = repository.primaryKeyValue(entity);

                if (saverevision) {
                    String nameOfEntity = JmoordbUtil.nombreEntity(entity.getClass().getName());
                    if (_validRevisionHistory(repositoryRevisionHistory, revisionHistoryServices, spanish)) {
                        repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                                "update", nameOfEntity, repository.toDocument(entity).toString()));
                    }

                }
                   JmoordbUtil.successMessage(spanish ? "Editado" : "Edited");
           
            } else {
                JmoordbUtil.errorMessage(nameOfMethod()+ " "+   repository.getException().toString());
            }
            edited = true;

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod()+ " "+ ex.getLocalizedMessage());
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
           JmoordbUtil.errorMessage(nameOfMethod()+ " "+ ex.getLocalizedMessage());

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
    default Boolean
            beforeSave() {
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
    default Boolean
            afterSave(Boolean saved
            ) {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="beforeEdit()">
    /**
     * se inyecta en el metodo save() antes de ejecutar el repository.edit();
     *
     * @return
     */
    default Boolean
            beforeEdit() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="afterEdit(Boolean edit)">
    /**
     * se invoca despues del metodo edit()
     *
     * @param edit
     * @return
     */
    default Boolean
            afterEdit(Boolean edited) {
        return true;

    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" beforeDelete()">

    /**
     * se inyecta en el metodo antes de ejecutar el repository.edit();
     *
     * @return
     */
    default Boolean
            beforeDelete() {
        return true;

    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="afterDelete(Boolean edit)">
    /**
     * se invoca despues del metodo deleted()
     *
     * @param edit
     * @return
     */
    default Boolean
            afterDelete(Boolean edit
            ) {
        return true;
    }// </editor-fold>

}
