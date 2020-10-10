/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class JmoordbDateUtils implements Serializable {


 // <editor-fold defaultstate="collapsed" desc="diaDeUnaFecha"> 
    public static Integer diaDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return dia;
    }
// </editor-fold>
    
      // <editor-fold defaultstate="collapsed" desc="mesDeUnaFecha"> 
    public static Integer mesDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return mes;
    }
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="anioDeUnaFecha"> 
    public static Integer anioDeUnaFecha(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return anio;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="java.util.Date insertHorasMinutosSegundosToDate(Date date, Integer hora,Integer minutos, Integer segundos) "> 
    /**
     * Inserta horas, minutos y segundos a una fecha
     *
     * @param date
     * @param hora
     * @param minutos
     * @param segundos
     * @return
     */
    public static java.util.Date insertHoursMinutesSecondsToDate(Date date, Integer hora, Integer minutos, Integer segundos) {
        Integer anio = anioDeUnaFecha(date);
        Integer mes = mesDeUnaFecha(date);
        Integer dia = diaDeUnaFecha(date);
        LocalDateTime start = LocalDateTime.of(anio, mes, dia, hora, minutos, segundos);
        Date ldate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        return ldate;
    }// </editor-fold>
}


