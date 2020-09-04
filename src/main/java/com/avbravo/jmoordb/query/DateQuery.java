/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.query;

import com.avbravo.jmoordb.query.enumerations.Comparison;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class DateQuery {
   private String field;
    Date start;
    Comparison comparision;
    Date end;
    Boolean excludedHours = true;

    public DateQuery() {
    }

    public DateQuery(String field, Date start, Comparison comparison, Date end,Boolean excludedHours) {
        this.field = field;
        this.start = start;
        this.comparision = comparision;
        this.end = end;
        this.excludedHours= excludedHours;
    }

    
    
    
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Comparison getComparision() {
        return comparision;
    }

    public void setComparision(Comparison comparision) {
        this.comparision = comparision;
    }

   
    
    
  

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Boolean getExcludedHours() {
        return excludedHours;
    }

    public void setExcludedHours(Boolean excludedHours) {
        this.excludedHours = excludedHours;
    }

    public static class Builder {
   private String field;
        Date start;
        Comparison comparision;
        Date end;
        Boolean excludedHours = true;

        public Builder field(String field) {
            this.field = field;
            return this;
        }
        public Builder start(Date start) {
            this.start = start;
            return this;
        }

        public Builder comparison(Comparison comparison) {
            this.comparision = comparison;
            return this;
        }

        public Builder end(Date end) {
            this.end = end;
            return this;
        }

        public Builder excludedHours(Boolean excludedHours) {
            this.excludedHours = excludedHours;
            return this;
        }

        public DateQuery build() {
            return new DateQuery(field, start, comparision, end, excludedHours);
        }

    }

}
