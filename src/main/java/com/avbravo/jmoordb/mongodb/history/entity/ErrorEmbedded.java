/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.mongodb.history.entity;

import com.avbravo.jmoordb.anotations.Embedded;
import com.avbravo.jmoordb.anotations.Id;
import java.util.Date;

/**
 *
 * @author avbravo
 */
public class ErrorEmbedded {

    @Id
    private Integer iderrorembedded;
    private String method;
    private Integer line;

    public ErrorEmbedded() {
    }

    public ErrorEmbedded(Integer iderrorembedded, String method, Integer line) {
        this.iderrorembedded = iderrorembedded;
        this.method = method;
        this.line = line;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getIderrorembedded() {
        return iderrorembedded;
    }

    public void setIderrorembedded(Integer iderrorembedded) {
        this.iderrorembedded = iderrorembedded;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

  

   

}
