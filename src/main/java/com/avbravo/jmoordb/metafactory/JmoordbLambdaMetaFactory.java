/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.metafactory;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * @author avbravo
 * FUENTE
 * https://dzone.com/articles/setters-method-handles-and-java-11
 */
public class JmoordbLambdaMetaFactory {
    public static Function createGetter(final MethodHandles.Lookup lookup,
                                        final MethodHandle getter) throws Exception{
        final CallSite site = LambdaMetafactory.metafactory(lookup, "apply",
                MethodType.methodType(Function.class),
                MethodType.methodType(Object.class, Object.class), //signature of method Function.apply after type erasure
                getter,
                getter.type()); //actual signature of getter
        try {
            return (Function) site.getTarget().invokeExact();
        } catch (final Exception e) {
            throw e;
        } catch (final Throwable e) {
            throw new Error(e);
        }
    }
    public static BiConsumer createSetter(final MethodHandles.Lookup lookup,
                                          final MethodHandle setter) throws Exception {
        final CallSite site = LambdaMetafactory.metafactory(lookup,
                "accept",
                MethodType.methodType(BiConsumer.class),
                MethodType.methodType(void.class, Object.class, Object.class), //signature of method BiConsumer.accept after type erasure
                setter,
                setter.type()); //actual signature of setter
        try {
            return (BiConsumer) site.getTarget().invokeExact();
        } catch (final Exception e) {
            throw e;
        } catch (final Throwable e) {
            throw new Error(e);
        }
    }
  

}
