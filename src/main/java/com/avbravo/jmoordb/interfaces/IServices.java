/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.interfaces;

import java.util.List;

/**
 *
 * @author avbravo
 */
public interface IServices<T>{
   
  // <editor-fold defaultstate="collapsed" desc=" List<Boletas> jsonQuery(@QueryParam("query") String query , @QueryParam("sort") String sort, @QueryParam("pagenumber") Integer pageNumber, @QueryParam("rowforpage") Integer rowForPage )">
  public  List<T> jsonQuery( String query ,  String sort,
     Integer pageNumber,  Integer rowForPage );
    // </editor-fold>   
  
  // <editor-fold defaultstate="collapsed" desc=" List<Boletas> jsonQueryWithoutPagination( @QueryParam("query") String query , @QueryParam("sort") String sort  ){">
   public List<T> jsonQueryWithoutPagination( String query ,  String sort  );
    // </editor-fold>   
    
    
   
    // <editor-fold defaultstate="collapsed" desc="Integer countJsonQuery(String query)">

    /**
     * devuelve el contador de documentos en base a un json query
     * @param query
     * @return 
     */
    public Integer countJsonQuery(String query) ;
    // </editor-fold>
}
