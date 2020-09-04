/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.query.enumerations;

/**
 *
 * @author avbravo
 */
public enum Arrays {
    all("all"),
    elemMatch("elemMatch"),
    size("size");

    private String value;

    private Arrays(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
