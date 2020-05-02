/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jakarta.jnosql.sort;

import com.avbravo.jakarta.jnosql.ServiceLoaderProvider;
import com.avbravo.jakarta.jnosql.sort.SortType;
import java.util.function.BiFunction;

/**
 *
 * @author avbravo
 */
public interface Sort {
    /**
     * Returns the field name
     *
     * @return the field name
     */
    String getName();

    /**
     * The {@link SortType}
     *
     * @return The {@link SortType}
     */
    SortType getType();

    /**
     * Creates a wew Sort instance to be used in a NoSQL query.
     *
     * @param name - the field name be used in a sort process
     * @param type - the way to be sorted
     * @return a sort instance
     * @throws NullPointerException when there are null parameters
     */
    static Sort of(String name, SortType type) {
        return ServiceLoaderProvider.get(SortProvider.class).apply(name, type);
    }

    /**
     * Creates a new Sort of the type {@link SortType#ASC}
     *
     * @param name the field name be used in a sort process
     * @return a sort instance
     * @throws NullPointerException when name is null
     */
    static Sort asc(String name) {
        return of(name, SortType.ASC);
    }

    /**
     * Creates a new Sort of the type {@link SortType#DESC}
     *
     * @param name the field name be used in a sort process
     * @return a sort instance
     * @throws NullPointerException when name is null
     */
    static Sort desc(String name) {
        return of(name, SortType.DESC);
    }


    /**
     * A provider of {@link Sort} where given a {@link String} and a {@link SortType}
     * it returns a new instance of {@link Sort}
     */
    interface SortProvider extends BiFunction<String, SortType, Sort> {

    }
}
