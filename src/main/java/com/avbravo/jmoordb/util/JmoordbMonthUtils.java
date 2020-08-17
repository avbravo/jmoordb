/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.jmoordb.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @authoravbravo
 */

public class JmoordbMonthUtils implements Serializable {

    private static final long serialVersionUID = 1L;
private List<String> listMeses = new ArrayList<>();

    /**
     * Creates a new instance of Meses
     */
    public JmoordbMonthUtils() {
        listMeses.add("Enero");
        listMeses.add("Febrero");
        listMeses.add("Marzo");
        listMeses.add("Abril");
        listMeses.add("Mayo");
        listMeses.add("Junio");
        listMeses.add("Julio");
        listMeses.add("Agosto");
        listMeses.add("Septiembre");
        listMeses.add("Octubre");
        listMeses.add("Noviembre");
        listMeses.add("Diciembre");
    }

   public List<String> getListMeses() {
        return listMeses;
    }

    public void setListMeses(List<String> listMeses) {
        this.listMeses = listMeses;
    }

    public Integer numeroMes(String mes) {
        Integer i = -1;
        for (String l : listMeses) {
            i++;
            if (l.toLowerCase().equals(mes.toLowerCase())) {
                return i +1;
            } 
        }

        return -1;

    }
    public String getNombreMes(Integer mes){
        return listMeses.get(mes);
        
    }





}
