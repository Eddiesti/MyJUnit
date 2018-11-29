package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

public class TestExecutor {
    private ListStores listStores = new ListStores();

    public void testByClass(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            addMethod(method);
        }
        for (Method method : listStores.getTestList()) {
            Object object = clazz.getDeclaredConstructor().newInstance();
            for (Method method1 : listStores.getBeforeList()) {
                try {
                    method1.invoke(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (Method method2 : listStores.getAfterList()) {
                try {
                    method2.invoke(object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testByPackage(String testablePackage) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ClassPath classPath = ClassPath.from(classLoader);
        ImmutableSet<ClassPath.ClassInfo> classInfos = classPath.getTopLevelClasses(testablePackage);

        for (ClassPath.ClassInfo classInfo : classInfos) {
            testByClass(classInfo.getClass());
        }
    }

    private void addMethod(Method method) {
        if (method.isAnnotationPresent(Before.class)) {
            listStores.getBeforeList().add(method);
        } else if (method.isAnnotationPresent(Test.class)) {
            listStores.getTestList().add(method);
        } else if (method.isAnnotationPresent(After.class)) {
            listStores.getAfterList().add(method);
        }
    }
}
