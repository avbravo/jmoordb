/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import static com.avbravo.jmoordb.util.JmoordbUtil.setHourToDate;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.glassfish.jersey.uri.UriComponent;

/**
 *
 * @author avbravo
 */
public class JmoordbDocument {
    
        private static String opertativeSystem = System.getProperty("os.name").toLowerCase();
     public static      Exception exception; 
        
    private static final Logger LOG = Logger.getLogger(JmoordbDocument.class.getName());
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    // static Pattern object, since pattern is fixed
    private static Pattern pattern;

    // non-static Matcher object because it's created from the input String
    private static Matcher matcher;

 
  
  // <editor-fold defaultstate="collapsed" desc="Document sortBuilder(HashMap<String,String>hashmap )>
  /**
   * crea un document sort en base a un hashmap
   * @param hashmap
   * @return 
   */
    public static Document sortBuilder(HashMap<String,String>map ){
            Document sort = new Document();
        try {
          
          sort.toJson();
          
            
          if(map == null || map.isEmpty()){
            
          }else{
              
              map.entrySet().forEach(m -> {
                  sort.append(m.getKey().toString(),createOrder(m.getValue().toString())) ;
              });  

          }
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbUtil.nameOfClass() + JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
              Logger.getLogger(JmoordbUtil.nameOfClass() + JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
        }
        return sort;
    }
       // </editor-fold>
    
    
    
    
      // <editor-fold defaultstate="collapsed" desc="Integer createOrder(String sorter)">
    /**
     * devuelve el indice de ordenacion
     * @param sorter
     * @return 
     */
   
 private static Integer createOrder(String sorter){
     Integer ordernumber =1;
     try {
sorter = sorter.trim().toLowerCase();
switch(sorter){
    case "asc":
        ordernumber=1;
        break;
    case "desc":
        ordernumber=-1; 
        break;
        default:
           ordernumber=1;
}
     } catch (Exception e) {
             System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbDocument.class.getName() + JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
              Logger.getLogger(JmoordbDocument.class.getName() + JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
     }
     return ordernumber;
 }
   // </editor-fold>
 
 
  // <editor-fold defaultstate="collapsed" desc="public Document sortBuilder(String sortfield, String order  )">
    /**
     * crea un documento para ordenar
     * @param sortfield
     * @param order: asc/desc
     * @return 
     */
    public static Bson filterEQBuilder(String fieldname, String value,String fieldtype  ){
        
     
          Bson filter  ;
         try{
fieldtype = fieldtype.toLowerCase();
         switch(fieldtype){
             case "integer":
                 filter =Filters.eq(fieldname,Integer.parseInt(value));
                 break;
             case "double":
                 filter =Filters.eq(fieldname,Double.parseDouble(value));
                 break;
             case "string":
                  filter =Filters.eq(fieldname,value);
                  break;
             case "date":
                  filter =Filters.eq(fieldname,JmoordbDateUtil.stringToISODate(value));
             case "boolean":
                 Boolean valueBoolean =false;
                 if(value.equals("true")){
                     valueBoolean=true;
                 }
                  filter =Filters.eq(fieldname,valueBoolean);
                  break;
             default:
                 filter =Filters.eq(fieldname,value);
                 
         }
         
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbUtil.nameOfClass()+ JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
   Logger.getLogger(JmoordbUtil.nameOfClass()+ JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
        }
            return null;
        
    }
       // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Document jsonToDocument(String json)">
    /**
     * Convierte un Json a Document
     * @param json
     * @return 
     */
    public static Document jsonToDocument(String json){
        return Document.parse(json.toString());
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String documentToJson(Document doc)">
    /**
     * convierre un Document a Json
     * @param doc
     * @return 
     */
    public static String documentToJson(Document doc){
        return doc.toJson();
    }
    // </editor-fold>
    
    
     // <editor-fold defaultstate="collapsed" desc="Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {">
    /**
   crea un filtro Bson entre fechas tomando en cuenta la hora
     * @param fieldnamestart
     * @param datestartvalue
     * @param fieldlimitname
     * @param datelimitvalue
     * @param docSort
     * @return 
     */
    public static Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {
        Bson filter = new Document();
        try {
      
              Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
       filter = Filters.and(Filters.gte(fieldnamestart, dateStart), Filters.lte(fieldlimitname, dateEnd));
return filter;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbUtil.nameOfClass()+ JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
        }

        return filter;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {">
    /**
     * crea un filtro Bson entre fechas sin tomar en cuenta la hora
     * @param fieldnamestart
     * @param datestartvalue
     * @param fieldlimitname
     * @param datelimitvalue
     * @return 
     */
    public static Bson createBsonBetweenDateUsingHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {
   Bson filter = new Document();
        try {
      
              Date dateStart = setHourToDate(datestartvalue, 0, 0);
            Date dateEnd = setHourToDate(datelimitvalue, 23, 59);
            filter = Filters.and(Filters.gte(fieldnamestart, datestartvalue), Filters.lte(fieldlimitname,  datelimitvalue));
            

return filter;
        } catch (Exception e) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbUtil.nameOfClass()+ JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
        }

        return filter;
    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="Bson createBsonBetweenDateWithoutHours(String fieldnamestart, Date datestartvalue, String fieldlimitname, Date datelimitvalue) {">
    public static String bsonToJson(Bson filter){
        String json="";
        try {
            BsonDocument asBsonDocument =filter.toBsonDocument(BsonDocument.class, 
    MongoClient.getDefaultCodecRegistry());
json = asBsonDocument.toJson();
        } catch (Exception e) {
              System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbUtil.nameOfClass()+ JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
        }
        return json;
    }
    
 // </editor-fold>
    
    public static String encodeJson(String query){
        try {
            
 return  UriComponent.encode(query, UriComponent.Type.QUERY_PARAM_SPACE_ENCODED);
            } catch (Exception e) {
              System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("Class:" + JmoordbUtil.nameOfClass() + " Metodo:" + JmoordbUtil.nameOfMethod());
            System.out.println("Error " + e.getLocalizedMessage());
            System.out.println("------------------------------------------------------------------------------------------------");
            Logger.getLogger(JmoordbUtil.nameOfClass()+ JmoordbUtil.nameOfMethod()).log(Level.SEVERE, null, e);
            exception = new Exception(JmoordbUtil.nameOfMethod(), e);
        }
    return query;
     
    }
}
