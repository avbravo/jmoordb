/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.metafactory;

import com.avbravo.jmoordb.pojos.UserInfo;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private PropertyDescriptor userInfoProperty;

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
        userInfoProperty = property.apply("userInfo");
        //RECORRO LOS METODOS 
        for (MethodDescriptor m : beanInfo.getMethodDescriptors()) {
            if (m.getMethod().getName().indexOf("set") != -1 || m.getMethod().getName().indexOf("get") != -1) {
                System.out.println("metodo " + m.getMethod().getName());
                System.out.println("getAnnotatedReturnType() " + m.getMethod().getAnnotatedReturnType().getType());
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

        final BiConsumer userInfoSetter = JmoordbLambdaMetaFactory.createSetter(lookup,
                lookup.unreflect(userInfoProperty.getWriteMethod()));

        nameSetter.accept(person, "Answer");
        valueSetter.accept(person, 42.0);
        userInfoSetter.accept(person, generateListUserinfo("avbravo","abo"));
        // AQUI AGREGA un List<UserInfo> mediante LambdaMetaFactory
        List<UserInfo> ux=person.getUserInfo();
        ux.add(generateUserinfo("avbravo","actualizando"));
        userInfoSetter.accept(person, ux);
        Integer i=0;
       

        assertEquals("Answer", person.getName());
        assertEquals(42.0, person.getValue(), 0.1);
        System.out.println("=====================================");
        for (UserInfo u : person.getUserInfo()) {
            System.out.println("username----> " + u.getUsername());
            System.out.println("descripcion----> " + u.getDescription());
            System.out.println("datetime----> " + u.getDatetime());
        }

    }

    public List<UserInfo> generateListUserinfo(String username, String description) {
        List<UserInfo> listUserinfo = new ArrayList<>();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            UUID uuid = UUID.randomUUID();

            listUserinfo.add(new UserInfo(uuid.toString(), username, date2, description));
        } catch (Exception e) {
            System.out.println("generateListUserinfo() " + e.getLocalizedMessage());
        }
        return listUserinfo;
    }  // </editor-fold>
    
     public UserInfo generateUserinfo(String username, String description) {
        UserInfo userinfo = new UserInfo();
        try {
            LocalDateTime ahora = LocalDateTime.now();
            Date date2 = Date.from(ahora.atZone(ZoneId.systemDefault()).toInstant());
            UUID uuid = UUID.randomUUID();
            userinfo.setIduserinfo(uuid.toString());
            userinfo.setUsername(username);
            userinfo.setDatetime(date2);
            userinfo.setDescription(description);

        } catch (Exception e) {
            System.out.println("generateUserinfo() " + e.getLocalizedMessage());
        }
        return userinfo;
    }  // </editor-fold>
}
