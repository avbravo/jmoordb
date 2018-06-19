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
@Getter
@Setter
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
}
