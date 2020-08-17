/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.util;

/**
 *
 * @author avbravo
 */
public class JmoordbDecomposedDate {
/**
 * Se usa para almacenar una fecha descompuesta
 */
    Integer year;
    Integer day;
    Integer month;
    Integer hour;
    Integer minute;
    String nameOfMonth;

    public JmoordbDecomposedDate() {
    }

    public JmoordbDecomposedDate(Integer year, Integer day, Integer month, Integer hour, Integer minute, String nameOfMonth) {
        this.year = year;
        this.day = day;
        this.month = month;
        this.hour = hour;
        this.minute = minute;
        this.nameOfMonth = nameOfMonth;
    }

    
    
    
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public void setNameOfMonth(String nameOfMonth) {
        this.nameOfMonth = nameOfMonth;
    }
    
    
    
    

}
