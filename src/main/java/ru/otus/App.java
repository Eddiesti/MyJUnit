package ru.otus;

import ru.otus.test.TestClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class App {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        ReflectionHolder reflectionHolder = new ReflectionHolder();
        TestClass testClass = new TestClass();
        reflectionHolder.testByClass(testClass.getClass());
    }
}
