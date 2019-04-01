/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.interfaces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @authoravbravo
 */
public interface IControllerOld<T> {

    public String preRenderView(String action);

    default public String refresh() {
        return "";
    }

    public String isNew();

    public void reset();

    public String showAll();

    public String save();

    public String edit();

    public String delete(Object item, Boolean deleteonviewpage);

    public String deleteAll();

    public String print();

    public String printAll();

    public String clear();

    // <editor-fold defaultstate="collapsed" desc="last">
  
    default public String last() {
        try {
              Integer page= 0;
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
          Integer  page = 1;
            move(page);
        } catch (Exception e) {
//        errorDialog(nameOfMethod(), e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

     // <editor-fold defaultstate="collapsed" desc="next">
    default public String next() {      
        try {
            Integer page= (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");
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
              Integer page= (Integer) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("page");
                  
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
}