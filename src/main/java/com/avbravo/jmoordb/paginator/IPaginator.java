/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.paginator;


import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.util.JmoordbUtil;
import static com.avbravo.jmoordb.util.JmoordbUtil.nameOfMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author avbravo
 */
public interface IPaginator {

    public default String last() {
        try {
            String controller = nameOfController();

            Paginator paginator = (Paginator) JmoordbContext.get("paginator" + controller);
            paginator.setPage(paginator.getNumberOfPage());
            JmoordbContext.put("paginator" + controller, paginator);
            move();
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="first">
    public default String first() {
        try {
            String controller = nameOfController();
            Paginator paginator = (Paginator) JmoordbContext.get("paginator" + controller);
            paginator.setPage(1);
            JmoordbContext.put("paginator" + controller, paginator);
            move();
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="next">
    public default String next() {
        try {
            String controller = nameOfController();
            // ------------------------------------
            Paginator paginator = (Paginator) JmoordbContext.get("paginator" + controller);
            if (paginator.getPage() < paginator.getNumberOfPage()) {
                paginator.setPage(paginator.getPage() + 1);
            }
            JmoordbContext.put("paginator" + controller, paginator);
            move();
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="back">
    public default String back() {
        try {
  
            String controller = nameOfController();
            Paginator paginator = (Paginator) JmoordbContext.get("paginator" + controller);
 
            if (paginator.getPage() > 1) {
             
                paginator.setPage(paginator.getPage() - 1);
                 
            }
            JmoordbContext.put("paginator" + controller, paginator);
            move();
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String skip()">
    /**
     * componentes <jmoordbjsf:paginator> toma el numero de pagina y lo mueve
     *
     * @param field
     * @param value
     * @return
     */
    public default String skip() {
        try {
          
            String controller = nameOfController();

            Integer pageforskip = (Integer) JmoordbContext.get("pageforskip" + controller);
          
            Paginator paginator = (Paginator) JmoordbContext.get("paginator" + controller);
            paginator.setPage(pageforskip);
            JmoordbContext.put("paginator" + controller, paginator);
            move();

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="move(Integer page)">
    public default void move() {
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String nameOfController() )">
    default String nameOfController() {
        String controller = "";
        try {
            if (JmoordbContext.get("nameOfController") == null) {
                controller = "";
            }
            controller = (String) JmoordbContext.get("nameOfController");

        } catch (Exception ex) {

            JmoordbUtil.errorMessage(nameOfMethod() + " " + ex.getLocalizedMessage());
        }

        return controller.trim();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="saveQuery(Document query, Document sort) ">
    public default void savePaginator(Object controller, Paginator paginator) {
        try {
          
            String nameOfController = controller.getClass().getSimpleName();
          

            JmoordbContext.put("paginator" + nameOfController, paginator);
         
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
    } // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void start(Object controller) ">

    public default void start(Object controller) {
        try {
            String nameOfController = controller.getClass().getSimpleName();
            JmoordbContext.put("nameOfController", nameOfController);

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
    } // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="PaginatorQuery getPaginatorQuery() ">

    /**
     * Devuelve el paginator query
     *
     * @return
     */
    public default Paginator getPaginator(Object controller) {
        Paginator paginator = new Paginator();
        try {
            String nameOfController = controller.getClass().getSimpleName();
            paginator = (Paginator) JmoordbContext.get("paginator" + nameOfController);

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return paginator;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PaginatorQuery getPaginatorQuery() ">
    /**
     * Devuelve el paginator query
     *
     * @return
     */
    public default Boolean existPaginator(Object controller) {
        Paginator paginator = new Paginator();
        try {
            String nameOfController = controller.getClass().getSimpleName();
            if (JmoordbContext.get("paginator" + nameOfController) == null) {
                return false;
            }
            return true;

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return false;
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer numberOfPages(Integer rows,Integer rowForPage)">
    default Integer numberOfPages(Integer rows, Integer rowForPage) {
        Integer numberOfPage = 1;
        try {

            if (rows > 0) {
                numberOfPage = rows / rowForPage;
                if ((rows % rowForPage) > 0) {
                    numberOfPage++;
                }
            }
        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return numberOfPage;
    }
    // </editor-fold>
    
    
    
    // <editor-fold defaultstate="collapsed" desc="List<Integer> arrayListOfPage(Integer numberOfPage) ">
    /**
     * Devuele un array list en base al numero de paginas pasadaas
     *
     * @param rowsForPage
     * @param doc
     * @return
     */
   default public List<Integer> arrayListOfNumber(Integer numberOfPage) {
        List<Integer> pages = new ArrayList<>();
        try {
            
       pages = IntStream.range(1,numberOfPage+1)
            .boxed()
            .collect(Collectors.toList());
       
             
            return pages;

        } catch (Exception e) {
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.println(
                    "Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            JmoordbUtil.errorMessage(nameOfMethod() + " " + e.getLocalizedMessage());
        }
        return pages;
    }
    // </editor-fold>
}
