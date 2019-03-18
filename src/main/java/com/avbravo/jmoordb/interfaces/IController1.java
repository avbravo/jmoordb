/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.interfaces;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordb.services.UserInfoServices;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.store.util.ResourcesFiles;
import com.avbravo.storeejb.producer.AutoincrementableServices;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
public interface IController1<T, V> {


    public String preRenderView(String action);

    default public String refresh() {
        return "";
    }

    public String isNew();

    public void reset();

    public String showAll();

//    public String save();
    public String edit();

    public String delete(Object item, Boolean deleteonviewpage);

    public String deleteAll();

    public String print();

    public String printAll();

    public String clear();

    // <editor-fold defaultstate="collapsed" desc="last">
    default public String last() {
        try {
            Integer page = 0;
            Integer sizeOfPage = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("sizeOfPage");

            page = sizeOfPage;
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
            Integer sizeOfPage = (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("sizeOfPage");

            if (page < sizeOfPage) {
                page++;
            }
//            JsfUtil.warningDialog("IController.next", "page: " + page.toString());
            move(page);
        } catch (Exception e) {
//            JsfUtil.errorDialog(nameOfMethod(), e.getLocalizedMessage());
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
//             JsfUtil.errorDialog(nameOfMethod(), e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    public String skip(Integer page);

    // <editor-fold defaultstate="collapsed" desc="skip(Integer page)">
//   default public String skip() {
//        try {
//              Integer page= (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");
//            move(page);
//        } catch (Exception e) {
//           JsfUtil.errorDialog(nameOfMethod(), e.getLocalizedMessage());
//        }
//        return "";
//    }// </editor-fold>
    public void move(Integer page);

    public String searchBy(String field);

    public default String nameOfClassAndMethod() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
        final String s = e.getClassName();
        return s.substring(s.lastIndexOf('.') + 1, s.length()) + "." + e.getMethodName();
    }

    public default String nameOfClass() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
        final String s = e.getClassName();
        return s.substring(s.lastIndexOf('.') + 1, s.length());
    }

    public default String nameOfMethod() {
        final StackTraceElement e = Thread.currentThread().getStackTrace()[2];
        final String s = e.getClassName();
        return e.getMethodName();
    }

    public Integer sizeOfPage();

    default public String save() {
        try {
            //Se cargan en el LoginController
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            String username = (String) sessionMap.get("username");
            Repository repositoryRevisionHistory = (Repository) sessionMap.get("repositoryRevisionHistory");
            RevisionHistoryServices revisionHistoryServices = (RevisionHistoryServices) sessionMap.get("revisionHistoryServices");

            ResourcesFiles rf = (ResourcesFiles) sessionMap.get("resourcesFiles");

            //Los pasa el usuaario


            Repository repository = (Repository) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("repository");
            Object entity = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("entity");
            String primarykeyvalue = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("primarykeyvalue");
            Boolean searchbyfieldsecond = (Boolean) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("searchbyfieldsecond");
            String fieldsecondarykeyname = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fieldsecondarykeyname");
            Object fieldsecondarykeyvalue = (Object) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("fieldsecondarykeyvalue");
            if (searchbyfieldsecond) {
                List<Object> list = repository.findBy(new Document(fieldsecondarykeyname, fieldsecondarykeyvalue));
                if (!list.isEmpty()) {
                    JsfUtil.warningMessage(rf.getAppMessage("warning.idexist"));
                    return "";
                }

               
            } else {
                Optional<Object> optional = repository.findById(entity);
                if (optional.isPresent()) {
                    JsfUtil.warningMessage(rf.getAppMessage("warning.idexist"));
                    return "";
                }
            }

            if (!beforeSave()) {
                return "";
            }
            if (repository.save(entity)) {
                repositoryRevisionHistory.save(revisionHistoryServices.getRevisionHistory(primarykeyvalue, username,
                        "create", entity.getClass().getName(), repository.toDocument(entity).toString()));
                JsfUtil.successMessage(rf.getAppMessage("info.save"));
                reset();
            } else {
                JsfUtil.successMessage("save() " + repository.getException().toString());
            }

            afterSave();
        } catch (Exception ex) {

            JsfUtil.warningDialog("save()", "error" + ex.getLocalizedMessage());
        }
        return "";

    }

    default Boolean beforeSave() {
        return true;
    }

    default Boolean afterSave() {
        return true;
    }
}
