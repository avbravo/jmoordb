/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

/**
 *
 * Maneja el tiempo de una operacion
 *
 * @author avbravo
 */
public class JmoordbTiempo {

  private  Integer dias = 0;
    private Integer horas = 0;
    private Integer minutos = 0;

    public JmoordbTiempo() {
    }

    
    
      public JmoordbTiempo(Integer dias, Integer horas, Integer minutos) {
          this.dias=dias;
          this.horas=horas;
          this.minutos=minutos;
    }

    
    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

}
