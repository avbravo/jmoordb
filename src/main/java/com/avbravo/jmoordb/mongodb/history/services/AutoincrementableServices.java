/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history.services;


import com.avbravo.jmoordb.mongodb.history.repository.AutoincrementablebRepository;
import com.avbravo.jmoordb.pojos.Autoincrementable;
import com.avbravo.jmoordb.util.JmoordbUtil;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @authoravbravo
 */
@Stateless
public class AutoincrementableServices {

    @Inject
    AutoincrementablebRepository autoincrementableRepository;

    public Integer getContador(String coleccion) {
        Integer id = 0;
        try {
            Optional<Autoincrementable> autoincrementableOptional = autoincrementableRepository.find(new Document("documento", coleccion));
            if (!autoincrementableOptional.isPresent()) {

                Autoincrementable autoincrementable = new Autoincrementable(coleccion, 0);
                if (autoincrementableRepository.save(autoincrementable)) {
                }
            }

            Autoincrementable autoincrementable = new Autoincrementable();
            autoincrementable = autoincrementableRepository.findOneAndUpdate("documento", coleccion, "contador");

            id = autoincrementable.getContador();
        } catch (Exception e) {
            JmoordbUtil.errorMessage("complete() " + e.getLocalizedMessage());
        }
        return id;
    }

}
