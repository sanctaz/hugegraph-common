/*
 * Copyright 2017 HugeGraph Authors
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.baidu.hugegraph.unit.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.junit.Test;

import com.baidu.hugegraph.perf.PerfUtil.Watched;
import com.baidu.hugegraph.testclass.TestClass.Base;
import com.baidu.hugegraph.testclass.TestClass.Sub;
import com.baidu.hugegraph.testutil.Assert;
import com.baidu.hugegraph.unit.BaseUnitTest;
import com.baidu.hugegraph.util.ReflectionUtil;
import com.google.common.reflect.ClassPath.ClassInfo;

import javassist.NotFoundException;

public class ReflectionUtilTest extends BaseUnitTest {

    @Test
    public void testIsSimpleType() {
        Assert.assertTrue(ReflectionUtil.isSimpleType(byte.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(char.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(short.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(int.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(long.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(float.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(double.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(boolean.class));

        Assert.assertTrue(ReflectionUtil.isSimpleType(Byte.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Character.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Short.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Integer.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Long.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Float.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Double.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(Boolean.class));
        Assert.assertTrue(ReflectionUtil.isSimpleType(String.class));

        Assert.assertFalse(ReflectionUtil.isSimpleType(Object.class));
        Assert.assertFalse(ReflectionUtil.isSimpleType(BaseUnitTest.class));
    }

    @Test
    public void testGetMethodsAnnotatedWith() {
        List<Method> methods;

        methods = ReflectionUtil.getMethodsAnnotatedWith(Sub.class,
                                                         Watched.class,
                                                         false);
        methods.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
        Assert.assertEquals(2, methods.size());
        Assert.assertEquals("func1", methods.get(0).getName());
        Assert.assertEquals("func3", methods.get(1).getName());


        methods = ReflectionUtil.getMethodsAnnotatedWith(Sub.class,
                                                         Watched.class,
                                                         true);
        methods.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
        Assert.assertEquals(3, methods.size());
        Assert.assertEquals("func", methods.get(0).getName());
        Assert.assertEquals("func1", methods.get(1).getName());
        Assert.assertEquals("func3", methods.get(2).getName());
    }

    @Test
    public void testClasses() throws IOException {
        @SuppressWarnings("unchecked")
        List<ClassInfo> classes = IteratorUtils.toList(ReflectionUtil.classes(
                                  "com.baidu.hugegraph.util"));
        Assert.assertEquals(13, classes.size());
        classes.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
        Assert.assertEquals("com.baidu.hugegraph.util.Bytes",
                            classes.get(0).getName());
        Assert.assertEquals("com.baidu.hugegraph.util.CheckSocket",
                            classes.get(1).getName());
        Assert.assertEquals("com.baidu.hugegraph.util.CollectionUtil",
                            classes.get(2).getName());
        Assert.assertEquals("com.baidu.hugegraph.util.VersionUtil",
                            classes.get(12).getName());
    }

    @Test
    public void testSuperClasses() throws NotFoundException {
        List<String> classes = ReflectionUtil.superClasses(Sub.class.getName());
        Assert.assertEquals(2, classes.size());
        classes.sort((c1, c2) -> c1.compareTo(c2));
        Assert.assertEquals(Base.class.getName(), classes.get(0));
        Assert.assertEquals(Object.class.getName(), classes.get(1));
    }
}
