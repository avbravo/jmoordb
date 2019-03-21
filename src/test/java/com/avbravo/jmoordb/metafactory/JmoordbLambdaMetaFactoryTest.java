/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.metafactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author avbravo
 */
public class JmoordbLambdaMetaFactoryTest {
    
    private PropertyDescriptor nameProperty;
    private PropertyDescriptor valueProperty;

    public JmoordbLambdaMetaFactoryTest() {
    }

    @Before
    public void init() throws Exception {
        final BeanInfo beanInfo = Introspector.getBeanInfo(JmoordbPersonEntity.class);
        final Function<String, PropertyDescriptor> property = name -> Stream.of(beanInfo.getPropertyDescriptors())
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not found: " + name));
        nameProperty = property.apply("name");
        valueProperty = property.apply("value");
        //RECORRO LOS METODOS 
        for (MethodDescriptor m : beanInfo.getMethodDescriptors()) {
            if (m.getMethod().getName().indexOf("set") != -1 || m.getMethod().getName().indexOf("get") != -1) {
                System.out.println("metodo " + m.getMethod().getName());
                System.out.println("getAnnotatedReturnType() " + m.getMethod().getAnnotatedReturnType().getType());

                ParameterDescriptor[] p = m.getParameterDescriptors();
                if (p != null) {
                    System.out.println("PARAMETERDESCRIPTOR");

                    for (ParameterDescriptor pd : p) {
                        System.out.println(pd);
                    }
                } else {
                    System.out.println("PARAMETERSDESCRIPTOR NULL");
                }

                System.out.println("attributesName " + m.attributeNames().toString());
                System.out.println("---------------------------");
            }

        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetter() throws Exception {
        final JmoordbPersonEntity person = new JmoordbPersonEntity();
        person.setName("avbravo");
        person.setValue(42.0);
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final Function nameGetter = JmoordbLambdaMetaFactory.createGetter(lookup,
                lookup.unreflect(nameProperty.getReadMethod()));

        final Function valueGetter = JmoordbLambdaMetaFactory.createGetter(lookup,
                lookup.unreflect(valueProperty.getReadMethod()));

        assertEquals("avbravo", nameGetter.apply(person));
        assertEquals(42.0, (double) valueGetter.apply(person), 0.1);
    }

    @Test
    public void testSetter() throws Exception {
        final JmoordbPersonEntity person = new JmoordbPersonEntity();

        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final BiConsumer nameSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                lookup.unreflect(nameProperty.getWriteMethod()));

        final BiConsumer valueSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                lookup.unreflect(valueProperty.getWriteMethod()));

        nameSetter.accept(person, "Answer");
        valueSetter.accept(person, 42.0);

        assertEquals("Answer", person.getName());
        assertEquals(42.0, person.getValue(), 0.1);
    }
    
}
