/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.paginator;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
public class Paginator {

    private String nameOfController;
    private Integer page;
    private Integer rowsForPage;
    private Integer numberOfPage;
    private Document query;
    private Document sort;
    private String title;

    public Paginator() {
    }

    public Paginator(String nameOfController, Integer page, Integer rowsForPage, Integer numberOfPage, Document query, Document sort, String title) {
        this.nameOfController = nameOfController;
        this.page = page;
        this.rowsForPage = rowsForPage;
        this.numberOfPage = numberOfPage;
        this.query = query;
        this.sort = sort;
        this.title = title;
    }

   
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   

    public String getNameOfController() {
        return nameOfController;
    }

    public void setNameOfController(String nameOfController) {
        this.nameOfController = nameOfController;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRowsForPage() {
        return rowsForPage;
    }

    public void setRowsForPage(Integer rowsForPage) {
        this.rowsForPage = rowsForPage;
    }

    public Integer getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(Integer numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    
    
    
    public Document getQuery() {
        return query;
    }

    public void setQuery(Document query) {
        this.query = query;
    }

    public Document getSort() {
        return sort;
    }

    public void setSort(Document sort) {
        this.sort = sort;
    }

    public static class Builder {

        private String nameOfController;
        private Integer page;
        private Integer rowsForPage;
        private Integer numberOfPage;
        private Document query;
        private Document sort;
        private String title;

        public Builder nameOfController(String nameOfController) {
            this.nameOfController = nameOfController;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder rowsForPage(Integer rowsForPage) {
            this.rowsForPage = rowsForPage;
            return this;
        }

        public Builder numberOfPage(Integer numberOfPage) {
            this.numberOfPage = numberOfPage;
            return this;
        }

        public Builder query(Document query) {
            this.query = query;
            return this;
        }

        public Builder sort(Document sort) {
            this.sort = sort;
            return this;
        }
        
        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Paginator build() {
            return new Paginator(nameOfController, page, rowsForPage, numberOfPage, query, sort, title);

        }
    }

}
