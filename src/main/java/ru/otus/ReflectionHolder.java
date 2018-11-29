package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.test.TestClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

public class ReflectionHolder {
    private List<Method> beforeList = new ArrayList<Method>();
    private List<Method> testList = new ArrayList<Method>();
    private List<Method> afterList = new ArrayList<Method>();

    public void testByClass(Class clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object object = clazz.getDeclaredConstructor().newInstance();
        java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            addMethod(method);
        }
        for (Method method : testList) {
            for (Method method1 : beforeList) {
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
            for (Method method2 : afterList) {
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
            beforeList.add(method);
        } else if (method.isAnnotationPresent(Test.class)) {
            testList.add(method);
        } else if (method.isAnnotationPresent(After.class)) {
            afterList.add(method);
        }
    }
}
