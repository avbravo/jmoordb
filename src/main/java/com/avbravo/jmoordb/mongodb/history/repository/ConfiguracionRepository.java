/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.mongodb.history.repository;
import javax.ejb.Stateless;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.mongodb.history.entity.Configuracion;
 

/**
 *
 * @author avbravo
 */
@Stateless
public class ConfiguracionRepository extends Repository<Configuracion> {

   
    public ConfiguracionRepository(){
        super(Configuracion.class);
    }


}
