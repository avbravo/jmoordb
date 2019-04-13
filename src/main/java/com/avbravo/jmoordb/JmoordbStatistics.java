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
    private Integer dataSize;
    private Integer storageSize;
    private Integer numExtents;
    private Integer indexes;
    private Integer indexSize;
    private Integer fileSize;
    private Integer nsSizeMB;
    private String dataFileVersion;

    public JmoordbStatistics() {
    }

    public JmoordbStatistics(String db, Integer collections, Integer objects, Double avgObjSize, Integer dataSize, Integer storageSize, Integer numExtents, Integer indexes, Integer indexSize, Integer fileSize, Integer nsSizeMB, String dataFileVersion) {
        this.db = db;
        this.collections = collections;
        this.objects = objects;
        this.avgObjSize = avgObjSize;
        this.dataSize = dataSize;
        this.storageSize = storageSize;
        this.numExtents = numExtents;
        this.indexes = indexes;
        this.indexSize = indexSize;
        this.fileSize = fileSize;
        this.nsSizeMB = nsSizeMB;
        this.dataFileVersion = dataFileVersion;
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

    public Integer getDataSize() {
        return dataSize;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }

    public Integer getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Integer storageSize) {
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

    public Integer getIndexSize() {
        return indexSize;
    }

    public void setIndexSize(Integer indexSize) {
        this.indexSize = indexSize;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getNsSizeMB() {
        return nsSizeMB;
    }

    public void setNsSizeMB(Integer nsSizeMB) {
        this.nsSizeMB = nsSizeMB;
    }

    public String getDataFileVersion() {
        return dataFileVersion;
    }

    public void setDataFileVersion(String dataFileVersion) {
        this.dataFileVersion = dataFileVersion;
    }

    @Override
    public String toString() {
        return "JmoordbStatistics{" + "db=" + db + ", collections=" + collections + ", objects=" + objects + ", avgObjSize=" + avgObjSize + ", dataSize=" + dataSize + ", storageSize=" + storageSize + ", numExtents=" + numExtents + ", indexes=" + indexes + ", indexSize=" + indexSize + ", fileSize=" + fileSize + ", nsSizeMB=" + nsSizeMB + ", dataFileVersion=" + dataFileVersion + '}';
    }

}
