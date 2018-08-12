/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Embedded;
import com.avbravo.ejbjmoordb.anotations.Id;
import com.avbravo.ejbjmoordb.pojos.UserInfo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
  
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
    
    
    
    
    
}
