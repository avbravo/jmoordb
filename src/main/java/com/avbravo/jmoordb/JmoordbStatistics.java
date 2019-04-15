/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb;

/**
 *
 * @author avbravo
 */
public class JmoordbStatistics {

    private String db;
    private Integer collections;
    private Integer objects;
    private Double avgObjSize;
    private Double dataSize;
    private Double storageSize;
    private Integer numExtents;
    private Integer indexes;
    private Double indexSize;
//    private Integer fileSize;
//    private Integer nsSizeMB;
//    private String dataFileVersion;
    //nuevas
 private Double   fsTotalSize ;
private Double fsUsedSize;

    public JmoordbStatistics() {
    }

    public JmoordbStatistics(String db, Integer collections, Integer objects, Double avgObjSize, Double dataSize, Double storageSize, Integer numExtents, Integer indexes, Double indexSize, Double fsTotalSize, Double fsUsedSize) {
        this.db = db;
        this.collections = collections;
        this.objects = objects;
        this.avgObjSize = avgObjSize;
        this.dataSize = dataSize;
        this.storageSize = storageSize;
        this.numExtents = numExtents;
        this.indexes = indexes;
        this.indexSize = indexSize;
        this.fsTotalSize = fsTotalSize;
        this.fsUsedSize = fsUsedSize;
    }

  

    public Double getFsTotalSize() {
        return fsTotalSize;
    }

    public void setFsTotalSize(Double fsTotalSize) {
        this.fsTotalSize = fsTotalSize;
    }

    public Double getFsUsedSize() {
        return fsUsedSize;
    }

    public void setFsUsedSize(Double fsUsedSize) {
        this.fsUsedSize = fsUsedSize;
    }
    
    
    
    
    
    

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public Integer getObjects() {
        return objects;
    }

    public void setObjects(Integer objects) {
        this.objects = objects;
    }

    public Double getAvgObjSize() {
        return avgObjSize;
    }

    public void setAvgObjSize(Double avgObjSize) {
        this.avgObjSize = avgObjSize;
    }

    public Double getDataSize() {
        return dataSize;
    }

    public void setDataSize(Double dataSize) {
        this.dataSize = dataSize;
    }

    public Double getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Double storageSize) {
        this.storageSize = storageSize;
    }

   

   

    public Integer getNumExtents() {
        return numExtents;
    }

    public void setNumExtents(Integer numExtents) {
        this.numExtents = numExtents;
    }

    public Integer getIndexes() {
        return indexes;
    }

    public void setIndexes(Integer indexes) {
        this.indexes = indexes;
    }

    public Double getIndexSize() {
        return indexSize;
    }

    public void setIndexSize(Double indexSize) {
        this.indexSize = indexSize;
    }

    @Override
    public String toString() {
        return "JmoordbStatistics{" + "db=" + db + ", collections=" + collections + ", objects=" + objects + ", avgObjSize=" + avgObjSize + ", dataSize=" + dataSize + ", storageSize=" + storageSize + ", numExtents=" + numExtents + ", indexes=" + indexes + ", indexSize=" + indexSize + ", fsTotalSize=" + fsTotalSize + ", fsUsedSize=" + fsUsedSize + '}';
    }

   

  

}
