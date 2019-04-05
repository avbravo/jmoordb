/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history.services;

import com.avbravo.jmoordb.mongodb.history.repository.RevisionHistoryRepository;
import com.avbravo.jmoordb.mongodb.history.repository.ConfiguracionRepository;
import com.avbravo.jmoordb.mongodb.history.repository.AutoincrementablebRepository;
import com.avbravo.jmoordb.mongodb.history.entity.Configuracion;
import com.avbravo.jmoordb.services.RevisionHistoryServices;
import com.avbravo.jmoordb.util.JmoordbUtil;

 
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class ConfiguracionServices {

    @Inject
    ConfiguracionRepository repository;
    //Repository
    @Inject
    AutoincrementablebRepository autoincrementablebRepository;
    @Inject
    AutoincrementableServices autoincrementableStoreejbServices;
   
  @Inject
    RevisionHistoryServices revisionHistoryServices;
    @Inject
    RevisionHistoryRepository revisionHistoryRepository;

    List<Configuracion> configuracionList = new ArrayList<>();

    public List<Configuracion> complete(String query) {
        List<Configuracion> suggestions = new ArrayList<>();
        try {
            suggestions = repository.complete(query);
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }

        return suggestions;
    }

    public List<Configuracion> getConfiguracionList() {
        try {
            configuracionList = repository.findAll(new Document("configuracion", 1));
        } catch (Exception e) {
            JmoordbUtil.errorMessage("getConfiguracionList() " + e.getLocalizedMessage());
        }
        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    /**
     * devuelve la configuracion inicial si no existe crea una con los
     * parametros basicos
     *
     * @return
     */
    public Configuracion generarConfiguracionInicial(String username) {
        Configuracion configuracion = new Configuracion();
        try {
            List<Configuracion> list = repository.findBy(new Document("activo", "si"));
            if (list.isEmpty() || list.size()==0) {
                Integer id = autoincrementableStoreejbServices.getContador("configuracion");
                configuracion.setIdconfiguracion(id);
                configuracion.setActivo("si");
                configuracion.setDecimales(2);
                configuracion.setFormatodecimal("#0.00");
                configuracion.setRowsforpage(25);
                configuracion.setFormatofecha("dd/MM/yyyy");               
                configuracion.setFormatofechahora("dd/MM/yyyy HH:mm:ss");
                configuracion.setItbms(0.07);
                configuracion.setMinQueryLengthAutocompleteSmall(1);
                configuracion.setMinQueryLengthAutocompleteMedium(3);
                configuracion.setMinQueryLengthAutocompleteLarge(4);

                configuracion.setUserInfo(repository.generateListUserinfo(username, "create"));
                if (repository.save(configuracion)) {
                    revisionHistoryRepository.save(revisionHistoryServices.getRevisionHistory(configuracion.getIdconfiguracion().toString(), username,
                            "create", "configuracion", repository.toDocument(configuracion).toString()));
                    
                  
                } else {
                    JmoordbUtil.successMessage("save() " + repository.getException().toString());
                }
            }
            else{
                configuracion=list.get(0);
            }
        } catch (Exception e) {
            JmoordbUtil.errorMessage("generarConfiguracionInicial() " + e.getLocalizedMessage());
        }
        return configuracion;
    }
    public Configuracion produce() {
        Configuracion configuracion = new Configuracion();
        try {

                configuracion.setActivo("si");
                configuracion.setDecimales(2);
                configuracion.setFormatodecimal("#0.00");
                configuracion.setRowsforpage(25);
                configuracion.setFormatofecha("dd/MM/yyyy");               
                configuracion.setFormatofechahora("dd/MM/yyyy HH:mm:ss");
                configuracion.setItbms(0.07);
                configuracion.setMinQueryLengthAutocompleteSmall(1);
                configuracion.setMinQueryLengthAutocompleteMedium(3);
                configuracion.setMinQueryLengthAutocompleteLarge(4);

             
        } catch (Exception e) {
            JmoordbUtil.errorMessage("produce() " + e.getLocalizedMessage());
        }
        return configuracion;
    }
}
