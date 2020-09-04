/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.query;

/**
 *
 * @author avbravo
 */
public class Sorter {

    private String field;
    private Sort sort;
    private Sorter sorter;

    public Sorter() {
    }

    public Sorter(String field, Sort sort, Sorter sorter) {
        this.field = field;
        this.sort = sort;
        this.sorter = sorter;
    }

    public Sorter getSorter() {
        return sorter;
    }

    public void setSorter(Sorter sorter) {
        this.sorter = sorter;
    }

  

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public static class Builder {

        private String field;
        private Sort sort;
         private Sorter sorter;

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder sort(Sort sort) {
            this.sort = sort;
            return this;
        }
        public Builder sorter(Sorter sorter) {
            this.sorter = sorter;
            return this;
        }

      

        public Sorter build() {
            return new Sorter(field, sort, sorter);
        }

    }

}
