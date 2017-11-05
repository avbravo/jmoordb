/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author avbravo
 */
@Getter
@Setter
public class Autoincrementable {

    @Id
    private String documento;
    private Integer contador;

    public Autoincrementable() {
    }

    public Autoincrementable(String documento, Integer contador) {
        this.documento = documento;
        this.contador = contador;
    }

    @Override
    public String toString() {
        return "Autoincrementable{" + "documento=" + documento + ", contador=" + contador + '}';
    }
}
