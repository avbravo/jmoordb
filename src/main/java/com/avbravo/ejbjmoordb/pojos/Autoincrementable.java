/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.ejbjmoordb.pojos;

import com.avbravo.ejbjmoordb.anotations.Id;
import java.util.Date;

/**
 *
 * @author avbravo
 */

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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }
    
    
    public static class Builder {

         private String documento;
    private Integer contador;

        public Builder withDocumento(String documento) {
            this.documento = documento;
            return this;
        }
        public Builder withContador(Integer contador) {
            this.contador= contador;
            return this;
        }

        public Autoincrementable build() {
            return new Autoincrementable(documento, contador);
        }

    }
}
