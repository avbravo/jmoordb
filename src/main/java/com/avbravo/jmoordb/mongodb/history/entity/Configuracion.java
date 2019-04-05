/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import com.avbravo.jmoordb.pojos.UserInfo;
import com.avbravo.jmoordb.pojos.UserInfo;
import java.util.List;
  
/**
 *
 * @author avbravo
 */

public class Configuracion {
    @Id
    private Integer idconfiguracion;
    private Double itbms;
    private Integer decimales;
    private String formatodecimal;
    private String formatofecha;
    private String formatofechahora;
    private Integer minQueryLengthAutocompleteSmall;
    private Integer minQueryLengthAutocompleteMedium;
    private Integer minQueryLengthAutocompleteLarge;
    private Integer rowsforpage;
    private String activo;
    
    
    @Embedded
    private List<UserInfo> userInfo;

    public Configuracion() {
    }

    public Configuracion(Integer idconfiguracion, Double itbms, Integer decimales, String formatodecimal, String formatofecha, String formatofechahora, Integer minQueryLengthAutocompleteSmall, Integer minQueryLengthAutocompleteMedium, Integer minQueryLengthAutocompleteLarge, Integer rowsforpage, String activo, List<UserInfo> userInfo) {
        this.idconfiguracion = idconfiguracion;
        this.itbms = itbms;
        this.decimales = decimales;
        this.formatodecimal = formatodecimal;
        this.formatofecha = formatofecha;
        this.formatofechahora = formatofechahora;
        this.minQueryLengthAutocompleteSmall = minQueryLengthAutocompleteSmall;
        this.minQueryLengthAutocompleteMedium = minQueryLengthAutocompleteMedium;
        this.minQueryLengthAutocompleteLarge = minQueryLengthAutocompleteLarge;
        this.rowsforpage = rowsforpage;
        this.activo = activo;
        this.userInfo = userInfo;
    }

    
    
    
    public Integer getIdconfiguracion() {
        return idconfiguracion;
    }

    public void setIdconfiguracion(Integer idconfiguracion) {
        this.idconfiguracion = idconfiguracion;
    }

    public Double getItbms() {
        return itbms;
    }

    public void setItbms(Double itbms) {
        this.itbms = itbms;
    }

    public Integer getDecimales() {
        return decimales;
    }

    public void setDecimales(Integer decimales) {
        this.decimales = decimales;
    }

    public String getFormatodecimal() {
        return formatodecimal;
    }

    public void setFormatodecimal(String formatodecimal) {
        this.formatodecimal = formatodecimal;
    }

    public String getFormatofecha() {
        return formatofecha;
    }

    public void setFormatofecha(String formatofecha) {
        this.formatofecha = formatofecha;
    }

    public String getFormatofechahora() {
        return formatofechahora;
    }

    public void setFormatofechahora(String formatofechahora) {
        this.formatofechahora = formatofechahora;
    }

    public Integer getMinQueryLengthAutocompleteSmall() {
        return minQueryLengthAutocompleteSmall;
    }

    public void setMinQueryLengthAutocompleteSmall(Integer minQueryLengthAutocompleteSmall) {
        this.minQueryLengthAutocompleteSmall = minQueryLengthAutocompleteSmall;
    }

    public Integer getMinQueryLengthAutocompleteMedium() {
        return minQueryLengthAutocompleteMedium;
    }

    public void setMinQueryLengthAutocompleteMedium(Integer minQueryLengthAutocompleteMedium) {
        this.minQueryLengthAutocompleteMedium = minQueryLengthAutocompleteMedium;
    }

    public Integer getMinQueryLengthAutocompleteLarge() {
        return minQueryLengthAutocompleteLarge;
    }

    public void setMinQueryLengthAutocompleteLarge(Integer minQueryLengthAutocompleteLarge) {
        this.minQueryLengthAutocompleteLarge = minQueryLengthAutocompleteLarge;
    }

    public Integer getRowsforpage() {
        return rowsforpage;
    }

    public void setRowsforpage(Integer rowsforpage) {
        this.rowsforpage = rowsforpage;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }
    
    public static class Builder {

     private Integer idconfiguracion;
    private Double itbms;
    private Integer decimales;
    private String formatodecimal;
    private String formatofecha;
    private String formatofechahora;
    private Integer minQueryLengthAutocompleteSmall;
    private Integer minQueryLengthAutocompleteMedium;
    private Integer minQueryLengthAutocompleteLarge;
    private Integer rowsforpage;
    private String activo;        
    @Embedded
    private List<UserInfo> userInfo;

       
        public Builder withIdconfiguracion(Integer idconfiguracion) {
            this.idconfiguracion = idconfiguracion;
            return this;
        }
        public Builder withItbms(Double itbms) {
            this.itbms = itbms;
            return this;
        }
        public Builder withDecimales(Integer decimales) {
            this.decimales = decimales;
            return this;
        }
      
        public Builder withFormatodecimal(String formatodecimal) {
            this.formatodecimal = formatodecimal;
            return this;
        }
        public Builder withFormatofecha(String formatofecha) {
            this.formatofecha = formatofecha;
            return this;
        }
        public Builder withFormatofechahora(String formatofechahora) {
            this.formatofechahora =formatofechahora;
            return this;
        }
       public Builder withMinQueryLengthAutocompleteSmall(Integer minQueryLengthAutocompleteSmall) {
            this.minQueryLengthAutocompleteSmall = minQueryLengthAutocompleteSmall;
            return this;
        }
       public Builder withMinQueryLengthAutocompleteMedium(Integer minQueryLengthAutocompleteMedium) {
            this.minQueryLengthAutocompleteMedium = minQueryLengthAutocompleteMedium;
            return this;
        }
       public Builder withMinQueryLengthAutocompleteLarge(Integer minQueryLengthAutocompleteLarge) {
            this.minQueryLengthAutocompleteLarge = minQueryLengthAutocompleteLarge;
            return this;
        }
       
        public Builder withRowsforpage(Integer rowsforpage) {
            this.rowsforpage = rowsforpage;
            return this;
        }
        public Builder withActivo(String activo) {
            this.activo = activo;
            return this;
        }
        public Builder withUserInfo(List<UserInfo> userInfo) {
            this.userInfo = userInfo;
            return this;
        }
        public Configuracion build() {
            return new Configuracion(idconfiguracion, itbms, decimales, formatodecimal, formatofecha, formatofechahora, minQueryLengthAutocompleteSmall, minQueryLengthAutocompleteMedium, minQueryLengthAutocompleteLarge, rowsforpage, activo, userInfo);
        }

    }
    
    
    
}
